package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import view.panels.ImagePanel;
import view.panels.NoiseAdditionFrame;
import view.panels.NoiseAdditionFrame.NoiseType;

public class NoiseMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	public NoiseAdditionFrame noiseFrame;

	public NoiseMenu(ImagePanel target){
		super("Add noise...");
		noiseFrame = new NoiseAdditionFrame(NoiseType.GAUSSIAN, target);
		
		JMenuItem gaussianNoise = new JMenuItem("Gaussian Noise");
		gaussianNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.GAUSSIAN);
				noiseFrame.setVisible(true);
			}
		});
		this.add(gaussianNoise);
		
		JMenuItem rayleighNoise = new JMenuItem("Rayleigh Noise");
		rayleighNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.RAYLEIGH);
				noiseFrame.setVisible(true);
			}
		});
		this.add(rayleighNoise);
		
		JMenuItem exponentialNoise = new JMenuItem("Exponential Noise");
		exponentialNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.EXPONENTIAL);
				noiseFrame.setVisible(true);
			}
		});
		this.add(exponentialNoise);
		
		
		JMenuItem saltPepperNoise=new JMenuItem("Salt & pepper noise");
		saltPepperNoise.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.SALT_PEPPER);
				noiseFrame.setVisible(true);
			}
		});
		this.add(saltPepperNoise);
		
	}
}
