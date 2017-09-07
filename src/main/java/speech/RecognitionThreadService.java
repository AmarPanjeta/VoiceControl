package speech;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class RecognitionThreadService extends Thread{
	
	private Thread t;
	private String threadName;
	private volatile Boolean run=false;
	private FlowControlService flowControlService;
	
	public RecognitionThreadService(String name,FlowControlService flowControlService) {
		this.threadName = name;
		this.flowControlService=flowControlService;
	    System.out.println("Creating " +  threadName );
	}
	@Override
	public void run() {
		System.out.println("radi");
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        configuration.setGrammarPath("resource:/dialog/");
        configuration.setUseGrammar(true);
        LiveSpeechRecognizer recognizer;
		try {
			configuration.setGrammarName("dialog");
			recognizer = new LiveSpeechRecognizer(configuration);
			// Start recognition process pruning previously cached data.
		     recognizer.startRecognition(true);
		     SpeechResult result;
		     System.out.println("started!");
		     while ((result = recognizer.getResult()) != null && run == true) {
		    	    System.out.println(result.getHypothesis());
		    	 	flowControlService.next(result.getHypothesis());
		    	 	
		    	 
		    	}
		     // Pause recognition process. It can be resumed then with startRecognition(false).
		     recognizer.stopRecognition();
		     System.out.println("XOXO");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void start() {
		if(t == null) {
          t = new Thread (this, threadName);
          run = true;
          t.start ();
		}
	}
	
	public synchronized void stopRecognition(){
		run = false;
		System.out.println("to je to");
	}
}


