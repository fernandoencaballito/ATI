package color_threshold;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		
		JOptionPane.showMessageDialog(new JFrame("Otsu Threshold"), String.format("Otsu Thresholds: r = %d , g = %d, b = %d", (int)Math.round(redThreshold), (int)Math.round(greenThreshold), (int)Math.round(blueThreshold)));
		
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
		
		while (clasesChanged) {
			clasesChanged = false;
			meanPixels = new HashMap<>();
			withinClassVariance = new HashMap<>();
			
			for (Map.Entry<Integer, List<Pixel>> entry : clases.entrySet()) {
				
				List<Pixel> list = entry.getValue();
				double red = list.stream().mapToDouble(pixel -> pixel.red).average().getAsDouble();
				double green = list.stream().mapToDouble(pixel -> pixel.green).average().getAsDouble();
				double blue = list.stream().mapToDouble(pixel -> pixel.blue).average().getAsDouble();
				meanPixels.put(entry.getKey(), new Vector3D(red, green, blue));

				double variance = list.stream().mapToDouble(
						p -> Math.pow(p.red - red, 2) + Math.pow(p.green - green, 2) + Math.pow(p.blue - blue, 2))
						.sum();
				variance /= list.size();
				variance = Math.sqrt(variance);
				withinClassVariance.put(entry.getKey(), variance);
			}
			printClases(clases, meanPixels, withinClassVariance);
			
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
					System.out.println(String.format("Varianza entre clases %d y %d: %.2f", k,j,betweenClassVarianceKJ));
					double varianceJ = withinClassVariance.get(j);
					
					if (varianceK >= betweenClassVarianceKJ || varianceJ >= betweenClassVarianceKJ) {
						positions[j] = k;
						List<Pixel> pixels = clases.get(k);
						pixels.addAll(clases.get(j));
						clases.remove(j);
						clases.put(k, pixels);
						clasesChanged = true;
					}
				}
			}
		}
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(Map.Entry<Integer, List<Pixel>> entry : clases.entrySet()) {
			Vector3D meanPixel = meanPixels.get(entry.getKey());
			Color color = new Color((int)Math.round(meanPixel.getX()), (int)Math.round(meanPixel.getY()), (int)Math.round(meanPixel.getZ()));
			int colorValue = color.getRGB();
			for(Pixel pixel : entry.getValue()){
				result.setRGB(pixel.x, pixel.y, colorValue);
			}
		}
		return result;
	}
	
	
	private static void printClases(Map<Integer, List<Pixel>> clases, Map<Integer, Vector3D> meanPixels, Map<Integer, Double> withinClassVariances){
		for(int i = 0; i<8; i++){
			if(!clases.containsKey(i))
				continue;		
			Vector3D meanPixel = meanPixels.get(i);
			System.out.println(String.format("Clase %d: color promedio: {%.2f, %.2f, %.2f}, varianza: %.2f", i, meanPixel.getX(), meanPixel.getY(), meanPixel.getZ(), withinClassVariances.get(i)));
		}
	}
}
