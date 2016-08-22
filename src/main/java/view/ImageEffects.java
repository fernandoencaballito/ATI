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
	
	public static BufferedImage sum(BufferedImage image1, BufferedImage image2){
		int newWidth = Math.max(image1.getWidth(),image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth*newHeight];
		int[] green = new int[newWidth*newHeight];
		int[] blue = new int[newWidth*newHeight];
		for(int i=0;i<newWidth;i++){
			for(int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color((i<image1.getWidth() && j<image1.getHeight())?image1.getRGB(i, j):0);
				Color pixel2Color = new Color((i<image2.getWidth() && j<image2.getHeight())?image2.getRGB(i, j):0);
				red[j*newWidth+i] = pixel1Color.getRed() + pixel2Color.getRed();
				green[j*newWidth+i] = pixel1Color.getGreen() + pixel2Color.getGreen();
				blue[j*newWidth+i] = pixel1Color.getBlue() + pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType());
	}
	
	public static BufferedImage minus(BufferedImage image1, BufferedImage image2){
		int newWidth = Math.max(image1.getWidth(),image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth*newHeight];
		int[] green = new int[newWidth*newHeight];
		int[] blue = new int[newWidth*newHeight];
		for(int i=0;i<newWidth;i++){
			for(int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color((i<image1.getWidth() && j<image1.getHeight())?image1.getRGB(i, j):0);
				Color pixel2Color = new Color((i<image2.getWidth() && j<image2.getHeight())?image2.getRGB(i, j):0);
				red[j*newWidth+i] = pixel1Color.getRed() - pixel2Color.getRed();
				green[j*newWidth+i] = pixel1Color.getGreen() - pixel2Color.getGreen();
				blue[j*newWidth+i] = pixel1Color.getBlue() - pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType());
	}
	
	public static BufferedImage product(BufferedImage image1, BufferedImage image2){
		int newWidth = Math.max(image1.getWidth(),image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth*newHeight];
		int[] green = new int[newWidth*newHeight];
		int[] blue = new int[newWidth*newHeight];
		for(int i=0;i<newWidth;i++){
			for(int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color((i<image1.getWidth() && j<image1.getHeight())?image1.getRGB(i, j):0);
				Color pixel2Color = new Color((i<image2.getWidth() && j<image2.getHeight())?image2.getRGB(i, j):0);
				red[j*newWidth+i] = pixel1Color.getRed() * pixel2Color.getRed();
				green[j*newWidth+i] = pixel1Color.getGreen() * pixel2Color.getGreen();
				blue[j*newWidth+i] = pixel1Color.getBlue() * pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType());
	}
	
	public static BufferedImage scalarProduct(BufferedImage image, int scalar){
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		int[] red = new int[newWidth*newHeight];
		int[] green = new int[newWidth*newHeight];
		int[] blue = new int[newWidth*newHeight];
		for(int i=0;i<newWidth;i++){
			for(int j = 0; j < newHeight; j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				red[j*newWidth+i] = pixelColor.getRed() * scalar;
				green[j*newWidth+i] = pixelColor.getGreen() * scalar;
				blue[j*newWidth+i] = pixelColor.getBlue() * scalar;
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image.getType());
	}
	
	private static BufferedImage buildImage(int[] red, int[] green, int[] blue, int width, int height, int type){
		BufferedImage newImage = new BufferedImage(width, height, type);
		red = linearNormalization(red);
		green = linearNormalization(green);
		blue = linearNormalization(blue);
		for(int i=0;i<width;i++){
			for(int j = 0; j < height; j++) {
				newImage.setRGB(i, j, new Color(red[j*width+i], green[j*width+i], blue[j*width+i]).getRGB());
			}
		}
		return newImage;
	}
	
	public static int[] linearNormalization(int[] matrix){
		int[] result = new int[matrix.length];
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i = 0; i<matrix.length ; i++){
			min = Math.min(min, matrix[i]);
			max = Math.max(max, matrix[i]);
		}
		int delta = max-min;
		while(min<0){
			max+=delta;
			min+=delta;
		}
		double scale = 255.0/(delta);
		int desp = min;
		if(delta <=255){
			scale = 1;
			desp = max-255;
		}
		if(max <= 255)
			desp = 0;
		
		for(int i=0;i<matrix.length;i++){
			result[i] = (int) Math.round(scale*(matrix[i] - desp));
		}
		return result;
	}
	
	public static Color meanPixelValue(BufferedImage image){
		double[] accum = new double[3];
		int[] matrix = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int width = image.getWidth();
		int height = image.getHeight();
		for(int i=0;i<width;i++){
			for(int j = 0; j < height; j++) {
				Color pixelColor = new Color(matrix[j*width+i]);
				int red = pixelColor.getRed();
				int green = pixelColor.getGreen();
				int blue = pixelColor.getBlue();
				accum[0]+=red;
				accum[1]+=green;
				accum[2]+=blue;
			}
		}
		accum[0]/=(height*width);
		accum[1]/=(height*width);
		accum[2]/=(height*width);
		int red = (int) Math.round(accum[0]);
		int green = (int) Math.round(accum[1]);
		int blue = (int) Math.round(accum[2]);
		return new Color(red, green, blue);
	}
}
