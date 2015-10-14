package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

public class SavingsAccount extends Account {
	
	private static final long serialVersionUID = 1L;
	private double minBalance;
	private double interestRate;

	public static void main(String[] args) {
		
	}
	
	public SavingsAccount() {
		this.minBalance = 0;
		this.interestRate = 0;
	}
	
	/*******************************************************************
	 * @return the minBalance
	 ******************************************************************/
	public double getMinBalance() {
		return minBalance;
	}
	
	/*******************************************************************
	 * @return the interestRate
	 ******************************************************************/
	public double getInterestRate() {
		return interestRate;
	}
	
	/*******************************************************************
	 * @return the serialVersionuid
	 ******************************************************************/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*******************************************************************
	 * @param minBalance the minBalance to set
	 ******************************************************************/
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}
	
	/*******************************************************************
	 * @param interestRate the interestRate to set
	 ******************************************************************/
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "SavingsAccount [minBalance=" + minBalance + ", "
//		+ "interestRate=" + interestRate + "]";
//	}
}