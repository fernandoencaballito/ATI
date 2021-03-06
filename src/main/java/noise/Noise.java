package noise;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import view.ImageEffects;

public class Noise {

	 //valores para sal y pimienta
	private static double p0=0.2;
	private static double p1=0.8;
	
	public static BufferedImage generateNoise(BufferedImage original, double image_percentage,
			RandomNumberGenerator generator) {
		
		if(image_percentage>1.0){
			throw new RuntimeException("[Noise] noise percentage must be lower than 1.0");
		}
		
		
		
		BufferedImage ans = null;
		Set<Point> modifiedPixels=new HashSet<Point>();
		int height = original.getHeight();
		int width = original.getWidth();
		int[] red_band = ImageEffects.getBand(original, 'r');

		long total_pixels = width * height;
		long pixels_to_modify = Math.round(image_percentage * total_pixels);
		System.out.println("[Noise] pixels to modify: " + pixels_to_modify);
		int x, y;
		Point currentPixel=null;
		for (int i = 0; i < pixels_to_modify; i++) {
			do{
			x = ThreadLocalRandom.current().nextInt(0, width);

			y = ThreadLocalRandom.current().nextInt(0, height);
			currentPixel=new Point(x, y);
			}while(modifiedPixels.contains(currentPixel));
			modifiedPixels.add(currentPixel);
			
			int original_color = red_band[y * width + x];
			//int noise = (int) Math.round(generator.generate());
			double noise =  generator.generate();
			assert (noise >= 0);
			int finalColor;

			if (generator.isAdditive()) {
				finalColor =(int) Math.round(original_color + noise);
			}else{
				finalColor =(int)Math.round( original_color * noise);
			}
			
			
			red_band[y * width + x] = finalColor;

		}
		int type=original.getType();
		if(type==BufferedImage.TYPE_BYTE_BINARY){
			type=BufferedImage.TYPE_BYTE_GRAY;
		}
		
		ans = ImageEffects.buildImage(red_band, red_band, red_band, width, height, type,
				ImageEffects::linearNormalization);
		return ans;
	}
	
	public static BufferedImage generateColorNoise(BufferedImage original, double image_percentage,
			RandomNumberGenerator generator) {
		
		if(image_percentage>1.0){
			throw new RuntimeException("[Noise] noise percentage must be lower than 1.0");
		}
		
		
		
		BufferedImage ans = null;
		Set<Point> modifiedPixels=new HashSet<Point>();
		int height = original.getHeight();
		int width = original.getWidth();
		int[] red_band = ImageEffects.getBand(original, 'r');
		int[] green_band = ImageEffects.getBand(original, 'g');
		int[] blue_band = ImageEffects.getBand(original, 'b');

		long total_pixels = width * height;
		long pixels_to_modify = Math.round(image_percentage * total_pixels);
		System.out.println("[Noise] pixels to modify: " + pixels_to_modify);
		int x, y;
		Point currentPixel=null;
		for (int i = 0; i < pixels_to_modify; i++) {
			do{
			x = ThreadLocalRandom.current().nextInt(0, width);

			y = ThreadLocalRandom.current().nextInt(0, height);
			currentPixel=new Point(x, y);
			}while(modifiedPixels.contains(currentPixel));
			modifiedPixels.add(currentPixel);
			
			int original_red_color = red_band[y * width + x];
			int original_green_color = green_band[y * width + x];
			int original_blue_color = blue_band[y * width + x];
			//int noise = (int) Math.round(generator.generate());
			double red_noise =  generator.generate();
			double green_noise =  generator.generate();
			double blue_noise =  generator.generate();
			assert (red_noise >= 0);
			assert (green_noise >= 0);
			assert (blue_noise >= 0);
			int red_finalColor;
			int green_finalColor;
			int blue_finalColor;

			if (generator.isAdditive()) {
				red_finalColor =(int) Math.round(original_red_color + red_noise);
				green_finalColor =(int) Math.round(original_green_color + green_noise);
				blue_finalColor =(int) Math.round(original_blue_color + blue_noise);
			}else{
				red_finalColor =(int)Math.round( original_red_color * red_noise);
				green_finalColor =(int)Math.round( original_green_color * green_noise);
				blue_finalColor =(int)Math.round( original_blue_color * blue_noise);
			}
			
			
			red_band[y * width + x] = red_finalColor;
			green_band[y * width + x] = green_finalColor;
			blue_band[y * width + x] = blue_finalColor;

		}
		int type=original.getType();
		if(type==BufferedImage.TYPE_BYTE_BINARY){
			type=BufferedImage.TYPE_BYTE_GRAY;
		}
		
		ans = ImageEffects.buildImage(red_band, green_band, blue_band, width, height, type,
				ImageEffects::linearNormalization);
		return ans;
	}
	
	public static BufferedImage generateSaltPepper(BufferedImage original, double image_percentage
			) {
		
		if(image_percentage>1.0){
			throw new RuntimeException("[Noise] noise percentage must be lower than 1.0");
		}
		
		
		
		BufferedImage ans = null;
		Set<Point> modifiedPixels=new HashSet<Point>();
		int height = original.getHeight();
		int width = original.getWidth();
		int[] red_band = ImageEffects.getBand(original, 'r');

		long total_pixels = width * height;
		long pixels_to_modify = Math.round(image_percentage * total_pixels);
		System.out.println("[Noise peper] pixels to modify: " + pixels_to_modify);
		
		
		
		int x, y;
		Point currentPixel=null;
		for (int i = 0; i < pixels_to_modify; i++) {
			do{
			x = ThreadLocalRandom.current().nextInt(0, width);

			y = ThreadLocalRandom.current().nextInt(0, height);
			currentPixel=new Point(x, y);
			}while(modifiedPixels.contains(currentPixel));
			modifiedPixels.add(currentPixel);
			
			int original_color = red_band[y * width + x];
			
			double probability=Math.random();
			
			
			
			int finalColor=original_color;
			//algoritmo sal y pimienta
			if(probability<=p0){
				finalColor=0;
			}else if(probability>=p1){
				finalColor=255;
			}
			
			//
			red_band[y * width + x] = finalColor;

		}
		int type=original.getType();
		if(type==BufferedImage.TYPE_BYTE_BINARY){
			type=BufferedImage.TYPE_BYTE_GRAY;
		}
		
		ans = ImageEffects.buildImage(red_band, red_band, red_band, width, height, type,
				ImageEffects::identityNormalization);
		return ans;
	}
}
