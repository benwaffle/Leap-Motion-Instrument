import javax.sound.midi.MidiUnavailableException;

public class Piano implements Instrument {

	private String name = "Grand Piano";
	private Audio audio;
	private int vol = 0;
	
	public Piano() throws MidiUnavailableException {
		init();
	}
	
	public Piano(int vol) throws MidiUnavailableException {
		init();
		setVol(vol);
	}
	
	private void init() throws MidiUnavailableException {
		audio = new Audio(name);
	}
	
	public void playNote(String note, int octave) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public void playNote(String note, int octave, int vol) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public void playNote(int note, int octave) throws Exception {
		audio.playNote(note, octave, vol);
	}

	public void playNote(int note, int octave, int vol) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public int getVol() {
		return vol;
	}

	public int setVol(int vol) {
		return this.vol = vol;
	}

	public int getChannel() {
		return audio.channelNum;
	}

	public void playDrum(String drum) throws Exception{
		
	}

	public void playDrum(String drum, int vol) throws Exception {
		throw new NotADrumExcpetion(name + " is not a drum!");
	}
	
	public String toString() {
		return name;
	}

}
