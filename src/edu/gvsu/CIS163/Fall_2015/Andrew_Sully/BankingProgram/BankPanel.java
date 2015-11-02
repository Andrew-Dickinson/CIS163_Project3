package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Comparator;

/***********************************************************************
 * Displays the GUI for interacting with the banking program
 **********************************************************************/

//TODO: Java style guide all over. But mostly here (Sully)
//TODO: Right clicking / double clicking an element pulls up the edit menu(Sully)
//TODO: Put nice calendar GUI in the AccountAddDialog (Andrew)
//TODO: XML Definitions file (Andrew)
//TODO: Nothing should have some default text (Andrew)
//TODO: a clear all button??(Sully)
    
public class BankPanel extends JPanel {
    // Instance attributes
	private JMenu file;
	private JMenu sort;
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
	private JMenuItem addAccountMenuButton;
	//an array of the column names in the JTable
	private JTable table;
	//used to manipulate data in a JTable
	private BankModel model;
	//menu bar
	private JMenuBar menuBar;
	//this allows us to have any size window and we can scroll through data
	private JScrollPane scrollPane;

    //The buttons along the bottom of the panel
    private JButton addAccountButton;
    private JButton removeAccountButton;
    private JButton editAccountButton;
    private JButton cloneAccountButton;

	// Constructor of main frame
	public BankPanel(JFrame frame){
	    //a new button Listener
		ButtonListener butListener = new ButtonListener();
		ColumnSortListener sortListener = new ColumnSortListener();
		menuBar = new JMenuBar();
		file = new JMenu("File");
		sort = new JMenu("Sort");
		quit = new JMenuItem("Quit");
		save = new JMenuItem("Save As ");
		load = new JMenuItem("Open");
		byAcctNum = new JMenuItem("By Account Number");
		byAcctOwn = new JMenuItem("By Account Owner");
		byDateOpen = new JMenuItem("By Date Opened");
		addAccountMenuButton = new JMenuItem("Add Account");

		// Create a new table instance
		model = new BankModel();
        model.addAccount(new CheckingAccount("1", "Andrew"));
        
		table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(900, 400));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Add the table to a scrolling pane
		scrollPane = new JScrollPane(table);
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        
    	table.getTableHeader().addMouseListener(sortListener);

		//adds actionListener butListener to objects
		quit.addActionListener(butListener);
		save.addActionListener(butListener);
		load.addActionListener(butListener);
		byAcctNum.addActionListener(butListener);
		byAcctOwn.addActionListener(butListener);
		byDateOpen.addActionListener(butListener);
		addAccountMenuButton.addActionListener(butListener);
		
		//formats the file and sort drop-down menus
		file.add(save);
		file.add(load);
		file.addSeparator();
		file.add(addAccountMenuButton);
		file.addSeparator();
		file.add(quit);
		sort.add(byAcctNum);
		sort.add(byAcctOwn);
		sort.add(byDateOpen);
		menuBar.add(file);
		menuBar.add(sort);

        JPanel buttonPanel = new JPanel();

        addAccountButton = new JButton("New Account");
        addAccountButton.addActionListener(butListener);
        buttonPanel.add(addAccountButton);

        removeAccountButton = new JButton("Delete Account");
        removeAccountButton.addActionListener(butListener);
        buttonPanel.add(removeAccountButton);

        editAccountButton = new JButton("Edit Account");
        editAccountButton.addActionListener(butListener);
        buttonPanel.add(editAccountButton);

        cloneAccountButton = new JButton("Clone Account");
        cloneAccountButton.addActionListener(butListener);
        buttonPanel.add(cloneAccountButton);
		
