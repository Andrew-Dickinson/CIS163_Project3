package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

/***********************************************************************
 * An account that has a monthly fee
 **********************************************************************/
public class CheckingAccount extends Account {
	//TODO: Javadoc this variable
	private static final long serialVersionUID = 1L;

    /**
     * The fee to be deducted from this account every month
     */
	private double monthlyFee;
	
	public CheckingAccount() {
            //TODO: Other constructors
			monthlyFee = 0;
	}
}