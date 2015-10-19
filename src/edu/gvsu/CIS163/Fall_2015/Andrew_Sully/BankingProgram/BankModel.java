package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//TODO: Javadoc this class
public class BankModel extends AbstractListModel {
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
        accounts.set(index, account);
        //TODO: Notify the fire thingy
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

    //TODO: add methods to load/save accounts from/to a binary file

    //TODO: add methods to load/save accounts from/to a text file

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
}