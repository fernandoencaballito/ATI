package detectors;

public class Leclerc implements Detector {

	public Leclerc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double g(double x, double sigma) {
		return Math.exp((-x*x)/(sigma*sigma));
	}

}
