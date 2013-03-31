public interface LeapInstrument {
	
	public void playNote(String note, int octave) throws Exception;
	
	public void playNote(String note, int octave, double vol) throws Exception;
	
	public void playNote(int note, int octave) throws Exception;
	
	public void playNote(int note, int octave, double vol) throws Exception;
	
	public void playDrum(String drum) throws Exception;
	
	public void playDrum(String drum, double vol) throws Exception;
	
	public double getVol();
	
	public double setVol(double vol);
	
	public int getChannel();
	
}
