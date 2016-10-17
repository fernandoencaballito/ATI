package border_detectors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import masks.Direction;
import masks.DirectionalSquareMask;
import masks.GaussianMask;
import masks.SobelMask;

public class Canny {
	
	private final static double ANGLE_0_45 = 22.5;
	private final static double ANGLE_45_90 = 67.5;
	private final static double ANGLE_90_135 = 112.5;
	private final static double ANGLE_135_0 = 157.5;
	private final static double WHITE = 255;
	private final static double BLACK = 0;
	
	
	public enum NeighbourType{
		FOUR_NEIGHBOURS,
		EIGHT_NEIGHBOURS
	}
	
	public static BufferedImage cannyBorderDetector(BufferedImage bufferedImage, int size, double sigma, NeighbourType type){
		
		// COPIO LA IMAGEN
		int height = bufferedImage.getHeight();
		System.out.println(height);
		int width = bufferedImage.getWidth();
		System.out.println(width);
		double[][] image = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				image[i][j] = new Color(bufferedImage.getRGB(j, i)).getRed();
			}
		}
		
		// SUAVIZADO GAUSSIANO
		
		GaussianMask gaussianFilterMask = new GaussianMask(size, 0, sigma);
		image = gaussianFilterMask.filterImage(image);
		
		// CALCULO DE MAGNITUD DE GRADIENTE
		
		List<Direction> directions = new ArrayList<>();
		directions.add(Direction.HORIZONTAL);
		directions.add(Direction.VERTICAL);
		DirectionalSquareMask mask = new DirectionalSquareMask(new SobelMask(), directions);
		
		List<double[][]> filteredImages = mask.getFilteredImages(image);
		double[][] Ix = filteredImages.get(0);
		double[][] Iy = filteredImages.get(1);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double ix = Ix[i][j];
				double iy = Iy[i][j];
				image[i][j] = Math.sqrt(ix*ix+iy*iy);  // guardo en image las magnitudes sintetizadas
			}
		}
		
		// CALCULO DE DIRECCIONES
		
		Direction[][] dirs = getAngles(Ix, Iy);
		
		// SUPRESION DE NO MAXIMOS
		
		image = nonMaximumSupression(image, dirs);
		
		// UMBRALIZACION CON HISTERESIS
		
		image = hysteresisThreshold(image, type);
		
		// PASO A BUFFEREDIMAGE DE NUEVO
		
		BufferedImage result = new BufferedImage(width, height, bufferedImage.getType());
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = (int) image[i][j];
				color = new Color(color, color, color).getRGB();
				result.setRGB(j, i, color);
			}
		}
		return result;
	}

	private static double[][] nonMaximumSupression(double[][] image, Direction[][] directions){
		int height = image.length;
		int width = image[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				switch (directions[i][j]) {
				case VERTICAL:
					if((i>0 && image[i-1][j] > image[i][j]) ||
							(i<height-1 && image[i+1][j] > image[i][j]))
						image[i][j] = 0;
				break;
				case HORIZONTAL:
					if((j>0 && image[i][j-1] > image[i][j]) ||
							(j<width-1 && image[i][j+1] > image[i][j]))
							image[i][j] = 0;
					break;
				case DIAGONAL_TOP_RIGHT:
					if((j>0 && i>0 && image[i-1][j-1] > image[i][j]) ||
							(j<width-1 && i<height-1 && image[i+1][j+1] > image[i][j]))
						image[i][j] = 0;
					break;
				case DIAGONAL_TOP_LEFT:
					if((j>0 && i<height-1 && image[i+1][j-1] > image[i][j]) ||
							(j<width-1 && i>0 && image[i-1][j+1] > image[i][j]))
						image[i][j] = 0;
					break;
				}
			}
		}
		
		return image;
	}
	
	private static double[][] hysteresisThreshold(double[][] image, NeighbourType neighbourType){
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		int height = image.length;
		int width = image[0].length;
		
		double[][] result = new double[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				min = Math.min(min, image[i][j]);
				max = Math.max(max, image[i][j]);
			}
		}
		
		double t1 = min + (max-min)/3;
		double t2 = min + (max-min)*2/3;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double value = image[i][j];
				if(value<t1){
					result[i][j] = BLACK;
				} else if(value>=t2){
					result[i][j] = WHITE;
				} else {
					switch (neighbourType) {
					case FOUR_NEIGHBOURS:
						if((i>0 && image[i-1][j]>t1) ||
								(j>0 && image[i][j-1]>t1) ||
								(j<width-1 && image[i][j+1]>t1) ||
								(i<height-1 && image[i+1][j]>t1))
							result[i][j] = WHITE;
						else
							result[i][j] = BLACK;
						break;
					case EIGHT_NEIGHBOURS:
						if((i>0 && image[i-1][j]>t1) ||
								(j>0 && image[i][j-1]>t1) ||
								(j<width-1 && image[i][j+1]>t1) ||
								(i<height-1 && image[i+1][j]>t1) ||
								(j>0 && i>0 && image[i-1][j-1]>t1) ||
								(j<width-1 && i>0 && image[i-1][j+1]>t1) ||
								(i<height-1 && j>0 && image[i+1][j-1]>t1) ||
								(i<height-1 && j<width-1 && image[i+1][j+1]>t1))
							result[i][j] = WHITE;
						else
							result[i][j] = BLACK;
						break;
					}
				}
			}
		}
		return result;
	}
	
	private static Direction angleDiscretization(double angle){
		angle = angle%180;
		if(angle<ANGLE_0_45)
			return Direction.HORIZONTAL;
		if(angle<ANGLE_45_90)
			return Direction.DIAGONAL_TOP_RIGHT;
		if(angle<ANGLE_90_135)
			return Direction.VERTICAL;
		if(angle<ANGLE_135_0)
			return Direction.DIAGONAL_TOP_LEFT;
		return Direction.HORIZONTAL;
	}
	
	private static Direction[][] getAngles(double[][] Ix, double[][] Iy){
		Direction[][] angles = new Direction[Ix.length][Ix[0].length];
		for (int i = 0; i < angles.length; i++) {
			for (int j = 0; j < angles[i].length; j++) {
				if(Ix[i][j] == 0)
					angles[i][j] = Direction.HORIZONTAL;
				else
					angles[i][j] = angleDiscretization(Math.toDegrees(Math.atan2(Iy[i][j], Ix[i][j]))+180);
			}
		}
		return angles;
	}

}
