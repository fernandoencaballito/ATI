package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageEffects {
	
	private static final int BLACK = 0;
	private static final int WHITE = 255;

	public static BufferedImage invertColors(BufferedImage image){
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int i=0;i<image.getWidth();i++){
			for(int j = 0; j < image.getHeight(); j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				newImage.setRGB(i, j, new Color(WHITE - pixelColor.getRed(), WHITE - pixelColor.getGreen(), WHITE - pixelColor.getBlue()).getRGB());
			}
		}
		return newImage;
	}
	
	public static BufferedImage threshold(BufferedImage image, int limit){
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int i=0;i<image.getWidth();i++){
			for(int j = 0; j < image.getHeight(); j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				int red = (pixelColor.getRed()<limit) ? BLACK : WHITE;
				int green = (pixelColor.getGreen()<limit) ? BLACK : WHITE;
				int blue = (pixelColor.getBlue()<limit) ? BLACK : WHITE;
				newImage.setRGB(i, j, new Color(red, green, blue).getRGB());
			}
		}
		return newImage;
	}
}
