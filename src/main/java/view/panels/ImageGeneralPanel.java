package view.panels;

import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/*
 *clase que contiene al panel de la imagen y su respectivo t�tulo en la ventana("original image" o "modified Image").
 * 
 */

public class ImageGeneralPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String title;
	public ImagePanel imagePanel;
	
	public ImageGeneralPanel(String title, String imageFileName) {
	
		this.title=title;
		JLabel label = new JLabel(title);
		label.setAlignmentX(CENTER_ALIGNMENT);
		imagePanel = new ImagePanel(imageFileName);
		
		

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(label);
		this.add(imagePanel);
		
	}
	public void setSelectAreaItem( JToggleButton selectAreaItem){
		imagePanel.setSelectAreaItem(selectAreaItem);
	}
	
	public BufferedImage getSelectedArea(){
		return imagePanel.getSelectedImage();
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
	public void pasteSelectedArea(ImageGeneralPanel otherImage) {
		imagePanel.pasteSelectedArea(otherImage.imagePanel);
		this.repaint();
		otherImage.repaint();
	}
	
	public boolean isColor(){
		return imagePanel.hasColor();
	}
	public void loadBlackImage(int width, int height) {
		imagePanel.loadBlackImage(width,height);
		
	}
	
}
