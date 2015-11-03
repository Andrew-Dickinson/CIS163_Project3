package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/***********************************************************************
 * A dialog that allows the user to enter information for the creation
 * and editing of CheckingAccounts and SavingsAccounts. Can be displayed
 * with pre-populated data or with blank fields. Includes live error
 * checking so that when something invalid is typed, the field turns red
 * and the "Ok" button is disabled until the error is corrected
 **********************************************************************/
public class AccountAddDialog {
    /**
     * The parent JPanel for this dialog
     */
    Component parent;

    /**
     * The Ok and Cancel buttons that will respectively,
     * advance or cancel the data entry process
     */
    JButton dialogOkButton;
    JButton dialogCancelButton;

    /**
     * The primary panel that will be displayed in the dialog box
     */
    JPanel primaryDialogPanel;

    /**
     * The fields in this dialog box
     */
    JTextField accountNumberField;
    JTextField ownerNameField;
    JTextField dateField;
    JTextField balanceField;
    JTextField minimumBalField;
    JTextField interestRateField;
    JTextField monthlyFeeField;

    /**
     * The radio buttons to select a type of account
     */
    JRadioButton savingsButton;
    JRadioButton checkingButton;

    /*******************************************************************
     * Instantiates (but does not display) this dialog with a
     * parent component
     * @param parent The component that this dialog is the child of
     ******************************************************************/
    public AccountAddDialog(Component parent){
        //TODO: Break into helper methods
        //TODO: Internal comments
        this.parent = parent;

        primaryDialogPanel = new JPanel();
        primaryDialogPanel.setLayout(new BorderLayout());

        RadioButtonListener listener = new RadioButtonListener();
        FieldUpdateListener fieldListener = new FieldUpdateListener();
        DialogButtonsListener dialogListener =
                                            new DialogButtonsListener();

        JPanel radioPanel = new JPanel(new FlowLayout());
        ButtonGroup typeButtonGroup = new ButtonGroup();
        savingsButton = new JRadioButton(
                SavingsAccount.classIdentifier, true
        );
        savingsButton.addActionListener(listener);
        checkingButton = new JRadioButton(
                CheckingAccount.classIdentifier
        );
        checkingButton.addActionListener(listener);

        typeButtonGroup.add(savingsButton);
        typeButtonGroup.add(checkingButton);

        radioPanel.add(savingsButton);
        radioPanel.add(checkingButton);

        primaryDialogPanel.add(radioPanel, BorderLayout.NORTH);

        JPanel fieldPanel = new JPanel(new GridLayout(7, 2));
        fieldPanel.setPreferredSize(new Dimension(400, 140));

        fieldPanel.add(new JLabel(
                Account.defaultDataHeaders[1].getFieldName() + " :")
        );
        accountNumberField = new JTextField();
        accountNumberField.getDocument()
                                    .addDocumentListener(fieldListener);
        fieldPanel.add(accountNumberField);

        fieldPanel.add(new JLabel(
                Account.defaultDataHeaders[2].getFieldName() + " :")
        );
        ownerNameField = new JTextField();
        ownerNameField.getDocument().addDocumentListener(fieldListener);
        fieldPanel.add(ownerNameField);

        fieldPanel.add(new JLabel(
                Account.defaultDataHeaders[3].getFieldName()
                + "(mm/dd/yyyy):")
        );
        dateField = new JTextField();

        //Fill it with today's date
        dateField.setText(Account.DATE_FORMAT.format(
                new GregorianCalendar().getTime()
        ));
        dateField.getDocument().addDocumentListener(fieldListener);
        JPanel dateButtonAndFieldPanel = new JPanel(new GridBagLayout());
        dateButtonAndFieldPanel.setPreferredSize(new Dimension(200, 20));
        fieldPanel.add(dateButtonAndFieldPanel);

        JCalendarButton calendarPopButton = new JCalendarButton();
        calendarPopButton.addPropertyChangeListener(
                (PropertyChangeEvent evt) -> {
                    if (evt.getNewValue() instanceof Date)
                        dateField.setText(
                                Account.DATE_FORMAT.format(
                                        (Date) evt.getNewValue()
                                )
                        );
                }
        );

        calendarPopButton.setPreferredSize(new Dimension(35, 20));
        dateField.setPreferredSize(new Dimension(165, 20));
        dateButtonAndFieldPanel.add(calendarPopButton);
        dateButtonAndFieldPanel.add(dateField);

        fieldPanel.add(new JLabel(
                Account.defaultDataHeaders[4].getFieldName() + " :")
        );
        balanceField = new JTextField();
        balanceField.getDocument().addDocumentListener(fieldListener);
        fieldPanel.add(balanceField);

        fieldPanel.add(new JLabel(
                SavingsAccount.uniqueHeaders[0].getFieldName() + " :")
        );
        minimumBalField = new JTextField();
        minimumBalField.getDocument().addDocumentListener(fieldListener);
        fieldPanel.add(minimumBalField);

        fieldPanel.add(new JLabel(
                SavingsAccount.uniqueHeaders[1].getFieldName() + " :")
        );
        interestRateField = new JTextField();
        interestRateField.getDocument()
                                    .addDocumentListener(fieldListener);
        fieldPanel.add(interestRateField);

        fieldPanel.add(new JLabel(
                CheckingAccount.uniqueHeaders[0].getFieldName() + " :")
        );
        monthlyFeeField = new JTextField();
        monthlyFeeField.getDocument().addDocumentListener(fieldListener);
        fieldPanel.add(monthlyFeeField);

        primaryDialogPanel.add(fieldPanel, BorderLayout.CENTER);

        JPanel okCancelPanel = new JPanel();
        dialogCancelButton = new JButton("Cancel");
        dialogCancelButton.addActionListener(dialogListener);
        okCancelPanel.add(dialogCancelButton);
        dialogOkButton = new JButton("Ok");
        dialogOkButton.addActionListener(dialogListener);
        okCancelPanel.add(dialogOkButton);

        primaryDialogPanel.add(okCancelPanel, BorderLayout.SOUTH);

        //Make sure the correct fields are enabled
        updateEnabledFields();
    }

