import javax.sound.midi.MidiUnavailableException;

public class DrumKit implements LeapInstrument {

	private String name = "Drum Kit";
	private Audio audio;
	private double vol = 0;
	
	public DrumKit() throws MidiUnavailableException {
		init();
	}
	
	public DrumKit(double vol) throws MidiUnavailableException {
		init();
		setVol(vol);
	}
	
	private void init() throws MidiUnavailableException {
		audio = new Audio();
	}
	
	public void playNote(String note, int octave) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playNote(String note, int octave, double vol) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}
	
	public void playNote(int note, int octave) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playNote(int note, int octave, double vol) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playDrum(String drum) throws Exception {
		audio.changeInstrument(drum);
		audio.playDrum(vol);
	}

	public void playDrum(String drum, double vol) throws Exception {
		audio.changeInstrument(drum);
		audio.playDrum(vol);
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
	
	public String toString() {
		return name;
	}

}
