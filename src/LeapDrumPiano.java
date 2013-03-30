/*
 * Created Feb 23, 2012
 * (C) 2012 Ben Iofel, David Maginley, and Princeton Ferro
 * Made @ Monthly Music Hackathon in NYC
 */

import com.leapmotion.leap.*;
import java.io.*;
import javax.sound.midi.MidiUnavailableException;

/*
 * drums: kick, snare, hihat, crash
 */

public class LeapDrumPiano {
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
	Instrument ins;
	Finger finger;
	Hand hand;
	//	float maxX=0,maxY=0,maxZ=0,minX=0,minY=0,minZ=0;
	float curx=0,cury=0,curz=0;
	public Audio audio;

	//	instrument X axis mapping vars
	int xAxisMin = -200;
	int xAxisMax = 200;
	int numTapAreas = 7;
	int tapAreaSize = (xAxisMax-xAxisMin)/numTapAreas;
//	int[] tapAreas = new int[numTapAreas];
	
	
	boolean drums = false; //false = piano

	//overriding default listener methods
	public void onInit(Controller controller){
		System.out.println("LEAP initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("LEAP Connected");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP); //enable only key tap gesture
//		for(int i=0;i<numTapAreas;i++){
//			tapAreas[i] = xAxisMin+(i*tapAreaSize);
//		}
	}

	public void onFrame(Controller controller){
		Frame frame = controller.frame();
		GestureList ges = frame.gestures();
		Gesture curGes;
		//		FingerList fingerlist = frame.fingers();

		//		System.out.print("Frame id: " + frame.id() + " timestamp: " + frame.timestamp());

		if(!frame.hands().empty()){ //some hands detected
			hand = frame.hands().get(0); //get 1st hand
		}
		if(!frame.fingers().empty()){ //some fingers detected
			finger = frame.fingers().get(0); //get 1st finger
		}

		if(!frame.fingers().empty()){
			curx = finger.tipPosition().getX();
			cury = finger.tipPosition().getY();
			curz = finger.tipPosition().getZ();
			if(cury<250){	//y limit
				if(ges.count() > 1) {		//only 1 tap at a time
					System.out.println("more than 1 gesture detected");
				} else if(ges.count()==1){
					System.out.println("tap detected");
					curGes = ges.get(0);
					float gesFinPosX = curGes.pointables().get(0).tipPosition().getX();
					//					System.out.println(curGes.type() + " at x: " + gesFinPos.getX() + " y: " + gesFinPos.getY() + " z: " +gesFinPos.getZ());
//					for(int i=0;i<numTapAreas;i++) if(gesFinPosX>tapAreas[i] && gesFinPosX<=tapAreas[i]+tapAreaSize){
					int i = ((int)gesFinPosX + xAxisMax) / tapAreaSize;
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
							System.out.println(i);
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
							drum = "Bass Drum 1";
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
					//					}
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