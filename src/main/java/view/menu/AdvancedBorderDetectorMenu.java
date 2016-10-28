package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import border_detectors.Canny;
import border_detectors.Canny.NeighbourType;
import view.panels.CannyFrame;
import view.panels.CannyFrame.FrameMode;
import view.panels.ImagePanel;

public class AdvancedBorderDetectorMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	public AdvancedBorderDetectorMenu(ImagePanel imagePanel) {
		super("Advance Border Detectors");
		
		JMenuItem canny = new JMenuItem("Canny");
		canny.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CannyFrame(imagePanel, FrameMode.CANNY).setVisible(true);
				
			}
		});
		this.add(canny);
		JMenuItem nonMaximumSupression = new JMenuItem("Non Maximum Supression");
		nonMaximumSupression.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CannyFrame(imagePanel, FrameMode.NON_MAXIMUM_SUPRESSION).setVisible(true);
			}
		});
		this.add(nonMaximumSupression);
		JMenuItem hysteresisThreshold = new JMenuItem("Hysteresis Threshold");
		hysteresisThreshold.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imagePanel.setImage(Canny.hysteresisThreshold(imagePanel.getImage(), NeighbourType.FOUR_NEIGHBOURS));
			}
		});
		this.add(hysteresisThreshold);
	}

}

