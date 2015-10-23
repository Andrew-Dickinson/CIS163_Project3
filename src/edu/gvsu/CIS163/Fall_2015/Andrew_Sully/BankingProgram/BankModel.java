package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.*;
import org.w3c.dom.*;

/***********************************************************************
 * This class contains a collection of accounts that can be sorted in
 * various ways and saved to and read from disk in a variety of formats
 **********************************************************************/
public class BankModel extends AbstractListModel implements Serializable {
    /*******************************************************************
     * Stores all of this accounts for this bank
     ******************************************************************/
    private ArrayList<Account> accounts;
    private ArrayList<Class> validAccountTypes;

    public BankModel(){
        //Set with default valid Account Types
        this(new Class[]{CheckingAccount.class, SavingsAccount.class});
    }

    public BankModel(Class[] validAccountTypes){
        //Create an empty ArrayList of accounts
        accounts = new ArrayList<Account>();
        this.validAccountTypes = new ArrayList<Class>();

        //Set the valid validAccountTypes
        for (Class accountType : validAccountTypes){
            addValidAccountType(accountType);
        }
    }

    public BankModel(Class[] validAccountTypes, ArrayList<Account> accounts){
        //Done this way so that we can encounter any invalid accounts
        for (Account account : accounts){
            addAccount(account);
        }
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
        return accounts.get(index);
    }

