import javax.swing.*;
import java.awt.*;

public class GUI extends Thread{
	JFrame frmLeapInstrument;
	public JSlider slider;
	public LeapVisualizerPanel visualizerPanel;

	public static void main(String[] args) {
		GUI window = new GUI();
		window.frmLeapInstrument.setVisible(true);
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frmLeapInstrument = new JFrame();									//JFrame
		frmLeapInstrument.setTitle("Leap Instrument");
		frmLeapInstrument.setResizable(false);
		frmLeapInstrument.setBounds(100, 100, 800, 600);
		frmLeapInstrument.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLeapInstrument.getContentPane().setLayout(null);

		JLabel lblLeapInstrument = new JLabel("Leap Instrument");			//JLabel "leap Instrument"
		lblLeapInstrument.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblLeapInstrument.setBounds(29, 6, 187, 45);
		frmLeapInstrument.getContentPane().add(lblLeapInstrument);

	slider = new JSlider(0, 100, 100);										//JSlider
		slider.setBounds(604, 22, 190, 29);
		frmLeapInstrument.getContentPane().add(slider);

		JLabel lblVolume = new JLabel("Volume");							//JLabel "volume"
		lblVolume.setBounds(674, 10, 61, 16);
		frmLeapInstrument.getContentPane().add(lblVolume);

		JComboBox<String> comboBox = new JComboBox<String>();				//Dropdown instrument selection
		comboBox.setBounds(421, 18, 175, 29);
		comboBox.addItem("Piano");
		comboBox.addItem("DrumKit");
		frmLeapInstrument.getContentPane().add(comboBox);

		visualizerPanel = new LeapVisualizerPanel();						//visualizer
		visualizerPanel.setBounds(0, 63, 800, 520);
		frmLeapInstrument.getContentPane().add(visualizerPanel);
	}
}
class LeapVisualizerPanel extends JComponent{								//component for drawing stuff
	private static final long serialVersionUID = 1337L;
	int fingerX, fingerY;													//x and y coords for circle

	public LeapVisualizerPanel() {
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 521);

		g.setColor(Color.cyan);
		g.fillOval(fingerX, fingerY, 40, 40);
	}
	
	public int getFingerX() {
		return fingerX;
	}
	public void setFingerX(int fingerX) {
		this.fingerX = fingerX;
	}
	public int getFingerY() {
		return fingerY;
	}
	public void setFingerY(int fingerY) {
		this.fingerY = fingerY;
	}
}
