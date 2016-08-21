package image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
/*
* @author Fernando Bejarano
*/
public class CustomArrayUtils {

	//recibe un arragle que se corresponde con la lectura por fila de una matriz y devuelve la lectura por columna de la matriz.
	public static<T extends Object> T[] traspose(T[] array, int heigt,int width,Class<T> clazz){
		T[] ans=(T[])Array.newInstance(clazz, 0);
		List<List< T>> rows=new ArrayList<List<T>>(); 
		
		
		int currentRowSize=0;
		List currentRow=null;
		for(T elem:array){
			if(currentRowSize==0){
				currentRow=new ArrayList<T>();
				rows.add(currentRow);
			}
			currentRow.add(elem);
			currentRowSize++;
			if(currentRowSize==width)
				currentRowSize=0;
			
		}
	
		
		
		//se lee por columna
		
		
		
		for(int col=0;col<width;col++){
			
			for(List<T> row: rows){
				
				ans=(T[]) ArrayUtils.add(ans, row.get(col));
			}
		}
		
		
		
		return (T[]) ans;
		
	}
	
}
