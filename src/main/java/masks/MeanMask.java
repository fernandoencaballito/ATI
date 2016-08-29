package masks;

public class MeanMask extends SquareMask {

	public MeanMask(int size) {
		super(size);
		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				set(i, j, 1.0/(size*size));
			}
		}
	}

}
