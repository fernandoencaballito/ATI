package keypoints;

/*
* @author Fernando Bejarano
*/
public class SusanMask {

	private static boolean[][] mask = new boolean[][] { { false, false, true, true, true, false, false },
			{ false, true, true, true, true, true, false }, { true, true, true, true, true, true, true },
			{ true, true, true, false, true, true, true }, { true, true, true, true, true, true, true },
			{ false, true, true, true, true, true, false }, { false, false, true, true, true, false, false } };
	private static int SIZE = 7;
	private static int HALF = (SIZE - 1) / 2;
	private static int MASK_PIXELS = 36;

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

	public BufferedImage getBordersAndCorners(int[][] originalImage) {
		return null;
	}

}
