package project3OOPT.ui.controller;

/*
 * ChangeRFCCommand 
 * Encapsulation of the update RFC view. It is used for ensuring the back and forward functionality
 * */
public class ChangeRFCCommand implements ICommand {
	private BrowserController bcont;
	private int number;

	public ChangeRFCCommand(BrowserController bcont, int number) {
		this.bcont = bcont;
		this.number = number;
	}
	
	public void execute(){
		bcont.updateRfcByNumber(this.number);
	}
}
