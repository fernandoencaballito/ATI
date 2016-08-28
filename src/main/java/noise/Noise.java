package noise;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import view.ImageEffects;

/*
* @author Fernando Bejarano
*/
public class Noise {

	public static BufferedImage generateNoise(BufferedImage original, double image_percentage,
			RandomNumberGenerator generator) {
		BufferedImage ans = null;

		int height = original.getHeight();
		int width = original.getWidth();
		int[] red_band = ImageEffects.getBand(original, 'r');

		long total_pixels = width * height;
		long pixels_to_modify = Math.round(image_percentage * total_pixels);
		System.out.println("[Noise] pixels to modify: " + pixels_to_modify);
		int x, y;
		for (int i = 0; i < pixels_to_modify; i++) {

			x = ThreadLocalRandom.current().nextInt(0, width);

			y = ThreadLocalRandom.current().nextInt(0, height);

			int original_color = red_band[y * width + x];
			int noise = (int) Math.round(generator.generate());
			assert (noise >= 0);
			int finalColor;

			if (generator.isAdditive()) {
				finalColor = original_color + noise;
			}else{
				finalColor = original_color * noise;
			}
			
			
			red_band[y * width + x] = finalColor;

		}
		ans = ImageEffects.buildImage(red_band, red_band, red_band, width, height, original.getType(),
				ImageEffects::linearNormalization);
		return ans;
	}

}
