package view.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import border_detectors.Canny;
import border_detectors.Canny.NeighbourType;
import masks.KirshMask;
import masks.PrewittMask;
import masks.SobelMask;
import masks.SquareMask;
import masks.UnnamedMask;

public class CannyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 250;
	private static final int HEIGHT = 160;
	private ImagePanel imagePanel;
	private JTextField size = new JTextField("0", 3);
	JPanel maskPanel;
	private JTextField sigma = new JTextField("0", 3);
	JRadioButton sobel = new JRadioButton("Sobel", true);
	JRadioButton prewitt = new JRadioButton("Prewitt", false);
	JRadioButton kirsh = new JRadioButton("Kirsh", false);
	JRadioButton other = new JRadioButton("Other", false);
	FrameMode mode;
	
	JPanel borderPanel;

	public enum FrameMode {
		CANNY("Canny"), NON_MAXIMUM_SUPRESSION("Non Maximum Supression");
		
		public String name;
		
		FrameMode(String s){
			this.name = s;
		}
	}
	
	public CannyFrame(ImagePanel target, FrameMode mode){
		super();
		this.setName(mode.name);
		this.mode = mode;
		
		this.imagePanel = target;
		this.setBounds(0, 500, WIDTH, HEIGHT);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		ButtonGroup bG1 = new ButtonGroup();
		bG1.add(sobel);
		bG1.add(prewitt);
		bG1.add(kirsh);
		bG1.add(other);
		
		borderPanel = new JPanel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Border Detector");
		borderPanel.setBorder(title);
		borderPanel.setLayout(new FlowLayout());
		borderPanel.add(sobel);
		borderPanel.add(prewitt);
		borderPanel.add(kirsh);
		borderPanel.add(other);
		
		this.add(borderPanel);
		
		
		maskPanel = new JPanel();
		maskPanel.setLayout(new FlowLayout());
		maskPanel.add(new JLabel("Size ="));
		maskPanel.add(size);
		maskPanel.add(new JLabel("σ ="));
		maskPanel.add(sigma);
		
		this.add(maskPanel);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int sizeValue = Integer.valueOf(size.getText());
				double sigmaValue = Double.valueOf(sigma.getText());
					
				SquareMask mask = sobel.isSelected() ? new SobelMask() : prewitt.isSelected() ? new PrewittMask(): kirsh.isSelected() ? new KirshMask() : new UnnamedMask();
				
				if(CannyFrame.this.mode == FrameMode.CANNY)
					CannyFrame.this.imagePanel.setImage(Canny.cannyBorderDetector(imagePanel.getImage(), sizeValue, sigmaValue, mask, NeighbourType.FOUR_NEIGHBOURS));
				else
					CannyFrame.this.imagePanel.setImage(Canny.nonMaximumSupression(imagePanel.getImage(), mask));
			}
		});
		this.add(confirmButton);
		
		this.setAlwaysOnTop(true);
		this.setVisible(false);
	}

	public void setImagePanel(ImagePanel imagePanel){
		this.imagePanel = imagePanel;
	}

}
