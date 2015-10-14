/**
 * 
 */
package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;
import java.io.Serializable;
import java.util.GregorianCalendar;


/***********************************************************************
 * @author Sully
 *
 **********************************************************************/
public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	private int number;
	private String owner;
	private GregorianCalendar dateOpened;
	private double balance;

	public Account()
	{
		number = 0;
		owner = "";
		//dateOpened = "";
		balance = 0;
	}

	/*******************************************************************
	 * @return the number
	 ******************************************************************/
	public int getNumber() {
		return number;
	}
	
	/*******************************************************************
	 * @return the owner
	 ******************************************************************/
	public String getOwner() {
		return owner;
	}
	
	/*******************************************************************
	 * @return the dateOpened
	 ******************************************************************/
	public GregorianCalendar getDateOpened() {
		return dateOpened;
	}
	
	/*******************************************************************
	 * @return the balance
	 ******************************************************************/
	public double getBalance() {
		return balance;
	}
	
	/*******************************************************************
	 * @return the serialversionuid
	 ******************************************************************/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*******************************************************************
	 * @param number the number to set
	 ******************************************************************/
	public void setNumber(int number) {
		this.number = number;
	}

	/*******************************************************************
	 * @param owner the owner to set
	 ******************************************************************/
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/*******************************************************************
	 * @param dateOpened the dateOpened to set
	 ******************************************************************/
	public void setDateOpened(GregorianCalendar dateOpened) {
		this.dateOpened = dateOpened;
	}

	/*******************************************************************
	 * @param balance the balance to set
	 ******************************************************************/
	public void setBalance(double balance) {
		this.balance = balance;
	}
}