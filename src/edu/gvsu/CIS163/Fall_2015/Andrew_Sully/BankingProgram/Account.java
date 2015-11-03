/**
 * 
 */
package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/***********************************************************************
 * A base account class with several important statics,
 * instance variables, getters and setters, and DOM generators/parsers
 **********************************************************************/
public abstract class Account implements Serializable, Cloneable {
    /**
     * A unique identifier for this class
     */
	private static final long serialVersionUID = 959565410L;
    /**
     * The headers used for the base Account properties
     */
    public static final HeaderName[] defaultDataHeaders = {
            new HeaderName(Account.class, "Account Type"),
            new HeaderName(Account.class, "Account Number"),
            new HeaderName(Account.class, "Owner Name"),
            new HeaderName(Account.class, "Date Opened"),
            new HeaderName(Account.class, "Balance")
    };

    /**
     * The format to use for all string representations of Account dates
     */
    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yyyy");

    /**
     * The character used to separate the values in toString()
     */
    public static final String toStringSeparator = ";";

    /**
     * The unique account number for this account
     */
	private String number;

    /**
     * The name of the owner of this account
     */
	private String owner;

    /**
     * The date that this account was created
     */
	private GregorianCalendar dateOpened;

    /**
     * The current balance of the account
     */
	private double balance;

    /*******************************************************************
     * Sets the instance variables according to the specified parameters
     * Called from sub-classes
     * @param number The unique account number to represent this Account
     * @param ownerName The name of the owner of this account
     * @param dateOpened The date this account was opened
     * @param balance The balance in this account
     * @throws IllegalArgumentException if a string parameter
     *                                  contains a ";" or if balance
     *                                  is less than 0
     ******************************************************************/
    public Account(String number, String ownerName,
                   GregorianCalendar dateOpened, double balance){
        //Calls to methods to ensure the data is in valid format
        setNumber(number);
        setOwnerName(ownerName);
        setBalance(balance);
        setDateOpened(dateOpened);
    }

    /*******************************************************************
     * Set account instance variables based on a dataString
     * @param dataString A string in the format generated by toString()
     * @throws IllegalArgumentException if string is formatted wrongly
     ******************************************************************/
    public abstract void parseFromString(String dataString);

	/*******************************************************************
	 * Gets the account number of this account
	 * @return The account number
	 ******************************************************************/
	public String getNumber() {
		return number;
	}
	
	/*******************************************************************
     * Gets the name of the owner of this account
	 * @return The name of the owner
	 ******************************************************************/
	public String getOwnerName() {
		return owner;
	}
	
