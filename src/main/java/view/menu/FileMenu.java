package view.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import view.main.MainFrame;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	public FileMenu(MainFrame main){
		super("File");
		this.setMnemonic(KeyEvent.VK_F);
		
		this.add(new NewMenu(main));
		
		this.add(new OpenItem(main.originalImagePanel, main.modifiedImagePanel, main));
		
		this.add(new SaveItem(main.modifiedImagePanel, main));		
	}

}