    /*******************************************************************
     * Adds an account to this model
     * @param account The account to add. Type must be
     *                registered through addValidAccount()
     * @throws IllegalArgumentException if there's already an account
     *                                  with the same account number or
     *                                  if Account.getClass() hasn't
     *                                  been registered through
     *                                  addValidAccount()
     ******************************************************************/
    public void addAccount(Account account){
        if (hasAccountNumber(account.getNumber()))
            throw new IllegalArgumentException();
        if (!validAccountTypes.contains(account.getClass()))
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
     * @param account The new account. Type must be
     *                registered through addValidAccount()
     * @throws IllegalArgumentException if there's already an account
     *                                  with the same account number or
     *                                  if Account.getClass() hasn't
     *                                  been registered through
     *                                  addValidAccount() or if
     *                                  index >= this.getSize()
     ******************************************************************/
    public void updateAccount(int index, Account account) {
        accounts.remove(index);
        if (hasAccountNumber(account.getOwnerName()))
            throw new IllegalArgumentException();
        if (!validAccountTypes.contains(account.getClass()))
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
     * Adds an accepted type for incoming accounts.
     * Must be set at least once in order to add a class
     * @param accountType The account type to add. Must extend Account
     * @throws IllegalArgumentException if accountType
     *                                  doesn't extend account
     ******************************************************************/
    public void addValidAccountType(Class accountType){
        if (Account.class.isAssignableFrom(accountType)){
            validAccountTypes.add(accountType);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Removes the accepted type for incoming accounts.
     * @param accountType The account type to remove
     * @throws IllegalArgumentException if accountType is not registered
     ******************************************************************/
    public void removeValidAccountType(Class accountType){
        if (!validAccountTypes.remove(accountType)){
            throw new IllegalArgumentException();
        }
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
                if (!sortAscending) {
                    //Just flip-flop a and b to sort descending
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
    public void sortByAccountName(final boolean sortAscending) {
        Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account a, Account b) {
                if (!sortAscending) {
                    //Just flip-flop a and b to sort descending
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
    public void sortByDateOpened(final boolean sortAscending) {
        Collections.sort(accounts, new Comparator<Account>() {
            public int compare(Account a, Account b) {
                if (!sortAscending) {
                    //Just flip-flop a and b to sort descending
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
        } catch(ClassNotFoundException | ObjectStreamException c) {
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Save the account list to a text file
     * @param filePath The place to save the list to
     * @throws IOException if an error occurs while writing the file
     ******************************************************************/
    public void saveToTextFile(String filePath) throws IOException {
        PrintWriter out;

        out = new PrintWriter(
                new BufferedWriter(new FileWriter(filePath))
        );

        //Add some lines at the beginning to represent the valid types
        for (Class validAccountType : validAccountTypes){
            String typeString = validAccountType.toGenericString();
            out.println(typeString.replaceFirst("public class ", ""));
        }

        //Add a separator character on it's own line
        out.println(Account.toStringSeparator);

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
        accounts = new ArrayList<Account>();
        validAccountTypes = new ArrayList<Class>();

        //Used to transition from classes to accounts
        boolean readingTypesStill = true;

        Scanner fileReader = new Scanner(new File(filePath));
        while (fileReader.hasNextLine()){
            String currentLine = fileReader.nextLine();

            if (readingTypesStill){
                //We need to read this as a class object or separator
                if (currentLine.equals(Account.toStringSeparator)){
                    //This is just the separator
                    readingTypesStill = false;
                } else {
                    //This is a class type to read
                    try {
                        Class newClass = Class.forName(currentLine);
                        addValidAccountType(newClass);
                    } catch (ClassNotFoundException e){
                        //This is a problem with the format of the file
                        throw new IllegalArgumentException();
                    }
                }
            } else {
                //We need to read this as an account object

                //Look at the first string in the toString() output
                String incomingAccountTypeName = currentLine
                        .split(Account.toStringSeparator)[0];

                //Instantiate the account with the right type
                Account incomingAccount = null;
                for (Class validAccountType : validAccountTypes){
                    String validAccountTypeName = Account
                         .getClassIdentifierFromClass(validAccountType);

                    if (incomingAccountTypeName
                                         .equals(validAccountTypeName)){
                        //This is a match
                        incomingAccount = Account
                           .getClassInstanceFromClass(validAccountType);
                    }
                }

                if (incomingAccount == null){
                    //This happens because the type identifier string
                    //for this line doesn't match any of the specified
                    //classes in this file. Therefore it's a file
                    //format problem
                    throw new IllegalArgumentException();
                } else {
                    //incomingAccount is instantiated, but needs to have
                    //its data filled

                    //Polymorphic
                    incomingAccount.parseFromString(currentLine);
                }

                //Add incomingAccount to the ArrayList of accounts
                addAccount(incomingAccount);
            }
        }

    }

    /*******************************************************************
     * Uses DOM to save the account list to an xml file
     * @param filePath The file to save to
     * @throws IOException if something goes wrong while writing
     ******************************************************************/
    public void saveToXMLFile(String filePath) throws IOException {
        Document dom;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement("accounts");

            //Add an element for each account
            for (Account account : accounts){
                Element e = account.getDOMNode(dom);
                rootEle.appendChild(e);
            }

            //Add our root element to the main dom
            dom.appendChild(rootEle);

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(dom),
                    new StreamResult(new FileOutputStream(filePath)));

        } catch (TransformerException | ParserConfigurationException te) {
            throw new IOException();
        }
    }

    /*******************************************************************
     * Reads an XML representation of a list of accounts and makes them
     * the accounts of this BankModel
     * @param filePath The file to read
     * @throws IOException If any kind of error occurs reading the file
     ******************************************************************/
    public void loadFromXMLFile(String filePath) throws IOException{
        accounts = new ArrayList<Account>();

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(filePath);

            Element doc = dom.getDocumentElement();

            //TODO: This method looses the order of the xml elements
            //This could be fixed with the right tag system

            NodeList savingsAccounts = doc
                  .getElementsByTagName(SavingsAccount.getClassIdentifierStatic());
            NodeList checkingAccounts = doc
                 .getElementsByTagName(CheckingAccount.getClassIdentifierStatic());

            for (int i = 0; i < savingsAccounts.getLength(); i++){
                Element accountEl = (Element) savingsAccounts.item(i);

                Account a = new SavingsAccount();
                a.parseFromDOMElement(accountEl);
                addAccount(a);
            }

            for (int i = 0; i < checkingAccounts.getLength(); i++){
                Element accountEl = (Element) checkingAccounts.item(i);

                Account a = new CheckingAccount();
                a.parseFromDOMElement(accountEl);
                addAccount(a);
            }

        } catch (ParserConfigurationException | SAXException pce) {
            throw new IOException();
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

    @Override
    public String toString(){
        String built = "";

        for (Account account : accounts)
            built += account + "\n";

        return built;
    }
}