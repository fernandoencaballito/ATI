

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
	public void testRAW() throws IOException {
		genericTest("./src/main/resources/LENAX.RAW");

	}

	@Test
	public void testPGM() throws IOException {
		genericTest("./src/main/resources/TEST.PGM");
	}

	@Test
	public void testPPM() throws IOException {
		genericTest("./src/main/resources/boxes_1.ppm");
	}

	@Test
	public void testBMP() throws IOException {
		genericTest("./src/main/resources/JackSig.bmp");

	}

	private void genericTest(String fileName) throws IOException {
		Image image = ImageUtils.loadImage(fileName);
		assertNotNull(image);
		BufferedImage bufferedImage = (BufferedImage) image;
		assertTrue(bufferedImage.getHeight() > 0);
		assertTrue(bufferedImage.getWidth() > 0);
	}

}
