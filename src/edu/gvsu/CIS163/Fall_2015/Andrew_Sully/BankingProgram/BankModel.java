package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//TODO: Javadoc this class
public class BankModel extends AbstractListModel implements Serializable {
    /**
     * Stores all of this accounts for this bank
     */
    private ArrayList<Account> accounts;

    public BankModel(){
        //Create an empty ArrayList of accounts
        accounts = new ArrayList<Account>();
        //TODO: Other constructors?
    }

    /*******************************************************************
     * Gets the number of accounts in this BankModel
     * @return The number of accounts
     ******************************************************************/
    @Override
    public int getSize() {
        return accounts.size();
    }

    /*******************************************************************
     * Gets the element at a specified index
     * @param index The index of the item to return
     * @return The item
     ******************************************************************/
    @Override
    public Object getElementAt(int index) {
        //TODO: Determine if this is the correct behavior + update javadoc
        return accounts.get(index);
    }

    /*******************************************************************
     * Adds an account to this model
     * @param account The account to add
     ******************************************************************/
    public void addAccount(Account account){
        if (hasAccountNumber(account.getNumber()))
            throw new IllegalArgumentException();
        accounts.add(account);
        //TODO: Notify the fire thingy
    }

    /*******************************************************************
     * Removes an account from this model
     * @param index The index of the account to remove
     * @throws IndexOutOfBoundsException if index >= this.getSize()
     ******************************************************************/
    public void removeAccount(int index){
        accounts.remove(index);
        //TODO: Notify the fire thingy
    }

    /*******************************************************************
     * Replaces an account with a new one
     * @param index The index of the account to replace
     * @param account The new account
     * @throws IndexOutOfBoundsException if index >= this.getSize()
     ******************************************************************/
    public void updateAccount(int index, Account account){
        accounts.remove(index);
        if (hasAccountNumber(account.getOwnerName()))
            throw new IllegalArgumentException();
        accounts.add(index, account);
        //TODO: Notify the fire thingy
    }

    /*******************************************************************
     * Check if this BankModel has an account with a certain number
     * @param number The number to check for
     * @return True if there is an account with accountNumber = number
     ******************************************************************/
    public boolean hasAccountNumber(String number){
        for (Account account : accounts){
            if (account.getNumber().equals(number)){
                return true;
            }
        }

        //We didn't find it
        return false;
    }

