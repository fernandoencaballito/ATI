package noise;

public class GaussianGenerator implements RandomNumberGenerator {
	
	private double std_deviation, mean_value;
	
	public GaussianGenerator(double std_deviation, double mean_value) {
		
		this.std_deviation=std_deviation;
		this.mean_value=mean_value;
	}
	
	@Override
	public boolean isAdditive() {
		// TODO Auto-generated method stub
		return true;
	}

	// genera un par de números con distribución gausiana
	//Minimo valor:
	public static double[] generateRandomNumber(double std_deviation, double mean_value) {
		double x1,x2;
		 
		do{
			x1 = Math.random();
		}while(!(x1>0.0));
		
		do{
			x2 = Math.random();
		}while(! (x2>0.0));
		
		 
		 
		double y1_std_normal = Math.sqrt(-2.0 * Math.log(x1)) * Math.cos(2.0 * Math.PI * x2);
		double y2_std_normal = Math.sqrt(-2.0 * Math.log(x1)) * Math.sin(2.0 * Math.PI * x2);

		double y1_normal = mean_value + std_deviation * y1_std_normal;
		double y2_normal = mean_value + std_deviation * y2_std_normal;

		double[] ans = { y1_normal, y2_normal };
		//System.out.println(Arrays.toString(ans));
		return ans;
	}

	



	@Override
	public double generate() {
		return generateRandomNumber(this.std_deviation, this.mean_value)[0]; 
	}
}
