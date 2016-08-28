package noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import view.ImageEffects;
import view.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class GaussianGenerator {

	// genera un par de números con distribución gausiana
	//Minimo valor:
	public static double[] generateRandomNumber(double std_deviation, double mean_value) {
		double x1,x2;
		 
		do{
			x1 = Math.random();
		}while(!(x1>0.0));
		
		do{
			x2 = Math.random();
		}while(! (x2>0.0));
		
		 
		 
		double y1_std_normal = Math.sqrt(-2.0 * Math.log(x1)) * Math.cos(2.0 * Math.PI * x2);
		double y2_std_normal = Math.sqrt(-2.0 * Math.log(x1)) * Math.sin(2.0 * Math.PI * x2);

		double y1_normal = mean_value + std_deviation * y1_std_normal;
		double y2_normal = mean_value + std_deviation * y2_std_normal;

		double[] ans = { y1_normal, y2_normal };
		return ans;
	}

	public static BufferedImage generateNoise(BufferedImage original, double std_deviation, double mean_value,
			double image_percentage) {
		BufferedImage ans=null;

		int height = original.getHeight();
		int width = original.getWidth();
		int []red_band=ImageEffects.getBand(original, 'r');
		
		
		long total_pixels = width * height;
		long pixels_to_modify = Math.round(image_percentage * total_pixels);
		int x, y;
		for (int i = 0; i < pixels_to_modify; i++) {

			x = ThreadLocalRandom.current().nextInt(0, width);

			y = ThreadLocalRandom.current().nextInt(0, width);
			
			int original_color=red_band[y * width + x];
			int noise=(int)Math.round(generateRandomNumber(std_deviation, mean_value)[0]);
			assert(noise>=0);
			int finalColor=original_color 		+ noise;
			
			red_band[y * width + x]=finalColor;
			
			
		}
		ans=ImageEffects.buildImage(red_band
				,red_band
				, red_band
				, width
				, height
				,original.getType(),
				ImageEffects::linearNormalization);
		return ans;
	}
}
