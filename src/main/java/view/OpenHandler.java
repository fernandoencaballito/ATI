package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/*
* @author Fernando Bejarano
*/
public class OpenHandler implements ActionListener {
	private JFileChooser fc;
	private OpenItem parent;
	private ImageGeneralPanel originalImageGeneralPanel, modifiedImageGeneralPanel;
	
	
	public OpenHandler( OpenItem parentItem, ImageGeneralPanel originalImagePanel, ImageGeneralPanel modifiedImagePanel) {
		fc=new JFileChooser("./src/main/resources");
		this.parent=parentItem;
		this.originalImageGeneralPanel=originalImagePanel;
		this.modifiedImageGeneralPanel=modifiedImagePanel;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Open file dialog");
		
		
			//abre la ventana de dialogo para abrir archivo.
            int returnVal = fc.showOpenDialog(parent);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
               //seleccionó un archivo
                String fileName=file.getName();
                System.out.println("Opening: " + fileName +".");
                
                originalImageGeneralPanel.loadImageFromFile(fileName);
                originalImageGeneralPanel.repaint();
                
                modifiedImageGeneralPanel.loadImageFromFile(fileName);
                modifiedImageGeneralPanel.repaint();
                
            } else {
            	//cancelo
            	 System.out.println("Open command cancelled by user.");
            }
            
		

	}

}
