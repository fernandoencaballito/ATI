

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.ImageUtils;
import static org.junit.Assert.*;

/*
* @author Fernando Bejarano
*/
public class TestImageUtils {
	@Before
	@After
	public void testCleanUp() {
		File varTmpDir = new File("./aux.bmp");
		boolean exists = varTmpDir.exists();
		assertFalse(exists);
	}

	@Test
	public void testRAW() throws Exception {
		genericTest("./src/main/resources/LENAX.RAW",256,256);

	}

	@Test
	public void testPGM() throws Exception {
		genericTest("./src/main/resources/TEST.PGM",0,0);
	}

	@Test
	public void testPPM() throws Exception {
		genericTest("./src/main/resources/boxes_1.ppm",0,0);
	}

	@Test
	public void testBMP() throws Exception {
		genericTest("./src/main/resources/JackSig.bmp",0,0);

	}

	private void genericTest(String fileName, int width, int height) throws Exception {
		
		Image image = ImageUtils.loadImage(fileName,width,height);
		assertNotNull(image);
		BufferedImage bufferedImage = (BufferedImage) image;
		int ansHeight=bufferedImage.getHeight();
		int ansWidth=bufferedImage.getWidth();
		assertTrue(ansHeight > 0);
		assertTrue(ansWidth> 0);
		System.out.println("[TestImageUtils]: testing with image"
						+fileName
						+" width:"
						+ansWidth 
						+ " height:"
						+ansHeight);
	}

}
