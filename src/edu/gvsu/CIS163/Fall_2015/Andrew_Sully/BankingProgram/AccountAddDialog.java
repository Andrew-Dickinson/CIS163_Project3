package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Andrew on 10/27/15.
 */
public class AccountAddDialog {
    /**
     * The parent JPanel for this dialog
     */
    Component parent;

    /**
     * The primary panel in the dialog box displayed
     */
    JPanel primaryDialogPanel;

    /**
     * The fields in this dialog
     */
    JTextField accountNumberField;
    JTextField ownerNameField;
    JTextField dateField;
    JTextField balanceField;
    JTextField minimumBalField;
    JTextField interestRateField;
    JTextField monthlyFeeField;

    JRadioButton savingsButton;
    JRadioButton checkingButton;

    public AccountAddDialog(Component parent){
        this.parent = parent;

        primaryDialogPanel = new JPanel();
        primaryDialogPanel.setLayout(new BorderLayout());

        JPanel radioPanel = new JPanel(new FlowLayout());
        ButtonGroup typeButtonGroup = new ButtonGroup();
        savingsButton = new JRadioButton(SavingsAccount.classIdentifier, true);
        checkingButton = new JRadioButton(CheckingAccount.classIdentifier);

        typeButtonGroup.add(savingsButton);
        typeButtonGroup.add(checkingButton);

        radioPanel.add(savingsButton);
        radioPanel.add(checkingButton);

        primaryDialogPanel.add(radioPanel, BorderLayout.NORTH);

        JPanel fieldPanel = new JPanel(new GridLayout(7, 2));

        fieldPanel.add(new JLabel(Account.defaultDataHeaders[1] + " :"));
        accountNumberField = new JTextField();
        fieldPanel.add(accountNumberField);

        fieldPanel.add(new JLabel(Account.defaultDataHeaders[2] + " :"));
        ownerNameField = new JTextField();
        fieldPanel.add(ownerNameField);

        fieldPanel.add(new JLabel(Account.defaultDataHeaders[3] + " :"));
        dateField = new JTextField();
        fieldPanel.add(dateField);

        fieldPanel.add(new JLabel(Account.defaultDataHeaders[4] + " :"));
        balanceField = new JTextField();
        fieldPanel.add(balanceField);

        fieldPanel.add(new JLabel(SavingsAccount.uniqueHeaders[0] + " :"));
        minimumBalField = new JTextField();
        fieldPanel.add(minimumBalField);

        fieldPanel.add(new JLabel(SavingsAccount.uniqueHeaders[1] + " :"));
        interestRateField = new JTextField();
        fieldPanel.add(interestRateField);

        fieldPanel.add(new JLabel(CheckingAccount.uniqueHeaders[0] + " :"));
        monthlyFeeField = new JTextField();
        fieldPanel.add(monthlyFeeField);

        primaryDialogPanel.add(fieldPanel, BorderLayout.CENTER);
    }

    /*******************************************************************
     * Display a dialog for the purpose of creating an account
     * @return The created account or null if canceled
     ******************************************************************/
    public Account displayDialog() {
        return displayDialog(null);
    }

    /*******************************************************************
     * Display a dialog for the purpose of creating or editing an
     * account
     * @param account The account to update
     * @return The created account or null if canceled
     ******************************************************************/
    public Account displayDialog(Account account) {
        //If we specify a pre-created account, fill in the previous details
        if (account != null) {
            HashMap<String, String> preData;
            preData = account.getClassDataAndHeaders();

            //Call our private helper to fill in the data
            setFieldData(preData);
        }

        //Launch a dialog box with the options
        int status = JOptionPane.showConfirmDialog(null,
                primaryDialogPanel, "Please Enter Account Data",
                JOptionPane.OK_CANCEL_OPTION);

        //If the user ended up hitting okay
        if (status == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            String ownerName = ownerNameField.getText();
            String date = dateField.getText();
            String balance = balanceField.getText();
            String minimumBalance = minimumBalField.getText();
            String interestRate = interestRateField.getText();
            String monthlyFee = monthlyFeeField.getText();

            try {
                Account incomingAccount;
                if (savingsButton.isSelected()) {
                    //Savings account is selected
                    Double minBal = Double.parseDouble(minimumBalance);
                    Double intRate = Double.parseDouble(interestRate);

                    SavingsAccount incomingSavings = new SavingsAccount();
                    incomingSavings.setMinBalance(minBal);
                    incomingSavings.setInterestRate(intRate);

                    incomingAccount = incomingSavings;
                } else {
                    //Checking account is selected
                    Double monFee = Double.parseDouble(monthlyFee);

                    CheckingAccount incomingChecking = new CheckingAccount();
                    incomingChecking.setMonthlyFee(monFee);

                    incomingAccount = incomingChecking;
                }

                incomingAccount.setNumber(accountNumber);
                incomingAccount.setOwnerName(ownerName);

                incomingAccount.setBalance(Double.parseDouble(balance));

                return incomingAccount;
            } catch (ClassCastException | NumberFormatException e){
                //There was an invalid entry
                return null;
            }
        }

        //The dialog box was likely canceled
        return null;
    }

    private void setFieldData(HashMap<String, String> preData){
        //Set the radio buttons correctly
        boolean checking = preData.get(Account.defaultDataHeaders[0])
                .equals(CheckingAccount.classIdentifier);

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
                preData.get(CheckingAccount.uniqueHeaders[0]));

        //Set the interest rate
        interestRateField.setText(
                preData.get(CheckingAccount.uniqueHeaders[1]));

        //Set the monthly fee
        monthlyFeeField.setText(
                preData.get(SavingsAccount.uniqueHeaders[0]));
    }
}
