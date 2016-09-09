package masks;

import view.ImageEffects;

public class XYSquareMask implements FilterMask{

	private SquareMask xMask;
	private SquareMask yMask;
	
	public XYSquareMask(int size) {
		xMask = new SquareMask(size);
		yMask = new SquareMask(size);
	}
	
	public void setMasks(SquareMask mask1, SquareMask mask2) {
		xMask = mask1;
		yMask = mask2;
	}

	@Override
	public double filter(double[] values) {
		return 0;
	}
	
	public double[][] filterImage(double[][] image){
		double[][] imageX = xMask.filterImage(image);
		double[][] imageY = yMask.filterImage(image);
		double[][] sintesisResult = sintesis(imageX, imageY);
		int[] normalized = new int[sintesisResult.length * sintesisResult[0].length];
		for (int i = 0; i < sintesisResult.length; i++) {
			for (int j = 0; j < sintesisResult[i].length; j++) {
				normalized[j*sintesisResult.length + i] = (int) Math.round(sintesisResult[i][j]);
			}
		}
		normalized = ImageEffects.linearNormalization(normalized);
		for (int i = 0; i < sintesisResult.length; i++) {
			for (int j = 0; j < sintesisResult[i].length; j++) {
				sintesisResult[i][j] = normalized[j*sintesisResult.length + i];
			}
		}
		return sintesisResult;
	}
	
	private double[][] sintesis(double[][] xImage, double[][] yImage){
		double[][] result = new double[xImage.length][xImage[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = Math.sqrt(Math.pow(xImage[i][j], 2) + Math.pow(yImage[i][j], 2));
			}
		}
		return result;
	}

}
