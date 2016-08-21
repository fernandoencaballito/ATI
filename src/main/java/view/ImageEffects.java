package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageEffects {

	public static BufferedImage invertColors(BufferedImage image){
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for(int i=0;i<image.getWidth();i++){
			for(int j = 0; j < image.getHeight(); j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				newImage.setRGB(i, j, new Color(255 - pixelColor.getRed(), 255 - pixelColor.getGreen(), 255 - pixelColor.getBlue()).getRGB());;
			}
		}
		return newImage;
	}
}
