package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import view.panels.BorderDetectorFrame;
import view.panels.BorderDetectorFrame.BorderDetectorType;
import view.panels.ImagePanel;

public class GradientOperatorsMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	BorderDetectorFrame borderFrame;

	public GradientOperatorsMenu(ImagePanel imagePanel) {
		super("Gradient Operators");
		borderFrame = new BorderDetectorFrame(BorderDetectorType.SOBEL, imagePanel);
		
		JMenuItem sobel = new JMenuItem("Sobel");
		sobel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borderFrame.changeBorderType(BorderDetectorType.SOBEL);
				borderFrame.setImagePanel(imagePanel);
				borderFrame.setVisible(true);
			}
		});
		this.add(sobel);
		
		JMenuItem prewitt = new JMenuItem("Prewitt");
		prewitt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borderFrame.changeBorderType(BorderDetectorType.PREWITT);
				borderFrame.setImagePanel(imagePanel);
				borderFrame.setVisible(true);			}
		});
		this.add(prewitt);
		
		JMenuItem kirsh = new JMenuItem("Kirsh");
		kirsh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borderFrame.changeBorderType(BorderDetectorType.KIRSH);
				borderFrame.setImagePanel(imagePanel);
				borderFrame.setVisible(true);			}
		});
		this.add(kirsh);
		
		JMenuItem unnamed = new JMenuItem("Other");
		unnamed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				borderFrame.changeBorderType(BorderDetectorType.UNNAMED);
				borderFrame.setImagePanel(imagePanel);
				borderFrame.setVisible(true);			}
		});
		this.add(unnamed);
		
	}

}
