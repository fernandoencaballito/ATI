package masks;

public class SobelMask extends XYSquareMask {
	
	public SobelMask() {
		super(3);
		setMasks(new SobelXMask(), new SobelYMask());	
	}
	class SobelXMask extends SquareMask{

		public SobelXMask() {
			super(3);
			set(0, 0, -1);
			set(0, 1, -2);
			set(0, 2, -1);
			for (int i = 0; i < 3; i++) {
				set(1, i, 0);
			}
			set(2, 0, 1);
			set(2, 1, 2);
			set(2, 2, 1);
		}
		
	}
	
	class SobelYMask extends SquareMask{

		public SobelYMask() {
			super(3);
			set(0, 0, -1);
			set(1, 0, -2);
			set(2, 0, -1);
			for (int i = 0; i < 3; i++) {
				set(i, 1, 0);
			}
			set(0, 2, 1);
			set(1, 2, 2);
			set(2, 2, 1);
		}
		
	}
}
