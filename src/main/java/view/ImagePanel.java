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
		
	}
	
    public ImagePanel(String fileName) {
       try {                
          image = (BufferedImage) ImageUtils.loadImage(fileName);
       } catch (IOException ex) {
            //TODO
       }
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
    
    
    @Override
    public int getWidth() {
    	return image.getWidth();
    	
    }
    
     @Override
    public int getHeight() {
    	 return image.getHeight();
     }

}
