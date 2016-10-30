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

import border_detectors.Hough;
import border_detectors.HoughThresholdData;

public class HoughThresholdFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private HoughThresholdSlider slider;
	
	public HoughThresholdFrame(ImagePanel imagePanel, HoughThresholdData data){
		super("Threshold percentage");
		this.slider = new HoughThresholdSlider(imagePanel, data);
		this.setBounds(0, 500, 600, 150);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.add(slider);
		this.setAlwaysOnTop(true);
	}

	class HoughThresholdSlider extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1L;
		private final int PERCENTAGE_MIN = 0;
		private final int PERCENTAGE_MAX = 100;
		static final int PIXEL_INIT = 0;
		double[][] image;
		ImagePanel panel;
		HoughThresholdData data;
		BufferedImage originalBufferedImage;

		public HoughThresholdSlider(ImagePanel imagePanel, HoughThresholdData data){
			panel = imagePanel;
			originalBufferedImage = imagePanel.getImage();
			this.data = data;
			
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JLabel sliderLabel = new JLabel("Threshold percentage", JLabel.CENTER);
			sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			JSlider pixelLimitValue = new JSlider(JSlider.HORIZONTAL, PERCENTAGE_MIN, PERCENTAGE_MAX, PIXEL_INIT);

			pixelLimitValue.addChangeListener(this);

			pixelLimitValue.setMajorTickSpacing(10);
			pixelLimitValue.setMinorTickSpacing(1);
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
					HoughThresholdFrame.this.setVisible(false);
				}
			});
			add(undo);
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int percentageValue = (int) source.getValue();
				panel.setImage(Hough.threshold(percentageValue, originalBufferedImage, data));
			}
		}
	}

}
