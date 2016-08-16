package view;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PixelEditionPanel extends JFrame{
	
	public enum PixelEditionMode{
		GREY,
		COLOR
	}

	private int window_width = 250;
	private int window_height = 120;
	private PixelEditionMode mode = PixelEditionMode.GREY;
	private JLabel x = new JLabel("0");
	private JLabel y = new JLabel("0");
	private int red=0, green=0, blue=0, color=0;
	JTextField r;
	JTextField g;
	JTextField b;
	JTextField c;
	private JLabel imagePanelName;
	private ImageGeneralPanel currentPanel;
	private JPanel positionPanel;
	private JPanel colorPixelValuePanel;
	private JPanel greyPixelValuePanel;
	
	private Container contentPane;

	public PixelEditionPanel(String title, MainFrame parent){
		super(title);
		this.setBounds(0, parent.getHeight(), window_width, window_height);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane = this.getContentPane();
		currentPanel = parent.originalImagePanel;
		this.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		imagePanelName = new JLabel(currentPanel.getTitle());
		imagePanelName.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(imagePanelName);
		positionPanel = new JPanel();
		positionPanel.setLayout(new FlowLayout());
		positionPanel.add(new JLabel("POSITION: ( x ="));
		positionPanel.add(x);
		positionPanel.add(new JLabel(", y ="));
		positionPanel.add(y);
		positionPanel.add(new JLabel(")"));

		contentPane.add(positionPanel);
		
		colorPixelValuePanel = new JPanel();
		colorPixelValuePanel.setLayout(new FlowLayout());
		r = new JTextField(String.valueOf(red),3);
		g = new JTextField(String.valueOf(green),3);
		b = new JTextField(String.valueOf(blue),3);
		colorPixelValuePanel.add(new JLabel("R:"));
		colorPixelValuePanel.add(r);
		colorPixelValuePanel.add(new JLabel("G:"));
		colorPixelValuePanel.add(g);
		colorPixelValuePanel.add(new JLabel("B:"));
		colorPixelValuePanel.add(b);

		contentPane.add(colorPixelValuePanel);
		
		greyPixelValuePanel = new JPanel();
		greyPixelValuePanel.setLayout(new FlowLayout());
		c = new JTextField(String.valueOf(color),3);
		greyPixelValuePanel.add(new JLabel("Pixel Value:"));
		greyPixelValuePanel.add(c);
		
		contentPane.add(greyPixelValuePanel);

		this.setMode(PixelEditionMode.GREY);
	}
	
	public void setMode(PixelEditionMode mode){
		this.mode = mode;
		if(mode == PixelEditionMode.GREY){
			colorPixelValuePanel.setVisible(false);
			greyPixelValuePanel.setVisible(true);
		} else{
			colorPixelValuePanel.setVisible(true);
			greyPixelValuePanel.setVisible(false);
		}
	}
	
	public void colorClick(int x, int y, int red, int green, int blue){
		this.setMode(PixelEditionMode.COLOR);
		this.x.setText(String.valueOf(x));
		this.y.setText(String.valueOf(y));
		this.red = red;		
		this.blue = blue;
		this.green = green;
		refreshColors();
	}
	
	public void greyScaleClick(int x, int y, int color){
		this.setMode(PixelEditionMode.GREY);
		this.x.setText(String.valueOf(x));
		this.y.setText(String.valueOf(y));
		this.color = color;
		refreshColors();
	}
	
	private void refreshColors(){
		this.r.setText(String.valueOf(red));
		this.g.setText(String.valueOf(green));
		this.b.setText(String.valueOf(blue));
		this.c.setText(String.valueOf(color));
	}
	
}
