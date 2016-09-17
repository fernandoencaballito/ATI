package masks;

public class KirshMask extends SquareMask {

	public KirshMask() {
		super(3);
		set(0, 0, 5);
		set(0, 1, 5);
		set(0, 2, 5);
		
		set(1, 0, -3);
		set(1, 1, 0);
		set(1, 2, -3);
		
		set(2, 0, -3);
		set(2, 1, -3);
		set(2, 2, -3);
	}

}
