package image;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import view.ImageEffects;

public class Contrast {

	/*
	 * función para incrementar el contraste.
	 * r1 y r2 son los valores de la pagina 6, clase "histogramasPresClase2.pdf"
	 * Si r>=r2, se aplica f(x)=2x. 
	 * Si r<=r1, se aplica f(x)=x/2.
	 * Sino , se aplica la identidad
	 */
	public static BufferedImage increaseContrast(BufferedImage original, double r1, double r2){
		System.out.println("r1"+ r1+ "r2"+ r2);
		BufferedImage ans = null;
		int height = original.getHeight();
		int width = original.getWidth();
		int[] red_band = ImageEffects.getBand(original, 'r');
		double s1 = r1 / 2;
		double s2 = (r2+255)/2;
		double a1 = s1/r1;
		double b1 = 0;
		double a2 = (s2-s1)/(r2-r1);
		double b2 = s1-(a2*r1);
		double a3 = (255-s2)/(255-r2);
		double b3 = s2-(a3*r2);
		
		System.out.printf("a= %f b= %f \n", a1,b1);
		System.out.printf("a= %f b= %f \n", a2,b2);
		System.out.printf("a= %f b= %f \n", a3,b3);
		for(int i=0;i<red_band.length;i++){
			double current_value=red_band[i];
			if(current_value<=r1){
				current_value=current_value * a1 + b1;
				
				
			}else if(current_value>=r2){
				current_value=current_value* a3 + b3;
			} else {
				current_value = current_value * a2 + b2;				
			}
			
			
			red_band[i]= (int) Math.round(current_value);
		}
		
		ans=ImageEffects.buildImage(red_band, red_band, red_band, width, height, original.getType(),
				ImageEffects::identityNormalization);
		
		return ans;

	}
}
