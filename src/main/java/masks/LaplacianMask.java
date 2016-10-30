package masks;

public class LaplacianMask extends LaplacianGenericMask{

	
	public LaplacianMask() {

	super(3);
	set(0, 0, 0);
	set(0,1,-1);
	set(0,2,0);
	
	set(1, 0, -1);
	set(1, 1, 4);
	set(1, 2, -1);
	
	set(2, 0, 0);
	set(2,1,-1);
	set(2,2,0);
	
	
	}

	
}
