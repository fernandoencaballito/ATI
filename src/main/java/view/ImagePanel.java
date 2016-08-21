package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String extension;
	private BufferedImage image;
	private PixelEditionPanel pixelPanel;

	public ImagePanel() {
		super();

	}

	public ImagePanel(String fileName) {
		loadImageFromFile(fileName);
		refreshExtension(fileName);
		MouseListener mouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int rgb = image.getRGB(x, y);
				//System.out.println(image.getType());
				Color c = new Color(rgb);
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				// grises
				if (red == green && red == blue && green == blue) {

					pixelPanel.greyScaleClick(x, y, green);
					System.out.println("[ImagePanel]Gris en(" + x + "," + y + "):" + green);

				} else {
					pixelPanel.colorClick(x, y, red, green, blue);
					System.out
							.println("[ImagePanel]Color en(" + x + "," + y + "):R" + red + ",B" + blue + ",G" + green);

				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		};
		this.addMouseListener(mouseListener);

	}

	public void setImage(Image image) {
		this.image = (BufferedImage) image;
		this.repaint();
		
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

	private void refreshExtension(String fileName) {
		extension = FilenameUtils.getExtension(fileName);
		extension = extension.toLowerCase();
	}

	public void swapImage(ImagePanel otherImagePanel) {
		this.extension = otherImagePanel.extension;
		this.image = deepCopy(otherImagePanel.image);
		this.repaint();

	}

	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public void setPixelPanel(PixelEditionPanel pixelPanel) {
		this.pixelPanel = pixelPanel;
	}
}
