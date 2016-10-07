import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.IntStream;

import org.junit.Test;

import image.ImageUtils;
import keypoints.SusanMask;

/*
* @author Fernando Bejarano
*/
public class TestSusan {

	@Test
	public void test1(){
		int image[][]=new int[][]{
			{0,0,1,2,3,0,0},
			{0,4,5,6,7,8,0},
			{9,10,11,12,13,14,15},
			{16,17,18,0,19,20,21},
			{22,23,24,25,26,27,28},
			{0,29,30,31,32,33,0},
			{0,0,34,35,36,0,0}
			
		};
		
		int ans[] =SusanMask.getElementsInMask(3, 3, image);
		
		
		assertFalse(IntStream.of(ans).anyMatch(x -> x == 0));
		
		assertTrue(IntStream.of(ans).anyMatch(x -> x == 1));		
		assertTrue(IntStream.of(ans).anyMatch(x -> x == 2));		
		assertTrue(IntStream.of(ans).anyMatch(x -> x == 3));		
		assertTrue(IntStream.of(ans).anyMatch(x -> x == 36));
		
		
	}
	@Test 
	public void test2() throws IOException, InterruptedException{
		String image_src="./src/main/resources/lenaGrande.png";
		Image image = ImageUtils.loadImage(image_src,0,0);
		assertNotNull(image);
		BufferedImage bufferedImage = (BufferedImage) image;
		int ansHeight=bufferedImage.getHeight();
		int ansWidth=bufferedImage.getWidth();
		System.out.println("Image: "+image_src+ " width:"+ansWidth+ " height: "+ ansHeight);
		
		BufferedImage result=SusanMask.getBordersAndCorners((BufferedImage) image);
		assertNotNull(result);
	}
	
}
