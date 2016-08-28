package noise;
/*
* @author Fernando Bejarano
*/
public class Rayleigh {

	
	public static double generateRandomNumber(double xi){
		double x=Math.random();
		return xi * Math.sqrt(-2.0* Math.log(1.0-x));
	}
}
