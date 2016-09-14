package masks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import view.ImageEffects;
import view.panels.ImagePanel;

public class LaplacianMask extends SquareMask{

	public static final int  WHITE=255;
	public LaplacianMask() {

	super(3);
	set(0, 0, 0);
	set(0,1,-1);
	set(0,2,0);
	
	set(1, 0, -1);
	set(1, 1, 4);
	set(1, 2, -1);
	
	set(2, 0, 0);
	set(2,1,-1);
	set(2,2,0);
	
	
	}
	public BufferedImage filter(ImagePanel imagePanel){
		return this.filter(imagePanel.getImage());
	}
	public BufferedImage filter(BufferedImage original){
		double [][] mask_applied;
		
		
		///aplica mascara
		int width = original.getWidth();
		int height = original.getHeight();
		BufferedImage result = new BufferedImage(width, height, original.getType());
		double[][] matrix = ImageEffects.getBandMatrix(original, 'r');//SOLO BANDA ROJA
		
		
		
		mask_applied= filterImage(matrix);
		
		///	
		
		
		
		//cruces por cero
		int[][] ceroCrossed=crossByCero(mask_applied);
		//BufferedImage ans=crossByCero(mask_applied);
//		int[] flatArray=Arrays.stream(ceroCrossed)
//						.flatMapToInt(Arrays::stream)
//						.toArray();
//		
		int flatArray[]=new int[width*height];
		for(int j=0;j<width;j++){
			for(int i=0;i<height;i++){
				int index=i*height+j;
				flatArray[index]=ceroCrossed[j][i];
			}
		}
		
				
		
		BufferedImage ans=ImageEffects.buildImage(flatArray, 
												flatArray, 
												flatArray, 
												width, 
												height, 
												original.getType()
												, ImageEffects::identityNormalization);
		
		return ans;
	}

	private int[][]crossByCero(double[][] bandMatrix) {
		int rows=bandMatrix.length;
		int cols=bandMatrix[0].length;
		
		
		int[][] ans=new int[rows][cols];
		/*
		//busca cruces por cero por fila
		for(int row=0;row<rows;row++){
			double previous=bandMatrix[row][0];
			
			for(int col=1;col<cols;col++){
				
				double current=bandMatrix[row][col];
				
				
				//se evalua si hubo cambio de signo
				if((previous>0 && current<0) || (previous<0 && current>0)){
					
					ans[row][col]=WHITE;
				}
				
				
				
				
			}
			
		}
		*/
		//busca cruces por cero por columna
		
		for(int col=0;col<cols;col++){
			double previous=bandMatrix[0][col];
			for(int row=1;row<rows;row++){
				
				double  current=bandMatrix[row][col];
				
				
				//se evalua si hubo cambio de signo
				if((previous>0 && current<0) || (previous<0 && current>0)){
					
					ans[row][col]=WHITE;
				}
				
				
				
				
			}
			
		}
		
		
//		return ImageEffects.buildImage(red, red, red, width, height, bandMatrix.getType(), ImageEffects::identityNormalization);
		return ans;
		
	}
	
}
