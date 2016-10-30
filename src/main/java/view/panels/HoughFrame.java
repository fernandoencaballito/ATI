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

import border_detectors.Hough;
import masks.KirshMask;
import masks.PrewittMask;
import masks.SobelMask;
import masks.SquareMask;
import masks.UnnamedMask;

public class HoughFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 350;
	private static final int HEIGHT = 160;
	private ImagePanel imagePanel;
	private JTextField rho_count = new JTextField("2", 3);
	private JTextField theta_count = new JTextField("2", 3);
	JPanel stepPanel;
	JRadioButton sobel = new JRadioButton("Sobel", true);
	JRadioButton prewitt = new JRadioButton("Prewitt", false);
	JRadioButton kirsh = new JRadioButton("Kirsh", false);
	JRadioButton other = new JRadioButton("Other", false);
	
	JPanel borderPanel;
	
	public HoughFrame(ImagePanel target){
		super("Hough Transform");
		
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
		
		stepPanel = new JPanel();
		stepPanel.setLayout(new FlowLayout());
		stepPanel.add(new JLabel("ρ ="));
		stepPanel.add(rho_count);
		stepPanel.add(new JLabel("θ ="));
		stepPanel.add(theta_count);
		
		this.add(stepPanel);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rho = Integer.valueOf(rho_count.getText());
				int theta = Integer.valueOf(theta_count.getText());
					
				SquareMask mask = sobel.isSelected() ? new SobelMask() : prewitt.isSelected() ? new PrewittMask(): kirsh.isSelected() ? new KirshMask() : new UnnamedMask();
				
				Hough.hough(HoughFrame.this.imagePanel, theta , rho, mask);
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

