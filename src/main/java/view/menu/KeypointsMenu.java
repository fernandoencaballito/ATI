package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import keypoints.SusanMask;
import view.panels.ImagePanel;
import view.panels.KeypointsParametersFrame;

public class KeypointsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	KeypointsParametersFrame frame;
	
	public KeypointsMenu(ImagePanel target){
		super("Keypoints");
		
		JMenuItem harris = new JMenuItem("Harris");
		harris.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new KeypointsParametersFrame(target);
				frame.setVisible(true);
			}
		});
		this.add(harris);
		
		JMenuItem susan = new JMenuItem("S.U.S.A.N.");
		susan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("S.U.S.A.N. clicked");
				BufferedImage original=target.getImage();
				BufferedImage modified=SusanMask.getBordersAndCorners(original);
				target.setImage(modified);
			}
		});
		this.add(susan);
		
		JMenuItem sift = new JMenuItem("SIFT");
		sift.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("SIFT clicked");
			}
		});
		this.add(sift);
	}

}
