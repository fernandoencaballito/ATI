package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import view.panels.ImagePanel;
import view.panels.InterestPointsParametersFrame;

public class InterestPointsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	InterestPointsParametersFrame frame;
	
	public InterestPointsMenu(ImagePanel target){
		super("Interest Points");
		
		JMenuItem harris = new JMenuItem("Harris");
		harris.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new InterestPointsParametersFrame(target);
				frame.setVisible(true);
			}
		});
		this.add(harris);
		
		JMenuItem susan = new JMenuItem("S.U.S.A.N.");
		susan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("S.U.S.A.N. clicked");
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
