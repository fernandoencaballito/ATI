package view;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
* @author Fernando Bejarano
*/
public class MiddlePanel extends JPanel{

	private static final String title=" Swap ";
	private static final java.awt.Color BACKGROUND_COLOR=Color.BLACK;
	private static final java.awt.Color FONT_COLOR=Color.white;
	public MiddlePanel() {
		super();
		this.setBackground(BACKGROUND_COLOR);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(title);
		label.setForeground(FONT_COLOR);
		// middleLabel.setAlignmentX(CENTER_ALIGNMENT);

		JButton leftArrow = new JButton("←");		

		JButton rightArrow = new JButton("→");

		this.add(label);
		this.add(leftArrow);
		this.add(rightArrow);
	}
}
