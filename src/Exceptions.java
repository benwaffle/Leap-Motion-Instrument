public class NotADrumException extends Exception {

	private static final long serialVersionUID = 4554485839751074094L;
	
	private String message = "Not a Drum!";
	
	public NotADrumException(String args) {
		message = args;
	}
	
	public String getMessage() {
		return message;
	}
	
}

public class NotAPitchedInstrumentException extends Exception {
	
	private static final long serialVersionUID = -9036848803641944909L;
	private String message = "Not a Pitched Instrument!";
	
	public NotAPitchedInstrumentException(String args) {
		message = args;
	}
	
	public String getMessage() {
		return message;
	}
	
}