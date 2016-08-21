package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/*
 *clase que contiene al panel de la imagen y su respectivo t�tulo en la ventana("original image" o "modified Image").
 * 
 */

public class ImageGeneralPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String title;
	private ImagePanel imagePanel;
	
	public ImageGeneralPanel(String title, String imageFileName,JMenuItem selectAreaItem) {
	
		this.title=title;
		JLabel label = new JLabel(title);
		label.setAlignmentX(CENTER_ALIGNMENT);
		imagePanel = new ImagePanel(imageFileName,selectAreaItem);
		
		

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


	public void swapImage(ImageGeneralPanel origin) {
		this.imagePanel.swapImage(origin.imagePanel);
		
	}


	public String getTitle() {
		return title;
	}


	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	
}
