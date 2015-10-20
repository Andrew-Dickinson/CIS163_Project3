package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 10/20/15.
 */
public class Tests {

    @Test(expected=IllegalArgumentException.class)
    public void testUniquieID(){
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("lasdjf"));
        bm.addAccount(new CheckingAccount("lasdjf"));
    }

    @Test
    public void testGetSize() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("1"));
        bm.addAccount(new SavingsAccount("2"));
        bm.addAccount(new CheckingAccount("3"));
        assertTrue(bm.getSize() == 3);
    }

    @Test
    public void testGetElementAt() {
        BankModel bm = new BankModel();
        Account a = new CheckingAccount("asldjf");
        bm.addAccount(new SavingsAccount("a"));
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new CheckingAccount("c"));
        bm.addAccount(a);

        assertEquals(a, bm.getElementAt(3));
    }

    @Test
    public void testRemoveAccount() {
        BankModel bm = new BankModel();
        Account a = new CheckingAccount("asldjf");
        bm.addAccount(new SavingsAccount("a"));
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new CheckingAccount("c"));
        bm.addAccount(new SavingsAccount("d"));
        bm.addAccount(a);

        bm.removeAccount(3);

        assertEquals(a, bm.getElementAt(3));
    }

    @Test
    public void testUpdateAccount() {
        BankModel bm = new BankModel();
        Account a = new SavingsAccount("asldjf");
        bm.addAccount(new SavingsAccount("1"));
        bm.addAccount(new SavingsAccount("2"));
        bm.addAccount(new CheckingAccount("3"));
        bm.addAccount(new SavingsAccount("4"));

        bm.updateAccount(2, a);

        assertEquals(a, bm.getElementAt(2));
    }

    @Test
    public void testSortByAccountNumber() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d"));
        bm.addAccount(new SavingsAccount("a"));

        bm.sortByAccountNumber();
        Account firstElem = (Account) bm.getElementAt(0);
        assertTrue(firstElem.getNumber().equals("a"));

        bm.sortByAccountNumber(false);
        firstElem = (Account) bm.getElementAt(0);
        assertTrue(firstElem.getNumber().equals("d"));
    }

    @Test
    public void testSortByAccountName() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("h", "b"));
        bm.addAccount(new SavingsAccount("z", "c"));
        bm.addAccount(new CheckingAccount("f", "d"));
        bm.addAccount(new SavingsAccount("m","a"));

        bm.sortByAccountName();
        Account firstElem = (Account) bm.getElementAt(0);
        assertTrue(firstElem.getOwnerName().equals("a"));

        bm.sortByAccountName(false);
        firstElem = (Account) bm.getElementAt(0);
        assertTrue(firstElem.getOwnerName().equals("d"));
    }

    @Test
    public void testSortByDateOpened() {
        //TODO: Implement this test
    }

    @Test
    public void testSaveToBinaryFile() throws Exception {
        //TODO: Implement this test
    }

    @Test
    public void testSaveToTextFile() throws Exception {
        //TODO: Implement this test
    }
}