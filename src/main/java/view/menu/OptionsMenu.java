package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

import view.ColorMode;
import view.main.MainFrame;

public class OptionsMenu extends JMenu{
	private static final long serialVersionUID = 1L;
	private MyMenuBar menuBar;
	private MainFrame mainPanel;
	
	public OptionsMenu(MyMenuBar menubar, MainFrame main){
		super("Options");
		this.menuBar = menubar;
		this.mainPanel = main;
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
				menuBar.changeColorMode(color);
			}
		});
		this.add(colorMode);
		
		JCheckBoxMenuItem panelMode = new JCheckBoxMenuItem("One Image Mode");
		panelMode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panelMode.isSelected()){
					mainPanel.originalImagePanel.setVisible(false);
					mainPanel.middlePanel.setVisible(false);
				} else {
					mainPanel.originalImagePanel.setVisible(true);
					mainPanel.middlePanel.setVisible(true);
				}
			}
		});
		this.add(panelMode);
	}
}
