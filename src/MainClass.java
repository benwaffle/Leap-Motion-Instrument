/*
 * Created Feb 23, 2012
 * (C) 2012 Ben Iofel, David Maginley, and Princeton Ferro
 * Made @ Monthly Music Hackathon in NYC
 */

import com.leapmotion.leap.*;
import java.io.*;
import java.util.ArrayList;

import javax.sound.midi.MidiUnavailableException;

/*
 * drums: kick, snare, hihat, crash
 */

public class MainClass {
	public static void main(String[] args) throws MidiUnavailableException {
		Lis lis = new Lis();
		Controller con = new Controller();

		con.addListener(lis);

		lis.audio = new Audio();
		
		System.out.println("Press any key to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("quitting");
		con.removeListener(lis);
	}
}

class Lis extends Listener{
	int octave = 4;
	LeapInstrument ins;
	Finger finger;
	Hand hand;
	Frame frame;
	int calibratedY;
	Thread t;
	ArrayList<Float> calibratingValues = new ArrayList<Float>();
	boolean calibrating = false, detectingTaps = false;

	float currentFingerX=0,currentFingerY=0,currentFingerZ=0;
	public Audio audio;

	//	instrument X axis mapping vars
	int xAxisMin = -200;
	int xAxisMax = 200;
	int numTapAreas = 7;
	int tapAreaSize = (xAxisMax-xAxisMin)/numTapAreas;	

	boolean drums = false; //false = piano

	//overriding default listener methods
	public void onInit(Controller controller){
		System.out.println("LEAP initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("LEAP Connected");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP); //enable only key tap gesture
		//calibrate Y value 
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("calibrating in\n3...");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
				System.out.println("2...");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
				System.out.println("1...");
				calibrating = true;
				try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
				//do after 2000 milliseconds
				int sum = 0, size = calibratingValues.size();
				for(int i=0;i<size;i++) sum += calibratingValues.get(i);	//add the y values
				if(size < 100){							//too few y values
					System.out.println("not enough y values. please try again");
					System.exit(0);
				} else {
					calibratedY = sum/size;				//avg of y values
					System.out.println("calibrated Y: " + calibratedY);
					if(calibratedY < 100){
						System.out.println("calibrated Y value is too low. please try again");
						System.exit(0);
					}
					calibrating = false;
					detectingTaps = true;
				}
			}
		});
		t.start();
		System.out.println("calibrated y: " + calibratedY);
	}

	public void onFrame(Controller controller){
		frame = controller.frame();
		GestureList ges = frame.gestures();
		finger = frame.fingers().get(0);
		
		if(calibrating){
			if(frame.fingers().count() != 1) System.out.println("wrong number of fingers. please only show 1");
			else calibratingValues.add(finger.tipPosition().getY());
		} else if (detectingTaps && !frame.fingers().empty()){
			// MAIN LOOP
			finger = frame.fingers().get(0);
			currentFingerX = finger.tipPosition().getX();
			currentFingerY = finger.tipPosition().getY();
			currentFingerZ = finger.tipPosition().getZ();
			if(calibratedY-finger.tipPosition().getY()>30){ // ON PRESS
				System.out.println("tap detected");
				float keyPressXPosition = finger.tipPosition().getX();
				int i = ((int)keyPressXPosition + xAxisMax) / tapAreaSize;
				if(!drums){
					try{
						int note = 0;
						switch(i) {
						case 0: note = 0; break;	//C
						case 1: note = 2; break;	//D
						case 2: note = 4; break;	//E
						case 3: note = 5; break;	//F
						case 4: note = 7; break;	//G
						case 5: note = 9; break;	//A
						case 6: note = 11; break;	//B
						}
						ins = new Piano(1);
						ins.playNote(note, octave);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String drum = "";
					switch(i) {
					case 0:
						drum = "Kick";
						break;
					case 1:
						drum = "Snare";
						break;
					case 2:
						drum = "HiHat";
						break;
					case 3:
						drum = "Crash";
						break;
					case 4:
						drum = "Floor Tom";
						break;
					case 5:
						drum = "Low Tom";
						break;
					case 6:
						drum = "High Tom";
						break;
					}
					try {
						ins = new DrumKit(1);
						ins.playDrum(drum);
						System.out.println("Playing " + drum);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void changeOctave(int change) {
		octave += change;
	}

	public void onDisconnect(Controller controller) {
		System.out.println("LEAP Disconnected");
	}
}