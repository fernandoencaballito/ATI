package border_detectors.active_contours;
/*
* @author Fernando Bejarano
*/

import java.io.File;
import java.util.List;

import view.panels.ImagePanel;

public class ActiveContourThread extends Thread {
	
	private ImagePanel original;
	private ImagePanel modified;
	List<File> images ;
	ActiveContours activeContours;
	Cronometer cronometer;
	public ActiveContourThread(ImagePanel original, ImagePanel modified, List<File> images) {
		super();
		this.original = original;
		this.modified = modified;
		this.images = images;
		activeContours = new ActiveContours(original, modified);
		cronometer = new Cronometer();
	}

	public void run(){
		
		
		boolean firstImage=true;
		for (File file : images) {
			System.out.println("[Active contour]: processing image " + file.toString());
			
			
			if(firstImage){
				firstImage=false;
				
			}else{
				original.loadImageFromFile(file.toString());
				modified.loadImageFromFile(file.toString());
			}
			
			cronometer.start();
			
			activeContours.mark_contour(10);
			
			String timeMsg=cronometer.stop();
			
			System.out.println(String.format("[Active contour]: processing image %s. %s" , file.toString(), timeMsg));
		}

		
	}

}
