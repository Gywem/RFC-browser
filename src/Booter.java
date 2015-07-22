import java.awt.EventQueue;

import project3OOPT.ui.controller.BrowserController;

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
 * The main class that boots the application controllers
 * */
public class Booter {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowserController broCntrl = BrowserController.getInstance();
					broCntrl.load();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
