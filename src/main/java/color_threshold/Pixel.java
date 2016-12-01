package color_threshold;

import java.awt.Color;

public class Pixel {
	int x;
	int y;
	double red;
	double green;
	double blue;
	
	public Pixel(int x, int y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
	}
	
}
