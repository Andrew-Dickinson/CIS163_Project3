package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

//Imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JMenuItem addAccount;
	//an array of the column names in the JTable
	private JTable table;
	//used to manipulate data in a JTable
	private BankModel model;
	//menu bar
	private JMenuBar menuBar;
	//this allows us to have any size window and we can scroll through data
	private JScrollPane scrollPane;

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
		addAccount = new JMenuItem("Add an Account");

		// Create a new table instance
		model = new BankModel();
        model.addAccount(new CheckingAccount("1", "Andrew"));
		table = new JTable(model);

		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		
		//adds actionListener butListener to objects
		quit.addActionListener(butListener);
		save.addActionListener(butListener);
		load.addActionListener(butListener);
		byAcctNum.addActionListener(butListener);
		byAcctOwn.addActionListener(butListener);
		byDateOpen.addActionListener(butListener);
		addAccount.addActionListener(butListener);
		
		//formats the file and sort drop-down menus
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(addAcct);
		addAcct.add(addAccount);
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
				model.sortByAccountNumber();
			}
			
			//sorts JTable by account owners names
			if(byAcctOwn == event.getSource()){
				model.sortByAccountName();
			}
			
			//sorts JTable by the accounts date opened
			if(byDateOpen == event.getSource()){
				model.sortByDateOpened();
			}
			
			// adds a checking account to the JTable
			if (addAccount == event.getSource()) {
                AccountAddDialog dialog = new AccountAddDialog(getParent());

                //Null if canceled or invalid data
                Account account = dialog.displayDialog();

                //adds account to bank Model
                if (account != null) {
                    model.addAccount(account);
                } else {
                    System.out.println("Err");
                }
			}
		}
	}	
}
