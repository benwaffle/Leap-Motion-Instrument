
public class NotADrumExcpetion extends Exception {

	private static final long serialVersionUID = 4554485839751074094L;
	
	private String message = "Not a Drum!";
	
	public NotADrumExcpetion(String args) {
		message = args;
	}
	
	public String getMessage() {
		return message;
	}
	
}
