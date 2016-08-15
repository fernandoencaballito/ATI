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
		JLabel label = new JLabel(title);
		label.setAlignmentX(CENTER_ALIGNMENT);
		imagePanel = new ImagePanel(imageFileName);
		
		

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(label);
		this.add(imagePanel);
		
	}
	
	
	public int getCurrentWidth() {
		return imagePanel.getImageWidth();
	}
	
	
	public int getCurrentHeight() {
		return imagePanel.getImageHeight()+62;
	}
	
	public void loadImageFromFile(String fileName){
		imagePanel.loadImageFromFile(fileName);
	}


	public void loadImageFromFile(String fileName, int width, int height) {
		imagePanel.loadImageFromFile(fileName,width,height);
		
	}
	public String getImageFormat(){
		return imagePanel.getFormat();
	}


	public void saveImage(String fileName) {
		imagePanel.saveImage(fileName);
	}
	
}
