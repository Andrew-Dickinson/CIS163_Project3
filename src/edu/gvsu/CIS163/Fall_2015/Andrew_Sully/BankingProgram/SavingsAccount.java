package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

/***********************************************************************
 * An account that has a minimum balance and an interest rate
 **********************************************************************/
public class SavingsAccount extends Account {
	//TODO: Javadoc
	private static final long serialVersionUID = 1L;
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
        if (balance > this.minBalance){
            super.setBalance(balance);
        } else {
            throw new IllegalArgumentException();
        }
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