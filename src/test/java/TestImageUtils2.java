import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import image.ImageUtils;
import junitx.framework.FileAssert;

/*Se comprueba la escritura
* @author Fernando Bejarano
*/
public class TestImageUtils2 {

	@Before
	@After
	public void testCleanUp() {
		File varTmpDir = new File("./auxFile.bmp");
		boolean exists = varTmpDir.exists();
		assertFalse(exists);
	}
	
	
private void genericTest(String fileName, int width, int height) throws Exception {
		String auxFileName=FilenameUtils.getFullPath(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		extension = extension.toLowerCase();
		auxFileName+="test"
					+ FilenameUtils.getBaseName(fileName)
					+"."+extension;
		
		boolean exists =(new File(auxFileName)).exists();
		//no debe haberse creado previamente el archivo auxiliar
		assertFalse(exists);
		
		//se carga una imagen
		Image image = ImageUtils.loadImage(fileName,width,height);
		assertNotNull(image);
		BufferedImage bufferedImage = (BufferedImage) image;
		
		
		
		
		
		//se la graba desde el buffer a un archivo auxiliar
		
		boolean status=ImageUtils.writeImage(bufferedImage, auxFileName);
		
		assertTrue(status);
		//se comprueba que se este guardado el arch aux
		exists =(new File(auxFileName)).exists();
		assertTrue(exists);
		
		//se  comprueban que sean iguales
		File auxFile= new File(auxFileName);
		if(extension.equals("raw"))
				FileAssert.assertBinaryEquals(new File(fileName),
							auxFile);
		//se borra el auxiliar
		auxFile.delete();
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
	
}
