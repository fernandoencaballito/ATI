package view.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.ColorMode;
import view.main.MainFrame;

public class RectangleAdditionPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private int window_width = 300;
	private int window_height = 120;
//	private ColorMode mode = ColorMode.GREY;
	private JTextField x = new JTextField("0", 3);
	private JTextField y = new JTextField("0", 3);
	private int red=0, green=0, blue=0, color=0;
	JTextField r;
	JTextField g;
	JTextField b;
	JTextField c;
	JTextField width = new JTextField("0", 3);
	JTextField height = new JTextField("0", 3);
	private ImageGeneralPanel currentPanel;
	private JPanel topLeftPanel;
	private JPanel widthHeightPanel;
	private JPanel colorValuePanel;
	private JPanel greyValuePanel;
	
	private Container contentPane;

	public RectangleAdditionPanel(String title, MainFrame parent){
		super(title);
		this.setBounds(0, 0, window_width, window_height);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		contentPane = this.getContentPane();
		currentPanel = parent.modifiedImagePanel;
		this.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		topLeftPanel = new JPanel();
		topLeftPanel.setLayout(new FlowLayout());
		topLeftPanel.add(new JLabel("TOP LEFT CORNER:  x ="));
		topLeftPanel.add(x);
		topLeftPanel.add(new JLabel(" y ="));
		topLeftPanel.add(y);

		contentPane.add(topLeftPanel);
		
		widthHeightPanel = new JPanel();
		widthHeightPanel.setLayout(new FlowLayout());
		widthHeightPanel.add(new JLabel("width:"));
		widthHeightPanel.add(width);
		widthHeightPanel.add(new JLabel("height:"));
		widthHeightPanel.add(height);

		contentPane.add(widthHeightPanel);
		
		colorValuePanel = new JPanel();
		colorValuePanel.setLayout(new FlowLayout());
		r = new JTextField(String.valueOf(red),3);
		g = new JTextField(String.valueOf(green),3);
		b = new JTextField(String.valueOf(blue),3);
		colorValuePanel.add(new JLabel("R:"));
		colorValuePanel.add(r);
		colorValuePanel.add(new JLabel("G:"));
		colorValuePanel.add(g);
		colorValuePanel.add(new JLabel("B:"));
		colorValuePanel.add(b);
		JButton colorOKButton = new JButton("OK");
		colorOKButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				red = Integer.valueOf(r.getText());
				green = Integer.valueOf(g.getText());
				blue = Integer.valueOf(b.getText());
				
				Color circleColor = new Color(red, green, blue);
				Graphics g = currentPanel.getImagePanel().getImage().createGraphics();
				g.setColor(circleColor);
				g.fillRect(Integer.valueOf(x.getText()), Integer.valueOf(y.getText()), Integer.valueOf(width.getText()), Integer.valueOf(height.getText()));
				currentPanel.repaint();
			}
		});
		colorValuePanel.add(colorOKButton);

		contentPane.add(colorValuePanel);
		
		greyValuePanel = new JPanel();
		greyValuePanel.setLayout(new FlowLayout());
		c = new JTextField(String.valueOf(color),3);
		greyValuePanel.add(new JLabel("Pixel Value:"));
		greyValuePanel.add(c);
		JButton greyOKButton = new JButton("OK");
		greyOKButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				color = Integer.valueOf(c.getText());
				Color circleColor = new Color(color, color, color);
				Graphics g = currentPanel.getImagePanel().getImage().createGraphics();
				g.setColor(circleColor);
				g.fillRect(Integer.valueOf(x.getText()), Integer.valueOf(y.getText()), Integer.valueOf(width.getText()), Integer.valueOf(height.getText()));
				currentPanel.repaint();
			}
		});
		greyValuePanel.add(greyOKButton);
		contentPane.add(greyValuePanel);

		this.setMode(ColorMode.GREY);
	}
	
	public void setMode(ColorMode mode){
//		this.mode = mode;
		if(mode == ColorMode.GREY){
			colorValuePanel.setVisible(false);
			greyValuePanel.setVisible(true);
		} else{
			colorValuePanel.setVisible(true);
			greyValuePanel.setVisible(false);
		}
	}
}
