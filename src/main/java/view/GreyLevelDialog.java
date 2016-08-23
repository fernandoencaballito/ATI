package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
* @author Fernando Bejarano
*/
public class GreyLevelDialog extends JDialog {

	private static final String TITLE="Grey levels";
	public GreyLevelDialog(ImageGeneralPanel imagePanel,BufferedImage selected, JFrame parent) {
		super(parent,TITLE);
		this.setTitle(TITLE);
		int pixelsCount=selected.getHeight()*selected.getWidth();
		
		Color greyLevelsAvg=ImageEffects.meanPixelValue(selected);
		
		StringBuffer str=new StringBuffer();
		str.append("Total pixels in area: ");
		str.append(pixelsCount);
		str.append("\n");
		if(imagePanel.isColor()){
			str.append(" Avg red level: ");
			str.append(greyLevelsAvg.getRed());
			str.append("\n Avg green level: ");
			str.append(greyLevelsAvg.getGreen());
			str.append("\n Avg blue level: ");
			str.append(greyLevelsAvg.getBlue());
			
		}else{
			str.append("Avg grey level: ");
			str.append(greyLevelsAvg.getBlue());
		}
		
		
		str.append("\n");
		
		JOptionPane.showMessageDialog(parent,str,TITLE,JOptionPane.INFORMATION_MESSAGE);
	
	}
}
