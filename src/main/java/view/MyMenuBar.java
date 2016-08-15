package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MyMenuBar extends JMenuBar {
	
	public MyMenuBar(ImageGeneralPanel originalImagePanel,ImageGeneralPanel modifiedImagePanel, JFrame parent){
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem open = new OpenItem(originalImagePanel,modifiedImagePanel, parent);
		
		
		open.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem save=new SaveItem(modifiedImagePanel,parent);

		file.add(open);
		file.add(save);
		this.add(file);
		
		JMenu edit = new JMenu("Edit");
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke('V', KeyEvent.CTRL_DOWN_MASK));

		edit.add(copy);
		edit.add(paste);
		this.add(edit);

		JMenuItem pixelValue = new JMenu("Pixel Value");
		this.add(pixelValue);
		
		JMenuItem greyLevels = new JMenu("Grey Levels");
		this.add(greyLevels);
		
		JMenuItem selectArea = new JMenu("Select Area");
		this.add(selectArea);
		
	}
}
