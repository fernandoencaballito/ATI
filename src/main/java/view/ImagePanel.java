package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String extension;
	private BufferedImage image;

	public ImagePanel() {
		super();

	}

	public ImagePanel(String fileName) {
		loadImageFromFile(fileName);
		refreshExtension(fileName);

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
		g.drawImage(image, 0, 0, null); // see javadoc for more info on the
										// parameters
	}

	public int getImageWidth() {
		return (image == null) ? 0 : image.getWidth();

	}

	public int getImageHeight() {
		return (image == null) ? 0 : image.getHeight();
	}

	public void loadImageFromFile(String filename) {
		try {
			image = (BufferedImage) ImageUtils.loadImage(filename, 0, 0);
		} catch (Exception ex) {
			System.out.println("[ImagePanel] error loading file " + filename);
			ex.printStackTrace();
		}
		refreshExtension(filename);
	}

	public void loadImageFromFile(String fileName, int width, int height) {
		try {
			image = (BufferedImage) ImageUtils.loadImage(fileName, width, height);
		} catch (Exception ex) {
			System.out.println("[ImagePanel] error loading file " + fileName);
			ex.printStackTrace();
		}
		refreshExtension(fileName);
	}

	public String getFormat() {
		return extension;
	}

	public void saveImage(String fileNameWithoutExtension) {
		String fileNameWithExtension = fileNameWithoutExtension + "." + extension;
		ImageUtils.writeImage(image, fileNameWithExtension);

	}
	
	private void refreshExtension(String fileName){
		extension = FilenameUtils.getExtension(fileName);
		extension = extension.toLowerCase();
	}
}
