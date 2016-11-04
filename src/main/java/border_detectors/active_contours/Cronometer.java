package border_detectors.active_contours;
/*
* @author Fernando Bejarano
*/
public class Cronometer {

	long last;
	
	
	public void start(){
		last=System.currentTimeMillis();
	}
	
	public String stop(){
		long current= System.currentTimeMillis();
		long elapsed=current  - last;
		long seconds=elapsed /1000;
		
		long fps=1000/elapsed;
		String ans=String.format("Processing time: %d miliseconds, fps: %d", elapsed,fps);
		
		return ans;
	}
	
}
