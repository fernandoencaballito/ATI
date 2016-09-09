package masks;

public interface FilterMask {

	public double filter(double[] values);
	
	public double[][] filterImage(double[][] image);
}
