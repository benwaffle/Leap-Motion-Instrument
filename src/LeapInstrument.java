/***************************
 * Leap Instrument (TBN)
****************************/

import com.leapmotion.leap.*;
import java.io.*;
import javax.sound.midi.MidiUnavailableException;

public class LeapInstrument extends Listener {
	GUI gui; //the interface for the program
	public void onInit(Controller controller) {
		//leap motion daemon initialization
		System.out.println("Listener initialized.");
	}
	public void onConnect(Controller controller) {
		//leap motion connect
		System.out.println("Leap Motion is connected.");
	}
	public void onFrame(Controller controller) {
		//leap motion frame - this is where main interaction is
		Frame frame = controller.frame(); //get frame instance of controller
		FingerList fingers = frame.fingers(); //get the number of fingers available
		for (int i=0; i<fingers.count(); i++) {
			//iterate through finger data and perform
			Finger finger = fingers.get(i);
			
		}
	}
	public void onDisconnect(Controller controller) {
		//leap motion disconnect
		System.out.println("Leap Motion has been disconnected.");
	}
	public LeapInstrument() {
		gui = new GUI();
	}
	public static void main(String[] args) throws MidiUnavailableException {
		LeapInstrument listener = new LeapInstrument();
		Controller con = new Controller(); //leap motion controller
		
		con.addListener(listener);
		
		System.out.println("Press any key to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Quitting");
		con.removeListener(listener);
	}
}
