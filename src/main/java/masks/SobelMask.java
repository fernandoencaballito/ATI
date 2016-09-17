package masks;

public class SobelMask extends SquareMask{

	public SobelMask() {
		super(3);
		set(0, 0, 1);
		set(0, 1, 2);
		set(0, 2, 1);
		for (int i = 0; i < 3; i++) {
			set(1, i, 0);
		}
		set(2, 0, -1);
		set(2, 1, -2);
		set(2, 2, -1);
	}
	
}
