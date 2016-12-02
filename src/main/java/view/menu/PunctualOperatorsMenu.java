package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.ImageEffects;
import view.panels.ImagePanel;

public class PunctualOperatorsMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	

	public PunctualOperatorsMenu(ImagePanel target){
		super("Punctual Operators");
		
		JMenuItem invert = new JMenuItem("Negative");
		invert.setAccelerator(KeyStroke.getKeyStroke('I', KeyEvent.CTRL_DOWN_MASK));
		invert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ImageEffects.invertColors(target.getImage()));
			}
		});
		this.add(invert);
		
		
		this.add(new ThresholdMenu(target));
		
		
		JMenuItem equalize= new JMenuItem("Equalize");
		equalize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				target.setImage(ImageEffects.ecualizeHistogram(target.getImage()));
				
			}
		});
		this.add(equalize);
		
		
		
		JMenuItem grey = new JMenuItem("Grey Image");
		grey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ImageEffects.getGreyImage(target.getImage()));
			}
		});
		this.add(grey);
		
		JMenuItem hsv_convertion = new JMenuItem("RGB to HSV and back to RGB");
		hsv_convertion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ImageEffects.playingWithHSV(target.getImage()));
			}
		});
		this.add(hsv_convertion);
	}
}
