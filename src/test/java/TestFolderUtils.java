import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import border_detectors.active_contours.FileComparator;
import border_detectors.active_contours.FolderUtils;

/*
* @author Fernando Bejarano
*/
public class TestFolderUtils {

	
	@Test
	public void test(){
		
		String imageFile="./src/main/resources/tp4/Frame27.jpeg";
		List<File> images=FolderUtils.getImagesIterator(imageFile);
		assertNotNull(images);
		for(File image:images){
			System.out.println("Image:"+image );
		}
		
	}
	
	@Test
	public void test2(){
		
		String imageFile="./src/main/resources/tp4/video sintetico/a2.jpg";
		List<File> images=FolderUtils.getImagesIterator(imageFile);
		assertNotNull(images);
		for(File image:images){
			System.out.println("Image:"+image );
		}
		
	}
	
	@Test
	public void test3(){
		File file1=new File("./src/main/resources/tp4/video sintetico/a2.jpg");
		File file2=new File("./src/main/resources/tp4/video sintetico/a10.jpg");
		
		FileComparator comparator=new FileComparator();
		
		assertEquals(0,comparator.compare(file1, file1));
		
		assertTrue(comparator.compare(file1, file2)<0);
	}
}
