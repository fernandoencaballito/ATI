package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import border_detectors.active_contours.ActiveContourThread;
import border_detectors.active_contours.ActiveContours;
import border_detectors.active_contours.FolderUtils;
import view.panels.ImagePanel;

/*
* @author Fernando Bejarano
*/
public class ActiveContoursMenu extends JMenu {

	public ActiveContoursMenu(ImagePanel original, ImagePanel modified) {

		super("Active Contours");

		JMenuItem activeContourSingleImage = new JMenuItem("Single image");
		this.add(activeContourSingleImage);
		activeContourSingleImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean isAreaSelected = original.isAreaSelected();

				if (isAreaSelected) {

					System.out.println("Active contour single image =>procesing");
					ActiveContours activeContours = new ActiveContours(original, modified);
					activeContours.mark_contour(100);

				} else {
					System.out.println("Active contour: no area selected");
				}
			}
		});

		JMenuItem activeContourMultipleImages = new JMenuItem("Multiple images");
		this.add(activeContourMultipleImages);
		activeContourMultipleImages.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isAreaSelected = original.isAreaSelected();

				if (isAreaSelected) {

					System.out.println("Active contour multiple image => start processing");
					ActiveContours activeContours = new ActiveContours(original, modified);

					List<File> images = FolderUtils.getImagesIterator(modified.getFilenameWithPath());
					
					ActiveContourThread thread=new ActiveContourThread(original, modified, images);
					thread.start();
					

				} else {
					System.out.println("Active contour: no area selected");
				}
			}
		});

	}

}
