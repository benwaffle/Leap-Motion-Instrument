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
	Finger finger;
	Hand hand;
	//	float maxX=0,maxY=0,maxZ=0,minX=0,minY=0,minZ=0;
	float curx=0,cury=0,curz=0;
	public Audio audio;

	//	instrument X axis mapping vars
	int xAxisMin = -100;
	int xAxisMax = 100;
	int numTapAreas = 4;
	int tapAreaSize = (xAxisMax-xAxisMin)/numTapAreas;
	int[] tapAreas = new int[numTapAreas];
	
	
	boolean drums = true; //false = piano

	//overriding default listener methods
	public void onInit(Controller controller){
		System.out.println("LEAP initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("LEAP Connected");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP); //enable only key tap gesture
		for(int i=0;i<numTapAreas;i++){
			tapAreas[i] = xAxisMin+(i*tapAreaSize);
		}
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
					for(int i=0;i<numTapAreas;i++) if(gesFinPosX>tapAreas[i] && gesFinPosX<=tapAreas[i]+tapAreaSize){
						if(!drums){
							switch(i) {
							//**********************PIANO**********************
							case 0:	
								audio.changeInstrument("Grand Piano");
								try {
									audio.playNote("C", 4, 1);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							case 1: 
								audio.changeInstrument("Grand Piano");
								try {
									audio.playNote("D", 4, 1);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							case 2: 
								audio.changeInstrument("Grand Piano");
								try {
									audio.playNote("E", 4, 1);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							case 3:
								audio.changeInstrument("Grand Piano");
								try {
									audio.playNote("F", 4, 1);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							}
						} else {
							//******************DRUMS*********************
							switch(i){
							case 0:	//kick
								audio.changeInstrument("Kick");
								audio.playDrum(1);
								System.out.println("playing kick");
								break;
							case 1: //snare
								audio.changeInstrument("Snare");
								audio.playDrum(1);
								System.out.println("playing snare");
								break;
							case 2: //hihat
								audio.changeInstrument("HiHat");
								audio.playDrum(1);
								System.out.println("playing hihat");
								break;
							case 3: //crash
								audio.changeInstrument("Crash");
								audio.playDrum(1);
								System.out.println("playing crash");
								break;
							}
						}
					}
				}
			}
		}
	}


	public void onDisconnect(Controller controller) {
		System.out.println("LEAP Disconnected");
	}
}