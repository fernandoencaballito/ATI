package view.menu;

import javax.swing.JMenuBar;
import javax.swing.JToggleButton;

import view.ColorMode;
import view.main.MainFrame;
import view.panels.ImageGeneralPanel;

public class MyMenuBar extends JMenuBar {	

	private static final long serialVersionUID = 1L;
	public MainFrame parent;
	private JToggleButton selectArea;
	private EditMenu editMenu;
	
	public MyMenuBar(MainFrame parent){
		this.parent = parent;
		ImageGeneralPanel originalImagePanel = parent.originalImagePanel;
		ImageGeneralPanel modifiedImagePanel = parent.modifiedImagePanel;
		
		this.add(new FileMenu(parent));
		
		editMenu = new EditMenu(parent);
		this.add(editMenu);
		
		this.add(new OptionsMenu(this, parent));
		
		this.add(new SpacialOperationsMenu(modifiedImagePanel.getImagePanel()));
		
		this.add(new PunctualOperatorsMenu(modifiedImagePanel.getImagePanel()));

		this.add(new OperationsMenu(parent));
		
		this.add(new GreyLevelsButton(originalImagePanel,parent));
		selectArea = new JToggleButton("Select Area");
		this.add(selectArea);
		
	}
	
	public  JToggleButton getSelectAreaItem(){
		return selectArea;
	}
	
	public void changeColorMode(ColorMode mode){
		editMenu.changeColorMode(mode);
	}
	
}
