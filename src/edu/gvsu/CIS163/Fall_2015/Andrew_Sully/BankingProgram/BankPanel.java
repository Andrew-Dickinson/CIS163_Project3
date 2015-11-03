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

//TODO: Finish Javadocing this class.(sully)

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
    
    /** Remove all accounts from the JTable. */
    private JButton removeAllAccountButton;
    
    /** Allows the user to edit the selected account. */
    private JButton editAccountButton;
    
    /** Adds an copy of the selected account to the JTable. */
    private JButton cloneAccountButton;
    
    /** Panel Specifically for the buttons cloneAccountButton,
     * editAccountButton, removeAccountButton and addAccountButton*/
    JPanel buttonPanel;
    
    /** JLayerdPane on which the JTable table is displayed. */
    JLayeredPane primaryDisplayArea;
    
	/** Constructor of main frame */
	public BankPanel(JFrame frame){
	    
		/** Creates a new ButtonListener*/
		ButtonListener butListener = new ButtonListener();
		
		/** Creates a new ColumnSortListener*/
		ColumnSortListener sortListener = new ColumnSortListener();
		
		/** Objects are Instantiated. */
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
		buttonPanel = new JPanel();
		addAccountButton = new JButton("New Account");
		removeAccountButton = new JButton("Delete Account");
		removeAllAccountButton = new JButton("Delete All Accounts");
        removeAllAccountButton.setForeground(Color.RED);
		cloneAccountButton = new JButton("Clone Account");
		editAccountButton = new JButton("Edit Account");
		primaryDisplayArea = new JLayeredPane();
		
		/** Create a new table instance. */
		model = new BankModel();
		table = new JTable(model);
        table.setPreferredScrollableViewportSize
                (new Dimension(900, 400));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        /** Adds the table to a scrolling pane. */
		scrollPane = new JScrollPane(table);
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
    	table.getTableHeader().addMouseListener(sortListener);
    	table.addMouseListener(new PopClickListener());

    	/** Adds actionListener butListener to objects. */
		quit.addActionListener(butListener);
		save.addActionListener(butListener);
		load.addActionListener(butListener);
		byAcctNum.addActionListener(butListener);
		byAcctOwn.addActionListener(butListener);
		byDateOpen.addActionListener(butListener);
		addAccountMenuButton.addActionListener(butListener);
		addAccountButton.addActionListener(butListener);
        removeAccountButton.addActionListener(butListener);
        removeAllAccountButton.addActionListener(butListener);
        editAccountButton.addActionListener(butListener);
        cloneAccountButton.addActionListener(butListener);
        
		/** Formats the file and sort drop-down menus. */
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

		/** Add Buttons to buttonPanel. */
        buttonPanel.add(addAccountButton);
        buttonPanel.add(removeAccountButton);
        buttonPanel.add(editAccountButton);
        buttonPanel.add(cloneAccountButton);


        
        /** Formats primaryDisplayArea. */
        primaryDisplayArea.setPreferredSize(new Dimension(900, 400));
        primaryDisplayArea.add(scrollPane, 0, 0);
        scrollPane.setBounds(0, 0, 900, 400);
        nothingTextLabel = new JLabel("No accounts yet...");
        primaryDisplayArea.add(nothingTextLabel, 1, 0);
        nothingTextLabel.setBounds(375, 190, 300, 20);

        JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BorderLayout());
        overallPanel.add(primaryDisplayArea, BorderLayout.NORTH);
        overallPanel.add(buttonPanel, BorderLayout.CENTER);
        JPanel housingFlowPanel = new JPanel();
        housingFlowPanel.add(removeAllAccountButton);
        overallPanel.add(housingFlowPanel, BorderLayout.SOUTH);
		frame.setJMenuBar(menuBar);

        this.add(overallPanel);
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

        /** Null if canceled or invalid data. */
        Account account = dialog.displayDialog();

        /** Adds Account to the bank model. */
        if (account != null) {
            if (!model.hasAccountNumber(account.getNumber())){
            	/** Account number is unique. */
                model.addAccount(account);
            } else {
            	/** Duplicated Account Number. Let's warn the
           	    *user. */
                JOptionPane.showMessageDialog(getParent(),
                        "Accounts must have unique account numbers",
                        "Duplicate Detected",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        checkIfNoAccountTextIsNeeded();
    }
    
    /*******************************************************************
     * TODO:
     ******************************************************************/
    private void checkIfNoAccountTextIsNeeded(){
        nothingTextLabel.setVisible(model.getRowCount() == 0);
    }
    
    /*******************************************************************
     *Edits the selected account, by displaying the AccountAddDialog.
     ******************************************************************/
    public void editAccount(){
    	 Account account = getSelectedAccount();
         if (account != null) {
             AccountAddDialog dialog =
                     new AccountAddDialog(getParent());

             /** Null if canceled or invalid data. */
             Account editedAccount = dialog.displayDialog(account);

             /** Updates account in the bank Model. */
             if (editedAccount != null) {
                 try {
                     model.updateAccount(account, editedAccount);
                 } catch (IllegalArgumentException e){
                	 /** Duplicated Account Number. Let's warn the
                	  *  user. */
                     JOptionPane.showMessageDialog(getParent(),
                    	"Accounts must have unique account numbers",
                    	"Duplicate Detected",
                        JOptionPane.ERROR_MESSAGE);
                 }
             }
         } else {
        	 /** No account was selected. Let's warn the user. */
             warnNoAccountSelected();
         }
    }
    
    /*******************************************************************
     *Clones the selected account, and asks the user for a new Account 
     *Number.
     ******************************************************************/
    public void cloneAccount(){
    	Account account = getSelectedAccount();
        if (account != null) {
            String newNumber = JOptionPane.showInputDialog(
            	getParent(),"Please enter a new account number: ",
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
        	/** No account was selected. Let's warn the user. */
            warnNoAccountSelected();
        }
    }
    
    /*******************************************************************
     *Deletes the selected account.
     ******************************************************************/
    public void deleteAccount(){
        if (getSelectedAccount() != null) {
            model.removeAccount(getSelectedAccount());
        } else {
            /** No account was selected. Let's warn the user. */
            warnNoAccountSelected();
        }

        checkIfNoAccountTextIsNeeded();
    }

    /*******************************************************************
     *TODO: (sully)
     ******************************************************************/
	private class ButtonListener implements ActionListener {
	
	    /***************************************************************
	     *TODO: (sully)
	     **************************************************************/
		public void actionPerformed(ActionEvent event) {

			/** Quits the application. */
			if(quit == event.getSource()){
				System.exit(0);
			}
			
			/** Saves the accounts to the selected file type. */
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
			
			/** Loads the accounts from the selected document. */
			if(load == event.getSource()){
                BankFileDialog bfd = new BankFileDialog(getParent());
                try {
                    BankModel bm = bfd.openDialog();
                    if (bm != null){
                        table.setModel(bm);
                        model = bm;
                    }
                } catch (IOException | IllegalArgumentException e){
                    JOptionPane.showMessageDialog(getParent(),
                        "An error occurred while loading this file",
                        "Unknown Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                checkIfNoAccountTextIsNeeded();
			}
			
			/** Sorts JTable by account numbers. */
			if(byAcctNum == event.getSource())
				model.sortByAccountNumber();
			
			/** Sorts JTable by account owners names. */
			if(byAcctOwn == event.getSource())
				model.sortByAccountName();
			
			/** Sorts JTable by the accounts date opened. */
			if(byDateOpen == event.getSource())
				model.sortByDateOpened();
			
			/** Adds an account. */
			if (addAccountMenuButton == event.getSource()) 
                showAddAccountDialog();

			/** Adds an account. */
            if (addAccountButton == event.getSource())
                showAddAccountDialog();

            /** Removes the selected account. */
            if (removeAccountButton == event.getSource())
            	deleteAccount();

            /** Edits the selected account. */
            if (editAccountButton == event.getSource())
               editAccount();
            
            /** Clones the selected account. */
            if (cloneAccountButton == event.getSource())
                cloneAccount();
            
            if (removeAllAccountButton == event.getSource())
            	model.removeAllAccounts();

                checkIfNoAccountTextIsNeeded();
		}
	}
	
    /*******************************************************************
     *TODO:(sully)
     ******************************************************************/
	private class ColumnSortListener extends MouseClickListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int col = table.columnAtPoint(e.getPoint());
  	        HeaderName name = model.getHeaders()[col];

            Comparator<Account> comp = 
            	model.getComparatorFromHeader(name);

            /**If comp is null, something is very badThat should never 
             * happen*/
            if (comp != null)
                model.sortByComparator(comp);
		}
	}
	
    /*******************************************************************
     *TODO: (Sully)
     ******************************************************************/
	private class PopUp extends JPopupMenu {
		
  	  	JMenuItem editAccount;
  	  	JMenuItem cloneAccount;
  	  	JMenuItem deleteAccount;
  	  	
  	  	ButtonListener butListener = new ButtonListener();
  	  	
  	    /***************************************************************
  	     *TODO: (Sully)
  	     **************************************************************/
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

  	    /***************************************************************
  	     *TODO: (Sully)
  	     **************************************************************/
  		private class ButtonListener implements ActionListener {
  			public void actionPerformed(ActionEvent event) {
      			if(editAccount == event.getSource())
      				editAccount();
      			
      			if(cloneAccount == event.getSource())
      				cloneAccount();
      			
      			if(deleteAccount == event.getSource())
      				deleteAccount();
  			}
  		}
	}
	
  	/*******************************************************************
  	 *TODO: (Sully)
    ******************************************************************/
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