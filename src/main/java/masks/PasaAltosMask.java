package masks;

public class PasaAltosMask extends SquareMask {

	public PasaAltosMask(int size) {
		super(size);
		int valorMedio = (size-1)/2;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i == valorMedio && j == valorMedio)
					set(i, j, (size*size - 1)/size*size);
				else
					set(i, j, -1/(size*size));				
			}
		}
	}

}
