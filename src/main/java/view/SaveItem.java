package view;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/*
* @author Fernando Bejarano
*/
public class SaveItem extends JMenuItem {

	public SaveItem(ImageGeneralPanel modifiedImagePanel, JFrame parent) {
		super("Save Image");
		this.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
		this.addActionListener(new SaveHandler(modifiedImagePanel,parent));
	}

}
