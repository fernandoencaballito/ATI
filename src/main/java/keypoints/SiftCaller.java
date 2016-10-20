package keypoints;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.process.ImageProcessor;
import image.ImageUtils;
import mpicbg.ij.SIFT;
import mpicbg.imagefeatures.Feature;
import mpicbg.imagefeatures.FloatArray2DSIFT;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class SiftCaller {
	final static private FloatArray2DSIFT.Param p = new FloatArray2DSIFT.Param();
	final static double DELTA = 0.4;
	private static int circleSize = 3;
	private static String containsMsg = "The image contains objects from the other";
	private static String equalsMsg = "The images are the same";
	private static String differentMsg = "The images are different";
	/**
	 * Draw a rotated square around a center point having size and orientation
	 * 
	 * @param o
	 *            center point
	 * @param scale
	 *            size
	 * @param orient
	 *            orientation
	 */
	static void drawSquare(ImageProcessor ip, double[] o, double scale, double orient) {
		scale /= 2;

		double sin = Math.sin(orient);
		double cos = Math.cos(orient);

		int[] x = new int[6];
		int[] y = new int[6];

		x[0] = (int) (o[0] + (sin - cos) * scale);
		y[0] = (int) (o[1] - (sin + cos) * scale);

		x[1] = (int) o[0];
		y[1] = (int) o[1];

		x[2] = (int) (o[0] + (sin + cos) * scale);
		y[2] = (int) (o[1] + (sin - cos) * scale);
		x[3] = (int) (o[0] - (sin - cos) * scale);
		y[3] = (int) (o[1] + (sin + cos) * scale);
		x[4] = (int) (o[0] - (sin + cos) * scale);
		y[4] = (int) (o[1] - (sin - cos) * scale);
		x[5] = x[0];
		y[5] = y[0];

		ip.drawPolygon(new Polygon(x, y, x.length));
	}

	// @Override //Java 6 fixes this
	public void run() throws IOException, InterruptedException {
		String fileName = "./src/main/resources/TEST.png";
		String fileName2 = "./src/main/resources/tp3/TEST_90grados.png";
		BufferedImage img = (BufferedImage) ImageUtils.loadImage(fileName, 0, 0);
		// List<Feature> f1=getFeatures(img);
		BufferedImage img2 = (BufferedImage) ImageUtils.loadImage(fileName2, 0, 0);
		compareKeypoints(img, img2);

	}

	public static List<Feature> getFeatures(BufferedImage img) {
		List<Feature> fs = new ArrayList<Feature>();

		final ImagePlus imp = new ImagePlus("", img);
		// if ( imp == null ) { System.err.println( "There are no images open"
		// ); return; }

		final GenericDialog gd = new GenericDialog("Test SIFT");

		SIFT.addFields(gd, p);
		// gd.showDialog();
		// if (gd.wasCanceled())
		// return;
		SIFT.readFields(gd, p);

		final ImageProcessor ip1 = imp.getProcessor().convertToFloat();
		final ImageProcessor ip2 = imp.getProcessor().duplicate().convertToRGB();

		final SIFT ijSift = new SIFT(new FloatArray2DSIFT(p));
		fs.clear();

		final long start_time = System.currentTimeMillis();
		System.out.print("processing SIFT ...");
		ijSift.extractFeatures(ip1, fs);
		System.out.println(" took " + (System.currentTimeMillis() - start_time) + "ms");

		System.out.println(fs.size() + " features identified and processed");

		// ip2.setLineWidth( 1 );
		// ip2.setColor( Color.red );
		// for ( final Feature f : fs )
		// drawSquare( ip2, new double[]{ f.location[ 0 ], f.location[ 1 ] },
		// p.fdSize * 4.0 * ( double )f.scale, ( double )f.orientation );
		//
		// new ImagePlus( imp.getTitle() + " Features ", ip2 ).show();

		return fs;
	}

	public static void compareKeypoints(BufferedImage img1, BufferedImage img2) {
		List<Feature> keyPoints1 = getFeatures(img1);
		List<Feature> keyPoints2 = getFeatures(img2);
		List<Feature> matchFrom1 = new ArrayList<Feature>();// keypoint en la
															// primera imagen
															// que coincidio
		List<Feature> matchFrom2 = new ArrayList<Feature>();// keypoint en la
															// segunda que
															// coincidio

		for (Feature keypoint1 : keyPoints1) {

			double minDis = Double.MAX_VALUE;
			Feature minDistFeature = null;
			for (Feature keypoint2 : keyPoints2) {

				double dist = keypoint1.descriptorDistance(keypoint2);

				if (dist < minDis) {
					minDis = dist;
					minDistFeature = keypoint2;
				}

			}
			//System.out.println("Min dist: " + minDis);
			if (minDis < DELTA) {
				matchFrom2.add(minDistFeature);
				matchFrom1.add(keypoint1);
			}

		}

		System.out.println("match1 size" + matchFrom1.size() + ". Match2 size:" + matchFrom2.size());

		double percentage = ((double) matchFrom1.size())
				/ ((keyPoints1.size() < keyPoints2.size()) ? keyPoints2.size() : keyPoints1.size());

	
		// String resultMsg=(percentage<0.90)?containsMsg:equalsMsg;
		String resultMsg;
		if (percentage >= 0.8) {
			resultMsg = equalsMsg;
		} else if (percentage >= 0.5) {
			resultMsg = containsMsg;
		} else {
			resultMsg = differentMsg;
		}

		String msg = String.format("Matched:%d keypoints.\n Percentage match:%f %%. \n => %s", matchFrom1.size(),
				percentage * 100, resultMsg);
		
		markKeypoints(matchFrom1, img1);

		markKeypoints(matchFrom2, img2);

		System.out.println(msg);

		
		//JOptionPane.showMessageDialog(null, msg);

	}

	private static void markKeypoints(List<Feature> keyPoints1, BufferedImage img) {
		Graphics graphics = img.createGraphics();
		graphics.setColor(Color.green);
		double[] location;
		double col;
		double row;
		for (Feature keypoint : keyPoints1) {
			location = keypoint.location;
			col = location[0];
			row = location[1];
			graphics.fillOval((int) (col - (circleSize - 1) / 2), (int) (row - (circleSize - 1) / 2), circleSize,
					circleSize);

		}

	}

	public static void compareKeypoints(ImagePanel img1, ImagePanel img2) {
		BufferedImage copy1 = copyBufferedImage(img1.getImage());
		BufferedImage copy2 = copyBufferedImage(img2.getImage());

		compareKeypoints(copy1, copy2);

		img1.setImage(copy1);
		img2.setImage(copy2);
	}

	private static BufferedImage copyBufferedImage(BufferedImage buffer) {
		int width = buffer.getWidth();
		int height = buffer.getHeight();

		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// se copia imagen original
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color = buffer.getRGB(i, j);
				result.setRGB(i, j, color);
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		SiftCaller test = new SiftCaller();
		test.run();
	}
}
