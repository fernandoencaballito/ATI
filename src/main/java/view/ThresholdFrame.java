package view;

import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ThresholdFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ThresholdSlider slider;
	
	public ThresholdFrame(ImagePanel imagePanel){
		super("Threshold");
		this.slider = new ThresholdSlider(imagePanel);
		this.setBounds(0, 500, 600, 120);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.add(slider);
		this.setAlwaysOnTop(true);
		
	}

	class ThresholdSlider extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1L;
		static final int PIXEL_MIN = 0;
		static final int PIXEL_MAX = 255;
		static final int PIXEL_INIT = 50;
		ImagePanel panel;
		BufferedImage originalImage;

		public ThresholdSlider(ImagePanel imagePanel) {
			panel = imagePanel;
			originalImage = panel.getImage();
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JLabel sliderLabel = new JLabel("Threshold value", JLabel.CENTER);
			sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			JSlider pixelLimitValue = new JSlider(JSlider.HORIZONTAL, PIXEL_MIN, PIXEL_MAX, PIXEL_INIT);

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
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int pixelValue = (int) source.getValue();
				panel.setImage(ImageEffects.threshold(originalImage, pixelValue));
			}
		}
	}
}
