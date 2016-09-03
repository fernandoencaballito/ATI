package noise;

public class Rayleigh implements RandomNumberGenerator{

	private double xi;
	
	public Rayleigh(double xi) {
		this.xi=xi;
	}
	
	public static double generateRandomNumber(double xi){
		double x=Math.random();
		return xi * Math.sqrt(-2.0* Math.log(1.0-x));
	}

	@Override
	public double generate() {
		return generateRandomNumber(this.xi);
	}
	@Override
	public boolean isAdditive() {
		// TODO Auto-generated method stub
		return false;
	}
}
