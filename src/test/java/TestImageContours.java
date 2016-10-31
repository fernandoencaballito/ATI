import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.util.Arrays;

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
	String initialFile="./src/main/resources/boxes_grey.ppm";
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
		assertEquals(Phi_value.L_IN, phi_values[4][3]);
		assertEquals(Phi_value.L_IN, phi_values[4][4]);
		assertEquals(Phi_value.L_IN, phi_values[4][5]);
		assertEquals(Phi_value.L_IN, phi_values[4][6]);
		

		assertEquals(Phi_value.L_IN, phi_values[5][3]);
		assertEquals(Phi_value.L_IN, phi_values[6][3]);
		

		assertEquals(Phi_value.L_IN, phi_values[7][4]);
		assertEquals(Phi_value.L_IN, phi_values[7][5]);
		

		assertEquals(Phi_value.L_IN, phi_values[5][6]);
		assertEquals(Phi_value.L_IN, phi_values[6][6]);
		
		
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
		assertEquals(Phi_value.OBJECT, phi_values[5][4]);
		assertEquals(Phi_value.OBJECT, phi_values[5][5]);
		assertEquals(Phi_value.OBJECT, phi_values[6][4]);
		assertEquals(Phi_value.OBJECT, phi_values[6][5]);
		
		
		//BACKGROUND
		assertEquals(Phi_value.BACKGROUD,phi_values[2][2]);
		assertEquals(Phi_value.BACKGROUD,phi_values[2][3]);
		assertEquals(Phi_value.BACKGROUD,phi_values[2][6]);
		assertEquals(Phi_value.BACKGROUD,phi_values[2][7]);
		

		assertEquals(Phi_value.BACKGROUD,phi_values[4][1]);
		assertEquals(Phi_value.BACKGROUD,phi_values[4][0]);

		assertEquals(Phi_value.BACKGROUD,phi_values[1][5]);
		assertEquals(Phi_value.BACKGROUD,phi_values[9][5]);
		
		
		System.out.println("Initialization:\n");
		
		for(Phi_value[] row: phi_values){
			for(Phi_value col:row){
				System.out.print(col+", ");
			}
			System.out.println();
			
		}
		
	}
}
