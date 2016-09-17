package masks;

public class LOGmask extends LaplacianGenericMask{

	
	public LOGmask(int size, double sigma) {
		super(size);
		int halfSize = (size-1)/2;
		for (int i = -halfSize; i <= halfSize; i++) {
			for (int j = -halfSize; j <= halfSize; j++) {
				double member1= -1/(Math.sqrt(2*Math.PI)* Math.pow(sigma, 3));
				double member2=2- (  (i*i+ j*j)/(sigma*sigma) );
				double member3=Math.exp(-(i*i+j*j)/(2*sigma*sigma));
				
				double normalValue =member1*member2*member3; 
				
				set(i+halfSize, j+halfSize, normalValue);					
			}
		}
		//normalizacion
//		double accum = 0;
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				accum += get(i, j);
//			}
//		}
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				set(i, j, get(i, j)/accum);
//				
//			}
//		}
	}
}
