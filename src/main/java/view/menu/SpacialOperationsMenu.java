package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import view.panels.FilterFrame;
import view.panels.ImagePanel;
import view.panels.FilterFrame.FilterType;

public class SpacialOperationsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	private FilterFrame filterFrame;
	
	public SpacialOperationsMenu(ImagePanel imagePanel){
		super("Spacial Operations");
		filterFrame = new FilterFrame(FilterType.MEAN, imagePanel);
		JMenuItem mediaFilter = new JMenuItem("Mean Filter");
		mediaFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterFrame.changeFilterType(FilterType.MEAN);
				filterFrame.setImagePanel(imagePanel);
				filterFrame.setVisible(true);
			}
		});
		this.add(mediaFilter);
		
		JMenuItem medianFilter = new JMenuItem("Median Filter");
		medianFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterFrame.changeFilterType(FilterType.MEDIAN);
				filterFrame.setImagePanel(imagePanel);
				filterFrame.setVisible(true);
			}
		});
		this.add(medianFilter);
		
		JMenuItem gaussianFilter = new JMenuItem("Gaussian Filter");
		gaussianFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				filterFrame.changeFilterType(FilterType.GAUSSIAN);
				filterFrame.setImagePanel(imagePanel);
				filterFrame.setVisible(true);
			}
		});
		this.add(gaussianFilter);
		
		JMenuItem borderFilter = new JMenuItem("Border Filter");
		borderFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterFrame.changeFilterType(FilterType.BORDER);
				filterFrame.setImagePanel(imagePanel);
				filterFrame.setVisible(true);
			}
		});
		this.add(borderFilter);
	}
}
