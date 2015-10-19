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
    //TODO: Javadoc this variable
	private static final long serialVersionUID = 1L;

    /**
     * The unique account number for this account
     */
	private int number;

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

	public Account()
	{
        //TODO: Different constructors?
		number = 0;
		owner = "";
		//dateOpened = "";
		balance = 0;
	}

	/*******************************************************************
	 * Gets the account number of this account
	 * @return The account number
	 ******************************************************************/
	public int getNumber() {
		return number;
	}
	
	/*******************************************************************
     * Gets the name of the owner of this account
	 * @return The name of the owner
	 ******************************************************************/
	public String getOwner() {
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
     * Gets the current balance of the account
	 * @return The current balance of the account
	 ******************************************************************/
	public double getBalance() {
		return balance;
	}
	
	/*******************************************************************
     *
	 * @return the serialversionuid
	 ******************************************************************/
	public static long getSerialversionuid() {
		//TODO: Figure out what this does and write javadoc
        return serialVersionUID;
	}
	
	/*******************************************************************
     * Sets the account number
	 * @param number The new account number for this account
	 ******************************************************************/
	public void setNumber(int number) {
		this.number = number;
	}

	/*******************************************************************
     * Sets the name of the owner of this account
	 * @param owner The name of the owner
	 ******************************************************************/
	public void setOwner(String owner) {
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
     * Sets the balance of the account
	 * @param balance The new balance of the account
	 ******************************************************************/
	public void setBalance(double balance) {
		this.balance = balance;
	}

    //TODO: Debit and credit methods
}