/*
 * (C) 2012 Ben Iofel, David Maginley, Princeton Ferro, and Zach Kaplan
 */

import com.leapmotion.leap.*;
import java.io.*;
import java.util.ArrayList;
import javax.sound.midi.MidiUnavailableException;

public class MainClass {
	public static void main(String[] args) throws MidiUnavailableException {
		Lis lis = new Lis();
		Controller con = new Controller();

		con.addListener(lis);
		
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
	int octave = 4;		//default octave
	LeapInstrument ins;	//instrument object
	Finger finger;		//main finger that is pressing keys
	Frame frame;		//Leap frame
	int calibratedY;	//calibrated Y value
	Thread t;			//thread for calibration
	ArrayList<Float> calibratingValues = new ArrayList<Float>();	//arraylist to hold y values, which are then averaged to calibratedY
	String state = "none";											//application state
	double volume = 1;												//volume from jslider
	GUI gui;														//gui
	double currentFingerX=0,currentFingerY=0,currentFingerZ=0;		//the finger's x,y, and z coordinates USE IN CANVAS
	
	//the variables for the different areas where the user can press keys
	int xAxisMin = -200;
	int xAxisMax = 200;
	int numTapAreas = 7;
	int tapAreaSize = (xAxisMax-xAxisMin)/numTapAreas;

	String instrument = "Piano";	//default instrument
	/*
	 * options:
	 * -Piano
	 * -DrumKit
	 */

	public void onInit(Controller controller){
		System.out.println("LEAP initialized");
		gui = new GUI();
		gui.frmLeapInstrument.setVisible(true);
	}

	public void onConnect(Controller controller) {	//when the leap is connected
		System.out.println("LEAP Connected");
		try {
			ins = new Piano(volume);				//initialize the ins instrument
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}
		t = new Thread(new Runnable() {				//thread to calibrate Y value
			@Override
			public void run() {
				//counter
				System.out.println("calibrating in 3...");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
				System.out.println("2...");
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
				System.out.println("1...");
				
				state = "calibrate";				//start calibrating
				try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
				//do after 2000 milliseconds
				int sum = 0, size = calibratingValues.size();	//sum --> sum of y values; size --> size of arraylist
				for(int i=0;i<size;i++) sum += calibratingValues.get(i);	//add up the y values
				calibratedY = sum/size;										//get the average of y values
				System.out.println("calibrated Y: " + calibratedY);
				state = "detectTaps";				//change state from calibrating to main loop. onFrame() will detect this and stop calibrating
				if(calibratedY < 100){										//if the calibrated Y value is too low (user should move finger higher)
					System.out.println("calibrated Y value is too low. please try again");
					System.exit(0);
				}
				
			}
		});
		t.start();									//start this thread
	}

	public void onFrame(Controller controller){
		frame = controller.frame();					//update the frame object
		finger = frame.fingers().get(0);			//update finger
		volume = gui.slider.getValue()/100.0;		//update volume
		ins.setVol(volume);
		gui.visualizerPanel.repaint();
		instrument = gui.comboBox.getSelectedItem().toString();
		
		currentFingerX = finger.tipPosition().getX();	//finger coordinate X
		currentFingerY = finger.tipPosition().getY();	//finger coordinate Y
//		currentFingerZ = finger.tipPosition().getZ();	//finger coordinate Z

		gui.visualizerPanel.setFingerX((int)map((long)currentFingerX, xAxisMin, xAxisMax, 0, 800));
		gui.visualizerPanel.setFingerY(520-(int)map((long)currentFingerY, 20, 250, 0, 520));
		
		if(state.equals("calibrate")){																					//if we are calibrating
			if(frame.fingers().count() != 1) System.out.println("wrong number of fingers. please only show 1");			//only allow 1 finger
			else calibratingValues.add(finger.tipPosition().getY());													//if we only have 1 finger, add the y value to the arraylist
		} else if (state.equals("detectTaps") && !frame.fingers().empty()){
			if(calibratedY-currentFingerY>30){									//if the finger goes 30 units below the calibrated (default) value
				int i = ((int)currentFingerX + xAxisMax) / tapAreaSize;			//get the index of the area where the finger is "pressing" down
				if(!ins.getClass().getName().equals(instrument)){				//if the ins object is different that the instrument variable
					try{
						switch(instrument){										//change the instrument
						case "Piano":
							ins = new Piano(volume);
							break;
						case "DrumKit":
							ins = new DrumKit(volume);
							break;
						}
					} catch (MidiUnavailableException e) { e.printStackTrace(); } 
				}
				if(!instrument.equals("DrumKit")){									//if the instrument is not a drumkit and therefore doesn't use 7 notes of 
					try{															//and therefore doesn't use 7 notes of different pitches
						int note = 0;
						switch(i) {													//get the note number for playNote()
						case 0: note = 0; break;	//C
						case 1: note = 2; break;	//D
						case 2: note = 4; break;	//E
						case 3: note = 5; break;	//F
						case 4: note = 7; break;	//G
						case 5: note = 9; break;	//A
						case 6: note = 11; break;	//B
						}
						ins.playNote(note, octave);				//play the note
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {										//if it is a drum
					String drum = "";
					switch(i) {									//get the type of drum
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
						ins.playDrum(drum);			//play the drum
						System.out.println("Playing " + drum);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				//code where !press
			}
		}
	}

	private void changeOctave(int change) {		//TODO: implement 4 finger swipe right or left to change octave  
		octave += change;
	}

	public void onDisconnect(Controller controller) {
		System.out.println("LEAP Disconnected");
	}
	
	public long map(long x, long in_min, long in_max, long out_min, long out_max) {
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
}