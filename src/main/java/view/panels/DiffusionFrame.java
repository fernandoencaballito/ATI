package view.panels;

import java.awt.FlowLayout;
import java.awt.Rectangle;
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

import detectors.Detector;
import detectors.Leclerc;
import detectors.Lorentziano;
import view.ImageEffects;

public class DiffusionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 250;
	private static final int HEIGHT = 160;
	private ImagePanel imagePanel;
	private JTextField t = new JTextField("0", 3);
	JPanel tPanel;
	private JTextField sigma = new JTextField("0", 3);
	JRadioButton isotropic = new JRadioButton("Isotropic", true);
	JRadioButton anisotropic = new JRadioButton("Anisotropic", false);
	JPanel typePanel;
	JPanel detectorPanel;
	JRadioButton leclerc = new JRadioButton("Leclerc", true);
	JRadioButton lorentziano = new JRadioButton("Lorentziano", false);

	
	public DiffusionFrame(ImagePanel target){
		super("Diffusion");
		this.imagePanel = target;
		this.setBounds(0, 500, WIDTH, HEIGHT);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		isotropic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isotropic.isSelected()){
					detectorPanel.setVisible(false);
					Rectangle currentBounds = DiffusionFrame.this.getBounds();
					DiffusionFrame.this.setBounds(currentBounds.x, currentBounds.y, WIDTH, HEIGHT);				}
			}
		});
		
		anisotropic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(anisotropic.isSelected()){
					detectorPanel.setVisible(true);
					Rectangle currentBounds = DiffusionFrame.this.getBounds();
					DiffusionFrame.this.setBounds(currentBounds.x, currentBounds.y, WIDTH, HEIGHT+60);
				}
			}
		});
		
		
		ButtonGroup bG1 = new ButtonGroup();
		bG1.add(isotropic);
		bG1.add(anisotropic);
		
		typePanel = new JPanel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Type of Diffusion");
		typePanel.setBorder(title);
		typePanel.setLayout(new FlowLayout());
		typePanel.add(isotropic);
		typePanel.add(anisotropic);
		
		this.add(typePanel);
		
		
		tPanel = new JPanel();
		tPanel.setLayout(new FlowLayout());
		tPanel.add(new JLabel("Tmax ="));
		tPanel.add(t);
		tPanel.add(new JLabel("σ ="));
		tPanel.add(sigma);
		
		this.add(tPanel);
		
		ButtonGroup bG2 = new ButtonGroup();
		bG2.add(leclerc);
		bG2.add(lorentziano);
		
		detectorPanel = new JPanel();
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Detector");
		detectorPanel.setBorder(title2);
		detectorPanel.setLayout(new FlowLayout());
		detectorPanel.add(leclerc);
		detectorPanel.add(lorentziano);
		detectorPanel.setVisible(false);
		this.add(detectorPanel);
		
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int tValue = Integer.valueOf(t.getText());
				int sigmaValue = Integer.valueOf(sigma.getText());
					
				Detector detector = lorentziano.isSelected() ? new Lorentziano() : new Leclerc();
				Boolean isIsotropic = DiffusionFrame.this.isotropic.isSelected();
				
				DiffusionFrame.this.imagePanel.setImage(ImageEffects.ansotropicDiffusion(imagePanel.getImage(), tValue, sigmaValue, detector, isIsotropic));
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
