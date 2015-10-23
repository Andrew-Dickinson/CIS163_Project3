package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

//Imports
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/***********************************************************************
 * Displays the GUI for interacting with the banking program
 **********************************************************************/

//TODO: in order, use a dialogue box to have user input account data
//		then use the user input to make new Accounts 
//		then add the new accounts into the Table probably make an 
//		 add method in this class using fireback and TableModelEvent(GUI)

//README:
//		Also need a way to delete/update accounts I am thinking that 
//		 another tab on the dropdown menu called "modify" that asks the
//		 user for the account number of the account they want to modify
//		 then gives them the option to delete or change data.
// if this is a viable solution, I would love to do it, because I 
//  enjoy it :D thanks!
//		Also do you want to have the Jtable to say what type of account
//		 it is or do you want a radio button to go between types of
//		 accounts?
    
public class BankPanel extends JPanel {
    // Instance attributes
	private JMenu file;
	private JMenu sort;
	private JMenu addAcct;
	private JMenuItem quit;
	//save to a Binary File
	private JMenuItem save;
	//Load from a Binary FIle
	private JMenuItem load;
	//sort by Account number
	private JMenuItem byAcctNum;
	//sort by account owner
	private JMenuItem byAcctOwn;
	//sort by date opened
	private JMenuItem byDateOpen;
	//add a savings account
	private JMenuItem addCheck;
	//add a checking account
	private JMenuItem addSave;
	//an array of the column names in the JTable
	private JTable table;
	//used to manipulate data in a JTable
	private DefaultTableModel model;
	//menu bar
	private JMenuBar menuBar;
	//this allows us to have any size window and we can scroll through data
	private JScrollPane scrollPane;

	private JFileChooser fileChoose;
	
	// Constructor of main frame
	public BankPanel(JFrame frame){
	    //a new button Listener
		ButtonListener butListener = new ButtonListener();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		sort = new JMenu("Sort");
		quit = new JMenuItem("Quit");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		addAcct = new JMenu("Add");
		byAcctNum = new JMenuItem("By Account Number");
		byAcctOwn = new JMenuItem("By Account Owner");
		byDateOpen = new JMenuItem("By Date Opened");
		addCheck = new JMenuItem("Add a Checking Account");
		addSave = new JMenuItem("Add a Savings Account");
		fileChoose = new JFileChooser();
		
		// Create columns names may want to change these, I don't know
		// exactly how we want to output the data.
		String columnNames[] = {"Account Type","Account Number",
				"Account Name","Date Opened","Balance",
				"Minimum Balance","Intrest Rate","Monthly Fee"};
		
		// Create some data this is a test
		Object dataValues[] ={"","","","","","","",""};
		
		// Create a new table instance
		table = new JTable(new DefaultTableModel(columnNames, 0));
		model = (DefaultTableModel) table.getModel();
		model.addRow(dataValues);
		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		
		//adds actionListener butListener to objects
		quit.addActionListener(butListener);
		save.addActionListener(butListener);
		load.addActionListener(butListener);
		byAcctNum.addActionListener(butListener);
		byAcctOwn.addActionListener(butListener);
		byDateOpen.addActionListener(butListener);
		addCheck.addActionListener(butListener);
		addSave.addActionListener(butListener);
		
		//formats the file and sort drop-down menus
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(addAcct);
		addAcct.add(addCheck);
		addAcct.add(addSave);
		file.addSeparator();
		file.add(quit);
		sort.add(byAcctNum);
		sort.add(byAcctOwn);
		sort.add(byDateOpen);
		menuBar.add(file);
		menuBar.add(sort);
		
		add(scrollPane);

		frame.setJMenuBar(menuBar);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//what happens when a button is pressed
			
			BankModel bm = new BankModel();
			
			//tests
	        bm.addAccount(new SavingsAccount("b"));
	        bm.addAccount(new SavingsAccount("c"));
	        bm.addAccount(new CheckingAccount("d"));
	        bm.addAccount(new SavingsAccount("a"));
	        
	        //filters needed to determine which type of file
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
			        "TXT files", "txt");
			FileNameExtensionFilter binFilter = new FileNameExtensionFilter(
			        "BIN files", "bin");
			FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(
			        "XML files", "xml");
			
			//quits the program
			if(quit == event.getSource()){
				System.exit(0);
			}
			
