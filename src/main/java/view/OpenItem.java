package view;
/*
* @author Fernando Bejarano
*/

import javax.swing.JMenuItem;

public class OpenItem extends JMenuItem {

	
	public OpenItem() {
		super("Open Image");
		this.addActionListener(new OpenHandler(this));
	}
}