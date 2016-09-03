package view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.apache.commons.io.FilenameUtils;

import image.ImageUtils;
import view.ColorMode;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String extension;
	private BufferedImage image;
	private PixelEditionPanel pixelPanel;
	private ColorMode colorMode;
	
	//selección de area
	private int lastPressedX;
	private int lastPressedY;
	private JToggleButton selectAreaItem;
	public Rectangle selectedRectangle;
	//
	public ImagePanel() {
		super();

	}

	public void setSelectAreaItem( JToggleButton selectAreaItem){
		this.selectAreaItem=selectAreaItem;
	}
	public ImagePanel(String fileName) {
		loadImageFromFile(fileName);
		//
		//this.image=Noise.generateNoise(image, 0.13, new GaussianGenerator(5, 100.0));
		//this.image=Noise.generateNoise(image, 1.0, new Rayleigh(4));
		//this.image=Noise.generateNoise(image, 1, new ExponentialGenerator(0.5));
		//
		refreshExtension(fileName);
		lastPressedX=0;
		lastPressedY=0;
		
		MouseListener mouseListener = new MouseListener() {
			ImagePanel imagePanel;
			private MouseListener init(ImagePanel imagePanel){
				this.imagePanel=imagePanel;
				return this;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				if((x>=image.getWidth() )  ||   (y>=image.getHeight())   )
					return;
					
					
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
				lastPressedX=e.getX();
				lastPressedY=e.getY();
				
				System.out.println("mouse pressed");

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int currentX=e.getX();
				int currentY=e.getY();
				
				if(selectAreaItem==null)
					return;
				
				if(selectAreaItem.isSelected()){
					 imagePanel.setSelectedRectangle(new Rectangle(lastPressedX
							, lastPressedY
							, (currentX-lastPressedX),
							(currentY-lastPressedY))
							);
					
				System.out.println("mouse released");
				System.out.println("cuadrado desde:("
									+lastPressedX
									+","
									+lastPressedY
									+")hasta:("
									+currentX
									+","
									+currentY
									+")"
									)
								;
				imagePanel.repaint();
				
//				System.out.println("Original ImagePanel id:"+imagePanel.hashCode());
//				System.out.println("Rectangle en original:"+imagePanel.selectedRectangle);
			}
				}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		}.init(this);
		this.addMouseListener(mouseListener);

	}

	protected void setSelectedRectangle(Rectangle rectangle) {
		this.selectedRectangle=rectangle;
		
	}

	private void detectColorMode() {
		int height=image.getHeight();
		int width=image.getWidth();
		
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				
				int rgb = image.getRGB(x, y);
				Color c = new Color(rgb);
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				if (!(red == green && red == blue && green == blue)){
					//Color
					System.out.println("[ImagePanel] color image loaded");
					colorMode=ColorMode.COLOR;
					return;
				}

			}
		}
		colorMode=ColorMode.GREY;
		System.out.println("[ImagePanel] grey image loaded");
	}

	public boolean hasColor(){
		return colorMode==ColorMode.COLOR;
	}
	
	public boolean isGrey(){
		return colorMode==ColorMode.GREY;
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
		if(selectedRectangle!=null){
			 Graphics2D g2 = (Graphics2D) g;
			 g2.setColor(Color.red);
			 g2.draw(selectedRectangle);
			 
		}
		//selectedRectangle=null;
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
		detectColorMode();
	}

	public void loadImageFromFile(String fileName, int width, int height) {
		try {
			image = (BufferedImage) ImageUtils.loadImage(fileName, width, height);
		} catch (Exception ex) {
			System.out.println("[ImagePanel] error loading file " + fileName);
			ex.printStackTrace();
		}
		refreshExtension(fileName);
		detectColorMode();
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

	static public BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public void setPixelPanel(PixelEditionPanel pixelPanel) {
		this.pixelPanel = pixelPanel;
	}

	public void pasteSelectedArea(ImagePanel otherImagePanel) {
		BufferedImage toPaste=otherImagePanel.getSelectedImage();
		
		if(toPaste!=null)
			this.image=toPaste;
		
		
	}

	protected BufferedImage getSelectedImage() {
		if(selectedRectangle==null)
			return null;
		BufferedImage subImage1=image.getSubimage(selectedRectangle.x
				,selectedRectangle.y
				, selectedRectangle.width
				,selectedRectangle.height);
		//(image.getColorModel(),image.getRaster().createCompatibleWritableRaster(selectedRectangle.width, selectedRectangle.height));
//		BufferedImage subImage2=new BufferedImage(image.getColorModel()
//									,image.getRaster().createCompatibleWritableRaster(selectedRectangle.width, selectedRectangle.height) 
//									, image.isAlphaPremultiplied()
//									, null);
//		
//		subImage1.copyData(subImage2.getRaster());
		
		
		BufferedImage subThree = new BufferedImage((int)selectedRectangle.getWidth()
									,(int)selectedRectangle.getHeight(), image.getType());

		
		Graphics2D g = subThree.createGraphics();
		try {
		    g.drawImage(subImage1, 0, 0, null);
		}
		finally {
		    g.dispose();
		}
		
		selectedRectangle=null;
		
		return subThree;
	}

	public void loadBlackImage(int width, int height) {
		this.image=new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		
		int blackRgb=0;
		for(int x=0;x<width;x++){
			for(int y=0;y<height;y++){
				this.image.setRGB(x, y, blackRgb);
				}
			}
		
		this.repaint();
		
		}
		
	
}
