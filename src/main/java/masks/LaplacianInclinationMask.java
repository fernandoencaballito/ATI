package masks;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import view.ImageEffects;
import view.panels.CrossByCeroFrame;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class LaplacianInclinationMask extends LaplacianMask {
	int threshold;
	ImagePanel imagePanel;

	public LaplacianInclinationMask(int threshold) {
		super();
		this.threshold=threshold;
	}
	
	public void semiFilter(ImagePanel imagePanel) {
		this.imagePanel = imagePanel;
		this.semiFilter(imagePanel.getImage());
	}
	
	public void semiFilter(BufferedImage original) {
		double[][] mask_applied;

		/// aplica mascara
		int width = original.getWidth();
		int height = original.getHeight();
		BufferedImage result = new BufferedImage(width, height, original.getType());
		double[][] matrix = ImageEffects.getBandMatrix(original, 'r');// SOLO
																		// BANDA
																		// ROJA
		double [][] aux=new double [height][width];
		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				
				aux[i][j] =matrix[j][i];
			}
		}
		
		mask_applied = filterImage(aux);

		crossByCeros(mask_applied);
		
		// cruces por cero
		//crossByCeros(mask_applied);
//		 int[] flatArray=Arrays.stream(ceroCrossed)
//		 .flatMapToInt(Arrays::stream)
//		 .toArray();
		
//		int flatArray[] = new int[width * height];
//		for (int j = 0; j < width; j++) {
//			for (int i = 0; i < height; i++) {
//				int index = i * height + j;
//				flatArray[index] = ceroCrossed[j][i];
//			}
//		}

//		BufferedImage ans = ImageEffects.buildImage(flatArray, flatArray, flatArray, width, height, original.getType(),
//				ImageEffects::identityNormalization);
//
//		return ans;
	}
	
	
	protected void crossByCeros(double[][] bandMatrix) {
		int rows = bandMatrix.length;
		int cols = bandMatrix[0].length;

		double[][] byRowSums = new double[rows][cols];
		double[][] byColSums = new double[rows][cols];

		// busca cruces por cero por fila
		for (int row = 0; row < rows; row++) {
			double previous = bandMatrix[row][0];

			for (int col = 1; col < cols; col++) {

				double current = bandMatrix[row][col];

				// se evalua si hubo cambio de signo
				if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {

					byRowSums[row][col] = Math.abs(previous) + Math.abs(current);
				}
				if (!(current >= -DELTA && current <= DELTA))
					previous = current;
			}

		}

		// busca cruces por cero por columna

		for (int col = 0; col < cols; col++) {
			double previous = bandMatrix[0][col];
			for (int row = 1; row < rows; row++) {

				double current = bandMatrix[row][col];

				// se evalua si hubo cambio de signo
				if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {

					byColSums[row][col] = Math.abs(previous) + Math.abs(current);
				}

				if (!(current >= -DELTA && current <= DELTA))
					previous = current;

			}

		}
		
		double max = Double.MIN_VALUE; 
		double min = Double.MAX_VALUE;
		for (int i = 0; i < byRowSums.length; i++) {
			for (int j = 0; j < byRowSums[i].length; j++) {
				max = Math.max(Math.max(max, byRowSums[i][j]), byColSums[i][j]);
				min = Math.min(Math.min(min, byRowSums[i][j]), byColSums[i][j]);
			}
		}

		CrossByCeroFrame frame = new CrossByCeroFrame(imagePanel, byRowSums, byColSums, min, max);
		frame.setVisible(true);

		// umbralización

		//se necesita el umbral!!!
		//int currentTheshold=0;
		

		//return threshold(currentTheshold, byRowSums, byColSums);

	}
	
	
public static BufferedImage threshold(int threshold, double[][] byRowSums ,
							double[][] byColSums, BufferedImage image){
	
	int rows=byRowSums.length;
	int cols=byRowSums[0].length;
	
	int[][] ans = new int[rows][cols];
	int[][] byRow = new int[rows][cols];
	int[][] byCol = new int[rows][cols];

	// umbralización

	
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {

			if (byRowSums[i][j] > threshold)
				byRow[i][j] = WHITE;

		}
	}
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols; j++) {

			if (byColSums[i][j] > threshold)
				byCol[i][j] = WHITE;

		}
	}

	// union entre cruces por fila y por columna

	for (int i = 0; i < rows; i++) {

		for (int j = 0; j < cols; j++) {

			if (byCol[i][j] == WHITE || byRow[i][j] == WHITE) {
				ans[i][j] = WHITE;
			}

		}
	}

	int[] flatArray=Arrays.stream(ans)
			 .flatMapToInt(Arrays::stream)
			 .toArray();
	
	return ImageEffects.buildImage(flatArray, flatArray, flatArray, image.getWidth(), image.getHeight(), image.getType(),
			ImageEffects::identityNormalization);
	

	
}

}
