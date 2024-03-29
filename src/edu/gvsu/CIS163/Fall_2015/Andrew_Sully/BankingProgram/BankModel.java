package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.io.*;
import java.util.*;

import javax.swing.table.AbstractTableModel;
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
public class BankModel extends AbstractTableModel implements Serializable{
    /**
     * Stores all of this accounts for this bank
     */
    private ArrayList<Account> accounts;

    /**
     * A list of the types of accounts allowed in this model
     */
    private ArrayList<Class> validAccountTypes;

    /**
     * An array of headers representing the data present in this model
     */
    private HeaderName[] headers;

    /*******************************************************************
     * A default constructor. For our purposes it sets
     * CheckingAccount and SavingsAccount as the allowed types
     ******************************************************************/
    public BankModel(){
        //Set with default valid Account Types
        this(new Class[]{CheckingAccount.class, SavingsAccount.class});
    }

    /*******************************************************************
     * Constructs the model with the given valid account types
     * @param validAccountTypes An array of valid account types
     ******************************************************************/
    public BankModel(Class[] validAccountTypes){
        //Create an empty ArrayList of accounts
        accounts = new ArrayList<Account>();
        this.validAccountTypes = new ArrayList<Class>();

        //Set the valid validAccountTypes
        for (Class accountType : validAccountTypes){
            addValidAccountType(accountType);
        }
    }

    /*******************************************************************
     * Constructs the model with the given valid account types and
     * pre-existing accounts
     * @param validAccountTypes An array of valid account types
     * @param accounts An ArrayList of accounts to include in the model
     ******************************************************************/
    public BankModel(Class[] validAccountTypes,
                     ArrayList<Account> accounts){
        this(validAccountTypes);

        //Done this way so that we can encounter any invalid accounts
        for (Account account : accounts){
            addAccount(account);
        }
    }

