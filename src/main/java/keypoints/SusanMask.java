package keypoints;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
* @author Fernando Bejarano
*/
public class SusanMask {

	private static final double DELTA = 0.01;
	private static boolean[][] mask = new boolean[][] { { false, false, true, true, true, false, false },
			{ false, true, true, true, true, true, false }, { true, true, true, true, true, true, true },
			{ true, true, true, false, true, true, true }, { true, true, true, true, true, true, true },
			{ false, true, true, true, true, true, false }, { false, false, true, true, true, false, false } };
	private static int SIZE = 7;
	private static int HALF = (SIZE - 1) / 2;
	private static int MASK_PIXELS = 36;
	private static int THRESHOLD=27;//umbral que se usa para comparar niveles de gris con el núcleo en la máscara.
	
	private static Color borderColor = Color.RED;//borders
	private static Color CORNER_COLOR = Color.green;//corners
	private static int circleSize = 3;
	
	// devuelve elementos de la imagen que caen dentro de la máscara
	// recibe la posición en la que se ubica el centro de la máscara respecto de
	// la imagen
	public static int[] getElementsInMask(int center_col, int center_row, int[][] image) {

		int[] ans = new int[MASK_PIXELS];
		int ans_index = 0;

		for (int mask_row = 0; mask_row < SIZE; mask_row++) {
			for (int mask_col = 0; mask_col < SIZE; mask_col++) {

				int image_row = center_row - (HALF - mask_row);
				int image_col = center_col - (HALF - mask_col);

				if (mask[mask_row][mask_col]) {
					int color = image[image_row][image_col];
					// System.out.println("ans_index:"+(ans_index++));
					ans[ans_index++] = color;
				}

			}
		}

		return ans;
	}

	public BufferedImage getBordersAndCorners(BufferedImage originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		int [][]original_image_matrix=new int[height][width];
		
		//se copia imagen original
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color=originalImage.getRGB(i, j);
				result.setRGB(i, j,color);
				original_image_matrix[j][i]=color;
				}
		}
	

		Graphics cornerGraphics = result.createGraphics();
		cornerGraphics.setColor(CORNER_COLOR);
		
		Graphics borderGraphics = result.createGraphics();
		borderGraphics.setColor(borderColor);
		
		//se itera por la imagen original
		
		for(int row=HALF;row<height;row++){
			for(int col=HALF;col<width;col++){
				int current_pixel=result.getRGB(col, row);
				//se obtienen los elementos que caen dentro de la máscara
				int [] elements_in_mask=getElementsInMask(col, row, original_image_matrix);
				
				
				//conteo de pixeles con mismo nivel de gris
				int same_grey_count=getSameGreyCount(elements_in_mask,current_pixel, THRESHOLD);
				
				
				//calculo de s
				double s=1-same_grey_count/MASK_PIXELS;
				
				
				//se evalua s
				
				evaluateCorners(row,col,s,cornerGraphics);
				
				evaluateBorders(row,col,s,borderGraphics);
				
			}
		}
		
		
		
		return result;
	}

	

	private void evaluateCorners(int row, int col, double s, Graphics cornerGraphics) {
		if(Math.abs(s-0.75) <DELTA){
			cornerGraphics.fillOval(col-(circleSize-1)/2, row-(circleSize-1)/2, circleSize, circleSize);
		}
		
	}
	private void evaluateBorders(int row, int col, double s, Graphics borderGraphics) {
		if(Math.abs(s-0.75) <DELTA){
			borderGraphics.fillOval(col-(circleSize-1)/2, row-(circleSize-1)/2, circleSize, circleSize);
		}
		
	}

	public int getSameGreyCount(int[] elements_in_mask, int current_pixel,int threshold) {
		
		int count=0;
		for(int elem:elements_in_mask){
			if(Math.abs(elem-current_pixel)<threshold)
				count++;
		}
		
		return count;
	}

}
