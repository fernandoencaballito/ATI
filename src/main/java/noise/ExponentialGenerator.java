package noise;
/*
* @author Fernando Bejarano
*/
public class ExponentialGenerator implements RandomNumberGenerator{

	private double lambda;
	
	
	public ExponentialGenerator(double lambda) {
		this.lambda=lambda;
				
	}
	
	public static double generateRandomNumber(double lambda){
		double x=Math.random();
		return - Math.log(x)/lambda;
	}

	@Override
	public double generate() {
		return generateRandomNumber(this.lambda);
	}

	@Override
	public boolean isAdditive() {
		
		return false;
	}
}
