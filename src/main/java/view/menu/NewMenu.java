package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.main.MainFrame;

public class NewMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	public NewMenu(MainFrame main){
		super("New");
		JMenuItem newFile = new JMenuItem("New Blank Page");
		newFile.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
		newFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.originalImagePanel.loadImageFromFile("newImage.png");
				main.originalImagePanel.repaint();
				main.modifiedImagePanel.loadImageFromFile("newImage.png");
				main.modifiedImagePanel.repaint();	
			}
		});
		
		JMenuItem newBlackFile = new JMenuItem("New Black Page");
		newBlackFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.originalImagePanel.loadBlackImage(200,200);
				main.originalImagePanel.repaint();
				main.modifiedImagePanel.loadBlackImage(200,200);
				main.modifiedImagePanel.repaint();	
			}
		});
		
		JMenuItem greyScaleFile = new JMenuItem("Grey Scale");
		JMenuItem colorScaleFile = new JMenuItem("Color Scale");
		
		this.add(newFile);
		this.add(newBlackFile);
		this.add(greyScaleFile);
		this.add(colorScaleFile);
	}
}
