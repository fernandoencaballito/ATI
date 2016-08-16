package view;
/*
* @author Fernando Bejarano
*/

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class OpenItem extends JMenuItem {

	public OpenItem(ImageGeneralPanel originalImagePanel,ImageGeneralPanel modifiedImagePanel, JFrame parent) {
		super("Open Image");
		this.addActionListener(new OpenHandler(this,originalImagePanel,modifiedImagePanel,parent));
		this.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
	}
}
