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
	private OpenItem item;
	public OpenHandler( OpenItem item) {
		fc=new JFileChooser();
		this.item=item;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Open file dialog");
		
		//Handle open button action.
       
            int returnVal = fc.showOpenDialog(item);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                System.out.println("Opening: " + file.getName() +".");
            } else {
            	 System.out.println("Open command cancelled by user.");
            }
            
		

	}

}
