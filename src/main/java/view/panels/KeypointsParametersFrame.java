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

import keypoints.Keypoints;
import masks.PrewittMask;
import masks.SobelMask;
import masks.SquareMask;

public class KeypointsParametersFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField size = new JTextField("0", 3);
	private JTextField standardDeviation = new JTextField("0", 3);
	JPanel gaussianPanel;
	JRadioButton sobel = new JRadioButton("Sobel", true);
	JRadioButton prewitt = new JRadioButton("Prewitt", false);
	private ImagePanel imagePanel;
	JPanel typePanel;
	JPanel detectorPanel;
	SquareMask mask = new SobelMask();
	
	public KeypointsParametersFrame(ImagePanel imagePanel) {
		super("Choose Keypoints Detector Parameters");
		this.imagePanel = imagePanel;
		this.setBounds(0, 500, 250, 220);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		sobel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sobel.isSelected()){
					mask = new SobelMask();
				}
			}
		});
		
		prewitt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(prewitt.isSelected()){
					mask = new PrewittMask();
				}
			}
		});
		
		
		ButtonGroup bG1 = new ButtonGroup();
		bG1.add(sobel);
		bG1.add(prewitt);
		
		typePanel = new JPanel();
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Border Mask");
		typePanel.setBorder(title);
		typePanel.setLayout(new FlowLayout());
		typePanel.add(sobel);
		typePanel.add(prewitt);
		
		this.add(typePanel);
				
		gaussianPanel = new JPanel();
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Gaussian Filter");
		gaussianPanel.setBorder(title2);
		gaussianPanel.setLayout(new FlowLayout());
		gaussianPanel.add(new JLabel("SIZE ="));
		gaussianPanel.add(size);
		gaussianPanel.add(new JLabel(" σ ="));
		gaussianPanel.add(standardDeviation);
		gaussianPanel.setVisible(true);
		
		this.add(gaussianPanel);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int sizeValue = Integer.valueOf(size.getText());
				if((sizeValue%2)==0){
					System.out.println("El tamaño de la mascara tiene que ser impar");
					return;
				}
					
				double standardDeviationValue = Double.valueOf(standardDeviation.getText());
				Keypoints.harris(imagePanel, mask, sizeValue, standardDeviationValue, imagePanel.getImage());
				KeypointsParametersFrame.this.setVisible(false);
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
