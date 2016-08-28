package noise;

import java.awt.image.BufferedImage;

import view.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class GaussianGenerator {

	//genera un par de números con distribución gausiana
	public static double[] generateRandomNumber(double std_deviation, double mean_value){
		
		double x1=Math.random();
		double x2=Math.random();
		double y1_std_normal=Math.sqrt(-2.0*Math.log(x1))  * Math.cos(2.0*Math.PI*x2);
		double y2_std_normal=Math.sqrt(-2.0*Math.log(x1))  * Math.sin(2.0*Math.PI*x2);
		
		
		double y1_normal=mean_value+std_deviation*y1_std_normal;
		double y2_normal=mean_value+std_deviation*y2_std_normal;
		
		double []ans={y1_normal,y2_normal};
		return ans;
	}
	
	
	public static BufferedImage generateNoise(BufferedImage original
							,double std_deviation
							, double mean_value,double image_percentage ){
		BufferedImage ans=ImagePanel.deepCopy(original);
		long total_pixels=ans.getWidth()*ans.getHeight();
		
		
		
		return ans;
	}
}
