package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.ColorHistogram;
import view.ColorMode;
import view.ImageEffects;
import view.main.MainFrame;
import view.panels.CircleAdditionPanel;
import view.panels.ContrastPanel;
import view.panels.PixelEditionPanel;
import view.panels.RectangleAdditionPanel;

public class EditMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	public PixelEditionPanel pixelPanel;
	public CircleAdditionPanel circlePanel;
	public RectangleAdditionPanel rectPanel;
	public ColorHistogram histogramFrame;
	public NoiseMenu noiseMenu;
	
	public EditMenu(MainFrame main){
		super("Edit");
		this.setMnemonic(KeyEvent.VK_E);
		pixelPanel = main.pixelPanel;
		circlePanel = new CircleAdditionPanel("Add a circle", main);
		rectPanel = new RectangleAdditionPanel("Add a rectangle", main);
		
		
		JMenuItem pixelValue = new JMenuItem("Pixel Value");
		pixelValue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pixelPanel.setVisible(true);
				
			}
		});
		this.add(pixelValue);
		
		JMenuItem histogram = new JMenuItem("Histogram");
		histogram.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				histogramFrame = new ColorHistogram("Grey Scale Histogram", ImageEffects.getHistogram(main.modifiedImagePanel.getImagePanel().getImage()));			
			}
		});
		this.add(histogram);
		
		JMenuItem circleAddition = new JMenuItem("Add a circle...");
		circleAddition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				circlePanel.setVisible(true);
				
			}
		});
		this.add(circleAddition);
		
		JMenuItem rectAddition = new JMenuItem("Add a rectangle...");
		rectAddition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rectPanel.setVisible(true);
				
			}
		});
		this.add(rectAddition);
		
		noiseMenu = new NoiseMenu(main.modifiedImagePanel.getImagePanel());
		this.add(noiseMenu);
		
		///Contraste
		JFrame contrastPanel=new ContrastPanel(main.modifiedImagePanel.getImagePanel());
		JMenuItem contrast=new JMenuItem("Contrast");
		contrast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				contrastPanel.setVisible(true);
			}
		});
		this.add(contrast);
		///
		
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem paste = new PasteMenuItem(main.originalImagePanel, main.modifiedImagePanel);
		JMenuItem undo = new JMenuItem("Undo Changes");
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.resetChanges();
				main.modifiedImagePanel.repaint();
			}
		});

		this.addSeparator();
		this.add(copy);
		this.add(paste);
		this.add(undo);
	}
	
	public void changeColorMode(ColorMode mode){
		pixelPanel.setMode(mode);
		circlePanel.setMode(mode);
		rectPanel.setMode(mode);
		noiseMenu.setMode(mode);
	}

}