    /*******************************************************************
     * Display a dialog for the purpose of creating an account with
     * blank default fields
     * @return The created account or null if canceled
     ******************************************************************/
    public Account displayDialog() {
        return displayDialog(null);
    }

    /*******************************************************************
     * Display a dialog for the purpose of editing an account.
     * Populates the entry fields based on the given account
     * @param account The account to update or null for new account
     * @return The created account or null if canceled
     ******************************************************************/
    public Account displayDialog(Account account) {
        //If we specify a pre-created account then
        //fill in the previous details
        if (account != null) {
            HashMap<HeaderName, String> preData;
            preData = account.getClassDataAndHeaders();

            //Call our private helper to fill in the data
            setFieldData(preData);

            //Make sure the correct fields are enabled
            updateEnabledFields();
        }

        //Launch a dialog box with the options
        int status = JOptionPane.showOptionDialog(parent,
                primaryDialogPanel, "Please Enter Account Data",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{dialogOkButton, dialogCancelButton},
                dialogOkButton);

        //If the user ended up hitting okay
        if (status == JOptionPane.YES_OPTION) {
            //Set some local strings to keep syntax manageable
            String accountNumber = accountNumberField.getText();
            String ownerName = ownerNameField.getText();
            String date = dateField.getText();
            String balance = balanceField.getText();
            String minimumBalance = minimumBalField.getText();
            String interestRate = interestRateField.getText();
            String monthlyFee = monthlyFeeField.getText();

            //Define a variable for the new account
            Account incomingAccount;
            if (savingsButton.isSelected()) {
                //Savings account is selected

                //Populate the savings account specific fields
                Double minBal = Double.parseDouble(minimumBalance);
                Double intRate = Double.parseDouble(interestRate);

                SavingsAccount incomingSavings = new SavingsAccount();
                incomingSavings.setMinBalance(minBal);
                incomingSavings.setInterestRate(intRate);

                //Set the incomingAccount variable
                incomingAccount = incomingSavings;
            } else {
                //Checking account is selected

                //Populate the checking account specific fields
                Double monFee = Double.parseDouble(monthlyFee);

                CheckingAccount incomingChecking = new CheckingAccount();
                incomingChecking.setMonthlyFee(monFee);

                //Set the incomingAccount Variable
                incomingAccount = incomingChecking;
            }

            //Parse the date entered
            //Should already be in a valid format because of the
            //dialog's built in error checking
            try {
                Date entered = Account.DATE_FORMAT.parse(date);
                //makes a GC with the user date
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(entered);
                //Apply it to the account
                incomingAccount.setDateOpened(gc);
            } catch (ParseException e) {
                //This should never happen, this is what we have
                // dialog validation for. So let's make some noise
                throw new IllegalArgumentException();
            }

            //Set the number and OwnerName fields. They already have
            //been validated by the dialog's error checker
            incomingAccount.setNumber(accountNumber);
            incomingAccount.setOwnerName(ownerName);

            //Set the balance. Again, it was validated by the dialog
            incomingAccount.setBalance(Double.parseDouble(balance));

            //Return the generated account
            return incomingAccount;
        }

        //The dialog box was canceled
        return null;
    }