    /*******************************************************************
     * Sorts the accounts based on the account number
     * @param sortAscending If true, the accounts are sorted in
     *                      ascending order. If false, the accounts are
     *                      sorted in descending order
     ******************************************************************/
    public void sortByAccountNumber(final boolean sortAscending){
        Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account a, Account b) {
                if (!sortAscending){
                    //Just flip-flop a and b to sort descending
                    //TODO: Make sure this works
                    Account temp = a;
                    a = b;
                    b = temp;
                }
                return a.getNumber().compareTo(b.getNumber());
            }
        });
    }

    /*******************************************************************
     *  Sort the accounts in ascending order based on the account number
     ******************************************************************/
    public void sortByAccountNumber(){
        //If reverse is not specified, we sort ascending
        sortByAccountNumber(true);
    }

    /*******************************************************************
     * Sorts the accounts based on the account name
     * @param sortAscending If true, the accounts are sorted in
     *                      ascending order. If false, the accounts are
     *                      sorted in descending order
     ******************************************************************/
    public void sortByAccountName(final boolean sortAscending){
        Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account a, Account b) {
                if (!sortAscending){
                    //Just flip-flop a and b to sort descending
                    //TODO: Make sure this works
                    Account temp = a;
                    a = b;
                    b = temp;
                }
                return a.getOwnerName().compareTo(b.getOwnerName());
            }
        });
    }

    /*******************************************************************
     *  Sort the accounts in ascending order based on the account name
     ******************************************************************/
    public void sortByAccountName(){
        //If reverse is not specified, we sort ascending
        sortByAccountName(true);
    }

    /*******************************************************************
     * Sorts the accounts based on the date opened
     * @param sortAscending If true, the accounts are sorted in
     *                      ascending order. If false, the accounts are
     *                      sorted in descending order
     ******************************************************************/
    public void sortByDateOpened(final boolean sortAscending){
        Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account a, Account b) {
                if (!sortAscending){
                    //Just flip-flop a and b to sort descending
                    //TODO: Make sure this works
                    Account temp = a;
                    a = b;
                    b = temp;
                }
                return a.getDateOpened().compareTo(b.getDateOpened());
            }
        });
    }

    /*******************************************************************
     *  Sort the accounts in ascending order based on the date opened
     ******************************************************************/
    public void sortByDateOpened(){
        //If reverse is not specified, we sort ascending
        sortByDateOpened(true);
    }

    /*******************************************************************
     * Save the account list to a binary file
     * @param filePath The place to save the list to
     * @throws IOException if an error occurs while writing the file
     ******************************************************************/
    public void saveToBinaryFile(String filePath) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    /*******************************************************************
     * Load the account list from a binary file
     * @param filePath The place to load the list from
     * @throws IOException if an error occurs while reading the file
     * @throws IllegalArgumentException If the file is formatted wrongly
     ******************************************************************/
    public void loadFromBinaryFile(String filePath) throws IOException {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            BankModel bm = (BankModel) in.readObject();
            accounts = bm.accounts;

            fileIn.close();
        } catch(ClassNotFoundException c) {
            throw new IllegalArgumentException();
        } catch (ObjectStreamException e){
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Save the account list to a text file
     * @param filePath The place to save the list to
     * @throws IOException if an error occurs while writing the file
     ******************************************************************/
    public void saveToTextFile(String filePath) throws IOException {
        //TODO: Test this method
        PrintWriter out;

        out = new PrintWriter(
                new BufferedWriter(new FileWriter(filePath))
        );

        //Put each account on its own line
        for (Account account : accounts){
            //Write each account based on it's own toString()
            out.println(account.toString());
        }

        out.close();
    }

    /*******************************************************************
     * Load the account list from a text file
     * @param filePath The place to load the list from
     * @throws IllegalArgumentException If the file is formatted wrongly
     * @throws IOException If something blows up while reading from file
     ******************************************************************/
    public void loadFromTextFile(String filePath) throws IOException {
        //TODO: Test this method
        accounts = new ArrayList<Account>();

        Scanner fileReader = new Scanner(new File(filePath));
        while (fileReader.hasNextLine()){
            String currentLine = fileReader.nextLine();

            //Look at the first string in the toString() output
            String accountType = currentLine
                                   .split(Account.toStringSeparator)[0];

            Account incomingAccount;
            if (accountType.equals(CheckingAccount.classIdentifier)){
                incomingAccount = new CheckingAccount();
            } else if (accountType.equals(
                                    SavingsAccount.classIdentifier)){
                incomingAccount = new SavingsAccount();
            } else {
                throw new IllegalArgumentException();
            }

            //Polymorphic
            incomingAccount.parseFromString(currentLine);

            accounts.add(incomingAccount);
        }

    }

    //TODO: add methods to load/save accounts from/to an XML file

    //TODO: Implement this:
    //To make updates to the accounts in the model immediately visible
    //in the JList on your GUI, it is important that the methods in the
    //BankModel class that modify (add, delete, and update) the accounts
    //notify the JList immediately after any changes. These notifications
    //can be sent from BankModel class using one of these methods:
    //fireIntervalAdded(), fireIntervalRemoved(), and fireContentsChanged()
    //The BankModel class inherits these methods from the
    //AbstractListModel class.

    /*******************************************************************
     * Test if other contains the same accounts sorted in the same way
     * @param other Another object
     * @return True if other is a BankModel and contains the same
     *         accounts sorted in the same way
     ******************************************************************/
    @Override
    public boolean equals(Object other){
        if (!(other instanceof BankModel))
            return false;

        BankModel o = (BankModel) other;

        //So we don't go out of bounds
        int minSize;
        if (o.getSize() < getSize()){
            minSize = o.getSize();
        } else {
            minSize = getSize();
        }

        for (int i = 0; i < minSize; i++){
            if (!o.getElementAt(i).equals(getElementAt(i))){
                //If any are unequal,
                return false;
            }
        }

        return true;
    }
}