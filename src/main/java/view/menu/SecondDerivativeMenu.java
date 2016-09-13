package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import masks.LaplacianMask;
import view.panels.ImagePanel;

public class SecondDerivativeMenu extends JMenu{

	public SecondDerivativeMenu(ImagePanel imagePanel) {
		super("Second Deriv. op");
		
		JMenuItem laplacianItem=new JMenuItem("Laplacian");
		laplacianItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LaplacianMask mask=new LaplacianMask();
				
				imagePanel.setImage(mask.filter(imagePanel));
			}
		});
		this.add(laplacianItem);
	}
	
	
}
