
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
