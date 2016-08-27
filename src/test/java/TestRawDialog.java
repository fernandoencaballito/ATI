import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.AssertionFailedError;
import view.RawDialog;

/*
* @author Fernando Bejarano
*/
public class TestRawDialog {

	private RawDialog dialog;
	
	
	@Before
	public void init(){
		dialog=new RawDialog();
	}
	
	
	@Test
	public void test1(){
		String str="hola";
		assertFalse(dialog.validInput(str));
	}
	@Test
	public void test2(){
		String str="primero,segundo";
		assertFalse(dialog.validInput(str));
	}
	
	@Test
	public void test3(){
		String str=",";
		assertFalse(dialog.validInput(str));
	}
	
	@Test
	public void test4(){
		String str="3,";
		assertFalse(dialog.validInput(str));
	}
	
	@Test
	public void test5(){
		String str=",3";
		assertFalse(dialog.validInput(str));
	}
	
	@Test
	public void test6(){
		String str="";
		assertFalse(dialog.validInput(str));
	}
	@Test
	public void test7(){
		String str=" ";
		assertFalse(dialog.validInput(str));
	}
	@Test
	public void test8(){
		String str=null;
		assertFalse(dialog.validInput(str));
	}
	
	@Test
	public void test9(){
		
		String str="90,135";
		assertTrue(dialog.validInput(str));
		assertEquals(90,dialog.getInputWidth());
		assertEquals(135,dialog.getInputHeight());
	}
	
	@Test
	public void test10(){
		String str="90,hola";
		assertFalse(dialog.validInput(str));
	}
}
