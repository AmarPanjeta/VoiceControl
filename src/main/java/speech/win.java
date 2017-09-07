package speech;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class win {

	private JFrame frame;
	private Boolean recongitionStarted;
	private RecognitionThreadService rts;
	private JLabel instructionsLabel;
	private JLabel inputLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					win window = new win();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public win() {
		recongitionStarted = false;
		rts = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAutoRequestFocus(false);
		frame.setBounds(100, 100, 492, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		instructionsLabel = new JLabel("Instructions");
		instructionsLabel.setVerticalAlignment(SwingConstants.TOP);
		instructionsLabel.setBounds(12, 45, 245, 132);
		frame.getContentPane().add(instructionsLabel);
		
		inputLabel = new JLabel("");
		inputLabel.setVerticalAlignment(SwingConstants.TOP);
		inputLabel.setBounds(269, 45, 199, 107);
		frame.getContentPane().add(inputLabel);
		
		JButton btnNewButton = new JButton("Start voice control");
		btnNewButton.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!recongitionStarted){
					recongitionStarted = true;
					FlowControlService fcs=new FlowControlService(instructionsLabel, inputLabel);
					rts=new RecognitionThreadService("voice", fcs);
					rts.start();
				}
				else { 
					rts.stopRecognition();
					recongitionStarted = false;
				}
			}
		});
		btnNewButton.setBounds(151, 301, 188, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnStopVoiceControl = new JButton("Stop voice control");
		btnStopVoiceControl.setBounds(151, 333, 188, 25);
		btnStopVoiceControl.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				rts.stopRecognition();
				
			}
		});
		frame.getContentPane().add(btnStopVoiceControl);	
	}
}
