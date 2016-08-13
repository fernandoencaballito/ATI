package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *clase que contiene al panel de la imagen y su respectivo título en la ventana("original image" o "modified Image").
 * 
 * @author Fernando Bejarano
 */

public class ImageGeneralPanel extends JPanel {
	private String title;
	private ImagePanel imagePanel;
	
	public ImageGeneralPanel(String title, String imageFileName) {
	
		this.title=title;
		imagePanel = new ImagePanel(imageFileName);
		
		JLabel label = new JLabel(title);
		label.setAlignmentX(CENTER_ALIGNMENT);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(label);
		this.add(imagePanel);
		
	}
	
	@Override
	public int getWidth() {
		return imagePanel.getWidth();
	}
}
