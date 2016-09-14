import static org.junit.Assert.*;

import org.junit.Test;

import masks.LOGmask;

public class TestLOGmask {
	private static final double epsilon=0.01;
	@Test
	public void test(){
		
		LOGmask mask=new LOGmask(7, 1);
		assertEquals(-0.7979,mask.get(3, 3),epsilon);
		assertEquals(0.008,mask.get(0, 0),epsilon);
	
		assertEquals(0.008,mask.get(6, 6),epsilon);
		assertEquals(0,mask.get(2, 2),epsilon);
		
	}
	

}
