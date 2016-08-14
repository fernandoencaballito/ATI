package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	public ImagePanel() {
		super();
		
	}
    public ImagePanel(String fileName) {
       loadImageFromFile(fileName);
	
    }
    
    public void setImage(Image image) {
        this.image = (BufferedImage) image;
    }
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
    
    
    public int getImageWidth() {
    	return (image==null)?0:image.getWidth();
    	
    }
    
    public int getImageHeight() {
    	 return (image==null)?0: image.getHeight();
     }

    public void loadImageFromFile(String filename){
    	try {
    		//TODO agregar ventana de dialogo para RAW
            image = (BufferedImage) ImageUtils.loadImage(filename,0,0);
         } catch (Exception ex) {
             System.out.println("[ImagePanel] error loading file "+filename);
             ex.printStackTrace();
         }
    }
}
