package detectors;

public class Lorentziano implements Detector {

	public Lorentziano() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double g(double x, double sigma) {
		return 1.0/(1+(x*x)/(sigma*sigma));
	}

}
