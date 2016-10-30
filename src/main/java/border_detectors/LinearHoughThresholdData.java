package border_detectors;

public class LinearHoughThresholdData extends HoughThresholdData {

	int[][] counts;
	double rho_step;
	double theta_step;
	int cant_theta;
	int cant_rho;
	double theta_min;
	double theta_max;
	double rho_min;
	double rho_max;	
	
	public LinearHoughThresholdData(int[][] counts, double rho_step, double theta_step, int cant_theta, int cant_rho,
			double theta_min, double theta_max, double rho_min, double rho_max) {
		super();
		this.counts = counts;
		this.rho_step = rho_step;
		this.theta_step = theta_step;
		this.cant_theta = cant_theta;
		this.cant_rho = cant_rho;
		this.theta_min = theta_min;
		this.theta_max = theta_max;
		this.rho_min = rho_min;
		this.rho_max = rho_max;
	}	
	
	
	@Override
	public boolean isCircular() {
		return false;
	}

}
