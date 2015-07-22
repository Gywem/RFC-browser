package project3OOPT.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import project3OOPT.manager.RfcManager;
import project3OOPT.persistance.model.Rfc;
import project3OOPT.ui.view.BrowserWindow;
/*
 * Project 3 - OOPT
 * Ignacio José Codoñer Gil - st number : 0416040
 * This RFC browser has all features required to get the high score. You are able to:
 * 	find a RFC by number in the text field from top
 * 	find a RFC by title (particular RFC) if you put a string in the text field from top
 * 	Showing a RFC and a table of results
 * 	Navigating using keyboard keys when you are seeing a specific RFC
 * 	use the back and forward functionality
 * 	search within a RFC view. The words searched will be bold.
 * 
 * It has been used the UI framework, Swing. Besides, for the functionality of search the plugin Jsoup, which allow to
 * manage easily the html content from a specific URL.
 * */

/*
 * Chat Controller 
 * Class in charge for listen the events from the views, manage the rfc data and update the views
 * */
public class BrowserController {
	
	private static BrowserController Singleton;
	public ActionListener findButtonL;
	
	public static BrowserController getInstance(){
		if(Singleton == null) return new BrowserController();
		else return Singleton;
	}
	
	private BrowserWindow mainWindow;
	private RfcManager RfcM;
	
	private ICommand actual;
	private Deque<ICommand> backCmds;
	private Deque<ICommand> forwardCmds;
	
	private BrowserController(){
		this.mainWindow = new BrowserWindow(this);
		this.RfcM = RfcManager.getInstance();
		this.backCmds = new ArrayDeque<ICommand>();
		this.forwardCmds = new ArrayDeque<ICommand>();
	}

	public void load() {
		mainWindow.setVisible(true);		
	}
	
	public void updateRfcByNumber(int number) {
		Rfc rfc = RfcM.getRfcByNumber(number);
		mainWindow.updateRfcView(rfc);
	}
	
	public void onFindButtonAction(ActionEvent event){
		String rfc = mainWindow.getRfcBrowser();
		
		if(this.actual != null) {
			this.backCmds.push(this.actual);
		}
		
		try{
			int numberRfc = Integer.parseInt(rfc);
			
			this.actual = new ChangeRFCCommand(this, numberRfc);
			
			this.updateRfcByNumber(numberRfc);
		} catch (NumberFormatException e) {
			String name = rfc;
			
			this.actual = new ListRFCCommand(this, name);
			
			this.loadRcfsByName(name);
		}
	}

	public void loadRcfsByName(String name) {
		List<Rfc> rfcs = RfcM.getRfcsByName(name);
		mainWindow.loadRfcsView(name, rfcs);
	}

	public void onBackButtonAction(ActionEvent arg0) {
		if(!this.backCmds.isEmpty()) {
			ICommand bc = this.backCmds.pop();
			
			bc.execute();
			
			this.forwardCmds.push(this.actual);
			this.actual = bc;
		}		
	}
	
	public void onForwardButtonAction(ActionEvent arg0) {
		if(!this.forwardCmds.isEmpty()) {
			ICommand bc = this.forwardCmds.pop();
			
			bc.execute();
			
			this.backCmds.push(this.actual);
			this.actual = bc;
		}		
	}
	
	public void onClickRowRfcTableAction(int numberRfc, MouseEvent arg0) {
		this.backCmds.push(this.actual);
		
		this.actual = new ChangeRFCCommand(this, numberRfc);
		
		this.updateRfcByNumber(numberRfc);
		
	}
	
	public BrowserWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(BrowserWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void onFindInRFCButtonAction(ActionEvent arg0) {
		mainWindow.highlightInRfc();
	}

	public void onCancelInRFCButtonAction(ActionEvent arg0) {
		mainWindow.getBackNoStyleText();
	}
}
