/**
 * 
 */
package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;
import java.io.Serializable;
import java.util.GregorianCalendar;


/***********************************************************************
 * A base account class with several
 * instance variables and getters and setters
 * @author Sully
 **********************************************************************/
public abstract class Account implements Serializable{
	private double balance;
	
    /*******************************************************************
     * A unique identifier for this class
     ******************************************************************/
	private static final long serialVersionUID = 959565410L;

    /*******************************************************************
     * The character used to separate the values in toString()
     ******************************************************************/
    public static final String toStringSeparator = ";";

    /*******************************************************************
     * The unique account number for this account
     ******************************************************************/
	private String number;

    /*******************************************************************
     * The name of the owner of this account
     ******************************************************************/
	private String owner;

    /*******************************************************************
     * The date that this account was created
     ******************************************************************/
	private GregorianCalendar dateOpened;

    /*******************************************************************
     * The current balance of the account
     ******************************************************************/

    public Account(String number, String ownerName,
                   GregorianCalendar dateOpened, double balance){
        //Calls to methods to ensure the data is in valid format
        setNumber(number);
        setOwnerName(ownerName);
        setBalance(balance);

        this.dateOpened = dateOpened;
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
	 * @return the serialversionuid
	 ******************************************************************/
	public static long getSerialversionuid() {
        return serialVersionUID;
	}
	
	/*******************************************************************
     * Sets the account number
	 * @param number The new account number for this account
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
     * Generates a string representation of this account
     * @return A string in the format:
     *       "ID:NUMBER;NAME;DATE_OPENED;BALANCE;(Any other parameters)"
     ******************************************************************/
    @Override
    public abstract String toString();

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
}