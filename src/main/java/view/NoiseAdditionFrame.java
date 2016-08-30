package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import noise.ExponentialGenerator;
import noise.GaussianGenerator;
import noise.Noise;
import noise.RandomNumberGenerator;
import noise.Rayleigh;

public class NoiseAdditionFrame extends JFrame {
	

	private static final long serialVersionUID = 1L;
	public NoiseType noiseType;
	public NoiseSlider slider;
	private int percentage = 0;
	private JTextField exponentialParameter = new JTextField("0", 3);
	private JTextField media = new JTextField("0", 3);
	private JTextField standardDeviation = new JTextField("0", 3);
	private JTextField rayleighParameter = new JTextField("0", 3);
	JPanel gaussianPanel;
	JPanel exponentialPanel;
	JPanel rayleighPanel;
	private ImagePanel imagePanel;
	
	public NoiseAdditionFrame(NoiseType type, ImagePanel imagePanel){
		noiseType = type;
		slider = new NoiseSlider(this);
		this.setBounds(0, 500, 500, 120);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new FlowLayout());//new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(slider);
		
		gaussianPanel = new JPanel();
		gaussianPanel.setLayout(new FlowLayout());
		gaussianPanel.add(new JLabel("μ ="));
		gaussianPanel.add(media);
		gaussianPanel.add(new JLabel(" σ ="));
		gaussianPanel.add(standardDeviation);
		gaussianPanel.setVisible(false);
		
		this.add(gaussianPanel);
		
		rayleighPanel = new JPanel();
		rayleighPanel.setLayout(new FlowLayout());
		rayleighPanel.add(new JLabel("ξ ="));
		rayleighPanel.add(rayleighParameter);
		rayleighPanel.setVisible(false);

		this.add(rayleighPanel);
		
		exponentialPanel = new JPanel();
		exponentialPanel.setLayout(new FlowLayout());
		exponentialPanel.add(new JLabel("λ ="));
		exponentialPanel.add(exponentialParameter);
		exponentialPanel.setVisible(false);

		this.add(exponentialPanel);
		
		this.changeNoiseType(noiseType);
		
		JButton confirmButton = new JButton("OK");
		ActionListener listener=new ActionListener() {
			JFrame parentFrame=null;
			
			private ActionListener init(JFrame parent){
				this.parentFrame=parent;
				return this;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double exponentialValue = Double.valueOf(exponentialParameter.getText());
				double mediaValue = Double.valueOf(media.getText());
				double standardDeviationValue = Double.valueOf(standardDeviation.getText());
				double rayleighValue = Double.valueOf(rayleighParameter.getText());
				
				BufferedImage image=imagePanel.getImage();
				RandomNumberGenerator generator=null;
				
				switch (noiseType) {
				case GAUSSIAN:
					//aca llama al metodo de ruido gaussiano con los parametros porcentaje = percentage, mu: mediaValue y sigma = standardDeviationValue 
					System.out.println("Aplicando ruido Gaussiano con porcentaje = " + percentage + " mu = " + mediaValue + " sigma = "+ standardDeviationValue);
					generator=new GaussianGenerator(standardDeviationValue, mediaValue);
					
					
					break;
				case RAYLEIGH:
					//aca llama al metodo de ruido de rayleigh con los parametros porcentaje = percentage, xi = rayleighValue
					System.out.println("Aplicando ruido Rayleigh con porcentaje = " + percentage + " param = " + rayleighValue);
					generator=new Rayleigh(rayleighValue);
					
					
					
					break;
				case EXPONENTIAL:
					//aca llama al metodo de ruido exponencial con los parametros porcentaje = percentage, lambda = exponentialValue
					System.out.println("Aplicando ruido Exponencial con porcentaje = " + percentage + "param = " + exponentialValue);
					generator=new ExponentialGenerator(exponentialValue);
					
					break;
				default:
					
					break;
				}
				
				
				BufferedImage modified=Noise.generateNoise(image, percentage/100.0, generator);
				imagePanel.setImage(modified);
				imagePanel.repaint();
				
			}
		}.init(this);
		
		
		confirmButton.addActionListener(listener);
		this.add(confirmButton);
		this.imagePanel=imagePanel;
		
		this.setAlwaysOnTop(true);
		this.setVisible(false);
	}
	
	public void changeNoiseType(NoiseType type){
		noiseType = type;
		String title = "";
		
		switch (noiseType) {
		case GAUSSIAN:
			exponentialPanel.setVisible(false);
			rayleighPanel.setVisible(false);
			gaussianPanel.setVisible(true);
			title="Gaussian noise";
			break;
		case RAYLEIGH:
			exponentialPanel.setVisible(false);
			gaussianPanel.setVisible(false);
			rayleighPanel.setVisible(true);
			title="Rayleigh noise";
			break;
		case EXPONENTIAL:
			gaussianPanel.setVisible(false);
			rayleighPanel.setVisible(false);
			exponentialPanel.setVisible(true);
			title="Exponential noise";
			break;
		default:
			break;
		}
		this.setTitle(title);
	}
	
	
	
	class NoiseSlider extends JPanel implements ChangeListener {

		private static final long serialVersionUID = 1L;
		static final int MIN = 0;
		static final int MAX = 100;
		static final int INIT = 0;
		NoiseAdditionFrame parent;

		public NoiseSlider(NoiseAdditionFrame parent) {
			this.parent = parent;
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			JLabel noiseLabel = new JLabel("Noise percentage", JLabel.CENTER);
			noiseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			JSlider noisePercentage = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);

			noisePercentage.addChangeListener(this);

			noisePercentage.setMajorTickSpacing(10);
			noisePercentage.setMinorTickSpacing(1);
			noisePercentage.setPaintTicks(true);
			noisePercentage.setPaintLabels(true);
			noisePercentage.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
			Font font = new Font("Serif", Font.ITALIC, 10);
			noisePercentage.setFont(font);

			// Put everything together.
			add(noiseLabel);
			add(noisePercentage);
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int percentage = (int) source.getValue();
				parent.percentage = percentage;
			}
		}
	}
	
	
	public enum NoiseType {
		GAUSSIAN,
		RAYLEIGH,
		EXPONENTIAL;
	}
}
