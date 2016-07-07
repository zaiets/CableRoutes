package custom.exceptions;

public class CableNotTracedException extends Exception {
	public CableNotTracedException (String info) {
		super("Cable " + info + " is not traced.");
	}
	
}
