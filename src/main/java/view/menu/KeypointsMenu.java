package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import keypoints.SiftCaller;
import keypoints.SusanMask;
import view.panels.ImagePanel;
import view.panels.KeypointsParametersFrame;
import view.panels.SusanThresholdFrame;

public class KeypointsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	KeypointsParametersFrame frame;
	SusanThresholdFrame susanFrame;
	public KeypointsMenu(ImagePanel target, ImagePanel original){
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
				susanFrame=new SusanThresholdFrame(target);
				susanFrame.setVisible(true);
				}
		});
		this.add(susan);
		
		JMenuItem sift = new JMenuItem("SIFT");
		sift.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("SIFT clicked");
				
				
				SiftCaller.compareKeypoints(target, original);
				
			}
		});
		this.add(sift);
	}

}
