package border_detectors.active_contours;

import java.io.File;
import java.util.Comparator;

import com.sun.tools.corba.se.idl.InterfaceGen;

/*
* @author Fernando Bejarano
*/
public class FileComparator implements Comparator<File>{

	private static final String regex="(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
	
	@Override
	public int compare(File o1, File o2) {
		if((o1==null && o1!=null) || (o2==null && o1!=null))
			return 0;
		
		
		String filename1=o1.getName();
		String filename2=o2.getName();
		
		String[] aux1=filename1.split(regex);
		String  num1_str=aux1[1];
		String[] aux2=filename2.split(regex);
		String num2_str=aux2[1];
		
		
		int num1=Integer.parseInt(num1_str);
		int num2=Integer.parseInt(num2_str);
		return num1-num2;
	}
	
}