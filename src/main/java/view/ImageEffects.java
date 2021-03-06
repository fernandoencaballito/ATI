package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import detectors.Detector;
import masks.FilterMask;

public class ImageEffects {

	private static final int BLACK = 0;
	private static final int WHITE = 255;

	public static BufferedImage invertColors(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				newImage.setRGB(i, j, new Color(WHITE - pixelColor.getRed(), WHITE - pixelColor.getGreen(),
						WHITE - pixelColor.getBlue()).getRGB());
			}
		}
		return newImage;
	}

	public static BufferedImage threshold(BufferedImage image, int limit) {
		return threshold(image, limit, limit, limit);
	}

	public static BufferedImage threshold(BufferedImage image, int redLimit, int greenLimit, int blueLimit) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				int red = (pixelColor.getRed() < redLimit) ? BLACK : WHITE;
				int green = (pixelColor.getGreen() < greenLimit) ? BLACK : WHITE;
				int blue = (pixelColor.getBlue() < blueLimit) ? BLACK : WHITE;
				newImage.setRGB(i, j, new Color(red, green, blue).getRGB());
			}
		}
		return newImage;
	}

	public static BufferedImage sum(BufferedImage image1, BufferedImage image2) {
		int newWidth = Math.max(image1.getWidth(), image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth * newHeight];
		int[] green = new int[newWidth * newHeight];
		int[] blue = new int[newWidth * newHeight];
		for (int i = 0; i < newWidth; i++) {
			for (int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color(
						(i < image1.getWidth() && j < image1.getHeight()) ? image1.getRGB(i, j) : 0);
				Color pixel2Color = new Color(
						(i < image2.getWidth() && j < image2.getHeight()) ? image2.getRGB(i, j) : 0);
				red[j * newWidth + i] = pixel1Color.getRed() + pixel2Color.getRed();
				green[j * newWidth + i] = pixel1Color.getGreen() + pixel2Color.getGreen();
				blue[j * newWidth + i] = pixel1Color.getBlue() + pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType(), ImageEffects::linearNormalization);
	}

	public static BufferedImage minus(BufferedImage image1, BufferedImage image2) {
		int newWidth = Math.max(image1.getWidth(), image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth * newHeight];
		int[] green = new int[newWidth * newHeight];
		int[] blue = new int[newWidth * newHeight];
		for (int i = 0; i < newWidth; i++) {
			for (int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color(
						(i < image1.getWidth() && j < image1.getHeight()) ? image1.getRGB(i, j) : 0);
				Color pixel2Color = new Color(
						(i < image2.getWidth() && j < image2.getHeight()) ? image2.getRGB(i, j) : 0);
				red[j * newWidth + i] = pixel1Color.getRed() - pixel2Color.getRed();
				green[j * newWidth + i] = pixel1Color.getGreen() - pixel2Color.getGreen();
				blue[j * newWidth + i] = pixel1Color.getBlue() - pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType(), ImageEffects::linearNormalization);
	}

	public static BufferedImage product(BufferedImage image1, BufferedImage image2) {
		int newWidth = Math.max(image1.getWidth(), image2.getWidth());
		int newHeight = Math.max(image1.getHeight(), image2.getHeight());
		int[] red = new int[newWidth * newHeight];
		int[] green = new int[newWidth * newHeight];
		int[] blue = new int[newWidth * newHeight];
		for (int i = 0; i < newWidth; i++) {
			for (int j = 0; j < newHeight; j++) {
				Color pixel1Color = new Color(
						(i < image1.getWidth() && j < image1.getHeight()) ? image1.getRGB(i, j) : 0);
				Color pixel2Color = new Color(
						(i < image2.getWidth() && j < image2.getHeight()) ? image2.getRGB(i, j) : 0);
				red[j * newWidth + i] = pixel1Color.getRed() * pixel2Color.getRed();
				green[j * newWidth + i] = pixel1Color.getGreen() * pixel2Color.getGreen();
				blue[j * newWidth + i] = pixel1Color.getBlue() * pixel2Color.getBlue();
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image1.getType(), ImageEffects::linearNormalization);
	}

	public static BufferedImage scalarProduct(BufferedImage image, int scalar) {
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		int[] red = new int[newWidth * newHeight];
		int[] green = new int[newWidth * newHeight];
		int[] blue = new int[newWidth * newHeight];
		for (int i = 0; i < newWidth; i++) {
			for (int j = 0; j < newHeight; j++) {
				Color pixelColor = new Color(image.getRGB(i, j));
				red[j * newWidth + i] = pixelColor.getRed() * scalar;
				green[j * newWidth + i] = pixelColor.getGreen() * scalar;
				blue[j * newWidth + i] = pixelColor.getBlue() * scalar;
			}
		}
		return buildImage(red, green, blue, newWidth, newHeight, image.getType(), ImageEffects::dynamicRange);
	}

	public static BufferedImage buildImage(int[] red, int[] green, int[] blue, int width, int height, int type,
			java.util.function.UnaryOperator<int[]> normalizationfunc) {
		BufferedImage newImage = new BufferedImage(width, height, type);
		red = normalizationfunc.apply(red);// linearNormalization(red);
		green = normalizationfunc.apply(green);// linearNormalization(green);
		blue = normalizationfunc.apply(blue);// linearNormalization(blue);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				newImage.setRGB(i, j,
						new Color(red[j * width + i], green[j * width + i], blue[j * width + i]).getRGB());
			}
		}
		return newImage;
	}

	public static int[] linearNormalization(int[] matrix) {
		int[] result = new int[matrix.length];
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			min = Math.min(min, matrix[i]);
			max = Math.max(max, matrix[i]);
		}
		int delta = max - min;
		int desp = min;
		double scale = 255.0 / (delta);

		if (delta <= 255) {
			scale = 1;
		}

		if (min >= 0 && max <= 255) {
			desp = 0;
		}

		for (int i = 0; i < matrix.length; i++) {
			result[i] = (int) Math.round(scale * (matrix[i] - desp));
		}
		return result;
	}

	public static double[][] linearNormalization(double[][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				min = Math.min(min, matrix[i][j]);
				max = Math.max(max, matrix[i][j]);
			}
		}
		double delta = max - min;
		double desp = min;
		double scale = 255.0 / (delta);

		if (delta <= 255) {
			scale = 1;
		}

		if (min >= 0 && max <= 255) {
			desp = 0;
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				result[i][j] = scale * (matrix[i][j] - desp);
			}
		}
		return result;
	}

	private static int[] dynamicRange(int[] matrix) {
		int[] result = new int[matrix.length];
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			min = Math.min(min, matrix[i]);
			max = Math.max(max, matrix[i]);
		}
		double c = 255.0 / Math.log(1 + max);
		for (int i = 0; i < matrix.length; i++) {
			result[i] = (int) Math.round(c * Math.log(1 + matrix[i]));
		}
		return result;

	}

	private static int[] gammaFunction(int[] matrix, double gamma) {
		int[] result = new int[matrix.length];
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			max = Math.max(max, matrix[i]);
		}
		double c = 255.0 / Math.pow(max, gamma);
		for (int i = 0; i < matrix.length; i++) {
			result[i] = (int) Math.round(c * Math.pow(matrix[i], gamma));
		}
		return result;
	}

	public static BufferedImage gammaFunction(BufferedImage image, double gamma) {
		int[] matrix = getBand(image, 'r');
		int[] result = gammaFunction(matrix, gamma);
		return buildImage(result, result, result, image.getWidth(), image.getHeight(), image.getType(),
				ImageEffects::linearNormalization);
	}

	public static BufferedImage dynamicRangeCompression(BufferedImage image) {
		int[] matrix = getBand(image, 'r');
		return buildImage(matrix, matrix, matrix, image.getWidth(), image.getHeight(), image.getType(),
				ImageEffects::dynamicRange);
	}

	public static Color meanPixelValue(BufferedImage image) {
		double[] accum = new double[3];
		int[] matrix = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int width = image.getWidth();
		int height = image.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color pixelColor = new Color(matrix[j * width + i]);
				int red = pixelColor.getRed();
				int green = pixelColor.getGreen();
				int blue = pixelColor.getBlue();
				accum[0] += red;
				accum[1] += green;
				accum[2] += blue;
			}
		}
		accum[0] /= (height * width);
		accum[1] /= (height * width);
		accum[2] /= (height * width);
		int red = (int) Math.round(accum[0]);
		int green = (int) Math.round(accum[1]);
		int blue = (int) Math.round(accum[2]);
		return new Color(red, green, blue);
	}

	private static Map<Integer, Double> histogram(int[] matrix) {
		Map<Integer, Double> histogram = new TreeMap<Integer, Double>();
		for (int i = 0; i < 256; i++)
			histogram.put(i, 0.0);
		for (int i = 0; i < matrix.length; i++) {
			int value = matrix[i];
			double amount = 0;
			if (histogram.containsKey(value)) {
				amount = histogram.get(value);
				amount += 1;
			} else {
				amount = 1.0;
			}
			histogram.put(value, amount);
		}
		for (int i = 0; i < 256; i++)
			histogram.put(i, histogram.get(i) / matrix.length);
		return histogram;
	}

	public static Map<Integer, Double> getHistogram(BufferedImage image) {
		return getHistogram(image, RGB.RED);
	}
	
	public static Map<Integer, Double> getHistogram(BufferedImage image, RGB band) {
		int[] matrix = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int[] colorMatrix = new int[matrix.length];
		switch (band) {
		case RED:
			for (int i = 0; i < colorMatrix.length; i++) {
				colorMatrix[i] = new Color(matrix[i]).getRed();
			}
			break;
		case GREEN:
			for (int i = 0; i < colorMatrix.length; i++) {
				colorMatrix[i] = new Color(matrix[i]).getGreen();
			}
			break;
		case BLUE:
			for (int i = 0; i < colorMatrix.length; i++) {
				colorMatrix[i] = new Color(matrix[i]).getBlue();
			}
			break;
		}
		return histogram(colorMatrix);
	}

	public static Map<Integer, Double> acumHistogram(Map<Integer, Double> histogram) {

		double currentAcum = 0;
		Map<Integer, Double> acumMap = new HashMap<Integer, Double>();

		for (int i = 0; i < 256; i++) {
			currentAcum += histogram.get(i);
			acumMap.put(i, currentAcum);

		}
		return acumMap;

	}

	public static BufferedImage ecualizeHistogram(BufferedImage image) {
		Map<Integer, Double> hist = getHistogram(image);
		Map<Integer, Double> acumHistogram = acumHistogram(hist);
		int[] normalizedValues = new int[256];
		double s_min = acumHistogram.get(0);

		for (int i = 0; i < 256; i++) {
			double notNormalized = acumHistogram.get(i);

			normalizedValues[i] = (int) Math.round(255 * (notNormalized - s_min) / (1 - s_min));

		}

		System.out.println("Equalizer, mapped values:" + Arrays.toString(normalizedValues));
		BufferedImage modified = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());// ImagePanel.deepCopy(image);

		for (int x = 0; x < image.getWidth(); x++) {

			for (int y = 0; y < image.getHeight(); y++) {
				Color pixel_color = new Color(image.getRGB(x, y));
				int final_grey_value = normalizedValues[pixel_color.getRed()];
				Color normalized_color = new Color(final_grey_value, final_grey_value, final_grey_value);
				modified.setRGB(x, y, normalized_color.getRGB());
			}

		}

		return modified;

	}

	public static BufferedImage getGreyImage(BufferedImage image) {
		int[] matrix = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		for (int i = 0; i < matrix.length; i++) {
			Color pixel = new Color(matrix[i]);
			matrix[i] = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
		}
		return buildImage(matrix, matrix, matrix, image.getWidth(), image.getHeight(), image.getType(),
				ImageEffects::linearNormalization);
	}

	public static int[] getBand(BufferedImage img, char channel) {
		int width = img.getWidth();
		int height = img.getHeight();
		int[] band_arr = new int[width * height];
		int current = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				Color color = new Color(img.getRGB(x, y));

				switch (channel) {
				case 'r': {
					current = color.getRed();
					break;

				}
				case 'g': {
					current = color.getGreen();
					break;
				}
				case 'b': {
					current = color.getBlue();
					break;
				}
				}

				band_arr[y * width + x] = current;
			}
		}

		return band_arr;
	}

	public static int[] moduloNormalization(int[] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			int value = matrix[i];
			matrix[i] = value % 255;
		}
		return matrix;
	}

	public static int[] identityNormalization(int[] matrix) {
		return matrix;
	}

	public static BufferedImage filter(BufferedImage image, FilterMask mask) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height, image.getType());
		double[][] redMatrix = new double[width][height];
		double[][] greenMatrix = new double[width][height];
		double[][] blueMatrix = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color(image.getRGB(i, j));
				redMatrix[i][j] = color.getRed();
				greenMatrix[i][j] = color.getGreen();
				blueMatrix[i][j] = color.getBlue();
			}
		}
		double[][] resultRedMatrix = mask.filterImage(redMatrix);
		double[][] resultGreenMatrix = mask.filterImage(greenMatrix);
		double[][] resultBlueMatrix = mask.filterImage(blueMatrix);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int red = (int) Math.round(resultRedMatrix[i][j]);
				int green = (int) Math.round(resultGreenMatrix[i][j]);
				int blue = (int) Math.round(resultBlueMatrix[i][j]);
				result.setRGB(i, j, new Color(red,green,blue).getRGB());
			}
		}
		return result;
	}
	
	public static BufferedImage globalThreshold(BufferedImage image){
		double T = 128.0;
		double auxT = 0;
		int iterations = 0;
		while(T-auxT > 1){
			auxT = T;
			int whiteCount = 0;
			int blackCount = 0;
			double m1 = 0;
			double m2 = 0;
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					int color = new Color(image.getRGB(i, j)).getRed();
					if(color>=T){
						m1+=color;
						whiteCount++;
					} else{
						m2+=color;
						blackCount++;
					}
				}
			}
			m1/=whiteCount;
			m2/=blackCount;
			T = (m1+m2)/2;
			iterations++;
		}
		JOptionPane.showMessageDialog(new JFrame("Global Threshold"), "Global Threshold: " + (int)Math.round(T) + "\n Iterations: " + iterations);
		return threshold(image, (int) Math.round(T));
	}
	
	public static BufferedImage applyOtsuThreshold(BufferedImage image){
		double threshold = getOtsuThreshold(image, RGB.RED);
		JOptionPane.showMessageDialog(new JFrame("Otsu Threshold"), "Otsu Threshold: " + (int)Math.round(threshold));
		return threshold(image, (int) Math.round(threshold));	
	}
	
	public static double getOtsuThreshold(BufferedImage image, RGB band){
		Map<Integer, Double> p1 = getHistogram(image, band);
		Map<Integer, Double> P1 = acumHistogram(p1);
		double[] varianza = new double[256];
		double[] mi = new double[256];
		double mg = 0;
		for (int i = 0; i < mi.length; i++) {
			mg += i*p1.get(i);
			mi[i] = (i==0) ? i*p1.get(i) : mi[i-1]+i*p1.get(i);
		}
		for(int i = 0; i < varianza.length; i++){
			double Pi = P1.get(i);
			varianza[i] = (Math.pow(mg*Pi-mi[i], 2))/(Pi*(1-Pi));
		}
		double max = Double.MIN_VALUE;
		List<Integer> results = new ArrayList<>();
		for(int i  = 0; i< varianza.length; i++){
			double value = varianza[i];
			if(value>max){
				results.clear();
				results.add(i);
				max = value;
			} else if(value == max){
				results.add(i);
			}
		}
		double threshold = 0;
		for(Integer i : results){
			threshold+=i;
		}
		threshold/=results.size();
		return threshold;
	}
	
	public static BufferedImage ansotropicDiffusion(BufferedImage image, int T, double sigma, Detector detector, Boolean isIsotropic){
		int width = image.getWidth();
		int height = image.getHeight();
		double lambda = 0.25;
		BufferedImage result = new BufferedImage(width, height, image.getType());
		double[][] matrix = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = new Color(image.getRGB(i, j));
				matrix[i][j] = color.getRed();
			}
		}
		double[][] aux = new double[width][height];
		for(int t = 0; t<T; t++){
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					aux[i][j] = matrix[i][j];
				}
			}
			for (int i = 1; i < width-1; i++) {
				for (int j = 1; j < height-1; j++) {
					double DN = aux[i+1][j] - aux[i][j];
					double DS = aux[i-1][j] - aux[i][j];
					double DE = aux[i][j+1] - aux[i][j];
					double DO = aux[i][j-1] - aux[i][j];
					
					double cn;
					double cs;
					double ce;
					double co;
					if(isIsotropic){
						cn = cs = ce = co = 1.0;
					} else{
						cn = detector.g(DN*aux[i][j], sigma);
						cs = detector.g(DS*aux[i][j], sigma);
						ce = detector.g(DE*aux[i][j], sigma);
						co = detector.g(DO*aux[i][j], sigma);
					}
					matrix[i][j] = aux[i][j] + lambda*(cn*DN+cs*DS+ce*DE+co*DO);
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color = (int) Math.round(matrix[i][j]);
				result.setRGB(i, j, new Color(color,color,color).getRGB());
			}
		}
		System.out.println("DONE");
		return result;
	}

	public static double[][] getBandMatrix(BufferedImage image, char band) {
		int width = image.getWidth();
		int height = image.getHeight();
		double[][] matrix = new double[width][height];
		double currentValue=-1;
		Color currentColor;
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				currentColor=new Color(image.getRGB(i, j));
				
				switch (band) {
				case 'r':{
					currentValue=currentColor.getRed();
					break;
				}
				case 'g':{
					currentValue=currentColor.getGreen();
					break;
					
				}
				case 'b':{
					currentValue=currentColor.getBlue();
					break;
				}
				
				default:
					
					System.out.println("[ImageEffects,getBandMatrix]: invalid band");
				}
				matrix[i][j] = currentValue;

			}
		}

		return matrix;

	}

	public static BufferedImage getBandBuffer(BufferedImage image, char band) {
		int width = image.getWidth();
		int height = image.getHeight();
		int currentValue=-1;
		Color currentColor;
		Color finalRGB=new Color(0,0,0);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				currentColor=new Color(image.getRGB(i, j));
				switch (band) {
				case 'r':{
					currentValue=currentColor.getRed();
					finalRGB=new Color(currentValue,0,0);
					break;
				}
				case 'g':{
					currentValue=currentColor.getGreen();
					finalRGB=new Color(0,currentValue,0);
					break;
					
				}
				case 'b':{
					currentValue=currentColor.getBlue();
					finalRGB=new Color(0,0,currentValue);
					break;
				}
				
				default:
					
					System.out.println("[ImageEffects,getBandMatrix]: invalid band");
				}
				image.setRGB(i, j, finalRGB.getRGB());

			}
		}

		return image;

	}
	
	
	
	
	public static double[][] getMatrix (BufferedImage bufferedImage){
		// COPIO LA IMAGEN
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		double[][] image = new double[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				image[i][j] = new Color(bufferedImage.getRGB(j, i)).getRed();
			}
		}
		return image;
	}
	
	public static BufferedImage getImage (double[][] matrix){
		int height = matrix.length;
		int width = matrix[0].length;
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage result = new BufferedImage(width, height, type);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = (int) matrix[i][j];
				color = new Color(color, color, color).getRGB();
				result.setRGB(j, i, color);
			}
		}
		return result;
	}
	
	public static BufferedImage playingWithHSV(BufferedImage image){
		int[][] matrix = new int[image.getHeight()][image.getWidth()];
		BufferedImage result =  new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = image.getRGB(j, i);
			}
		}
		matrix = HSV_to_RGB(RGB_to_HSV(matrix));
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				result.setRGB(j, i, matrix[i][j]);
			}
		}
		return result;
	}
	
	public static Vector3D[][] RGB_to_HSV(int[][] rgbs){
		Vector3D[][] result = new Vector3D[rgbs.length][rgbs[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				Color color = new Color(rgbs[i][j]);
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				float[] hsvs= Color.RGBtoHSB(r, g, b, null);
				
				result[i][j] = new Vector3D(hsvs[0], hsvs[1], hsvs[2]);
			}
		}
		return result;
	}
	public static int[][] HSV_to_RGB(Vector3D[][] hsvs){
		int[][] result = new int[hsvs.length][hsvs[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				float h = (float) hsvs[i][j].getX();
				float s = (float)hsvs[i][j].getY();
				float v = (float) hsvs[i][j].getZ();
				int rgbs= Color.HSBtoRGB(h, s, v);
				
				result[i][j] = rgbs;
			}
		}
		return result;
	}
}
