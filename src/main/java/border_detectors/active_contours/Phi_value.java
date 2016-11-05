package border_detectors.active_contours;
/*
* @author Fernando Bejarano
*/
public  enum Phi_value {BACKGROUD(3), L_OUT(1),L_IN(-1),OBJECT(-3);
	
	private final int value;
	
	Phi_value(int value){
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
	
	

};