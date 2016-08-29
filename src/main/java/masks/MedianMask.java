package masks;

import java.util.Arrays;

public class MedianMask extends SquareMask implements FilterMask{
	
	public MedianMask(int size) {
		super(size);
	}

	@Override
	public double filter(double[] values) {
		Arrays.sort(values);
		return (double)values[(values.length-1)/2];
	}
}
