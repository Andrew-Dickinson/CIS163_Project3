package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

//Imports
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
/// enjoy it :D thanks!


public class BankPanel extends JFrame
{
	// Instance attributes
	private JMenu file;
	private JMenu sort;
	private JFrame frame; 	
	private JMenuItem quit;
	//save to a Binary File
	private JMenuItem saveToB;
	//Load from a Binary FIle
	private JMenuItem loadFromB;
	//save to a Text file
	private JMenuItem saveToT;
	//load from a text file
	private JMenuItem loadFromT;
	//save to a XML document
	private JMenuItem saveToX;
	//load from an XML document
	private JMenuItem loadFromX;
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
	private JMenuBar menuBar;
	private JScrollPane scrollPane;

	// Constructor of main frame
	public BankPanel()
	{
		//a new button Listener
		ButtonListener butListener = new ButtonListener();
		//Initializes objects 
		frame = new JFrame("Bank Application"); 
		menuBar = new JMenuBar();
		file = new JMenu("File");
		sort = new JMenu("Sort");
		quit = new JMenuItem("Quit");
		saveToB = new JMenuItem("Save To Binary");
		loadFromB = new JMenuItem("Load From Binary");
		saveToT = new JMenuItem("Save To Txt");
		loadFromT = new JMenuItem("Load From Txt");
		saveToX = new JMenuItem("Save To XML");
		loadFromX = new JMenuItem("Load From XML");
		byAcctNum = new JMenuItem("By Account Number");
		byAcctOwn = new JMenuItem("By Account Owner");
		byDateOpen = new JMenuItem("By Date Opened");
		addCheck = new JMenuItem("Add a Checking Account");
		addSave = new JMenuItem("Add a Savings Account");
		
		// Create columns names may want to change these, I don't know
		// exactly how we want to output the data.
		String columnNames[] = {"Account Type","Account Number",
				"Account Name","Date Opened","Balance",
				"Minimum Balance","Intrest Rate","Monthly Fee"};
		
		// Create some data this is a test
		Object dataValues[][] ={{"","","","","","","",""}};
		
		// Create a new table instance
		table = new JTable(dataValues, columnNames);
		
		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		
		//adds actionListener butListener to objects
		quit.addActionListener(butListener);
		saveToB.addActionListener(butListener);
		loadFromB.addActionListener(butListener);
		saveToT.addActionListener(butListener);
		loadFromT.addActionListener(butListener);
		saveToX.addActionListener(butListener);
		loadFromX.addActionListener(butListener);
		byAcctNum.addActionListener(butListener);
		byAcctOwn.addActionListener(butListener);
		byDateOpen.addActionListener(butListener);
		addCheck.addActionListener(butListener);
		addSave.addActionListener(butListener);
		
		//formats the file and sort drop-down menus
		file.add(addCheck);
		file.add(addSave);
		file.addSeparator();
		file.add(saveToB);
		file.add(loadFromB);
		file.addSeparator();
		file.add(saveToT);
		file.add(loadFromT);
		file.addSeparator();
		file.add(saveToX);
		file.add(loadFromX);
		file.addSeparator();
		file.add(quit);
		sort.add(byAcctNum);
		sort.add(byAcctOwn);
		sort.add(byDateOpen);
		menuBar.add(file);
		menuBar.add(sort);
		
		//frame stuff
		frame.add(scrollPane);
		frame.setTitle("Bank Application");
		frame.setJMenuBar(menuBar);
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String args[])
	{
		new BankPanel();
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//what happens when a button is pressed
			//quits the program
			if(quit == event.getSource()){
				System.exit(0);
			}
			
			//saves accounts to a binary file
			if(saveToB == event.getSource()){
				//TODO: save to binary
			}
			
			//loads accounts from a binary file
			if(loadFromB == event.getSource()){
				//TODO: load from binary
			}
			
			//saves accounts to a Text file
			if(saveToT == event.getSource()){
				//TODO: save to text
			}
			
			//loads accounts from a text file
			if(loadFromT == event.getSource()){
				//TODO: load from text
			}
			
			//saves accounts to a XML file
			if(saveToX == event.getSource()){
				//TODO: save to XML
			}
			
			//loads accounts from a XML file
			if(loadFromX == event.getSource()){
				//TODO: load from XML
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
				
				//TODO: Put this data into the account classes and 
				//		into the JTable 
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
				
				//TODO: Put this data into the account classes and 
				//		into the JTable 
			}
		}
	}	
}
