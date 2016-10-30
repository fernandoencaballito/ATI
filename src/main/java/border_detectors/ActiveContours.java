package border_detectors;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sun.jdi.connect.Connector.SelectedArgument;

import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class ActiveContours {

	
	private ImagePanel original, modified;
	private Phi_value[][] phi_values;
	
	public Phi_value[][] getPhi_values() {
		return phi_values;
	}

	public ActiveContours(ImagePanel original, ImagePanel modified){
		this.original=original;
		this.modified=modified;
	}
	
	public void initialise(){
		Rectangle selectionRectangle=original.getSelectionRectangle();
		int rows=original.getImageHeight();
		int cols=original.getImageWidth();
		phi_values=new Phi_value[rows][cols];
		
		
		initialise_phi_values(selectionRectangle);
		
		
		
	}
	
	public void initialise_phi_values(Rectangle selectionRectangle){

		int rows=original.getImageHeight();
		int cols=original.getImageWidth();
		
		for(int row=0;row<rows;row++){
			
			for(int col=0;col<cols;col++){
				//[fondo] pixeles fuera del rectangulo
				if(!selectionRectangle.contains(col, row)){
					phi_values[row][col]=Phi_value.BACKGROUD;
					continue;
				}else{
					
					
					//[ Lout]pixeles del borde del rectangulo
					

					
					//[Lin]pixeles adyacentes interiores al borde del rectangulo marcado
					if(selectionRectangle.contains(col, row)){
						phi_values[row][col]=Phi_value.L_IN;
					}
					
					//[objeto] pixeles interiores del rectangulo que no estan en Lin
					
					
				}
				
				
				
			
				
				
			}
		}
		
	}
	
	
}