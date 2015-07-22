package project3OOPT.ui.controller;

/*
 * ListRFCCommand 
 * Encapsulation of the list RFC view. It is used for ensuring the back and forward functionality
 * */
public class ListRFCCommand implements ICommand {
	private BrowserController bcont;
	private String name;

	public ListRFCCommand(BrowserController bcont, String name) {
		this.bcont = bcont;
		this.name = name;
	}
	
	public void execute(){
		bcont.loadRcfsByName(this.name);
	}
}
