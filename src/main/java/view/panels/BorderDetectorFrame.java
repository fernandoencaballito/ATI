package view.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import masks.Direction;
import masks.DirectionalSquareMask;
import masks.KirshMask;
import masks.PrewittMask;
import masks.SobelMask;
import masks.UnnamedMask;
import view.ImageEffects;

public class BorderDetectorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public BorderDetectorType borderType;
	JPanel directionsPanel;
	private ImagePanel imagePanel;
	JCheckBox horizontal = new JCheckBox("Horizontal(-)");
	JCheckBox vertical = new JCheckBox("Vertical(|)");
	JCheckBox diagonal1 = new JCheckBox("Diagonal(/)");
	JCheckBox diagonal2 = new JCheckBox("Diagonal(\\)");
	
	public BorderDetectorFrame(BorderDetectorType type, ImagePanel imagePanel){
		this.imagePanel = imagePanel;
		borderType = type;
		this.setBounds(0, 500, 400, 120);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));		
		
		directionsPanel = new JPanel();
		directionsPanel.setLayout(new FlowLayout());
		directionsPanel.add(horizontal);
		directionsPanel.add(vertical);
		directionsPanel.add(diagonal1);
		directionsPanel.add(diagonal2);
		
		this.add(directionsPanel);
			
		this.changeBorderType(borderType);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(horizontal.isSelected() || vertical.isSelected() || diagonal1.isSelected() || diagonal2.isSelected())){
					System.out.println("Debe Seleccionar al menos una dirección");
					return;
				}
					
				List<Direction> directions = new ArrayList<>();
				if(horizontal.isSelected())
					directions.add(Direction.HORIZONTAL);
				if (vertical.isSelected())
					directions.add(Direction.VERTICAL);
				if(diagonal1.isSelected())
					directions.add(Direction.DIAGONAL_TOP_RIGHT);
				if(diagonal2.isSelected())
					directions.add(Direction.DIAGONAL_TOP_LEFT);
				switch (borderType) {
				case SOBEL:
					BorderDetectorFrame.this.imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new DirectionalSquareMask(new SobelMask(), directions)));
					break;
				case KIRSH:
					BorderDetectorFrame.this.imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new DirectionalSquareMask(new KirshMask(), directions)));
					break;
				case PREWITT:
					BorderDetectorFrame.this.imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new DirectionalSquareMask(new PrewittMask(), directions)));
					break;
				case UNNAMED:
					BorderDetectorFrame.this.imagePanel.setImage(ImageEffects.filter(imagePanel.getImage(), new DirectionalSquareMask(new UnnamedMask(), directions)));
					break;
				default:
					break;
				}	
				BorderDetectorFrame.this.setVisible(false);
			}
		});
		this.add(confirmButton);
		
		this.setAlwaysOnTop(true);
		this.setVisible(false);
	}
	
	public void changeBorderType(BorderDetectorType type){
		this.setTitle(type.toString() + " BORDER DETECTOR");
		borderType = type;
	}	
	
	public void setImagePanel(ImagePanel imagePanel){
		this.imagePanel = imagePanel;
	}	
	
	public enum BorderDetectorType {
		SOBEL,
		KIRSH,
		UNNAMED,
		PREWITT
	}

}