    /*******************************************************************
     * Sets the default data in the fields according to preData
     * @param preData A HashMap generated by the
     *                Account.getClassDataAndHeaders() method
     ******************************************************************/
    private void setFieldData(HashMap<HeaderName, String> preData){
        //Get the correct type from the provided data
        boolean checking = preData.get(Account.defaultDataHeaders[0])
                .equals(CheckingAccount.classIdentifier);

        //Set the radio buttons according to the computed type
        checkingButton.setSelected(checking);
        savingsButton.setSelected(!checking);

        //Set the account number
        accountNumberField.setText(
                preData.get(Account.defaultDataHeaders[1]));

        //Set the owner name
        ownerNameField.setText(
                preData.get(Account.defaultDataHeaders[2]));

        //Set the opening date
        dateField.setText(
                preData.get(Account.defaultDataHeaders[3]));

        //Set the balance
        balanceField.setText(
                preData.get(Account.defaultDataHeaders[4]));

        //Set the minimum balance
        minimumBalField.setText(
                preData.get(SavingsAccount.uniqueHeaders[0]));

        //Set the interest rate
        interestRateField.setText(
                preData.get(SavingsAccount.uniqueHeaders[1]));

        //Set the monthly fee
        monthlyFeeField.setText(
                preData.get(CheckingAccount.uniqueHeaders[0]));
    }

    /*******************************************************************
     * Enable and disable the appropriate text fields based on the
     * current radio button state
     ******************************************************************/
    private void updateEnabledFields(){
        if (checkingButton.isSelected()){
            //Checking account selected
            minimumBalField.setEnabled(false);
            interestRateField.setEnabled(false);
            monthlyFeeField.setEnabled(true);
        } else {
            //Savings account selected
            minimumBalField.setEnabled(true);
            interestRateField.setEnabled(true);
            monthlyFeeField.setEnabled(false);
        }

        //Make sure the correct fields have errors displayed
        updateErrorIndication();
    }

    /*******************************************************************
     * Indicates an error in the field. Undoes showGood()
     * @param field The field to modify
     ******************************************************************/
    private void showError(JTextField field){
        field.setBackground(Color.PINK);
    }

    /*******************************************************************
     * Indicates no error in the field. Undoes showError()
     * @param field The field to modify
     ******************************************************************/
    private void showGood(JTextField field){
        field.setBackground(Color.WHITE);
    }

