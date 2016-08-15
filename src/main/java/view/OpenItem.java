package view;
/*
* @author Fernando Bejarano
*/

import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class OpenItem extends JMenuItem {

	
	
	public OpenItem(ImageGeneralPanel originalImagePanel,ImageGeneralPanel modifiedImagePanel, JFrame parent) {
		super("Open Image");
		this.addActionListener(new OpenHandler(this,originalImagePanel,modifiedImagePanel,parent));
	}
}
