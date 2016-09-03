package view.menu;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.panels.ImageGeneralPanel;

public class OpenItem extends JMenuItem {

	private static final long serialVersionUID = 1L;

	public OpenItem(ImageGeneralPanel originalImagePanel,ImageGeneralPanel modifiedImagePanel, JFrame parent) {
		super("Open Image");
		this.addActionListener(new OpenHandler(this,originalImagePanel,modifiedImagePanel,parent));
		this.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
	}
}
