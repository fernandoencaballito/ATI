package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import interestPoints.InterestPoint;
import masks.KirshMask;
import masks.PrewittMask;
import masks.SobelMask;
import view.panels.ImagePanel;

public class InterestPointsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	public InterestPointsMenu(ImagePanel target){
		super("Interest Points");
		
		JMenuItem harris = new JMenuItem("Harris");
		harris.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InterestPoint.harris(target, new PrewittMask(), 7, 2, target.getImage());
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
