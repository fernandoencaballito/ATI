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

	private static final int WINDOW_WIDTH = 950;
	private static final int WINDOW_HEIGHT = 400;

	private Container contentPane;

	public MainFrame() throws HeadlessException {
		super("Image Processor - Developed by Fernando Bejarano and Lucas Soncini");
		this.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(new MyMenuBar());

		// area principal

		contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.setBackground(Color.black);

		JPanel originalImagePanel = new JPanel();
		JLabel originalLabel = new JLabel("Original Image");
		originalLabel.setAlignmentX(CENTER_ALIGNMENT);

		ImagePanel imagePanel = new ImagePanel("cameraman.png");
		originalImagePanel.setLayout(new BoxLayout(originalImagePanel, BoxLayout.Y_AXIS));
		originalImagePanel.add(originalLabel);
		originalImagePanel.add(imagePanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setBackground(Color.BLACK);
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		JLabel middleLabel = new JLabel(" Swap ");
		middleLabel.setForeground(Color.white);
		// middleLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton leftArrow = new JButton("←");		

		JButton rightArrow = new JButton("→");

		middlePanel.add(middleLabel);
		middlePanel.add(leftArrow);
		middlePanel.add(rightArrow);

		JPanel modifiedImagePanel = new JPanel();
		JLabel modifiedLabel = new JLabel("Modified Image");
		modifiedLabel.setAlignmentX(CENTER_ALIGNMENT);

		ImagePanel imagePanel2 = new ImagePanel("cameraman.png");
		modifiedImagePanel.setLayout(new BoxLayout(modifiedImagePanel, BoxLayout.Y_AXIS));
		modifiedImagePanel.add(modifiedLabel);
		modifiedImagePanel.add(imagePanel2);

		contentPane.add(originalImagePanel);
		contentPane.add(middlePanel);
		contentPane.add(modifiedImagePanel);
		int width= imagePanel.getImage().getWidth()*2+50;
		int height = imagePanel.getImage().getHeight()+62;
		this.setBounds(0, 0, width, height);
		this.setVisible(true);
	}

}
