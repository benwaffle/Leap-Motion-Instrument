import java.util.*;

//instrument info

public class Instrument {
	/********************
	 * Certain values, such as instrument type,
	 * are to be kept static from their initial
	 * declarations.
	*********************/
	private String name = "";
	public String getName() {
		return name;
	}
	private String type = "pitched"; //can also be "not-pitched"
	public String getType() {
		return type;
	}
	public double fingerDistance;
		/* this is the default finger distance necessary to change
		 * notes, such as the space between piano keys or harp
		 * strings
		 */
	public static String InstrumentNameToPitched(String name) {
		Map<String,String> nameList = new HashMap<String,String>();
		nameList.put("Piano", "pitched");
		nameList.put("Drum Kit", "not-pitched");
		return nameList.get(name);
	}
	public Instrument(String name) throws Exception {
		/* we can determine if the instrument is pitched or not,
		   based on its name */
		String pitched = InstrumentNameToPitched(name);
		if (pitched == null)
			throw new InvalidInstrumentNameException();
		
	}
}

class InvalidInstrumentNameException extends Exception {
	private static final long serialVersionUID = 3048234708234800283L;
	private String message = "Invalid instrument name given.";
	public String getMessage() {
		return message;
	}
}

class NotADrumException extends Exception {
	private static final long serialVersionUID = 4554485839751074094L;
	private String message = "Not a drum!";
	public String getMessage() {
		return message;
	}
}

class NotAPitchedInstrumentException extends Exception {
	private static final long serialVersionUID = -9036848803641944909L;
	private String message = "Not a pitched instrument!";
	public String getMessage() {
		return message;
	}
}