	/*******************************************************************
     * Gets the date that the account was opened
	 * @return The date the account was opened
	 ******************************************************************/
	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}

    /*******************************************************************
     * Gets the date that the account was opened
	 * @return The date the account opened in milliseconds since epoch
	 ******************************************************************/
	public long getDateOpenedInMillis() {
		return dateOpened.getTimeInMillis();
	}
	
	/*******************************************************************
     * Gets the current balance of the account
	 * @return The current balance of the account
	 ******************************************************************/
	public double getBalance() {
		return balance;
	}
	
	/*******************************************************************
     * Gets the serial UID of this class
	 * @return the serialversionuid for this class
	 ******************************************************************/
	public static long getSerialversionuid() {
        return serialVersionUID;
	}
	
	/*******************************************************************
     * Sets the account number
	 * @param number The new account number for this account
     * @throws IllegalArgumentException if number contains a ";"
	 ******************************************************************/
	public void setNumber(String number) {
        if (number.contains(";"))
            //This would cause conflicts with the toString() output
            throw new IllegalArgumentException();
		this.number = number;
	}

	/*******************************************************************
     * Sets the name of the owner of this account
	 * @param owner The name of the owner
     * @throws IllegalArgumentException if owner contains a ";"
	 ******************************************************************/
	public void setOwnerName(String owner) {
        if (number.contains(";"))
            //This would cause conflicts with the toString() output
            throw new IllegalArgumentException();
        this.owner = owner;
	}

	/*******************************************************************
     * Sets the date that the account was opened
	 * @param dateOpened The date that the account was opened
	 ******************************************************************/
	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}

    /*******************************************************************
     * Sets the date that the account was opened
     * @param milliSec The calendar date in milliseconds since epoch
     ******************************************************************/
    public void setDateOpenedInMillis(long milliSec) {
        dateOpened.setTimeInMillis(milliSec);
    }

	/*******************************************************************
     * Sets the balance of the account
	 * @param balance The new balance of the account
     * @throws IllegalArgumentException if balance < 0
	 ******************************************************************/
	public void setBalance(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException();
		this.balance = balance;
	}

    /*******************************************************************
     * Creates an array of HeaderNames for the properties stored in this
     * Account
     * Example field names for this class:
     *      {"Account Number", "Owner Name", "Date Opened", "Balance"}
     * @return The array of data headers
     ******************************************************************/
    public abstract HeaderName[] getDataHeaders();
    //Isn't a static method because java doesn't support
    // polymorphic static methods

    /*******************************************************************
     * Returns a unique (human readable) identifying name for the
     * account class
     * @return A human readable unique class name
     ******************************************************************/
    public abstract String getClassIdentifier();
    //Isn't a static method because java doesn't support
    // polymorphic static methods

    /*******************************************************************
     * Generates a HashMap with the data headers as keys
     * @return The generated HashMap
     ******************************************************************/
    public abstract HashMap<HeaderName, String> getClassDataAndHeaders();

    /*******************************************************************
     * Gets a comparator based on the name of the header that will sort
     * in ascending order
     * @param head The header to look up
     * @return The appropriate comparator or null if none is found
     ******************************************************************/
    public Comparator<Account> getComparatorFromHeader(HeaderName head){
        return getComparatorFromHeader(head, true);
    }

    /*******************************************************************
     * Gets a comparator based on the name of the header
     * @param header The header to look up
     * @param ascending If true, the comparator will sort ascending
     * @return The appropriate comparator or null if none is found
     ******************************************************************/
    public abstract Comparator<Account>
          getComparatorFromHeader(HeaderName header, boolean ascending);

    /*******************************************************************
     * Gets a comparator based on the name of the header if the header
     * corresponds to a base account header
     * @param header The header to look up
     * @param ascending If true, the comparator will sort ascending
     * @return The appropriate comparator or null if none is found
     ******************************************************************/
    public Comparator<Account> getBaseComparator(HeaderName header,
                                                 boolean ascending){
        //Gets multiplied at each compareTo so that
        // we can reverse the sort if specified
        int reverse;
        if (ascending){
            reverse = 1;
        } else {
            reverse = -1;
        }

        //Uses lambda expressions to simplify the syntax
        //For each of the headers, we check if it's equal and generate
        //the appropriate comparator
        if (header.equals(defaultDataHeaders[0])){
            return (Account a1, Account a2) -> reverse *
             a1.getClassIdentifier().compareTo(a2.getClassIdentifier());
        } else if (header.equals(defaultDataHeaders[1])){
            return (Account a1, Account a2) -> reverse *
                    a1.getNumber().compareTo(a2.getNumber());
        } else if (header.equals(defaultDataHeaders[2])){
            return (Account a1, Account a2) -> reverse *
                    a1.getOwnerName().compareTo(a2.getOwnerName());
        } else if (header.equals(defaultDataHeaders[3])){
            return (Account a1, Account a2) -> reverse *
                    a1.getDateOpened().compareTo(a2.getDateOpened());
        } else if (header.equals(defaultDataHeaders[4])){
            return (Account a1, Account a2) -> reverse *
                    Double.compare(a1.getBalance(), a2.getBalance());
        }

        //We didn't find the specified header
        return null;
    }

    /*******************************************************************
     * Generates a HashMap with the data headers as keys for the
     * components in this Account base class
     * @return The generated HashMap
     ******************************************************************/
    protected HashMap<HeaderName, String> getBaseHashMap(){
        HashMap<HeaderName, String> map = new HashMap<>();
        map.put(defaultDataHeaders[0], getClassIdentifier());
        map.put(defaultDataHeaders[1], getNumber());
        map.put(defaultDataHeaders[2], getOwnerName());
        map.put(defaultDataHeaders[3], DATE_FORMAT.format(
                getDateOpened().getTime())
        );
        map.put(defaultDataHeaders[4], Double.toString(getBalance()));
        return map;
    }

    /*******************************************************************
     * Given two conflicting sets of data headers, this function creates
     * a properly sorted array with no duplicate elements. It's
     * basically a tip-to-tail merge of two arrays
     * @param h1 The headers from one Account class
     * @param h2 The headers from another Account class
     * @return The combined array
     ******************************************************************/
    public static HeaderName[] resolveHeaders(HeaderName[] h1,
                                              HeaderName[] h2){
        //Create a single list to store the combined headers
        ArrayList<HeaderName> computed = new ArrayList<>();

        //For each one in h1, if it's not already in the list, add it
        for (HeaderName header : h1){
            if (!computed.contains(header)){
                computed.add(header);
            }
        }

        //For each one in h2, if it's not already in the list, add it
        for (HeaderName header : h2){
            if (!computed.contains(header)){
                computed.add(header);
            }
        }

        //Convert the result to an array and return it
        return computed.toArray(new HeaderName[computed.size()]);
    }

    /*******************************************************************
     * Generates a string representation of this account
     * @return A string in the format:
     *       "ID:NUMBER;NAME;DATE_OPENED;BALANCE;(Any other parameters)"
     ******************************************************************/
    @Override
    public abstract String toString();

    /*******************************************************************
     * Generates a DOM element that represents this account. For XML
     * @param dom The parent document for this element
     * @return A DOM element that represents the account
     ******************************************************************/
    public abstract Element getDOMNode(Document dom);

    /*******************************************************************
     * Parses an account from an element and sets instance variables
     * For use in XML
     * @param element The element to parse from
     ******************************************************************/
    public abstract void parseFromDOMElement(Element element);

    /*******************************************************************
     * Checks the equality of this and other
     * @param other The other object to compare to
     * @return True of they are equal
     ******************************************************************/
    @Override
	public boolean equals(Object other){
        if (other instanceof Account){
            //We don't have to check anything else because
            //these numbers are unique
            Account o = (Account) other;
            return o.getNumber().equals(getNumber());
        }
        //Other is not an Account
        return false;
    }


    /*******************************************************************
     * Produces an identical copy of this account at a separate
     * location in memory. Is a deep copy of the fields
     * @return A reference to the clone
     ******************************************************************/
    @Override
    public Account clone(){
        try {
            Account clone = (Account) super.clone();

            //Will "clone" the gregorian calendar as required
            //by super.clone()
            clone.setDateOpenedInMillis(clone.getDateOpenedInMillis());

            return clone;
        } catch (CloneNotSupportedException | ClassCastException e){
            return null;
        }
    }

    /*******************************************************************
     * Gets the class identifier from the class object for an account
     * @param accountClass The account class object
     * @return the class identifier
     * @throws IllegalArgumentException if accountClass doesn't
     *                                  extend account
     ******************************************************************/
    public static String getClassIDFromClass(Class accountClass){
        String validAccountTypeClassID;
        try {
            Account validAccountInstance = (Account) accountClass
                                                .newInstance();
            validAccountTypeClassID = validAccountInstance
                                                .getClassIdentifier();
        } catch (InstantiationException | ClassCastException |
                IllegalAccessException e){
            //accountClass is wrong
            throw new IllegalArgumentException();
        }

        return validAccountTypeClassID;
    }

    /*******************************************************************
     * Instantiates a valid accountClass with the default constructor
     * @param accountClass a valid account class type
     * @return An instance of the specified type
     * @throws IllegalArgumentException if accountClass
     *                                  doesn't extend account
     ******************************************************************/
    public static Account getInstanceFromClass(Class accountClass){
        try {
            return  (Account) accountClass.newInstance();
        } catch (InstantiationException | ClassCastException
                | IllegalAccessException e){
            throw new IllegalArgumentException();
        }
    }
}
