import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;


public class GUI extends Thread{

	JFrame frmLeapInstrument;
	public Canvas canvas;
	private JSlider slider;
	int numfingers;
	int[][] fingerCoords;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmLeapInstrument.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(int numfingers, int[][] fingerCoords) {
		initialize();
		this.numfingers = numfingers;
		this.fingerCoords = fingerCoords;
	}
	
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLeapInstrument = new JFrame();
		frmLeapInstrument.setTitle("Leap Instrument");
		frmLeapInstrument.setResizable(false);
		frmLeapInstrument.setBounds(100, 100, 800, 600);
		frmLeapInstrument.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLeapInstrument.getContentPane().setLayout(null);
		
		canvas = new Canvas();
		canvas.setBounds(0, 57, 800, 521);
		frmLeapInstrument.getContentPane().add(canvas);
		
		JLabel lblLeapInstrument = new JLabel("Leap Instrument");
		lblLeapInstrument.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblLeapInstrument.setBounds(29, 6, 187, 45);
		frmLeapInstrument.getContentPane().add(lblLeapInstrument);
		
		slider = new JSlider(0, 100, 100);
		slider.setBounds(604, 22, 190, 29);
		frmLeapInstrument.getContentPane().add(slider);
		
		JLabel lblVolume = new JLabel("Volume");
		lblVolume.setBounds(674, 6, 61, 16);
		frmLeapInstrument.getContentPane().add(lblVolume);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(421, 18, 175, 29);
		comboBox.addItem("Piano");
		comboBox.addItem("DrumKit");
		frmLeapInstrument.getContentPane().add(comboBox);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.blue);
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setSlider(JSlider slider) {
		this.slider = slider;
	}
}
