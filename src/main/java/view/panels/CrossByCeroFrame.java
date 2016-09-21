package view.panels;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import masks.LaplacianInclinationMask;

public class CrossByCeroFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private CrossByCeroSlider slider;
	
	public CrossByCeroFrame(ImagePanel imagePanel, double[][] byRowSums, double[][] byColSums, double min, double max){
		super("Threshold");
		this.slider = new CrossByCeroSlider(imagePanel, byRowSums, byColSums, min, max);
		this.setBounds(0, 500, 600, 150);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.add(slider);
		this.setAlwaysOnTop(true);
	}

	class CrossByCeroSlider extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1L;
		private int pixel_min;
		private int pixel_max;
		static final int PIXEL_INIT = 0;
		ImagePanel panel;
		double[][] originalImage;
		double[][] byRowSums;
		double[][] byColSums;
		BufferedImage originalBufferedImage;

		public CrossByCeroSlider(ImagePanel imagePanel,double[][] byRowSums, double[][] byColSums, double min, double max) {
			panel = imagePanel;
			this.byColSums = byColSums;
			this.byRowSums = byRowSums;
			originalBufferedImage = imagePanel.getImage();
			this.pixel_min = (int) Math.round(min);
			this.pixel_max = (int) Math.round(max);
			
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JLabel sliderLabel = new JLabel("Threshold value", JLabel.CENTER);
			sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			JSlider pixelLimitValue = new JSlider(JSlider.HORIZONTAL, pixel_min, pixel_max, PIXEL_INIT);

			pixelLimitValue.addChangeListener(this);

			pixelLimitValue.setMajorTickSpacing((int) ((max-min)/10));
			pixelLimitValue.setMinorTickSpacing((int) ((max-min)/100));
			pixelLimitValue.setPaintTicks(true);
			pixelLimitValue.setPaintLabels(true);
			pixelLimitValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
			Font font = new Font("Serif", Font.ITALIC, 10);
			pixelLimitValue.setFont(font);

			// Put everything together.
			add(sliderLabel);
			add(pixelLimitValue);
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			JButton undo = new JButton("Undo");
			undo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					panel.setImage(originalBufferedImage);
					CrossByCeroFrame.this.setVisible(false);
				}
			});
			add(undo);
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int pixelValue = (int) source.getValue();
				panel.setImage(LaplacianInclinationMask.threshold(pixelValue, byRowSums, byColSums, originalBufferedImage));
			}
		}
	}
}
