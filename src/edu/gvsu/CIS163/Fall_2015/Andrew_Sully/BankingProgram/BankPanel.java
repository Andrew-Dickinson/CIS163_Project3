package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

/**Imports*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Comparator;

/***********************************************************************
 * Displays the GUI for interacting with the banking program
 **********************************************************************/

//TODO: a clear all button??(Sully)
//TODO: Javadoc this class thoroughly

public class BankPanel extends JPanel {

	
	/** JMenu that gives you options on how to manipulate the file.*/
	private JMenu file;
	
	/** JMenu that gives you options on how to sort the JTable. */
	private JMenu sort;
	
	/** JMenuItem that allows the user to exit the application. */
	private JMenuItem quit;
	
	/** Menu Item that allows the user to save to a file*/
	private JMenuItem save;
	
	/** Menu Item that allows the user tp load from a file*/
	private JMenuItem load;
	
	/** Sort the JTable by Account number. */
	private JMenuItem byAcctNum;
	
	/** Sort the JTable by account owner. */
	private JMenuItem byAcctOwn;
	
	/** Sort the JTable by date opened. */
	private JMenuItem byDateOpen;
	
	/** Add an account to the JTable. */
	private JMenuItem addAccountMenuButton;
	
	/** JTable that outputs the accounts and all of their information.*/
	private JTable table;
	
	/** Used to manipulate data in a JTable. */
	private BankModel model;
	
	/** Menu bar used to help the user find actions. */
	private JMenuBar menuBar;
	
	/** Allows the JTable to have any size window can scroll through 
	 * data. */
	private JScrollPane scrollPane;

	/** Label that informs the user that no accounts have been added. */
    private JLabel nothingTextLabel;

    /** Add an account to the JTable. */
    private JButton addAccountButton;
    
    /** Remove an account from the JTable. */
    private JButton removeAccountButton;
    
    /** Allows the user to edit the selected account. */
    private JButton editAccountButton;
    
    /** Adds an copy of the selected account to the JTable. */
    private JButton cloneAccountButton;
    
	/** Constructor of main frame */
	public BankPanel(JFrame frame){
	    
		/** Creates a new ButtonListener*/
		ButtonListener butListener = new ButtonListener();
		
		/** Creates a new ColumnSortListener*/
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

		/** Create a new table instance*/
		model = new BankModel();
		table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(900, 400));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Adds the table to a scrolling pane
		scrollPane = new JScrollPane(table);
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
    	table.getTableHeader().addMouseListener(sortListener);
    	table.addMouseListener(new PopClickListener());

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

        JLayeredPane primaryDisplayArea = new JLayeredPane();
        primaryDisplayArea.setPreferredSize(new Dimension(900, 400));
        primaryDisplayArea.add(scrollPane, 0, 0);
        scrollPane.setBounds(0, 0, 900, 400);

        nothingTextLabel = new JLabel("No accounts yet...");
        primaryDisplayArea.add(nothingTextLabel, 1, 0);
        nothingTextLabel.setBounds(375, 190, 300, 20);
             	
		this.add(primaryDisplayArea);
        this.add(buttonPanel);
		frame.setJMenuBar(menuBar);
	}

    /*******************************************************************
     * Get the selected account from the JTable
     * @return The currently selected account or null
     ******************************************************************/
    private Account getSelectedAccount(){
        int row = table.getSelectedRow();
        if (row == -1){
            return null;
        } else {
            return model.getAccount(row);
        }
    }

    /*******************************************************************
     * Displays a warning dialog to the user when they try to use a
     * button that requires a selection and have nothing selected
     ******************************************************************/
    private void warnNoAccountSelected(){
        JOptionPane.showMessageDialog(getParent(),
                "You didn't select an account to preform this action",
                "Please select an account",
                JOptionPane.WARNING_MESSAGE);
    }

    /*******************************************************************
     * Displays the dialog from the AccountAddDialog class and adds the
     * new account to the model as applicable
     ******************************************************************/
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

        checkIfNoAccountTextIsNeeded();
    }

    private void checkIfNoAccountTextIsNeeded(){
        nothingTextLabel.setVisible(model.getRowCount() == 0);
    }
    
    public void editAccount(){
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
             }
         } else {
             //No account was selected. Let's warn the user
             warnNoAccountSelected();
         }
    }
    
    public void cloneAccount(){
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
    
    public void deleteAccount(){
        if (getSelectedAccount() != null) {
            model.removeAccount(getSelectedAccount());
        } else {
            //No account was selected. Let's warn the user
            warnNoAccountSelected();
        }

        checkIfNoAccountTextIsNeeded();
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

                checkIfNoAccountTextIsNeeded();
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
            	deleteAccount();
            }

            if (editAccountButton == event.getSource()){
               editAccount();
            }

            if (cloneAccountButton == event.getSource()){
                cloneAccount();
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
	
	private class PopUp extends JPopupMenu {
		
  	  	JMenuItem editAccount;
  	  	JMenuItem cloneAccount;
  	  	JMenuItem deleteAccount;
  	  	
  	  	ButtonListener butListener = new ButtonListener();
  	  	public PopUp()
  	  	{
  		  	editAccount = new JMenuItem("Edit Account");
  		  	editAccount.addActionListener(butListener);
  	      	add(editAccount);
  	      	
  	      	cloneAccount = new JMenuItem("Clone Account");
  	      	cloneAccount.addActionListener(butListener);
	      	add(cloneAccount);
	      	
	      	deleteAccount = new JMenuItem("Delete Account");
  	      	deleteAccount.addActionListener(butListener);
	      	add(deleteAccount);
  	  	}
  	    
  		private class ButtonListener implements ActionListener {
  			public void actionPerformed(ActionEvent event) {
      			if(editAccount == event.getSource())
      			{
      				editAccount();
      			}
      			if(cloneAccount == event.getSource())
      			{
      				cloneAccount();
      			}
      			if(deleteAccount == event.getSource())
      			{
      				deleteAccount();
      			}
  			}
  		}
  	}
	  
	  public class PopClickListener extends MouseAdapter {
  	    public void mousePressed(MouseEvent e){
  	        if (e.isPopupTrigger()){
  	  	        PopUp menu = new PopUp();
  	  	        menu.show(e.getComponent(), e.getX(), e.getY());
  	  	    }
  	    }

  	    public void mouseReleased(MouseEvent e){
  	        if (e.isPopupTrigger()){
  	  	        PopUp menu = new PopUp();
  	  	        menu.show(e.getComponent(), e.getX(), e.getY());
  	  	    }
  	    }
  	}
}
