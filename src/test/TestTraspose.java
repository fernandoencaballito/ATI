import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import image.CustomArrayUtils;

import static org.junit.Assert.*;
/*
* @author Fernando Bejarano
*/
public class TestTraspose {

	@Test
	public void test1() {
		int[] intArray = { 0, 1, 2, 3, 4, 5, 6, 7 };

		Integer[] array = ArrayUtils.toObject(intArray);

		int[] intArrayExpected = { 0, 2, 4, 6, 1, 3, 5, 7 };

		Integer[] arrayExpected = ArrayUtils.toObject(intArrayExpected);
		
		
		Integer[]ans=CustomArrayUtils.traspose(array, 4, 2,Integer.class);
		
		assertArrayEquals(arrayExpected, ans);
		
	}
}
