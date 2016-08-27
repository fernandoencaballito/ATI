package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
* @author Fernando Bejarano
*/
public class GreyLevelsButton extends JButton {

	private ImageGeneralPanel imagePanel;

	public GreyLevelsButton(ImageGeneralPanel originalImagePanel, JFrame parent) {
		super("Grey Levels");
		this.imagePanel = originalImagePanel;
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage selected = imagePanel.getSelectedArea();
				if (selected != null) {
					new GreyLevelDialog(imagePanel,selected, parent);
					imagePanel.repaint();
				}
			}
		});
	}
}