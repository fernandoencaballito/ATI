package keypoints;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import masks.Direction;
import masks.DirectionalSquareMask;
import masks.GaussianMask;
import masks.SquareMask;
import view.panels.ImagePanel;
import view.panels.KeypointsThresholdFrame;

public class Keypoints {

	public static void harris(ImagePanel imagePanel, SquareMask referenceMask, int size, double sigma, BufferedImage image){		//pedimos despues el porcentaje para umbralizar
		int width = image.getWidth();
		int height = image.getHeight();
		double[][] redMatrix = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color(image.getRGB(i, j));
				redMatrix[i][j] = color.getRed();
			}
		}
		GaussianMask gaussianFilterMask = new GaussianMask(size, 0, sigma);
		List<Direction> directions = new ArrayList<>();
		directions.add(Direction.HORIZONTAL);
		directions.add(Direction.VERTICAL);
		DirectionalSquareMask mask = new DirectionalSquareMask(referenceMask, directions);
		List<double[][]> redImages = mask.getFilteredImages(redMatrix);
		double[][] redImageX = redImages.get(0);
		double[][] redImageY = redImages.get(1);
		
		double[][] redImageX2 = gaussianFilterMask.filterImage(square(redImageX));
		double[][] redImageY2 = gaussianFilterMask.filterImage(square(redImageY));
		double[][] redImageXY = gaussianFilterMask.filterImage(mult(redImageX, redImageY));
		
		double[][] redCim = cim(redImageX2, redImageY2, redImageXY);
		
		KeypointsThresholdFrame frame = new KeypointsThresholdFrame(imagePanel, redCim);//, greenCim, blueCim);
		frame.setVisible(true);
		
	}
	
	private static double[][] mult(double[][] matrix1, double[][] matrix2){
		int width = matrix1.length;
		int height = matrix1[0].length;
		double[][] result = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result[i][j] = matrix1[i][j] * matrix2[i][j];
			}
		}
		return result;
	}
	
	private static double[][] square(double[][] matrix){
		return mult(matrix,matrix);
	}
	
	private static double[][] cim(double[][] Ix2, double[][] Iy2,double[][] Ixy){
		int width = Ix2.length;
		int height = Ix2[0].length;
		double[][] result = new double[width][height];
		double k = 0.04;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double ix2 = Ix2[i][j];
				double iy2 = Iy2[i][j];
				double ixy = Ixy[i][j];
				result[i][j] = (ix2 * iy2 - ixy * ixy) - k * (ix2 + iy2) * (ix2 + iy2);
			}
		}
		return result;
	}

	public static BufferedImage threshold(int percentage, double[][] redCim, BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result.setRGB(i, j, image.getRGB(i, j));
				max = Math.max(max, redCim[i][j]);
				min = Math.min(min, redCim[i][j]);
			}
		}
		System.out.println("MIN: "+ min);
		System.out.println("MAX: "+ max);
		double scale = (double)percentage / 100;
		scale = 1-scale;
		double limitValue = min + (max-min)*scale;
		System.out.println("limitValue: "+ limitValue);
		Color circleColor = Color.RED;
		Graphics g = result.createGraphics();
		g.setColor(circleColor);
		int circleSize = 3;
		for (int i = 1; i < width-1; i++) {
			for (int j = 1; j < height-1; j++) {
				if(redCim[i][j] >= limitValue){
					g.fillOval(i-(circleSize-1)/2, j-(circleSize-1)/2, circleSize, circleSize);
				}
			}
		}
		return result;
	}

	
}
