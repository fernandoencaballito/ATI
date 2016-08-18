package view;

import javax.swing.JButton;

public class SwapImageButton extends JButton {
	private static final long serialVersionUID = 1L;
	private String title;
	private ImageGeneralPanel origin;
	private ImageGeneralPanel destination;
	
	public SwapImageButton(String title,ImageGeneralPanel origin, ImageGeneralPanel destination) {
	super(title);
	this.title=title;
	this.origin=origin;
	this.destination=destination;
	this.addActionListener(new SwapHandler(origin,destination));
	}

}
