package view.menu;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.panels.ImageGeneralPanel;

public class SaveItem extends JMenuItem {

	private static final long serialVersionUID = 1L;

	public SaveItem(ImageGeneralPanel modifiedImagePanel, JFrame parent) {
		super("Save Image");
		this.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
		this.addActionListener(new SaveHandler(modifiedImagePanel,parent));
	}

}
