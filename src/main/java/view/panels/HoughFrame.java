package view.panels;

import java.awt.FlowLayout;
import java.awt.Point;
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

import border_detectors.Hough;
import masks.KirshMask;
import masks.PrewittMask;
import masks.SobelMask;
import masks.SquareMask;
import masks.UnnamedMask;

public class HoughFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 350;
	private static final int HEIGHT = 220;
	private ImagePanel imagePanel;
	
	private JTextField rho_count = new JTextField("2", 3);
	private JTextField theta_count = new JTextField("2", 3);
	JPanel stepPanelLinear;
	
	private JTextField radius_count = new JTextField("2", 3);
	private JLabel labelX = new JLabel("X =");
	private JLabel labelY = new JLabel("Y =");
	private JTextField centre_x = new JTextField("0", 3);
	private JTextField centre_y = new JTextField("0", 3);
	JPanel stepPanelCircular;
	
	JRadioButton sobel = new JRadioButton("Sobel", true);
	JRadioButton prewitt = new JRadioButton("Prewitt", false);
	JRadioButton kirsh = new JRadioButton("Kirsh", false);
	JRadioButton other = new JRadioButton("Other", false);
	JPanel borderPanel;
	
	JRadioButton linear = new JRadioButton("Lines", true);
	JRadioButton circular = new JRadioButton("Circles", false);
	JRadioButton circularCentered = new JRadioButton("Centred Circles", false);
	JPanel modePanel;
	
	public HoughFrame(ImagePanel target){
		super("Hough Transform");
		
		this.imagePanel = target;
		this.setBounds(0, 400, WIDTH, HEIGHT);
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
		
		circular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(circular.isSelected()){
					stepPanelLinear.setVisible(false);
					stepPanelCircular.setVisible(true);
					centre_x.setVisible(true);
					centre_y.setVisible(true);
					labelX.setVisible(true);
					labelY.setVisible(true);
				}
			}
		});
		
		circularCentered.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(circularCentered.isSelected()){
					stepPanelLinear.setVisible(false);
					stepPanelCircular.setVisible(true);
					centre_x.setVisible(false);
					centre_y.setVisible(false);
					labelX.setVisible(false);
					labelY.setVisible(false);
				}
			}
		});
		
		linear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(linear.isSelected()){
					stepPanelLinear.setVisible(true);
					stepPanelCircular.setVisible(false);
				}
			}
		});
		
		
		ButtonGroup bG2 = new ButtonGroup();
		bG2.add(linear);
		bG2.add(circular);
		bG2.add(circularCentered);
		
		modePanel = new JPanel();
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Target");
		modePanel.setBorder(title2);
		modePanel.setLayout(new FlowLayout());
		modePanel.add(linear);
		modePanel.add(circular);
		modePanel.add(circularCentered);
		
		this.add(modePanel);
		
		stepPanelLinear = new JPanel();
		stepPanelLinear.setLayout(new FlowLayout());
		stepPanelLinear.add(new JLabel("ρ ="));
		stepPanelLinear.add(rho_count);
		stepPanelLinear.add(new JLabel("θ ="));
		stepPanelLinear.add(theta_count);
		stepPanelLinear.setVisible(true);
		this.add(stepPanelLinear);
		
		stepPanelCircular = new JPanel();
		stepPanelCircular.setLayout(new FlowLayout());
		stepPanelCircular.add(new JLabel("R ="));
		stepPanelCircular.add(radius_count);
		stepPanelCircular.add(labelX);
		stepPanelCircular.add(centre_x);
		stepPanelCircular.add(labelY);
		stepPanelCircular.add(centre_y);
		stepPanelCircular.setVisible(false);
		
		this.add(stepPanelCircular);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SquareMask mask = sobel.isSelected() ? new SobelMask() : prewitt.isSelected() ? new PrewittMask(): kirsh.isSelected() ? new KirshMask() : new UnnamedMask();
				
				if(linear.isSelected()){
					int rho = Integer.valueOf(rho_count.getText());
					int theta = Integer.valueOf(theta_count.getText());
					Hough.linearHough(HoughFrame.this.imagePanel, theta , rho, mask);					
				} else if(circular.isSelected()){
					int radius = Integer.valueOf(radius_count.getText());
					Point centre = new Point(Integer.valueOf(centre_x.getText()), Integer.valueOf(centre_y.getText()));
					Hough.circularHough(HoughFrame.this.imagePanel, radius, mask, centre);
				} else if(circularCentered.isSelected()){
					int radius = Integer.valueOf(radius_count.getText());
					Hough.circularHough(HoughFrame.this.imagePanel, radius, mask);
				}
				HoughFrame.this.setVisible(false);
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