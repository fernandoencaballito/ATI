package masks;

public class GaussianMask extends SquareMask {

	public GaussianMask(int size, double mu, double sigma) {
		super(size);
		int halfSize = (size-1)/2;
		for (int i = -halfSize; i <= halfSize; i++) {
			for (int j = -halfSize; j <= halfSize; j++) {
				double normalValue = (1/(2*Math.PI*sigma*sigma)) * Math.exp((-i*i-j*j)/(sigma*sigma));
				set(i+halfSize, j+halfSize, normalValue);
			}
		}
		double accum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				accum += get(i, j);
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				set(i, j, get(i, j)/accum);
				//System.out.println(i + "," + j + ": " + get(i, j));
			}
		}
		
	}
}
