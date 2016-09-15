package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import masks.LOGmask;
import masks.LaplacianMask;
import view.panels.ImagePanel;

public class SecondDerivativeMenu extends JMenu{

	public SecondDerivativeMenu(ImagePanel imagePanel) {
		super("Second Deriv. op");
		
		JMenuItem laplacianItem=new JMenuItem("Laplacian");
		laplacianItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LaplacianMask mask=new LaplacianMask();
				
				imagePanel.setImage(mask.filter(imagePanel));
			}
		});
		this.add(laplacianItem);
		
		JMenuItem log_item=new JMenuItem("LoG");
		log_item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String input = (String)JOptionPane.showInputDialog(imagePanel
												, "Insert σ:"
												, "Laplacian of Gausian filter"
												, JOptionPane.PLAIN_MESSAGE);
				
				if(input ==null || input.length()==0)
					return;
				
				double sigma=0;
				try{
				sigma=Double.valueOf(input);
				}catch(NumberFormatException ex){
					System.out.println("[SecondDerivativeMenu,log]:invalid sigma");
					return;
				}
				int maskSize=(int) Math.floor(sigma*2+1);
				
				System.out.println("[SecondDerivativeMenu,log]: sigma:"+sigma+ "  Masksize:"+maskSize);
				LOGmask mask=new LOGmask(maskSize,	sigma);
				
				imagePanel.setImage(mask.filter(imagePanel));
			}
		});
		this.add(log_item);
		
		
		
	}
	
	
}
