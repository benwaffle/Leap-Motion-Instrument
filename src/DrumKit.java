import javax.sound.midi.MidiUnavailableException;


public class DrumKit implements Instrument {

	private String name = "Drum Kit";
	private Audio audio;
	private int vol = 0;
	
	public DrumKit() throws MidiUnavailableException {
		init();
	}
	
	public DrumKit(int vol) throws MidiUnavailableException {
		init();
		setVol(vol);
	}
	
	private void init() throws MidiUnavailableException {
		audio = new Audio();
	}
	
	public void playNote(String note, int octave) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playNote(String note, int octave, int vol) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}
	
	public void playNote(int note, int octave) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playNote(int note, int octave, int vol) throws Exception {
		throw new NotAPitchedInstrumentException(name + " is not a pitched Instrument!");
	}

	public void playDrum(String drum) throws Exception {
		audio.changeInstrument(drum);
		audio.playDrum(vol);
	}

	public void playDrum(String drum, int vol) throws Exception {
		audio.changeInstrument(drum);
		audio.playDrum(vol);
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

}
