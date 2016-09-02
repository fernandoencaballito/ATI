package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import image.Contrast;

public class ContrastPanel extends JFrame{
	private JTextField r1TextField;
	private JTextField r2TextField;
	
	private ImagePanel imagePanel;
	
	public ContrastPanel(ImagePanel imagePanel){
		this.imagePanel=imagePanel;
		
		this.setBounds(0, 500, 500, 120);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new FlowLayout());//new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("r1"));
		r1TextField=new JTextField("0",3);
		this.add(r1TextField);
		
		
		this.add(new JLabel("r2"));
		r2TextField=new JTextField("0",3);
		this.add(r2TextField);
		JButton confirmButton=new JButton("Ok");
		ActionListener listener=new ActionListener() {
			
			JFrame parent;
			
			public void init(JFrame parent){
				this.parent=parent;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				int r1=Integer.valueOf(r1TextField.getText());
				int r2=Integer.valueOf(r2TextField.getText());
				
				BufferedImage image=imagePanel.getImage();
				BufferedImage modified=Contrast.increaseContrast(image, r1, r2);
				imagePanel.setImage(modified);
				imagePanel.repaint();
				
				
			}
		};
		confirmButton.addActionListener(listener);
		
		this.add(confirmButton);
	}

}
