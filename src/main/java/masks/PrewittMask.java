package masks;

public class PrewittMask extends XYSquareMask {

	public PrewittMask() {
		super(3);
		setMasks(new PrewittXMask(), new PrewittYMask());
	}
	class PrewittXMask extends SquareMask{

		public PrewittXMask() {
			super(3);
			set(0, 0, -1);
			set(0, 1, -1);
			set(0, 2, -1);
			for (int i = 0; i < 3; i++) {
				set(1, i, 0);
			}
			set(2, 0, 1);
			set(2, 1, 1);
			set(2, 2, 1);
		}
		
	}
	
	class PrewittYMask extends SquareMask{

		public PrewittYMask() {
			super(3);
			set(0, 0, -1);
			set(1, 0, -1);
			set(2, 0, -1);
			for (int i = 0; i < 3; i++) {
				set(i, 1, 0);
			}
			set(0, 2, 1);
			set(1, 2, 1);
			set(2, 2, 1);
		}
		
	}

}
