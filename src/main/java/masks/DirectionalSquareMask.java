package masks;

import java.util.ArrayList;
import java.util.List;

import view.ImageEffects;

public class DirectionalSquareMask implements FilterMask{

	private List<SquareMask> masks = new ArrayList<>();
	
	public DirectionalSquareMask(SquareMask mask, List<Direction> directions) { //pasarle la comun por defecto
		if(mask.size != 3)
			throw new IllegalArgumentException("Mask size must be 3");
		System.out.println("SIZE= " + directions.size());
		for(Direction dir : directions){
			masks.add(getDirectionMask(mask, dir));
		}
	}



	private SquareMask getDirectionMask(SquareMask mask, Direction dir) {
		SquareMask result = new SquareMask(mask.size);
		switch (dir) {
		case HORIZONTAL:
			for (int i = 0; i < mask.size; i++) {		//	a	d	g
				for (int j = 0; j < mask.size; j++) {	//	b	e	h
					result.set(i, j, mask.get(j, i));	//	c	f	i
				}
			}
			break;
		case VERTICAL:									//	a	b	c
			result = mask;								//	d	e	f
			break;										//	g	h	i
		case DIAGONAL_TOP_RIGHT:
			result.set(0, 0, mask.get(0, 1));
			result.set(0, 1, mask.get(0, 2));
			result.set(0, 2, mask.get(1, 2));
			result.set(1, 0, mask.get(0, 0));
			result.set(1, 1, mask.get(1, 1));			//	b	c	f
			result.set(1, 2, mask.get(2, 2));			//	a	e	i
			result.set(2, 0, mask.get(1, 0));			//	d	g	h
			result.set(2, 1, mask.get(2, 0));
			result.set(2, 2, mask.get(2, 1));
			break;
		case DIAGONAL_TOP_LEFT:
			result.set(0, 0, mask.get(1, 0));
			result.set(0, 1, mask.get(0, 0));			//	d	a	b
			result.set(0, 2, mask.get(0, 1));			//	g	e	c
			result.set(1, 0, mask.get(2, 0));			//	h	i	f
			result.set(1, 1, mask.get(1, 1));
			result.set(1, 2, mask.get(0, 2));
			result.set(2, 0, mask.get(2, 1));
			result.set(2, 1, mask.get(2, 2));
			result.set(2, 2, mask.get(1, 2));
			break;
		default:
			break;
		}
		return result;
	}



	public double[][] filterImage(double[][] image){
		List<double[][]> images = new ArrayList<>();
		for (SquareMask mask : masks) {
			images.add(mask.filterImage(image));
		}
		double[][] sintesisResult = sintesis(images);
		return ImageEffects.linearNormalization(sintesisResult);
	}
	
	private double[][] sintesis(List<double[][]> images){
		if(images.size()==1){
			return images.get(0);
		}
		if(images.size() == 2){
			double[][] xImage = images.get(0);
			double[][] yImage = images.get(1);
			double[][] result = new double[xImage.length][xImage[0].length];
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					result[i][j] = Math.sqrt(Math.pow(xImage[i][j], 2) + Math.pow(yImage[i][j], 2));
				}
			}
			return result;
		}
		if(images.size() == 4){
			double[][] image1 = images.get(0);
			double[][] image2 = images.get(1);
			double[][] image3 = images.get(2);
			double[][] image4 = images.get(3);
			double[][] result = new double[image1.length][image1[0].length];
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					result[i][j] = Math.max(Math.max(image1[i][j], image2[i][j]), Math.max(image3[i][j], image4[i][j]));
				}
			}
			return result;
		}
		return null;
	}


	@Override
	public double filter(double[] values) {
		return 0; //not used
	}

}