    /*******************************************************************
     * Checks the entered data for errors and then calls showError() for
     * the appropriate fields and disables the ok button if any of the
     * entered data is invalid.
     ******************************************************************/
    private void updateErrorIndication(){
        //Counts the total number of fields with problem
        int problems = 0;

        //Get the data into string form for cleaner syntax
        String accountNumber = accountNumberField.getText();
        String ownerName = ownerNameField.getText();
        String date = dateField.getText();
        String balance = balanceField.getText();
        String minimumBalance = minimumBalField.getText();
        String interestRate = interestRateField.getText();
        String monthlyFee = monthlyFeeField.getText();

        //Validate the entered account number. Checks for: account
        // number being equal to "" or having a ";" in it
        if (accountNumber.equals("") || accountNumber.contains(";")){
            showError(accountNumberField);
            problems += 1;
        } else {
            showGood(accountNumberField);
        }

        //Validate the entered owner name. Checks for: owner name being
        // equal to "" or having a ";" in it
        if (ownerName.equals("")|| ownerName.contains(";")){
            showError(ownerNameField);
            problems += 1;
        } else {
            showGood(ownerNameField);
        }

        //Validates the entered date
        if (!validDate(date)){
            showError(dateField);
            problems += 1;
        } else {
            showGood(dateField);
        }

        //If balance is invalid, or minimumBalance is enabled and
        // minBal > bal, then we make balance error
        if (!validDouble(balance)
                || (
                    validDouble(minimumBalance) && validDouble(balance)
                    && minimumBalField.isEnabled()
                    && Double.parseDouble(minimumBalance)
                    > Double.parseDouble(balance)
                ) || (Double.parseDouble(balance) < 0)){
            showError(balanceField);
            problems += 1;
        } else {
            showGood(balanceField);
        }

        //Validate minimum balance, but only if it's enabled
        if (    (
                    !validDouble(minimumBalance) ||
                    Double.parseDouble(minimumBalance) < 0
                )  && minimumBalField.isEnabled()) {

            showError(minimumBalField);
            problems += 1;
        } else {
            showGood(minimumBalField);
        }

        //Validate interest rate, but only if it's enabled
        if (    (
                !validDouble(interestRate) ||
                        Double.parseDouble(interestRate) < 0
                )  && interestRateField.isEnabled()) {
            showError(interestRateField);
            problems += 1;
        } else {
            showGood(interestRateField);
        }

        //Validate monthly fee, but only if it's enabled
        if (    (
                !validDouble(monthlyFee) ||
                        Double.parseDouble(monthlyFee) < 0
                )  && monthlyFeeField.isEnabled()) {
            showError(monthlyFeeField);
            problems += 1;
        } else {
            showGood(monthlyFeeField);
        }

        //Check the total problems
        if (problems == 0){
            //No problems. Enable the "Ok" button'
            dialogOkButton.setEnabled(true);
        } else {
            //We got problems, disable the "Ok" button
            dialogOkButton.setEnabled(false);
        }
    }

    /*******************************************************************
     * Determine whether a given string is a valid double
     * @return True if the string is valid. False if it's invalid
     ******************************************************************/
    private boolean validDouble(String doubleVal){
        try {
            Double.parseDouble(doubleVal);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

    /*******************************************************************
     * Determine whether a given string is a valid date according to the
     * format: Account.DATE_FORMAT
     * @return True if the string is valid. False if it's invalid
     ******************************************************************/
    private boolean validDate(String dateVal){
        try {
            Account.DATE_FORMAT.parse(dateVal);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /*******************************************************************
     * Gets the optionPane instance from a component directly inside it.
     * A static method used in the listener below
     * @param parent The component
     * @return The jOptionPane instance
     ******************************************************************/
    private static JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent)parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    /*******************************************************************
     * The listener for the AccountType radio buttons
     * Just calls the update method to hide or show appropriate fields
     ******************************************************************/
    private class RadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateEnabledFields();
        }
    }

    /*******************************************************************
     * DocumentListener to call updateErrorIndication() any
     * time anything happens to the text fields
     ******************************************************************/
    private class FieldUpdateListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateErrorIndication();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateErrorIndication();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateErrorIndication();
        }
    }

    /*******************************************************************
     * Process the OK and Cancel button clicks by passing the clicks on
     * to the appropriate methods in the enclosing JOptionPane
     ******************************************************************/
    private class DialogButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //The parent jOptionPane for these buttons
            JOptionPane pane = getOptionPane((JComponent) e.getSource());

            if (e.getSource() == dialogCancelButton){
                pane.setValue(dialogCancelButton);
            }

            if (e.getSource() == dialogOkButton){
                pane.setValue(dialogOkButton);
            }
        }
    }
}
