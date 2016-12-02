package view.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import color_threshold.ColorThresholding;
import color_threshold.Pixel;

public class ColorThresholdingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Map<Integer, JCheckBox> checkboxes = new HashMap<Integer, JCheckBox>();
	
	public ColorThresholdingFrame(ImagePanel imagePanel, Map<Integer, List<Pixel>> clases, Map<Integer, Vector3D> meanPixels){
		super("Color Thresholding");
		this.setBounds(0, 500, 600, 150);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel checkBoxPanel = new JPanel();
        for (Map.Entry<Integer, List<Pixel>> clazz : clases.entrySet()) {
        	JCheckBox checkbox = new JCheckBox("Clase " + clazz.getKey().toString(),true);
        	checkbox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean[] hasToPaint = new boolean[8];
					for (int i = 0; i < 8; i++) {
						hasToPaint[i] = checkboxes.containsKey(i) ? checkboxes.get(i).isSelected() : false;
					}
					ColorThresholding.paintImage(imagePanel, clases, meanPixels, hasToPaint);
				}
			});
        	
        	checkBoxPanel.add(checkbox);
        	checkboxes.put(clazz.getKey(), checkbox);
		}
        mainPanel.add(checkBoxPanel);

        this.add(mainPanel);
		this.setAlwaysOnTop(true);
	}
}
