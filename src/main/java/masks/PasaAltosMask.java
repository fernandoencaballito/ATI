package masks;

public class PasaAltosMask extends SquareMask {

	public PasaAltosMask(int size) {
		super(size);
		int valorMedio = (size-1)/2;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i == valorMedio && j == valorMedio)
					set(i, j, ((double)size*size - 1)/(size*size));
				else
					set(i, j, -1.0/(size*size));
				System.out.println("("+i+","+j+"):"+get(i, j));
			}
		}
		
	}
	
	@Override
	public double[][] filterImage(double[][] image) {
		double[][] filteredImage = super.filterImage(image);
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		int halfSize = (size-1)/2;
		for (int i = halfSize; i < filteredImage.length - halfSize; i++) {
			for (int j = halfSize; j < filteredImage[i].length - halfSize; j++) {
				max = Math.max(max, filteredImage[i][j]);
				min = Math.min(min, filteredImage[i][j]);
			}
		}
		if(max < 255 && min > 0){
			return filteredImage;
		}
		double aux = 255/(max-min);
		for (int i = halfSize; i < filteredImage.length- halfSize; i++) {
			for (int j = halfSize; j < filteredImage[i].length - halfSize; j++) {
				filteredImage[i][j] = aux * (filteredImage[i][j] - min);
			}
		}
		return filteredImage;
	}
}
