package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import masks.GaussianMask;
import masks.MeanMask;
import masks.MedianMask;
import masks.PasaAltosMask;

public class FilterFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public FilterType filterType;
	private JTextField size = new JTextField("0", 3);
	private JTextField media = new JTextField("0", 3);
	private JTextField standardDeviation = new JTextField("0", 3);
	JPanel gaussianPanel;
	JPanel sizePanel;
	private ImagePanel imagePanel;
	
	public FilterFrame(FilterType type, ImagePanel imagePanel){
		this.imagePanel = imagePanel;
		filterType = type;
		this.setBounds(0, 500, 250, 120);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		sizePanel = new JPanel();
		sizePanel.setLayout(new FlowLayout());
		sizePanel.add(new JLabel("SIZE ="));
		sizePanel.add(size);
		
		this.add(sizePanel);
		
		gaussianPanel = new JPanel();
		gaussianPanel.setLayout(new FlowLayout());
		gaussianPanel.add(new JLabel("μ ="));
		gaussianPanel.add(media);
		gaussianPanel.add(new JLabel(" σ ="));
		gaussianPanel.add(standardDeviation);
		gaussianPanel.setVisible(false);
		
		this.add(gaussianPanel);
			
		this.changeFilterType(filterType);
		
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int sizeValue = Integer.valueOf(size.getText());
				double mediaValue = Double.valueOf(media.getText());
				double standardDeviationValue = Double.valueOf(standardDeviation.getText());
				switch (filterType) {
				case GAUSSIAN:
					FilterFrame.this.imagePanel.setImage(ImageEffects.filter(FilterFrame.this.imagePanel.getImage(), new GaussianMask(sizeValue, mediaValue, standardDeviationValue)));
					break;
				case MEAN:
					FilterFrame.this.imagePanel.setImage(ImageEffects.filter(FilterFrame.this.imagePanel.getImage(), new MeanMask(sizeValue)));
					break;
				case MEDIAN:
					FilterFrame.this.imagePanel.setImage(ImageEffects.filter(FilterFrame.this.imagePanel.getImage(), new MedianMask(sizeValue)));					
					break;
				case BORDER:
					FilterFrame.this.imagePanel.setImage(ImageEffects.filter(FilterFrame.this.imagePanel.getImage(), new PasaAltosMask(sizeValue)));					
					break;
				default:
					break;
				}	
			}
		});
		this.add(confirmButton);
		
		this.setAlwaysOnTop(true);
		this.setVisible(false);
	}
	
	public void changeFilterType(FilterType type){
		this.setTitle(type.toString() + " FILTER");
		filterType = type;
		switch (filterType) {
		case GAUSSIAN:
			gaussianPanel.setVisible(true);
			break;
		case MEAN:
			gaussianPanel.setVisible(false);
			break;
		case MEDIAN:
			gaussianPanel.setVisible(false);
			break;
		case BORDER:
			gaussianPanel.setVisible(false);
			break;
		default:
			break;
		}
	}	
	
	public void setImagePanel(ImagePanel imagePanel){
		this.imagePanel = imagePanel;
	}	
	
	public enum FilterType {
		GAUSSIAN,
		MEAN,
		MEDIAN,
		BORDER;
	}
	
}
