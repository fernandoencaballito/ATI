package view.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

import view.menu.MyMenuBar;
import view.panels.ImageGeneralPanel;
import view.panels.ImagePanel;
import view.panels.MiddlePanel;
import view.panels.PixelEditionPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	//valores iniciales
	private int window_width = 950;
	private int window_height = 400;
	//
	private static final String FRAME_TITLE="Image Processor - Developed by Fernando Bejarano and Lucas Soncini";
	
	private Container contentPane;
	
	public ImageGeneralPanel originalImagePanel;
	public ImageGeneralPanel modifiedImagePanel;
	public MiddlePanel middlePanel;
	public PixelEditionPanel pixelPanel;
	

	String initialFile="final/arbolFlashero.png";
	
	public MainFrame() throws HeadlessException {
		super(FRAME_TITLE);
		this.setBounds(0, 0, window_width, window_height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// area principal
		
		contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.setBackground(Color.black);

//		String initialFile="./src/main/resources/tp4/video sintetico/a1.jpg";

		//String initialFile="./src/main/resources/cameraman-chopped.png";
		//String initialFile="/home/administrator/negro.png";
		originalImagePanel = new ImageGeneralPanel("Original Image"
												, initialFile);
		
		

		modifiedImagePanel = new ImageGeneralPanel("Modified Image"
												, initialFile);
		
//		BufferedImage buf=modifiedImagePanel.imagePanel.getImage();
//		modifiedImagePanel.imagePanel.setImage(ImageEffects.getBandBuffer(buf, 'b'));
		
//		

//		modifiedImagePanel = new ImageGeneralPanel("Modified Image"
//												, "./src/main/resources/JackSig.bmp");
//		
//		BufferedImage buf=modifiedImagePanel.imagePanel.getImage();
//		
		
		
		middlePanel = new MiddlePanel(originalImagePanel,modifiedImagePanel);
		
		pixelPanel = new PixelEditionPanel("Pixel Edition", this);
		originalImagePanel.getImagePanel().setPixelPanel(pixelPanel);
		modifiedImagePanel.getImagePanel().setPixelPanel(pixelPanel);
		
		
		contentPane.add(originalImagePanel);
		contentPane.add(middlePanel);
		contentPane.add(modifiedImagePanel);
		
		window_width= originalImagePanel.getCurrentWidth()*2+50;
		window_height = originalImagePanel.getCurrentHeight();
		
		System.out.println("windows_width"+window_width);
		System.out.println("windows_height"+window_height);
		
		//MENU
				MyMenuBar menu=new MyMenuBar(this);
				this.setJMenuBar(menu);
				JToggleButton selectAreaItem=menu.getSelectAreaItem();
		//
		this.originalImagePanel.setSelectAreaItem(selectAreaItem);	
				
		this.setBounds(0, 0, window_width, window_height);
		this.setVisible(true);
	}
	
	public void resetChanges(){
		modifiedImagePanel.getImagePanel().setImage(ImagePanel.deepCopy(originalImagePanel.getImagePanel().getImage()));
	}

}
