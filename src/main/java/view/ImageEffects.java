package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
		int[] matrix = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int[] redMatrix = new int[matrix.length];
		for (int i = 0; i < redMatrix.length; i++) {
			redMatrix[i] = new Color(matrix[i]).getRed();
		}
		return histogram(redMatrix);
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
		double[][] matrix = getBandMatrix(image, 'r');
		
		
		
		double[][] resultMatrix = mask.filterImage(matrix);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixelValue = (int) Math.round(resultMatrix[i][j]);
				double aux = resultMatrix[i][j];

				result.setRGB(i, j, new Color(pixelValue, pixelValue, pixelValue).getRGB());

			}
		}
		return result;
	}

	public static double[][] getBandMatrix(BufferedImage image, char band) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height, image.getType());
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
}
