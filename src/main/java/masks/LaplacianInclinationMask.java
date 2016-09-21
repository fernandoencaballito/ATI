package masks;

/*
* @author Fernando Bejarano
*/
public class LaplacianInclinationMask extends LaplacianMask {
	int threshold;

	public LaplacianInclinationMask(int threshold) {
		super();
		this.threshold=threshold;
	}
	
	@Override
	protected int[][] crossByCero(double[][] bandMatrix) {
		int rows = bandMatrix.length;
		int cols = bandMatrix[0].length;

		int[][] ans = new int[rows][cols];
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

		// umbralización

		//se necesita el umbral!!!
		int currentTheshold=0;
		

		return threshold(currentTheshold, byRowSums, byColSums);

	}
	
	
public static int[][] threshold(int threshold, double[][] byRowSums ,
							double[][] byColSums ){
	
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

	return ans;

	
}

}