    /*******************************************************************
     * Constructs the model with the given pre-existing accounts
     * @param accounts An ArrayList of accounts to include in the model
     ******************************************************************/
    public BankModel(ArrayList<Account> accounts){
        this();

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
    public int getRowCount() {
        if (accounts != null)
            return accounts.size();
        return 0;
    }

    /*******************************************************************
     * Gets the number of columns of data in this BankModel
     * @return The number of columns
     ******************************************************************/
    @Override
    public int getColumnCount() {
        if (getHeaders() != null) {
            return getHeaders().length;
        }

        return 0;
    }

    /*******************************************************************
     * Gets the element at a specified index
     * @param row The row of the item to return
     * @param col The column of the item to return
     * @return The item at the specified position
     ******************************************************************/
    @Override
    public Object getValueAt(int row, int col) {
        //Get the value from the Account's hashmap
        HashMap<HeaderName, String> dataMap = getAccount(row)
                                        .getClassDataAndHeaders();
        //Determine the name of the requested data
        HeaderName requestedData = getHeaders()[col];

        //Return it if it exists or "N/A" if not
        String data = dataMap.get(requestedData);
        if (data != null){
            return data;
        } else {
            return  "N/A";
        }
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

        //The column headers could have changed
        fireTableStructureChanged();
    }

    /*******************************************************************
     * Removes an account from this model
     * @param index The index of the account to remove
     * @throws IndexOutOfBoundsException if index >= this.getSize()
     ******************************************************************/
    public void removeAccount(int index){
        accounts.remove(index);

        //The column headers could have changed
        fireTableStructureChanged();
    }
    
    /*******************************************************************
     * Removes all accounts from this model
     ******************************************************************/
    public void removeAllAccounts(){
        accounts.removeAll(accounts);

        //The column headers could have changed
        fireTableStructureChanged();
    }

    /*******************************************************************
     * Removes an account from this model
     * Finds the location of the account and calls removeAccount(int)
     * @param account The account to remove
     * @throws IllegalArgumentException if account isn't found
     ******************************************************************/
    public void removeAccount(Account account){
        int index = -1;
        for (int i = 0; i < accounts.size(); i++){
            if (account.equals(accounts.get(i))){
                index = i;
                break;
            }
        }

        //If we didn't find it, throw an exception
        if (index == -1)
            throw new IllegalArgumentException();

        //Call the removal based on index method
        removeAccount(index);
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
        //Remove and re-add to make duplicate account number
        //checking easier
        accounts.remove(index);

        if (hasAccountNumber(account.getNumber()))
            throw new IllegalArgumentException();
        if (!validAccountTypes.contains(account.getClass()))
            throw new IllegalArgumentException();

        //Add the new account in the original account's location
        accounts.add(index, account);

        //The column headers could have changed
        fireTableStructureChanged();
    }

    /*******************************************************************
     * Replaces an account with a new one
     * @param oldAccount The account to replace
     * @param newAccount The new account. Type must be
     *                registered through addValidAccount()
     * @throws IllegalArgumentException if there's already an account
     *                                  with the same account number or
     *                                  if Account.getClass() hasn't
     *                                  been registered through
     *                                  addValidAccount()
     ******************************************************************/
    public void updateAccount(Account oldAccount, Account newAccount) {
        int index = 0;
        for (int i = 0; i < accounts.size(); i++){
            if (oldAccount.equals(accounts.get(i))){
                index = i;
                break;
            }
        }

        updateAccount(index, newAccount);
    }

    /*******************************************************************
     * Gets an account at a certain position
     * @param index The index of the account to get
     * @return  The account in the specified slot
     * @throws IndexOutOfBoundsException if index >= this.getRowCount()
     ******************************************************************/
    public Account getAccount(int index){
        //Use a clone for security this class's data
        return accounts.get(index).clone();
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
     * Gets a comparator based on the name of the header
     * @param header The header to look up
     * @return The appropriate comparator or null if none is found
     ******************************************************************/
    public Comparator<Account> getComparatorFromHeader(HeaderName header){
        return getComparatorFromHeader(header, true);
    }

    /*******************************************************************
     * Gets a comparator based on the name of the header
     * @param header The header to look up
     * @param ascending If true, the comparator will sort ascending
     * @return The appropriate comparator or null if none is found
     ******************************************************************/
    public Comparator<Account> getComparatorFromHeader(HeaderName header,
                                                 boolean ascending){
        //For each type, try to create the comparator.
        //If we find one that works, return it
        for (Class accountType : validAccountTypes){
            Account accountOfType = Account.getInstanceFromClass(accountType);
            Comparator<Account> comp = accountOfType
                    .getComparatorFromHeader(header, ascending);

            if (comp != null){
                return comp;
            }
        }

        //We didn't find it
        return null;
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

        //Tell the GUI we updated
        fireTableDataChanged();
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

        //Tell the GUI we updated
        fireTableDataChanged();
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

        //Tell the GUI we updated
        fireTableDataChanged();
    }

    /*******************************************************************
     *  Sort the accounts in ascending order based on the date opened
     ******************************************************************/
    public void sortByDateOpened(){
        //If reverse is not specified, we sort ascending
        sortByDateOpened(true);
    }

    /*******************************************************************
     * Sort the accounts based on the provided comparator
     * @param comparator The comparator to sort based on
     ******************************************************************/
    public void sortByComparator(Comparator<Account> comparator){
        Collections.sort(accounts, comparator);

        //Tell the GUI we updated
        fireTableDataChanged();
    }

    /*******************************************************************
     * Reconcile the headers from all of the accounts in this class and
     * store them in an array
     ******************************************************************/
    private void resolveHeaders(){
        //Create an array of arrays of headers to be resolved into a
        // single array of headers
        HeaderName[][] headerArrays = new HeaderName[accounts.size()][];

        //Clone the accounts so that sorting doesn't screw them up
        ArrayList<Account> cloned = (ArrayList<Account>) accounts.clone();

        //Sort in a standardized way. Based on ID Number
        //Solves a weird sorting bug where the headers are out of
        // sync across the program
        Collections.sort(cloned,
                getComparatorFromHeader(Account.defaultDataHeaders[1]));

        //For each account, copy it's proposed headers into the the
        //corresponding array slot
        for (int i = 0; i < headerArrays.length; i++){
            headerArrays[i] = cloned.get(i).getDataHeaders();
        }

        //We don't need any funky business if there's only one account
        if (headerArrays.length == 1) {
            headers = headerArrays[0];
            return;
        }

        //Call a recursive static method to resolve the headers
        headers = resolveHeadersRecurse(headerArrays);
    }

    /*******************************************************************
     * Uses recursion to compute the combined header array
     ******************************************************************/
    public static HeaderName[] resolveHeadersRecurse(HeaderName h1[][]){
        if (h1.length == 2){
            //Base case. Call our static method that combines two arrays
            return  Account.resolveHeaders(h1[0], h1[1]);
        } else {
            //Remove the last element from h1
            HeaderName[][] newh1 = new HeaderName[h1.length - 1][];
            for (int i = 0; i < newh1.length; i++){
                newh1[i] = h1[i];
            }

            //Combine the result from the shorted array with the
            //last entry
            return Account.resolveHeaders(
                    resolveHeadersRecurse(newh1),
                    h1[h1.length - 1]);
        }
    }

    /*******************************************************************
     * Updates headers and returns the new array
     * @return An up-to-date array of headers or null if
     *         this has no Accounts
     ******************************************************************/
    public HeaderName[] getHeaders(){
        if (getRowCount() > 0) {
            resolveHeaders();
            return headers;
        }

        return null;
    }

    /*******************************************************************
     * The the name of a specific column. A wrapper on getHeaders()
     * @param column The index of the column to get the header for
     * @return The column header
     ******************************************************************/
    @Override
    public String getColumnName(int column) {
        return getHeaders()[column].getFieldName();
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
        //Wipe out the old data
        accounts.clear();
        validAccountTypes.clear();

        //Take in the new file
    	FileInputStream fileIn = new FileInputStream(filePath);
        try {
            ObjectInputStream in = new ObjectInputStream(fileIn);

            //Read in the BankModel from the file
            BankModel bm = (BankModel) in.readObject();
            accounts = bm.accounts;
            validAccountTypes = bm.validAccountTypes;
        } catch(ClassNotFoundException | ObjectStreamException c) {
            //This was a file formatting problem
            throw new IllegalArgumentException();
        }
        finally{
        	fileIn.close();
        }

        //Tell the GUI we updated
        fireTableStructureChanged();
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
                         .getClassIDFromClass(validAccountType);

                    if (incomingAccountTypeName
                                         .equals(validAccountTypeName)){
                        //This is a match
                        incomingAccount = Account
                           .getInstanceFromClass(validAccountType);
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

        //Tell the GUI we updated
        fireTableStructureChanged();
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

            //This element will be the parent to all of the account elements
            Element acctElement = dom.createElement("Accounts");

            //This element will be the parent to all of the accountType elements
            Element typeElement = dom.createElement("AccountTypes");

            //Add each accountType
            for (Class accountType : validAccountTypes){
                Element e = dom.createElement("AccountType");
                String typeString = accountType.toGenericString();
                typeString = typeString.replaceFirst("public class ", "");
                e.appendChild(dom.createTextNode(typeString));
                typeElement.appendChild(e);
            }

            //Add an element for each account
            for (int i = 0; i < accounts.size(); i++){
                Element e = accounts.get(i).getDOMNode(dom);
                Element sortedNumber = dom.createElement("SortedNumber");
                sortedNumber.appendChild(dom.createTextNode(Integer.toString(i)));
                e.appendChild(sortedNumber);
                acctElement.appendChild(e);
            }

            //House the account and type info in the same element
            Element rootEle = dom.createElement("BankModel");
            rootEle.appendChild(typeElement);
            rootEle.appendChild(acctElement);

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
        //Reset the values of the instance array lists
        accounts = new ArrayList<Account>();
        validAccountTypes = new ArrayList<Class>();

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

            //Query for all of the account types
            NodeList accountTypesQuery = doc.getElementsByTagName("AccountType");

            //For each of these types, add it to the
            // valid account type list
            for (int i = 0; i < accountTypesQuery.getLength(); i++){
                Node accountTypeNode = accountTypesQuery.item(i);
                Element accountTypeEle = (Element) accountTypeNode;
                String accountTypeString = accountTypeEle.getTextContent();

                try {
                    Class accountType = Class.forName(accountTypeString);
                    addValidAccountType(accountType);
                } catch (ClassNotFoundException e){
                    //There's a problem with the format of the file
                    throw new IllegalArgumentException();
                }
            }

            //Will hold all of the account elements of every type
            ArrayList<Element> accountElements = new ArrayList<Element>();

            //For each account type, we gather all accounts of that type
            for (Class accountType : validAccountTypes){
                String tagName = Account.getClassIDFromClass(accountType);

                //This contains all of the accounts of this type
                NodeList accounts = doc.getElementsByTagName(tagName);

                //Add all the accounts of this type to the overall list
                for (int i = 0; i < accounts.getLength(); i++){
                    accountElements.add((Element) accounts.item(i));
                }
            }

            //Will hold a correctly ordered list of Account objects
            ArrayList<Account> orderedAccounts = new ArrayList<Account>();

            //Set initial size (different from capacity. see Arraylist docs)
            for (int i = 0; i < accountElements.size(); i++){
                orderedAccounts.add(null);
            }

            //For each of the account elements
            for (Element accountEle : accountElements){
                for (Class accountType : validAccountTypes){
                    String typeName = Account.getClassIDFromClass(accountType);
                    if (typeName.equals(accountEle.getTagName())) {
                        //This is a match

                        //Instantiate and populate the Account
                        Account a = Account.getInstanceFromClass(accountType);
                        a.parseFromDOMElement(accountEle); //Polymorphic

                        //Figure out where it goes in the order
                        NodeList orderNumbers = accountEle.getElementsByTagName("SortedNumber");
                        int pos = Integer.parseInt(orderNumbers.item(0)
                                .getFirstChild().getTextContent());

                        //Add this to the ordered list in the correct spot
                        orderedAccounts.set(pos, a);
                    }
                }
            }

            //This ensures the order is maintained
            for (Account a : orderedAccounts){
                addAccount(a);
            }

            //Tell the GUI we updated
            fireTableStructureChanged();
        } catch (ParserConfigurationException | SAXException pce) {
            throw new IOException();
        }
    }

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
        if (o.getRowCount() < getRowCount()){
            minSize = o.getRowCount() - 1;
        } else {
            minSize = getRowCount() - 1;
        }

        for (int i = 0; i < minSize; i++){
            if (!o.getAccount(i).equals(getAccount(i))){
                //If any are unequal,
                return false;
            }
        }

        return true;
    }

    /*******************************************************************
     * Creates a string representation of this model for debug purposes
     * @return A string representation
     ******************************************************************/
    @Override
    public String toString(){
        String built = "";

        for (Account account : accounts)
            built += account + "\n";

        return built;
    }
}