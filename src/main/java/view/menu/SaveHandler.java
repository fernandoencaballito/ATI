package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import view.panels.ImageGeneralPanel;

public class SaveHandler implements ActionListener {

	private JFileChooser fc;
	private JFrame parentFrame;
	private ImageGeneralPanel  modifiedImageGeneralPanel;

	public SaveHandler(ImageGeneralPanel modifiedImagePanel,JFrame parent) {
		fc = new JFileChooser();
		this.parentFrame=parent;
		this.modifiedImageGeneralPanel=modifiedImagePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Save file dialog");
		
		
		// abre la ventana de dialogo para guardar archivo.
		int returnVal = fc.showSaveDialog(parentFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// seleccionó un archivo
			String fileName = file.getAbsolutePath();
			System.out.println("Saving: " + fileName + ".");
			modifiedImageGeneralPanel.saveImage(fileName);
		} else {
			// cancelo
			System.out.println("Open command cancelled by user.");
		}

	}

}
