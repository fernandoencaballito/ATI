package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import view.ImageEffects;
import view.main.MainFrame;
import view.panels.ImageGeneralPanel;

public class OperationsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private ImageGeneralPanel modifiedImagePanel;
	private ImageGeneralPanel originalImagePanel;

	public OperationsMenu(MainFrame parent){
		
		super("Operations");
		modifiedImagePanel = parent.modifiedImagePanel;
		originalImagePanel = parent.originalImagePanel;
		JMenuItem sum = new JMenuItem("Sum");
		sum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image1 = originalImagePanel.getImagePanel().getImage();
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.sum(image1, image2));
			}
		});
		this.add(sum);
		JMenuItem minus = new JMenuItem("Minus");
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image1 = originalImagePanel.getImagePanel().getImage();
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.minus(image1, image2));
			}
		});
		this.add(minus);
		JMenuItem product = new JMenuItem("Product");
		product.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image1 = originalImagePanel.getImagePanel().getImage();
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.product(image1, image2));
			}
		});
		this.add(product);
		JMenuItem scalarProduct = new JMenuItem("Scalar Product");
		scalarProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.scalarProduct(image2, 5));
			}
		});
		this.add(scalarProduct);
		JMenuItem dynamicRangeCompression = new JMenuItem("Dynamic Range Compression");
		dynamicRangeCompression.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.dynamicRangeCompression(image2));
			}
		});
		this.add(dynamicRangeCompression);
		JMenuItem gammaFunction = new JMenuItem("Gamma Function");
		gammaFunction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image2 = modifiedImagePanel.getImagePanel().getImage();
				 String input = (String)JOptionPane.showInputDialog(parent
						 , "Insert gamma value:"
						 , "Gamma power function"
						 , JOptionPane.PLAIN_MESSAGE);
		
				double gamma=Double.valueOf(input);
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.gammaFunction(image2, gamma));
			}
		});
		this.add(gammaFunction);
		
	}
}