			//saves the account data to files
			if(save == event.getSource()){
				//TODO;			
				fileChoose.setFileFilter(txtFilter);
				fileChoose.setFileFilter(binFilter);
				fileChoose.setFileFilter(xmlFilter); 	
				if (fileChoose.showSaveDialog(getParent())== JFileChooser.APPROVE_OPTION) {
			        File fileToSave = fileChoose.getSelectedFile();
			        System.out.println("Path save: "+fileToSave.getAbsolutePath());
					String filePath = ""+fileToSave.getAbsolutePath();
			        try {
						bm.saveToTextFile(filePath+".txt");
					} catch (IOException e) {
						e.printStackTrace();
					}
			        try {
						bm.saveToBinaryFile(filePath+".bin");
					} catch (IOException e) {
						e.printStackTrace();
					}
			        try {
						bm.saveToXMLFile(filePath+".xml");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			//loads account data from files
			if(load == event.getSource()){
				fileChoose.setFileFilter(txtFilter);
				fileChoose.setFileFilter(binFilter);
				fileChoose.setFileFilter(xmlFilter); 	
				
			    if(fileChoose.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
			    	File fileToSave = fileChoose.getSelectedFile();
			    	System.out.println("Path load: "+fileToSave.getAbsolutePath());
					String filePath = ""+fileToSave.getAbsolutePath();
//				    try {
//						bm.loadFromTextFile(filePath);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				    try {
//						bm.loadFromBinaryFile(filePath);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				    try {
//						bm.loadFromXMLFile(filePath);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
			    } 
			}
			
			//sorts JTable by account numbers
			if(byAcctNum == event.getSource()){
				//TODO: sort by account number
			}
			
			//sorts JTable by account owners names
			if(byAcctOwn == event.getSource()){
				//TODO: sort by owner name
			}
			
			//sorts JTable by the accounts date opened
			if(byDateOpen == event.getSource()){
				//TODO: sort by date opened
			}
			
			// adds a checking account to the JTable
			if (addCheck == event.getSource()) {
				// TODO: add a checking account
				
				
				// User inputed account number
				JTextField numField = new JTextField();
				// User inputed owner name
				JTextField ownField = new JTextField();
				// User inputed date
				JTextField dateField = new JTextField();
				// User inputed balance
				JTextField balField = new JTextField();
				// User inputed monthly fee
				JTextField monField = new JTextField();

				// popup menu that asks for input
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new GridLayout(10, 2));
				myPanel.add(new JLabel("Account Number:  "));
				myPanel.add(numField);
				myPanel.add(new JLabel("Owner Name:"));
				myPanel.add(ownField);
				myPanel.add(new JLabel("Date Created:"));
				myPanel.add(dateField);
				myPanel.add(new JLabel("Balance:"));
				myPanel.add(balField);
				myPanel.add(new JLabel("Monthly Fee:"));
				myPanel.add(monField);

				// type of dialog box and if they hit okay or cancel
				int result = JOptionPane.showConfirmDialog(null, 
						myPanel, "Please Enter Checking Account Data",
						JOptionPane.OK_CANCEL_OPTION);

				// if the user hit okay
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("owner value: " + 
							ownField.getText());
					System.out.println("Number value: " +
							numField.getText());
					System.out.println("date value: " + 
							dateField.getText());
					System.out.println("Balance value: " +
							balField.getText());
					System.out.println("monthly Fee value: " +
							monField.getText());
				}
				
				//information to put into JTable
				String[] newAcct ={"Checking Account ",
						ownField.getText(),
						numField.getText(),
						dateField.getText(),
						balField.getText(),
						"-","-",
						monField.getText()};
				
				//putting info into JTable
				model.addRow(newAcct);
				
				
				//TODO: Put this data into the account 
			}

			// adds a savings account to the JTable
			if (addSave == event.getSource()) {
				// User inputed account number
				JTextField numField = new JTextField();
				// User inputed owner name
				JTextField ownField = new JTextField();
				// User inputed date
				JTextField dateField = new JTextField();
				// User inputed balance
				JTextField balField = new JTextField();
				// User inputed minimum balance
				JTextField minField = new JTextField();
				// User inputed intrest rate
				JTextField intField = new JTextField();

				// popup menu that asks for input
				JPanel myPanel = new JPanel();
				myPanel.setLayout(new GridLayout(12, 2));
				myPanel.add(new JLabel("Account Number:"));
				myPanel.add(numField);
				myPanel.add(new JLabel("Owner Name:"));
				myPanel.add(ownField);
				myPanel.add(new JLabel("Date Created:"));
				myPanel.add(dateField);
				myPanel.add(new JLabel("Balance:"));
				myPanel.add(balField);
				myPanel.add(new JLabel("Minimum Balance:"));
				myPanel.add(minField);
				myPanel.add(new JLabel("Intrest Rate:"));
				myPanel.add(intField);

				// type of dialog box and if they hit okay or cancel
				int result = JOptionPane.showConfirmDialog(null, 
						myPanel,"Please Enter Savings Account Data",
						JOptionPane.OK_CANCEL_OPTION);

				// if the user hit okay
				if (result == JOptionPane.OK_OPTION) {
					System.out.println("owner value: " + 
							ownField.getText());
					System.out.println("Number value: " +
							numField.getText());
					System.out.println("date value: " +
							dateField.getText());
					System.out.println("Balance value: " + 
							balField.getText());
					System.out.println("minimum balance value: " +
							minField.getText());
					System.out.println("Intrest Rate value: " + 
							intField.getText());
				}
				
				//information to put into JTable
				String[] newAcct ={"Saving Account ",
						ownField.getText(),
						numField.getText(),
						dateField.getText(),
						balField.getText(),
						minField.getText(),
						intField.getText(),
						"-"};
				
				//putting info into JTable
				model.addRow(newAcct);
				
				//TODO: Put this data into the account classes 
			}
		}
	}	
}
