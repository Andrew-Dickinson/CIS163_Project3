package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.io.Serializable;

/***********************************************************************
 * An account that has a minimum balance and an interest rate
 **********************************************************************/
public class SavingsAccount extends Account implements Serializable {
	//TODO: Javadoc
	private static final long serialVersionUID = 996106642L;

    /**
     * Identifies the class for the toString() method
     */
    public static final String classIdentifier = "SavingsAccount";

    /**
     * The number of items represented in the output of toString()
     */
    private static final int numberOfItemsInToString = 7;

    /**
     * The minimum balance the account must have
     */
	private double minBalance;
    /**
     * The interest rate for this account
     */
	private double interestRate;
	
	public SavingsAccount() {
        //TODO: Other constructors
		this.minBalance = 0;
		this.interestRate = 0;
	}

    public SavingsAccount(String number){
        this();
        setNumber(number);
    }

    public SavingsAccount(String number, String name){
        this(number);

        setOwnerName(name);
    }

    /*******************************************************************
     * Set account instance variables based on a dataString
     * @param dataString A string in the format generated by toString()
     * @throws IllegalArgumentException if string is formatted wrongly
     ******************************************************************/
    @Override
    public void parseFromString(String dataString){
        String[] parsedStrings = dataString.split(toStringSeparator);
        if (parsedStrings.length != numberOfItemsInToString)
            throw new IllegalArgumentException();

        if(!parsedStrings[0].equals(classIdentifier))
            throw new IllegalArgumentException();

        try  {
            //Try to parse out the strings into the various variables
            setNumber(parsedStrings[1]);
            setOwnerName(parsedStrings[2]);
            //TODO: Find a way to convert a string to a Gregorian calendar
//            setDateOpened(parsedStrings[3]);
            setBalance(Double.parseDouble(parsedStrings[4]));
            setMinBalance(Double.parseDouble(parsedStrings[5]));
            setInterestRate(Double.parseDouble(parsedStrings[6]));
        } catch (NumberFormatException | NullPointerException e){
            //If the numbers weren't right, it's an argument problem
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
	 * Gets the minimum required balance for this account
	 * @return the minimum required balance
	 ******************************************************************/
	public double getMinBalance() {
		return minBalance;
	}
	
	/*******************************************************************
     * Gets the interest rate for this account
	 * @return the interest rate for this account
	 ******************************************************************/
	public double getInterestRate() {
		return interestRate;
	}
	
	/*******************************************************************
	 * @return the serialVersionuid
	 ******************************************************************/
	public static long getSerialversionuid() {
		//TODO: Javadoc this
        return serialVersionUID;
	}
	
	/*******************************************************************
     * Sets the minimum balance for this account
	 * @param minBalance the new minimum balance to set
	 ******************************************************************/
	public void setMinBalance(double minBalance) {
        //TODO: Fix potential bug where minBalance could be set lower than balance
		this.minBalance = minBalance;
	}
	
	/*******************************************************************
     * Sets the interest rate for this account
	 * @param interestRate the new interest rate to set
	 ******************************************************************/
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}


    /*******************************************************************
     * Sets the balance of the account if balance >= this.minBalance
     * @param balance The new balance of the account
     * @throws IllegalArgumentException if balance < this.minBalance
     ******************************************************************/
    @Override
    public void setBalance(double balance){
        //TODO: Determine if this is the correct behavior of minBalance
        if (balance >= this.minBalance){
            super.setBalance(balance);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Generates a string representation of this account
     * @return A string in the format:
     *    "ID:NUMBER;NAME;DATE_OPENED;BALANCE;MIN_BALANCE;INTEREST_RATE"
     ******************************************************************/
    @Override
    public String toString(){
        //Hidden toString calls here
        return  classIdentifier + toStringSeparator
                + getNumber() + toStringSeparator
                + getOwnerName() + toStringSeparator
                + getDateOpened() + toStringSeparator
                + getBalance() + toStringSeparator
                + getMinBalance() + toStringSeparator
                + getInterestRate();
    }

    //TODO: Clean this up
//	/* (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "SavingsAccount [minBalance=" + minBalance + ", "
//		+ "interestRate=" + interestRate + "]";
//	}
}