package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class MainFrame extends JFrame {

	//valores iniciales
	private int window_width = 950;
	private int window_height = 400;
	//
	private static final String FRAME_TITLE="Image Processor - Developed by Fernando Bejarano and Lucas Soncini";
	
	private Container contentPane;

	public MainFrame() throws HeadlessException {
		super(FRAME_TITLE);
		this.setBounds(0, 0, window_width, window_height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		// area principal

		contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.setBackground(Color.black);

		String initialFile="./src/main/resources/boxes_1.ppm";
		ImageGeneralPanel originalImagePanel = new ImageGeneralPanel("Original Image"
												, initialFile);
		
		

		ImageGeneralPanel modifiedImagePanel = new ImageGeneralPanel("Modified Image"
												, initialFile);
		JPanel middlePanel = new MiddlePanel(originalImagePanel,modifiedImagePanel);
		
		this.setJMenuBar(new MyMenuBar(originalImagePanel,modifiedImagePanel,this));
		contentPane.add(originalImagePanel);
		contentPane.add(middlePanel);
		contentPane.add(modifiedImagePanel);
		
		window_width= originalImagePanel.getCurrentWidth()*2+50;
		window_height = originalImagePanel.getCurrentHeight();
		
		System.out.println("windows_width"+window_width);
		System.out.println("windows_height"+window_height);
		
		this.setBounds(0, 0, window_width, window_height);
		this.setVisible(true);
	}

}
