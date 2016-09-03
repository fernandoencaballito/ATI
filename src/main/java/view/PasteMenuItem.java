package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class PasteMenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;
	private ImageGeneralPanel originalImagePanel;
	private ImageGeneralPanel modifiedImagePanel;
	
	 public PasteMenuItem(ImageGeneralPanel originalImage, ImageGeneralPanel modifiedImage) {
		super("Paste");
		this.originalImagePanel=originalImage;
		this.modifiedImagePanel=modifiedImage;
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("OriginalImagePanel id:"+originalImagePanel.imagePanel.hashCode());
//				System.out.println("Rectangle en original:"+originalImagePanel.imagePanel.selectedRectangle);
//				System.out.println("Rectangle en modified:"+modifiedImagePanel.imagePanel.selectedRectangle);
				modifiedImagePanel.pasteSelectedArea(originalImagePanel);
			}
		});
	}	
}
