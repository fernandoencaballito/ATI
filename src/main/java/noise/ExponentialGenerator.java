package noise;
/*
* @author Fernando Bejarano
*/
public class ExponentialGenerator {

	
	
	public static double generateRandomNumber(double lambda,double x){
		return - Math.log(x)/lambda;
	}
}
