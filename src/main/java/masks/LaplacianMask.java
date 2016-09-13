package masks;

import java.awt.image.BufferedImage;

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
		BufferedImage original=imagePanel.getImage();
		BufferedImage mask_applied=ImageEffects.filter(original, this);
		
		BufferedImage ans=crossByCero(mask_applied);
		return ans;
	}

	private BufferedImage crossByCero(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
	 
		int[] red=ImageEffects.getBand(image, 'r');
		int[] ans=new int[width*height];
		
		//busca cruces por cero por fila
		for(int row=0;row<height;row++){
			int previous=red[row*width];
			for(int col=1;col<width;col++){
				int index=row*width+col;
				int current=red[index];
				
				
				//se evalua si hubo cambio de signo
				if((previous>0 && current<0) || (previous<0 && current>0)){
					
					ans[index]=WHITE;
				}
				
				
				
				
			}
			
		}
		
		//busca cruces por cero por columna
		for(int col=0;col<width;col++){
			int previous=red[col];
			for(int row=1;row<height;row++){
				int index=row*width+col;
				int current=red[index];
				
				
				//se evalua si hubo cambio de signo
				if((previous>0 && current<0) || (previous<0 && current>0)){
					
					ans[index]=WHITE;
				}
				
				
				
				
			}
			
		}
		
		
		return ImageEffects.buildImage(red, red, red, width, height, image.getType(), ImageEffects::identityNormalization);
		
	}
	
}
