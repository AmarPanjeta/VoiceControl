package speech;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FlowControlService {
	private Integer step;
	private Double balance;
	private String previousCommand;
	
	private JLabel instructionsLabel;
	private JLabel inputLabel;
	
    private static final Map<Integer, String> INSTRUCTIONS =
	        new HashMap<Integer, String>();

	    static {
	        INSTRUCTIONS.put(1,"<html>Commands:<br> Go to the bank account <br> Open notepad <br> Exit the program </html>");
	        INSTRUCTIONS.put(2,"<html>This is bank account voice menu: <br> Example: balance <br>Example: withdraw zero point five <br> Example: deposit one two three <br> Example: back </html>");
	    }
	
	public FlowControlService(JLabel instructionsLabel,JLabel inputLabel){
		this.instructionsLabel=instructionsLabel;
		this.inputLabel=inputLabel;
		this.step=1;
		this.balance=0.00;
		this.instructionsLabel.setText(INSTRUCTIONS.get(step));
		
	}
	
	public void next(String input){
		if(step==1 && input.toLowerCase().contains("bank account")){
			advanceStepAndSetInstructions(2);
		}
		else if(step==1 && input.toLowerCase().contains("open notepad")){
			Runtime r=Runtime.getRuntime();
			Process p;
			try {
				if(System.getProperty("os.name").toLowerCase().contains("windows")) {
					p = r.exec("notepad");
				}else if(System.getProperty("os.name").toLowerCase().contains("linux")) {
					p=r.exec("gedit");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(step==1 && input.toLowerCase().contains("exit")){
			System.out.println("exit");
			System.exit(0);
		}
		else if(step==2 && input.toLowerCase().contains("back")){
			advanceStepAndSetInstructions(1);
		}
		else if(step==2 && input.toLowerCase().contains("balance")){
			outputBalance();
		}
		else if(step==2 && input.toLowerCase().contains("deposit")){
			advanceStepAndSetInput(3, input);
		}
		else if(step==2 && input.toLowerCase().contains("withdraw")){
			advanceStepAndSetInput(4, input);
		}
		else if((step==3 || step==4) && input.toLowerCase().equals("no")){
			clearInput();
		}
		else if((step==3 || step==4) && input.toLowerCase().equals("yes")){
			executeStoredBankCommand();
		}
		
	}
	
	public void advanceStepAndSetInstructions(int step){
		this.step=step;
		this.instructionsLabel.setText(INSTRUCTIONS.get(step));
		this.inputLabel.setText("");
	}
	
	public void advanceStepAndSetInput(int step, String command){
		this.step=step;
		this.inputLabel.setText("<html>Did you mean:[Yes/No] <br>"+command +"<br></html>");
		this.previousCommand=command;
	}
	
	public void outputBalance(){
		this.inputLabel.setText("Balance: "+ balance.toString());
	}
	
	public void clearInput(){
		this.previousCommand="";
		this.inputLabel.setText("");
		this.step=2;
	}
	
	public void executeStoredBankCommand(){
		if(!previousCommand.isEmpty()){
			Double number=StringToNumberUtils.parseNumber(previousCommand.split("\\s"));
			if(previousCommand.contains("deposit")){
				balance+=number;
				inputLabel.setText("Deposited: "+ number.toString());
			}
			else{
				balance-=number;
				inputLabel.setText("Withdrawn: "+ number.toString());
			}
			previousCommand="";
			step=2;
		}
	}
	
	public int getStep(){
		return step;
	}
	
	public void setStep(int step){
		this.step=step;
	}
}
