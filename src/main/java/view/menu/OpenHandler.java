package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

import view.RawDialog;
import view.panels.ImageGeneralPanel;

public class OpenHandler implements ActionListener {
	private JFileChooser fc;
	private JFrame parentFrame;
//	private OpenItem parentButton;
	private ImageGeneralPanel originalImageGeneralPanel, modifiedImageGeneralPanel;

	public OpenHandler(OpenItem parentItem, ImageGeneralPanel originalImagePanel,
			ImageGeneralPanel modifiedImagePanel,JFrame parent) {
		fc = new JFileChooser("./src/main/resources");
//		this.parentButton = parentItem;
		this.originalImageGeneralPanel = originalImagePanel;
		this.modifiedImageGeneralPanel = modifiedImagePanel;
		this.parentFrame=parent;
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Open file dialog");

		// abre la ventana de dialogo para abrir archivo.
		int returnVal = fc.showOpenDialog(parentFrame);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			// seleccionó un archivo
			String fileName = file.getAbsolutePath();
			System.out.println("Opening: " + fileName + ".");

			if (isRaw(fileName)) {
				RawDialog rawDialog=new RawDialog(parentFrame);
				if(!rawDialog.validDimensions())
					return;
				int width=rawDialog.getInputWidth();
				int height=rawDialog.getInputHeight();
				
				originalImageGeneralPanel.loadImageFromFile(fileName,width,height);
				modifiedImageGeneralPanel.loadImageFromFile(fileName,width,height);
				
			} else {
				originalImageGeneralPanel.loadImageFromFile(fileName);
				//TODO descomentar esta linea!!!
				modifiedImageGeneralPanel.loadImageFromFile(fileName);
			}

			originalImageGeneralPanel.repaint();

			modifiedImageGeneralPanel.repaint();

		} else {
			// cancelo
			System.out.println("Open command cancelled by user.");
		}

	}

	private boolean isRaw(String fileName) {
		String extension = FilenameUtils.getExtension(fileName);
		extension = extension.toLowerCase();
		
		return extension.equals("raw");
			
		
	}

}
