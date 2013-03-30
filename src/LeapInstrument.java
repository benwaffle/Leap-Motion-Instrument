public interface LeapInstrument {
	
	public void playNote(String note, int octave) throws Exception;
	
	public void playNote(String note, int octave, int vol) throws Exception;
	
	public void playNote(int note, int octave) throws Exception;
	
	public void playNote(int note, int octave, int vol) throws Exception;
	
	public void playDrum(String drum) throws Exception;
	
	public void playDrum(String drum, int vol) throws Exception;
	
	public int getVol();
	
	public int setVol(int vol);
	
	public int getChannel();
	
}
