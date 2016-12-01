package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import color_threshold.ColorThresholding;
import view.ImageEffects;
import view.panels.ImagePanel;
import view.panels.ThresholdFrame;

public class ThresholdMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	public ThresholdFrame thresholdFrame;
	
	public ThresholdMenu(ImagePanel target){
		super("Threshold");
		
		JMenuItem umbral = new JMenuItem("Manual Threshold");
		umbral.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thresholdFrame = new ThresholdFrame(target);
				thresholdFrame.setVisible(true);
			}
		});
		this.add(umbral);
		
		JMenuItem umbralGlobal = new JMenuItem("Global Threshold");
		umbralGlobal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ImageEffects.globalThreshold(target.getImage()));
			}
		});
		this.add(umbralGlobal);
		
		JMenuItem umbralOtzu = new JMenuItem("Otzu Threshold");
		umbralOtzu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ImageEffects.applyOtsuThreshold(target.getImage()));
			}
		});
		this.add(umbralOtzu);
		
		JMenuItem colorThreshold = new JMenuItem("Color Threshold");
		colorThreshold.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				target.setImage(ColorThresholding.colorThreshold(target.getImage()));
			}
		});
		this.add(colorThreshold);
	}

	
}
