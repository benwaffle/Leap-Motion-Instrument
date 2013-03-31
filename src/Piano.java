import javax.sound.midi.MidiUnavailableException;

public class Piano implements LeapInstrument {

	private String name = "Grand Piano";
	private Audio audio;
	private double vol = 0;
	
	public Piano() throws MidiUnavailableException {
		init();
	}
	
	public Piano(double vol) throws MidiUnavailableException {
		init();
		setVol(vol);
	}
	
	private void init() throws MidiUnavailableException {
		audio = new Audio(name);
	}
	
	public void playNote(String note, int octave) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public void playNote(String note, int octave, double vol) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public void playNote(int note, int octave) throws Exception {
		audio.playNote(note, octave, vol);
	}

	public void playNote(int note, int octave, double vol) throws Exception {
		audio.playNote(note, octave, vol);
	}
	
	public double getVol() {
		return vol;
	}

	public double setVol(double vol) {
		return this.vol = vol;
	}

	public int getChannel() {
		return audio.channelNum;
	}

	public void playDrum(String drum) throws Exception{
		throw new NotADrumException(name + " is not a drum!");
	}

	public void playDrum(String drum, double vol) throws Exception {
		throw new NotADrumException(name + " is not a drum!");
	}
	
	public String toString() {
		return name;
	}

}
