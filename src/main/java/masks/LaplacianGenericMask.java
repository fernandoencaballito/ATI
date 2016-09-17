package masks;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import view.ImageEffects;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public abstract class LaplacianGenericMask extends SquareMask {
	public static final int WHITE = 255;

	public LaplacianGenericMask(int size) {
		super(size);

	}

	public BufferedImage filter(ImagePanel imagePanel) {
		return this.filter(imagePanel.getImage());
	}

	public BufferedImage filter(BufferedImage original) {
		double[][] mask_applied;

		/// aplica mascara
		int width = original.getWidth();
		int height = original.getHeight();
		BufferedImage result = new BufferedImage(width, height, original.getType());
		double[][] matrix = ImageEffects.getBandMatrix(original, 'r');// SOLO
																		// BANDA
																		// ROJA

		mask_applied = filterImage(matrix);

		for (double[] currentRow : mask_applied) {
			System.out.println(Arrays.toString(currentRow));
		}

		///

		// cruces por cero
		int[][] ceroCrossed = crossByCero(mask_applied);
		// BufferedImage ans=crossByCero(mask_applied);
		// int[] flatArray=Arrays.stream(ceroCrossed)
		// .flatMapToInt(Arrays::stream)
		// .toArray();
		//
		int flatArray[] = new int[width * height];
		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				int index = i * height + j;
				flatArray[index] = ceroCrossed[j][i];
			}
		}

		BufferedImage ans = ImageEffects.buildImage(flatArray, flatArray, flatArray, width, height, original.getType(),
				ImageEffects::identityNormalization);

		return ans;
	}

	private int[][] crossByCero(double[][] bandMatrix) {
		int rows = bandMatrix.length;
		int cols = bandMatrix[0].length;

		int[][] ans = new int[rows][cols];
		int[][] byRow = new int[rows][cols];
		int[][] byCol = new int[rows][cols];

		// busca cruces por cero por fila
		for (int row = 0; row < rows; row++) {
			double previous = bandMatrix[row][0];

			for (int col = 1; col < cols; col++) {

				double current = bandMatrix[row][col];

				// se evalua si hubo cambio de signo
				if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {

					byRow[row][col] = WHITE;
				}

			}

		}

		// busca cruces por cero por columna

		for (int col = 0; col < cols; col++) {
			double previous = bandMatrix[0][col];
			for (int row = 1; row < rows; row++) {

				double current = bandMatrix[row][col];

				// se evalua si hubo cambio de signo
				if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {

					byCol[row][col] = WHITE;
				}
				previous = current;

			}

		}

		//intersección entre cruces por fila y por columna
		
		for(int i=0;i<rows;i++ ){
			
			for(int j=0;j<cols;j++){
				
				if(byCol[i][j]== WHITE && byRow[i][j]==WHITE){
					ans[i][j]=WHITE;
				}
				
			}
		}
		
		
		
		return ans;

	}
}
