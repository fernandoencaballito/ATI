package view;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RawDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String title = " RAW image";
	private static final String message = "Complete <width>\",\" <height> ";

	private static Icon icon = null;
	private int width=-1;
	private int height=-1;

	public RawDialog() {

	}
	// la ventana de dialogo para imagen RAW espera "ancho,alto". La coma va
	// entre los dos valores.
	public RawDialog(JFrame parent) {

		super(parent,title);

		String input = null;
		boolean firstTime = true;
		do {
			if (!firstTime) {
				System.out.println("[RawDialog]:invalid input");
			}
			input = showDialog(parent);
			//System.out.println(input);
			if(input==null){
				System.out.println("[RawDialog]: cancell");
				break;
			}
		} while (!validInput(input));
		
		
		if(validInput(input))
			System.out.println("[RawDialog].Raw image width: "
							+this.width
							+", height:"
							+this.height);
		

	}

	public boolean validDimensions(){
		return width!=-1 && height!=-1;
	}
	public boolean validInput(String str) {
		if (str == null || str.length() == 0)
			return false;
		if (!(str.contains(",")))
			return false;
		String[] values = str.split(",");

		if (values.length != 2)
			return false;

		String widthStr = values[0];
		String heightStr = values[1];

		if (widthStr.length() == 0 || heightStr.length() == 0)
			return false;

		int width, height;

		try {
			width = Integer.parseInt(widthStr);
			height = Integer.parseInt(heightStr);
		} catch (NumberFormatException e) {
			System.out.println("[RawDialog]:invalid input");
			return false;
		}
		this.width=width;
		this.height=height;
		
		return true;
	}

	public static String showDialog(JFrame parent) {

		String ans = (String) JOptionPane.showInputDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE, icon, null,
				"width,height");
		return ans;
	}
	public int getInputWidth() {
		return width;
	}
	public int  getInputHeight() {
		return height;
	}

}
