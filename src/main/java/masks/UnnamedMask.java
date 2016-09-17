package masks;

public class UnnamedMask extends SquareMask {

	public UnnamedMask() {
		super(3);
		set(0, 0, 1);
		set(0, 1, 1);
		set(0, 2, 1);
		
		set(1, 0, 1);
		set(1, 1, -2);
		set(1, 2, 1);
		
		set(2, 0, -1);
		set(2, 1, -1);
		set(2, 2, -1);
	}

}
