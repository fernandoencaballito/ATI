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
		return ImageEffects.linearNormalization(sintesisResult);
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
