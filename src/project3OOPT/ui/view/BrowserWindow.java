package project3OOPT.ui.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Point;
import java.awt.TextField;
import java.awt.Label;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import project3OOPT.persistance.model.Rfc;
import project3OOPT.ui.controller.BrowserController;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/*
 * BrowserWindow
 * Contains the definition of view components, its configuration in the screen and the functionality for changing them.
 * */
public class BrowserWindow extends JFrame {

	private JPanel contentPane;
	
	private TextField rfcBrowserTF;
	
	private JTextPane browserTextP;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	
	private BrowserController bCnt;
	private JButton btnBack;
	private JButton btnForward;
	private JTable table;
	private JPanel panel_1;
	private JPanel panel_2;
	
	private CardLayout cl;
	private JLabel lblSearch;
	private JTextField textField;
	private JButton btnFindWithinRfc;
	private JButton btnCancel;
	
	private String currentCardSection;
	
	private String browserTextNoStyle;
	
	// Listeners
	public class findButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			bCnt.onFindButtonAction(arg0);
		}
	}
	
	public class backButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			bCnt.onBackButtonAction(arg0);
		}
	}
	
	public class forwardButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			bCnt.onForwardButtonAction(arg0);
		}
	}
	
	public class findInRFCButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			bCnt.onFindInRFCButtonAction(arg0);
		}
	}
	
	public class CancelInRFCButtonL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			bCnt.onCancelInRFCButtonAction(arg0);
		}
	}


	/**
	 * Create the frame.
	 */
	public BrowserWindow(final BrowserController bCnt) {
		this.bCnt = bCnt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		Label label = new Label("RFC");
		panel.add(label);
		
		rfcBrowserTF = new TextField();
		panel.add(rfcBrowserTF);
		
		JButton btnNewButton = new JButton("Find");
		panel.add(btnNewButton);
		
		btnBack = new JButton("<- Back");
		panel.add(btnBack);
		btnBack.addActionListener(new backButtonL());
		
		btnForward = new JButton("Forward ->");
		panel.add(btnForward);
		btnForward.addActionListener(new forwardButtonL());
		
		
		
		btnNewButton.addActionListener(new findButtonL());
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblSearch = new JLabel("Search");
		panel_2.add(lblSearch);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		btnFindWithinRfc = new JButton("Find within RFC");
		panel_2.add(btnFindWithinRfc);
		
		btnFindWithinRfc.addActionListener(new findInRFCButtonL());
		
		btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);
		
		btnCancel.addActionListener(new CancelInRFCButtonL());
		
		browserTextP = new JTextPane();
		browserTextP.setEditable(false);
		browserTextP.addKeyListener(new KeyListener()
        {
            @Override 
            public void keyPressed(KeyEvent e)
            {
                int keyCode = e.getKeyCode();
                Point pActual = scrollPane.getViewport().getViewPosition();
                
                switch( keyCode ) { 
                    case KeyEvent.VK_UP:
                    	pActual.y = Math.max(0,pActual.y - 100);
                        break;
                    case KeyEvent.VK_DOWN:
                    	pActual.y = Math.min(pActual.y+100, browserTextP.getHeight()-scrollPane.getHeight());
                        break;
                 }
                scrollPane.getViewport().setViewPosition(pActual);
            }

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
      });
		browserTextP.setContentType("text/html");
		panel_1.setLayout(new CardLayout(0, 0));
		
		
		scrollPane = new JScrollPane(browserTextP);
		panel_1.add(scrollPane, "name_293710737869");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rfc n\u00BA", "Title"
			}
		){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        DefaultTableModel model = (DefaultTableModel)table.getModel();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		            
		            int rfcNum = (int) model.getValueAt(row, 0);
		            
		            bCnt.onClickRowRfcTableAction(rfcNum, me);
		        }
		    }
		});
		
		scrollPane2 = new JScrollPane(table);
		panel_1.add(scrollPane2, "name_302444747942");
		
		this.cl = (CardLayout)(panel_1.getLayout());
	}
	
	public String getRfcBrowser() {
		return rfcBrowserTF.getText();
	}

	public void updateRfcView(Rfc rfc) {
		this.cl.show(panel_1, "name_293710737869");
		currentCardSection = "name_293710737869";
		updateContent();
		
		if(rfc != null) {
			rfcBrowserTF.setText(rfc.getNumber()+"");
			rfcBrowserTF.revalidate();
			rfcBrowserTF.repaint();
			
			browserTextNoStyle = rfc.getBodyRfc();
			browserTextP.setText(browserTextNoStyle);
			
			scrollPane.getViewport().setViewPosition(new Point(0,0));
			
			browserTextP.updateUI();
			scrollPane.requestFocus();
		} else {
			browserTextP.setText("Sorry. The RFC with this number was not found.");
		}
	}
	
	public void updateContent(){
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	public void loadRfcsView(String name, List<Rfc> rcfs) {
		this.cl.show(panel_1, "name_302444747942");
		currentCardSection = "name_302444747942";
		updateContent();
		
		rfcBrowserTF.setText(name+"");
		browserTextP.updateUI();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		Iterator<Rfc> it = rcfs.iterator();
		
		while(it.hasNext()){
			Rfc rfc = it.next();
			
			model.addRow(new Object[]{rfc.getNumber(), rfc.getTitle()});
		}
		
	}

	public void highlightInRfc() {
		String word2 = textField.getText();
		
		if(word2 != null && currentCardSection == "name_293710737869") {
			String browserText = this.browserTextNoStyle.replace(word2, "<b>"+word2+"</b>");
			
			browserTextP.setText(browserText);
			

			scrollPane.getViewport().setViewPosition(new Point(0,0));
			browserTextP.updateUI();
		}
		
	}

	public void getBackNoStyleText() {
		textField.setText("");
		
		if(currentCardSection == "name_293710737869") {			
			browserTextP.setText(this.browserTextNoStyle);
			

			scrollPane.getViewport().setViewPosition(new Point(0,0));
			browserTextP.updateUI();
		}
	}

}
