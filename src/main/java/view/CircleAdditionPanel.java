package view;

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

public class CircleAdditionPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private int window_width = 250;
	private int window_height = 120;
//	private ColorMode mode = ColorMode.GREY;
	private JTextField x = new JTextField("0", 3);
	private JTextField y = new JTextField("0", 3);
	private int red=0, green=0, blue=0, color=0;
	JTextField r;
	JTextField g;
	JTextField b;
	JTextField c;
	JTextField radius = new JTextField("0", 3);
	private ImageGeneralPanel currentPanel;
	private JPanel centerPanel;
	private JPanel radiusPanel;
	private JPanel colorValuePanel;
	private JPanel greyValuePanel;
	
	private Container contentPane;

	public CircleAdditionPanel(String title, MainFrame parent){
		super(title);
		this.setBounds(0, 0, window_width, window_height);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		contentPane = this.getContentPane();
		currentPanel = parent.modifiedImagePanel;
		this.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.add(new JLabel("CENTER:  x ="));
		centerPanel.add(x);
		centerPanel.add(new JLabel(" y ="));
		centerPanel.add(y);

		contentPane.add(centerPanel);
		
		radiusPanel = new JPanel();
		radiusPanel.setLayout(new FlowLayout());
		radiusPanel.add(new JLabel("RADIUS:"));
		radiusPanel.add(radius);

		contentPane.add(radiusPanel);
		
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
				Integer diam = Integer.valueOf(radius.getText()) * 2;
				g.fillOval(Integer.valueOf(x.getText()), Integer.valueOf(y.getText()), diam, diam);
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
				Integer diam = Integer.valueOf(radius.getText()) * 2;
				g.fillOval(Integer.valueOf(x.getText()), Integer.valueOf(y.getText()), diam, diam);
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
