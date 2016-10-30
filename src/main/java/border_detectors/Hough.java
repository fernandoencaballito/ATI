package border_detectors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import masks.Direction;
import masks.DirectionalSquareMask;
import masks.SquareMask;
import view.ImageEffects;
import view.panels.HoughThresholdFrame;
import view.panels.ImagePanel;

public class Hough {
	static double epsilon = 1;
	static final double THETA_MIN = -90;
	static final double THETA_MAX = 90;
	static final int WHITE = 255;
	static final int BLACK = 0;
	
	public static void hough(ImagePanel imagePanel, int cant_thetha, int cant_rho, SquareMask sqmask){
		BufferedImage bufferedImage = imagePanel.getImage();
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		
		// BUSCAMOS BORDES CON LA MASCARA PASADA
		List<Direction> dirs = new ArrayList<>();
		dirs.add(Direction.HORIZONTAL);
		dirs.add(Direction.VERTICAL);
		DirectionalSquareMask mask = new DirectionalSquareMask(sqmask, dirs);
		double[][] image = mask.filterImage(Canny.getMatrix(bufferedImage));
		image = ImageEffects.linearNormalization(image);
		
		//UMBRALIZAMOS
		image = Canny.getMatrix(ImageEffects.otzuThreshold(Canny.getImage(image)));
		
		//DISCRETIZAMOS ESPACIO NORMAL (theta y rho)
		
		double D = Math.max(height, width);
		double rho_max = Math.sqrt(2) * D;
		double rho_min = -rho_max;
		double rho_step = rho_max * 2/ (cant_rho-1);
		double theta_step =THETA_MAX * 2/ (cant_thetha-1);
		
		//PARA CADA PUNTO BLANCO INCREMENTAMOS VALOR DE TODAS LAS RECTAS QUE PASAN POR EL PUNTO
		//TOMO COMO EJE DE COORDENADAS ABAJO A LA IZQUIERDA

		int[][] discreteMatrix = new int[cant_rho][cant_thetha];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				if(image[i][j] == WHITE){
					for (int i2 = 0; i2 < discreteMatrix.length; i2++) {
						for (int j2 = 0; j2 < discreteMatrix[i2].length; j2++) {
							if(matches_line_equation(rho_min + i2*rho_step, THETA_MIN + j2*theta_step, j, height - i))
								discreteMatrix[i2][j2]++;
						}
					}
				}
			}
		}
		
		HoughThresholdData data = new Hough().new HoughThresholdData(discreteMatrix, rho_step, theta_step, cant_thetha, cant_rho, THETA_MIN, THETA_MAX, rho_min, rho_max);
		HoughThresholdFrame frame = new HoughThresholdFrame(imagePanel, data);
		frame.setVisible(true);
		
	}
	
	private static boolean matches_line_equation(double rho, double thetha, int x, int y){
		double value = x*Math.cos(Math.toRadians(thetha)) + y*Math.sin(Math.toRadians(thetha));
		return Math.abs(value-rho) < epsilon;
	}

	public static BufferedImage threshold(int percentage, BufferedImage original, HoughThresholdData data){
		List<double[]> aux = new ArrayList<>();
		int height = original.getHeight();
		int width = original.getWidth();
		BufferedImage result = copyImage(original);
		int[][] count = data.counts;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count[i].length; j++) {
				max = Math.max(max, count[i][j]);
			}
		}
		double threshold = ((double) percentage) / 100.0;
		threshold = 1.0 - threshold;
		threshold *= max;
		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count[i].length; j++) {
				if(count[i][j] >= threshold){
					aux.add(new double[]{data.rho_min + i * data.rho_step, data.theta_min + j * data.theta_step});
					System.out.println(count[i][j]);
				}
			}
		}
		
		Color lineColor = Color.GREEN;
		int lineRGB = lineColor.getRGB();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				for (double[] params : aux) {
					double rho = params[0];
					double theta = params[1];
					if(matches_line_equation(rho, theta, j, height-i)){
						result.setRGB(j, i, lineRGB);
						break;
					}
				}
			}
		}
		return result;
	}
	
	public class HoughThresholdData{
		int[][] counts;
		double rho_step;
		double theta_step;
		int cant_theta;
		int cant_rho;
		double theta_min;
		double theta_max;
		double rho_min;
		double rho_max;
		
		public HoughThresholdData(int[][] counts, double rho_step, double theta_step, int cant_theta, int cant_rho,
				double theta_min, double theta_max, double rho_min, double rho_max) {
			super();
			this.counts = counts;
			this.rho_step = rho_step;
			this.theta_step = theta_step;
			this.cant_theta = cant_theta;
			this.cant_rho = cant_rho;
			this.theta_min = theta_min;
			this.theta_max = theta_max;
			this.rho_min = rho_min;
			this.rho_max = rho_max;
		}		
	}
	
	private static BufferedImage copyImage(BufferedImage original){
		BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < original.getWidth(); i++) {
			for (int j = 0; j < original.getHeight(); j++) {
				image.setRGB(i, j, original.getRGB(i, j));
			}
		}
		return image;
	}
}
