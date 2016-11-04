package border_detectors.active_contours;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* @author Fernando Bejarano
*/
public class FolderUtils {

	

	public static List<File> getImagesIterator(String filename){
		
		File file=new File(filename);
		String fileRelativeName=file.getName();
		String directoryName=file.getParent();
		File directory=new File(directoryName);
		
		System.out.println("Directory:"+directoryName);
		File[] files= directory.listFiles();
		try{
		Arrays.sort(files,new FileComparator());
		}catch(Exception e){
		Arrays.sort(files);
		};
		
		//se devuelven las imagenes siguientes a la actual,seguida por las anteriores
		
		
		List<File> before=new ArrayList<File>();
		
		List<File> after=new ArrayList<File>();
		boolean found=false;
		for(File currentFile: files){
			
			if(!found && currentFile.getName().equals(fileRelativeName)){
				found=true;
				
				
			}
			
			
			if(found){
				after.add(currentFile);
				
			}else{
				before.add(currentFile);
			}
			
		}
		
		
		after.addAll(before);
		
		
		return after;
		
	}
	
	
	
	
}
