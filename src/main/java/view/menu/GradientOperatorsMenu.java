package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import masks.PrewittMask;
import masks.SobelMask;
import view.ImageEffects;
import view.panels.ImagePanel;

public class GradientOperatorsMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	public GradientOperatorsMenu(ImagePanel imagePanel) {
		super("Gradient Operators");
		
		JMenuItem sobel = new JMenuItem("Sobel");
		sobel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new SobelMask()));
			}
		});
		this.add(sobel);
		
		JMenuItem prewitt = new JMenuItem("Prewitt");
		prewitt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new PrewittMask()));
			}
		});
		this.add(prewitt);
	}

}
