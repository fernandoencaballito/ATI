package color_threshold;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import view.ImageEffects;
import view.RGB;

public class ColorThresholding {

	public static BufferedImage colorThreshold(BufferedImage image){
		Map<Integer, List<Pixel>> clases = new HashMap<>();
		Map<Integer, Vector3D> meanPixels = new HashMap<>();
		Map<Integer, Double> withinClassVariance = new HashMap<>();
		Integer[] positions = {0, 1, 2, 3, 4, 5, 6, 7};
		double redThreshold = ImageEffects.getOtsuThreshold(image, RGB.RED);
		double greenThreshold = ImageEffects.getOtsuThreshold(image, RGB.GREEN);
		double blueThreshold = ImageEffects.getOtsuThreshold(image, RGB.BLUE);
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color pixel = new Color(image.getRGB(j, i));
				boolean red = pixel.getRed() > redThreshold;
				boolean green = pixel.getGreen() > greenThreshold;
				boolean blue = pixel.getBlue() > blueThreshold;
				
				int clazz = 0;
				clazz += red ? 4 : 0;		//en "binario"
				clazz += green ? 2 : 0;
				clazz += blue ? 1: 0;
				
				List<Pixel> aux = clases.getOrDefault(clazz, new ArrayList<>());
				aux.add(new Pixel(j, i, pixel));
				clases.put(clazz, aux);
			}
		}
		
		boolean clasesChanged = true;
		
		while (!clasesChanged) {
			
			clasesChanged = true;
			
			for (Map.Entry<Integer, List<Pixel>> entry : clases.entrySet()) {
				
				meanPixels = new HashMap<>();
				withinClassVariance = new HashMap<>();
				
				List<Pixel> list = entry.getValue();
				double red = list.stream().mapToDouble(pixel -> pixel.red).average().getAsDouble();
				double green = list.stream().mapToDouble(pixel -> pixel.green).average().getAsDouble();
				double blue = list.stream().mapToDouble(pixel -> pixel.blue).average().getAsDouble();
				meanPixels.put(entry.getKey(), new Vector3D(red, green, blue));

				double variance = list.stream().mapToDouble(
						p -> Math.pow(p.red - red, 2) + Math.pow(p.green - green, 2) + Math.pow(p.blue - blue, 2))
						.sum();
				variance = Math.sqrt(variance);
				variance /= list.size();

				withinClassVariance.put(entry.getKey(), variance);
			}

			for (int k = 0; k < 8; k++) {
				if (!withinClassVariance.containsKey(k))
					continue;
				Vector3D meanK = meanPixels.get(k);
				double varianceK = withinClassVariance.get(k);

				for (int j = k + 1; j < 8; j++) {
					if (!withinClassVariance.containsKey(j))
						continue;

					Vector3D meanJ = meanPixels.get(j);

					double betweenClassVarianceKJ = meanK.distance(meanJ);

					double varianceJ = withinClassVariance.get(j);

					if (varianceK >= betweenClassVarianceKJ || varianceJ >= betweenClassVarianceKJ) {
						positions[j] = k;
						List<Pixel> pixels = clases.get(k);
						pixels.addAll(clases.get(j));
						clases.remove(j);
						clases.put(k, pixels);
						clasesChanged = false;
					}
				}
			}
		}
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(Map.Entry<Integer, List<Pixel>> entry : clases.entrySet()) {
			Vector3D meanPixel = meanPixels.get(entry.getKey());
			Color color = new Color(Math.round(meanPixel.getX()), Math.round(meanPixel.getY()), Math.round(meanPixel.getZ()));
			int colorValue = color.getRGB();
			for(Pixel pixel : entry.getValue()){
				result.setRGB(pixel.x, pixel.y, colorValue);
			}
		}
		return result;
	}
}
