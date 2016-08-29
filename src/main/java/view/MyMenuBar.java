package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import view.NoiseAdditionFrame.NoiseType;

public class MyMenuBar extends JMenuBar {	

	private static final long serialVersionUID = 1L;
	public PixelEditionPanel pixelPanel;
	public CircleAdditionPanel circlePanel;
	public RectangleAdditionPanel rectPanel;
	public MainFrame parent;
	private JToggleButton selectArea;
	public ThresholdFrame thresholdFrame;
	public ColorHistogram histogramFrame;
	public NoiseAdditionFrame noiseFrame ;
	
	public MyMenuBar(MainFrame parent){
		this.parent = parent;
		ImageGeneralPanel originalImagePanel = parent.originalImagePanel;
		ImageGeneralPanel modifiedImagePanel = parent.modifiedImagePanel;
		pixelPanel = parent.pixelPanel;
		circlePanel = new CircleAdditionPanel("Add a circle", parent);
		rectPanel = new RectangleAdditionPanel("Add a rectangle", parent);
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem open = new OpenItem(originalImagePanel,modifiedImagePanel, parent);
		
		JMenuItem save = new SaveItem(modifiedImagePanel,parent);
		
		JMenu newMenu = new JMenu("New");
		JMenuItem newFile = new JMenuItem("New Blank Page");
		newFile.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
		newFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				originalImagePanel.loadImageFromFile("newImage.png");
				originalImagePanel.repaint();
				modifiedImagePanel.loadImageFromFile("newImage.png");
				modifiedImagePanel.repaint();	
			}
		});
		
		JMenuItem newBlackFile = new JMenuItem("New Black Page");
		newBlackFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				originalImagePanel.loadBlackImage(200,200);
				originalImagePanel.repaint();
				modifiedImagePanel.loadBlackImage(200,200);
				modifiedImagePanel.repaint();	
			}
		});
		
		JMenuItem greyScaleFile = new JMenuItem("Grey Scale");
		JMenuItem colorScaleFile = new JMenuItem("Color Scale");
		
		newMenu.add(newFile);
		newMenu.add(newBlackFile);
		newMenu.add(greyScaleFile);
		newMenu.add(colorScaleFile);
		
		file.add(newMenu);
		file.add(open);
		file.add(save);
		this.add(file);
		
		JMenu edit = new JMenu("Edit");
		file.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem pixelValue = new JMenuItem("Pixel Value");
		pixelValue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pixelPanel.setVisible(true);
				
			}
		});
		edit.add(pixelValue);
		
		JMenuItem histogram = new JMenuItem("Histogram");
		histogram.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				histogramFrame = new ColorHistogram("Grey Scale Histogram", ImageEffects.getHistogram(modifiedImagePanel.getImagePanel().getImage()));			
			}
		});
		edit.add(histogram);
		
		JMenuItem circleAddition = new JMenuItem("Add a circle...");
		circleAddition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				circlePanel.setVisible(true);
				
			}
		});
		edit.add(circleAddition);
		
		JMenuItem rectAddition = new JMenuItem("Add a rectangle...");
		rectAddition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rectPanel.setVisible(true);
				
			}
		});
		edit.add(rectAddition);
		
		JMenu noiseAddition = new JMenu("Add noise...");
		
		JMenuItem gaussianNoise = new JMenuItem("Gaussian Noise");
		gaussianNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.GAUSSIAN);
				noiseFrame.setVisible(true);
			}
		});
		noiseAddition.add(gaussianNoise);
		
		JMenuItem rayleighNoise = new JMenuItem("Rayleigh Noise");
		rayleighNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.RAYLEIGH);
				noiseFrame.setVisible(true);
			}
		});
		noiseAddition.add(rayleighNoise);
		
		JMenuItem exponentialNoise = new JMenuItem("Exponential Noise");
		exponentialNoise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noiseFrame.changeNoiseType(NoiseType.EXPONENTIAL);
				noiseFrame.setVisible(true);
			}
		});
		noiseAddition.add(exponentialNoise);
		
		edit.add(noiseAddition);
		
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem paste = new PasteMenuItem(originalImagePanel, modifiedImagePanel);
		paste.setAccelerator(KeyStroke.getKeyStroke('V', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem undo = new JMenuItem("Undo Changes");
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.resetChanges();
				parent.modifiedImagePanel.repaint();
			}
		});

		edit.addSeparator();
		edit.add(copy);
		edit.add(paste);
		edit.add(undo);
		this.add(edit);
		
		JMenu options = new JMenu("Options");
		JCheckBoxMenuItem colorMode = new JCheckBoxMenuItem("Color enabled");
		colorMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorMode color;
				if(colorMode.isSelected()){
					color = ColorMode.COLOR;
				} else {
					color = ColorMode.GREY;
				}
				pixelPanel.setMode(color);
				circlePanel.setMode(color);
				rectPanel.setMode(color);
			}
		});
		options.add(colorMode);
		this.add(options);
		

		JButton greyLevels = new GreyLevelsButton(originalImagePanel,parent);

		JMenu pOperators = new JMenu("Punctual Operators");
		JMenuItem invert = new JMenuItem("Negative");
		invert.setAccelerator(KeyStroke.getKeyStroke('I', KeyEvent.CTRL_DOWN_MASK));
		invert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.invertColors(modifiedImagePanel.getImagePanel().getImage()));
			}
		});
		pOperators.add(invert);
		
		JMenuItem umbral = new JMenuItem("Threshold");
		umbral.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thresholdFrame = new ThresholdFrame(modifiedImagePanel.getImagePanel());
				thresholdFrame.setVisible(true);
			}
		});
		pOperators.add(umbral);
		
		
		
		JMenuItem equalize= new JMenuItem("Equalize");
		equalize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				modifiedImagePanel
						.getImagePanel()
						.setImage(
								ImageEffects.ecualizeHistogram(modifiedImagePanel.getImagePanel().getImage()));
				
			}
		});
		pOperators.add(equalize);
		
		
		
		JMenuItem grey = new JMenuItem("Grey Image");
		grey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modifiedImagePanel.getImagePanel().setImage(ImageEffects.getGreyImage(modifiedImagePanel.getImagePanel().getImage()));
			}
		});
		pOperators.add(grey);
		this.add(pOperators);
		
		
		this.add(greyLevels);
		
		selectArea = new JToggleButton("Select Area");
		this.add(selectArea);
		
		noiseFrame = new NoiseAdditionFrame(NoiseType.GAUSSIAN, modifiedImagePanel.getImagePanel());
	}
	public  JToggleButton getSelectAreaItem(){
		return selectArea;
	}
	
}