		this.add(scrollPane);
        this.add(buttonPanel);
		frame.setJMenuBar(menuBar);
	}

    /**
     * Get the selected account from the JTable
     * @return The currently selected account or null
     */
    private Account getSelectedAccount(){
        int row = table.getSelectedRow();
        if (row == -1){
            return null;
        } else {
            return model.getAccount(row);
        }
    }

    /**
     * Displays a warning dialog to the user when they try to use a
     * button that requires a selection and have nothing selected
     */
    private void warnNoAccountSelected(){
        JOptionPane.showMessageDialog(getParent(),
                "You didn't select an account to preform this action",
                "Please select an account",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays the dialog from the AccountAddDialog class and adds the
     * new account to the model as applicable
     */
    private void showAddAccountDialog(){
        AccountAddDialog dialog = new AccountAddDialog(getParent());

        //Null if canceled or invalid data
        Account account = dialog.displayDialog();

        //adds account to bank Model
        if (account != null) {
            if (!model.hasAccountNumber(account.getNumber())){
                //The new account's number is unique
                model.addAccount(account);
            } else {
                //The account number is a duplicate. Alert the user
                JOptionPane.showMessageDialog(getParent(),
                        "Accounts must have unique account numbers",
                        "Duplicate Detected",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Err");
        }
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
                try {
                    BankModel bm = bfd.openDialog();
                    if (bm != null){
                        table.setModel(bm);
                        model = bm;
                    }
                } catch (IOException | IllegalArgumentException e){
                    //There was a problem with loading the file
                    JOptionPane.showMessageDialog(getParent(),
                            "An error occurred while loading this file",
                            "Unknown Error",
                            JOptionPane.ERROR_MESSAGE);
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
			if (addAccountMenuButton == event.getSource()) {
                showAddAccountDialog();
			}

            if (addAccountButton == event.getSource()){
                showAddAccountDialog();
            }

            if (removeAccountButton == event.getSource()){
                if (getSelectedAccount() != null) {
                    model.removeAccount(getSelectedAccount());
                } else {
                    //No account was selected. Let's warn the user
                    warnNoAccountSelected();
                }
            }

            if (editAccountButton == event.getSource()){
                Account account = getSelectedAccount();
                if (account != null) {
                    AccountAddDialog dialog =
                            new AccountAddDialog(getParent());

                    //Null if canceled or invalid data
                    Account editedAccount = dialog.displayDialog(account);

                    //Updates account in the bank Model
                    if (editedAccount != null) {
                        try {
                            model.updateAccount(account, editedAccount);
                        } catch (IllegalArgumentException e){
                            //The account number is a duplicate. Alert the user
                            JOptionPane.showMessageDialog(getParent(),
                                    "Accounts must have unique account numbers",
                                    "Duplicate Detected",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        //There was an error or the user canceled. We can't tell
                        //TODO: ^ Fix
                    }
                } else {
                    //No account was selected. Let's warn the user
                    warnNoAccountSelected();
                }
            }

            if (cloneAccountButton == event.getSource()){
                Account account = getSelectedAccount();
                if (account != null) {
                    String newNumber = JOptionPane.showInputDialog(
                            getParent(),
                            "Please enter a new account number: ",
                            account.getNumber());
                    if (newNumber != null){
                        if (!model.hasAccountNumber(newNumber)) {
                            account.setNumber(newNumber);
                            model.addAccount(account);
                        } else {
                            JOptionPane.showMessageDialog(getParent(),
                                    "Account number must be unique",
                                    "Error while adding account",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    //No account was selected. Let's warn the user
                    warnNoAccountSelected();
                }
            }
		}
	}
	
	private class ColumnSortListener extends MouseClickListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int col = table.columnAtPoint(e.getPoint());
  	        HeaderName name = model.getHeaders()[col];

            Comparator<Account> comp = model.getComparatorFromHeader(name);

            //If comp is null, something is very bad
            //That should never happen
            if (comp != null)
                model.sortByComparator(comp);
		}
	}
	
//	public class handleRight extends MouseClickListener {
//		public void mouseClicked(MouseEvent e)
//	    {
//	        if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()){
//	            System.out.println("Right Worked");
//	            
//	        }
//	    }
//	}
}
