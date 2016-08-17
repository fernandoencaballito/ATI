package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MyMenuBar extends JMenuBar {	
	
	public PixelEditionPanel pixelPanel;
	public MainFrame parent;
	
	public MyMenuBar(MainFrame parent){
		this.parent = parent;
		ImageGeneralPanel originalImagePanel = parent.originalImagePanel;
		ImageGeneralPanel modifiedImagePanel = parent.modifiedImagePanel;
		pixelPanel = parent.pixelPanel;
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem open = new OpenItem(originalImagePanel,modifiedImagePanel, parent);
		
		JMenuItem save=new SaveItem(modifiedImagePanel,parent);

		file.add(open);
		file.add(save);
		this.add(file);
		
		JMenu edit = new JMenu("Edit");
		
		JMenuItem pixelValue = new JMenuItem("Pixel Value");
		pixelValue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pixelPanel.setVisible(true);
				
			}
		});
		edit.add(pixelValue);
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke('V', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem undo = new JMenuItem("Undo Changes");
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.resetChanges();
				parent.modifiedImagePanel.repaint();
			}
		});

		edit.addSeparator();
		edit.add(copy);
		edit.add(paste);
		edit.add(undo);
		this.add(edit);
		
		JMenuItem greyLevels = new JMenu("Grey Levels");
		this.add(greyLevels);
		
		JMenuItem selectArea = new JMenu("Select Area");
		this.add(selectArea);
		
	}
}
