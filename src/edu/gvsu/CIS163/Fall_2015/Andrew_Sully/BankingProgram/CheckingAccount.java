package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

/***********************************************************************
 * An account that has a monthly fee
 **********************************************************************/
public class CheckingAccount extends Account {
	//TODO: Javadoc this variable
	private static final long serialVersionUID = 920560622L;

    /**
     * Identifies the class for the toString() method
     */
    public static final String classIdentifier = "CheckingAccount";

    /**
     * The number of items represented in the output of toString()
     */
    private static final int numberOfItemsInToString = 6;

    /**
     * The fee to be deducted from this account every month
     */
	private double monthlyFee;
	
	public CheckingAccount() {
            //TODO: Other constructors
			monthlyFee = 0;
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
            setMonthlyFee(Double.parseDouble(parsedStrings[5]));
        } catch (NumberFormatException e){
            //If the numbers weren't right, it's an argument problem
            throw new IllegalArgumentException();
        } catch (NullPointerException e){
            //If one of the strings is null, it's an argument problem
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Get the monthly fee for this account
     * @return The monthly fee
     ******************************************************************/
    public double getMonthlyFee() {
        return monthlyFee;
    }

    /*******************************************************************
     * Set the monthly fee for this account
     * @param monthlyFee The new monthly fee
     ******************************************************************/
    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    /*******************************************************************
     * Generates a string representation of this account
     * @return A string in the format:
     *          "ID:NUMBER;NAME;DATE_OPENED;BALANCE;MONTHLY_FEE"
     ******************************************************************/
    @Override
    public String toString(){
        //Hidden toString calls for dateOpened, balance, and monthlyFee
        //toStringSeparator = ";"
        return  classIdentifier + toStringSeparator
                + getNumber() + toStringSeparator
                + getOwnerName() + toStringSeparator
                + getDateOpened() + toStringSeparator
                + getBalance() + toStringSeparator
                + getMonthlyFee();
    }
}