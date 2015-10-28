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
import java.util.GregorianCalendar;

/***********************************************************************
 * Displays the GUI for interacting with the banking program
 **********************************************************************/

//TODO: the sorts and the remove I can do both i'm just really tired.
    
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
	private BankModel model;
	//menu bar
	private JMenuBar menuBar;
	//this allows us to have any size window and we can scroll through data
	private JScrollPane scrollPane;

	//bankModel 
	BankModel bm = new BankModel();
	
	// Constructor of main frame
	public BankPanel(JFrame frame){
	    //a new button Listener
		ButtonListener butListener = new ButtonListener();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		sort = new JMenu("Sort");
		quit = new JMenuItem("Quit");
		save = new JMenuItem("Save As ");
		load = new JMenuItem("Load");
		addAcct = new JMenu("Add");
		byAcctNum = new JMenuItem("By Account Number");
		byAcctOwn = new JMenuItem("By Account Owner");
		byDateOpen = new JMenuItem("By Date Opened");
		addCheck = new JMenuItem("Add a Checking Account");
		addSave = new JMenuItem("Add a Savings Account");

		// Create a new table instance
		model = new BankModel();
        model.addAccount(new CheckingAccount("1", "Andrew"));
		table = new JTable(new DefaultTableModel(model.getHeaders(), 0));

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
		
		this.add(scrollPane);
		frame.setJMenuBar(menuBar);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			//what happens when a button is pressed

	        //filters needed to determine which type of file
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
			        "TXT files", "txt");
			FileNameExtensionFilter binFilter = new FileNameExtensionFilter(
			        "BNK files", "bnk");
			FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(
			        "XML files", "xml");
			
			//quits the program
			if(quit == event.getSource()){
				System.exit(0);
			}
			
			//saves the account data to files
			if(save == event.getSource()){
                BankFileDialog bfd = new BankFileDialog(getParent());
                if (bfd.saveDialog(model)
                        .equals(DialogStatus.OPERATION_FAILED)){
                    JOptionPane.showMessageDialog(getParent(),
                            "An error occurred while writing this file",
                            "Unknown Error",
                            JOptionPane.ERROR_MESSAGE);
                }
			}
			
			//loads account data from files
			if(load == event.getSource()){
                BankFileDialog bfd = new BankFileDialog(getParent());
                BankModel bm = bfd.openDialog();
                if (bm == null){
                    JOptionPane.showMessageDialog(getParent(),
                            "An error occurred while loading this file",
                            "Unknown Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    table.setModel(bm);
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
				myPanel.add(new JLabel("Date Created(Month/Day/Year:"));
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
					
					//converts dateField.gettext() to an integer array
					String[] gcDates = dateField.getText().split("/");
					int[] gcintDates = new int[3];
					for (int i = 0; i < gcDates.length; i++) {
						gcintDates[i] = Integer.parseInt(gcDates[i]);
					}
					
					//makes a GC with the user date
					GregorianCalendar gc= new GregorianCalendar(gcintDates[2],gcintDates[0],gcintDates[1]);
					long temp = gc.getTimeInMillis();
					
					//makes a new account
					CheckingAccount ca = new CheckingAccount();
					String dataString = "CheckingAccount;"+numField.getText()+";"+ownField.getText()+";"+temp+";"
							+balField.getText()+";"+monField.getText();
					
					//adds account to bank Model
					bm.addAccount(ca);
					ca.parseFromString(dataString);
				}
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
					
					//converts dateField.gettext() to an integer array
					String[] gcDates = dateField.getText().split("/");
					int[] gcintDates = new int[3];
					for (int i = 0; i < gcDates.length; i++) {
						gcintDates[i] = Integer.parseInt(gcDates[i]);
					}
					
					//makes a GC with the user date
					GregorianCalendar gc= new GregorianCalendar(gcintDates[2],gcintDates[0],gcintDates[1]);
					long temp = gc.getTimeInMillis();
					
					//makes a new account
					SavingsAccount sa = new SavingsAccount();
					String dataString = "SavingsAccount;"+numField.getText()+";"+ownField.getText()+";"+temp+";"
							+balField.getText()+";"+minField.getText()+";"+intField.getText();
					
					//adds account to bank Model
					bm.addAccount(sa);
					sa.parseFromString(dataString);
				}
			}
		}
	}	
}
