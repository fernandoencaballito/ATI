package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* @author Fernando Bejarano
*/
public class SwapHandler implements ActionListener {

	private ImageGeneralPanel origin, destination;
	public SwapHandler(ImageGeneralPanel origin, ImageGeneralPanel destination) {
		this.origin=origin;
		this.destination=destination;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("[SwapHandler]: swaping");
		destination.swapImage(origin);

	}

}
