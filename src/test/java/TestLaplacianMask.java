import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import image.ImageUtils;
import masks.LaplacianMask;

/*
* @author Fernando Bejarano
*/
public class TestLaplacianMask {
	
	
	

	@Test
	public void test1() throws IOException, InterruptedException{
		genericTest("./src/main/resources/cameraman-chopped.png",0,0);
	}
	
	@Test
	public void test2() throws IOException, InterruptedException{
		genericTest("./src/main/resources/LENAX.RAW",256,256);
	}
	@Test
	public void test3() throws IOException, InterruptedException{
		genericTest("./src/main/resources/probandoRuido.png",0,0);
	}
	
	@Test
	public void test4() throws IOException, InterruptedException{
		genericTest("./src/main/resources/JackSig.bmp",0,0);
	}
	
	public void genericTest( String fileName,int width,int height) throws IOException, InterruptedException{
		BufferedImage original=original= (BufferedImage) ImageUtils.loadImage(fileName, width, height);
		LaplacianMask mask=new LaplacianMask();
		BufferedImage result=mask.filter(original);
	}

}
