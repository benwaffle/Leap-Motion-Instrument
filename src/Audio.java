/*
 * Created Feb 23, 2012
 * (C) 2012 Princeton Ferro
 * Made @ Monthly Music Hackathon in NYC
 * Version 0.1
 */

import java.util.*;
import javax.sound.midi.*;

public class Audio {
	public Synthesizer synth;
	public MidiChannel channel;
	int channelNum = 0;
	int currentPercussionInstrument = 0;
	HashMap<String,int[]> instruments = new HashMap<String, int[]>();
	public void playNote(String note, int octave, double volume) throws Exception {
		//synthesize a note, based on parameters
		
		int keyCode = 0;
		/********************************
		 * keyCode represents key type
		 * 0 - C
		 * 1 - C#
		 * 2 - D
		 * 3 - Eb
		 * 4 - E
		 * 5 - F
		 * 6 - F#
		 * 7 - G
		 * 8 - Ab
		 * 9 - A
		 * 10 - Bb
		 * 11 - B
		 * ---------------
		 * octave starts at 0: C, and can
		 * be negative (bass) or positive
		 *********************************/
		switch(note) {
			case "C": keyCode = 0; break;
			case "C#": 
				case "Db": keyCode = 1; break;
			case "D": keyCode = 2; break;
			case "Eb":
				case "D#": keyCode = 3; break;
			case "E": keyCode = 4; break;
			case "F": keyCode = 5; break;
			case "F#": 
				case "Gb": keyCode = 6; break;
			case "G": keyCode = 7; break;
			case "Ab":
				case "G#": keyCode = 8; break;
			case "A": keyCode = 9; break;
			case "Bb":
				case "A#": keyCode = 10; break;
			case "B": keyCode = 11; break;
		}
		
		int actualKey = (12*octave)+keyCode;
		channel.noteOn(actualKey, (int)(volume*100));
		
		if (channelNum == 0)
			System.out.println("Note "+note+" ("+keyCode+") is being played currently at octave "+octave+".");
		else System.out.println("Current Instrument: "+actualKey);
	}
	public void playNote(int note, int octave, double volume) throws Exception {
		String kNote = "";
		switch(note%12) {
		case 0: kNote = "C"; break;
		case 1: kNote = "C#"; break;
		case 2: kNote = "D"; break;
		case 3: kNote = "Eb"; break;
		case 4: kNote = "E"; break;
		case 5: kNote = "F"; break;
		case 6: kNote = "F#"; break;
		case 7: kNote = "G"; break;
		case 8: kNote = "Ab"; break;
		case 9: kNote = "A"; break;
		case 10: kNote = "Bb"; break;
		case 11: kNote = "B"; break;
		}
		playNote(kNote, octave, volume);
	}
	public void playDrum(double volume) {
		channel.noteOn(currentPercussionInstrument, (int)(volume*100));
	}
	public void changeInstrument(String instrumentType) {
		//change the instrument type, according to a string
		/*
		Instrument instrument = instruments[0];
		for (Instrument n:instruments) //search for instrument
			if (n.getName().equals(instrumentType)) {
				instrument = n;
				break;
			}
		synth.loadInstrument(instrument);
		System.out.println("Changed instrument to "+instrument.getName()+".");
		*/
		channelNum = instruments.get(instrumentType)[0]; //channel num
		currentPercussionInstrument = instruments.get(instrumentType)[1]; //current instrument
		channel = synth.getChannels()[channelNum];
	}
	public Audio() throws MidiUnavailableException {
		init();
	}
	public Audio(String i) throws MidiUnavailableException {
		init();
		changeInstrument(i);
	}
	private void init() throws MidiUnavailableException {
		synth = MidiSystem.getSynthesizer();
		synth.open();
		channel = synth.getChannels()[channelNum]; //set to piano
		
		//put in instrument data
		instruments.put("Kick", new int[] {9, 35}); //{channel,instrument_type}
		instruments.put("HiHat", new int[] {9, 42});
		instruments.put("Snare", new int[] {9, 40});
		instruments.put("Crash", new int[] {9, 55});
		instruments.put("Floor Tom", new int[] {9, 41});
		instruments.put("Low Tom", new int[] {9, 45});
		instruments.put("High Tom", new int[] {9, 50});
		instruments.put("Grand Piano", new int[] {0, 0});
	}
}
