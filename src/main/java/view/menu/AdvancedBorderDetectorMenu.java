package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import border_detectors.Canny;
import border_detectors.Canny.NeighbourType;
import view.panels.ImagePanel;

public class AdvancedBorderDetectorMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	public AdvancedBorderDetectorMenu(ImagePanel imagePanel) {
		super("Advance Border Detectors");
		
		JMenuItem canny4 = new JMenuItem("4 neighbour Canny");
		canny4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.setImage(Canny.cannyBorderDetector(imagePanel.getImage(), 3, 1, NeighbourType.FOUR_NEIGHBOURS));
			}
		});
		this.add(canny4);
		JMenuItem canny8 = new JMenuItem("8 neighbour Canny");
		canny8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.setImage(Canny.cannyBorderDetector(imagePanel.getImage(), 3, 1, NeighbourType.EIGHT_NEIGHBOURS));
			}
		});
		this.add(canny8);
	}

}

