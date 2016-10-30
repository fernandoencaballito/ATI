package border_detectors;

import java.awt.Point;

public class CircularHoughThresholdData extends HoughThresholdData {

	int[] counts;
	Point centre;
	double radius_min;
	double radius_max;
	double radius_step;
	int cant_radius;
	
	public CircularHoughThresholdData(int[] counts, Point centre, double radius_min, double radius_max,
			double radius_step, int cant_radius) {
		super();
		this.counts = counts;
		this.centre = centre;
		this.radius_min = radius_min;
		this.radius_max = radius_max;
		this.radius_step = radius_step;
		this.cant_radius = cant_radius;
	}

	@Override
	public boolean isCircular() {
		return true;
	}

}
