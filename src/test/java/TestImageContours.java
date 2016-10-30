import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

import border_detectors.ActiveContours;
import border_detectors.Phi_value;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class TestImageContours {

	ImagePanel original, modified;
	String initialFile="./src/main/resources/TEST.png";
	@Before
	public void init(){
		
		original=new ImagePanel(initialFile);
		
		modified=new ImagePanel(initialFile);
	}
	
	@Test
	public void test1(){
		ActiveContours activeContours=new ActiveContours(original,modified);
		Rectangle rect=new Rectangle(2, 3, 6, 6);
		original.setSelectedRectangle(rect);
		activeContours.initialise();
		
		 Phi_value[][] phi_values=activeContours.getPhi_values();
		//L in
		
		
		
		
		//L out
		 assertEquals(Phi_value.L_OUT, phi_values[3][2]);
		 assertEquals(Phi_value.L_OUT, phi_values[3][3]);
		 assertEquals(Phi_value.L_OUT, phi_values[3][4]);
		 assertEquals(Phi_value.L_OUT, phi_values[3][7]);
			
		 assertEquals(Phi_value.L_OUT, phi_values[5][2]);
		 assertEquals(Phi_value.L_OUT, phi_values[6][2]);
		 assertEquals(Phi_value.L_OUT, phi_values[7][2]);
		
		 
		 assertEquals(Phi_value.L_OUT, phi_values[5][7]);
		 assertEquals(Phi_value.L_OUT, phi_values[6][7]);
		 assertEquals(Phi_value.L_OUT, phi_values[7][7]);
		
		 
		 assertEquals(Phi_value.L_OUT, phi_values[8][3]);
		 assertEquals(Phi_value.L_OUT, phi_values[8][4]);
		 assertEquals(Phi_value.L_OUT, phi_values[8][5]);
		
		//OBJETO
		
		//BACKGROUND
		
		
	}
}
