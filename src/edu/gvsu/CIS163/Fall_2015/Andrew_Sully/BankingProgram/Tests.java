package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import org.junit.Test;

import java.io.IOException;
import java.util.GregorianCalendar;
import static org.junit.Assert.*;

/***********************************************************************
 * Contains basic tests for this package
 **********************************************************************/
public class Tests {
    //Test that two overlapping account numbers throws an error
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void testUniqueID(){
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("lasdjf"));
        bm.addAccount(new CheckingAccount("lasdjf"));
    }

    //Test that even in an update, account numbers stay unique
    @Test(expected=java.lang.IllegalArgumentException.class)
    public void testUniqueIDWithUpdate(){
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("2"));

        Account secondAccount = new SavingsAccount("blah");
        bm.addAccount(secondAccount);

        Account thirdAccount = secondAccount.clone();
        thirdAccount.setNumber("2");
        bm.updateAccount(secondAccount, thirdAccount);
    }

    //Test that added an unsupported class throws an error
    @Test(expected=IllegalArgumentException.class)
    public void testUnsupportedClass(){
        BankModel bm = new BankModel(new Class[]{CheckingAccount.class});
        bm.addAccount(new SavingsAccount());
    }

    //Test the getRows() function
    @Test
    public void testGetRows() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("1"));
        bm.addAccount(new SavingsAccount("2"));
        bm.addAccount(new CheckingAccount("3"));
        assertTrue(bm.getRowCount() == 3);
    }

    //Test getting an account at a specific index
    @Test
    public void testGetAccount() {
        BankModel bm = new BankModel();
        Account a = new CheckingAccount("asldjf", "Andrew");
        bm.addAccount(new SavingsAccount("1"));
        bm.addAccount(new SavingsAccount("2"));
        bm.addAccount(new CheckingAccount("3"));
        bm.addAccount(a);

        assertEquals(a, bm.getAccount(3));
    }

    //Test the getValueAt() function for the model
    @Test
    public void testGetElementAt() {
        BankModel bm = new BankModel();
        Account a = new CheckingAccount("asldjf");
        bm.addAccount(new SavingsAccount("a"));
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new CheckingAccount("c", "Andrew"));
        bm.addAccount(a);

        assertEquals("Andrew", bm.getValueAt(2, 2));
    }

    //Test the removal of an account from the model
    @Test
    public void testRemoveAccount() {
        BankModel bm = new BankModel();
        Account a = new CheckingAccount("asldjf", "Andrew");
        bm.addAccount(new SavingsAccount("a"));
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new CheckingAccount("c"));
        bm.addAccount(new SavingsAccount("d"));
        bm.addAccount(a);

        bm.removeAccount(3);

        assertEquals("Andrew", bm.getValueAt(3, 2));
    }

    //Test the updating of an account in the model
    @Test
    public void testUpdateAccount() {
        BankModel bm = new BankModel();
        Account a = new SavingsAccount("asldjf", "Andrew");
        bm.addAccount(new SavingsAccount("1"));
        bm.addAccount(new SavingsAccount("2"));
        bm.addAccount(new CheckingAccount("3"));
        bm.addAccount(new SavingsAccount("4"));

        bm.updateAccount(2, a);

        assertEquals("asldjf", bm.getValueAt(2, 1));
    }

    //Test sorting based on account number
    @Test
    public void testSortByAccountNumber() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d"));
        bm.addAccount(new SavingsAccount("a"));

        bm.sortByAccountNumber();
        Account firstElem = bm.getAccount(0);
        assertTrue(firstElem.getNumber().equals("a"));

        bm.sortByAccountNumber(false);
        firstElem = bm.getAccount(0);
        assertTrue(firstElem.getNumber().equals("d"));
    }

    //Test sorting based on owner name
    @Test
    public void testSortByAccountName() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("h", "b"));
        bm.addAccount(new SavingsAccount("z", "c"));
        bm.addAccount(new CheckingAccount("f", "d"));
        bm.addAccount(new SavingsAccount("m", "a"));

        bm.sortByAccountName();
        Account firstElem = bm.getAccount(0);
        assertTrue(firstElem.getOwnerName().equals("a"));

        bm.sortByAccountName(false);
        firstElem = bm.getAccount(0);
        assertTrue(firstElem.getOwnerName().equals("d"));
    }

    //Test sorting based on the date opened
    @Test
    public void testSortByDateOpened() {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("h", "b", new GregorianCalendar(2015, 2, 23)));
        bm.addAccount(new SavingsAccount("z", "c", new GregorianCalendar(2015, 6, 16)));
        bm.addAccount(new CheckingAccount("f", "d", new GregorianCalendar(2015, 1, 6)));
        bm.addAccount(new SavingsAccount("m", "a", new GregorianCalendar(2015, 5, 1)));

        bm.sortByDateOpened();
        Account firstElem = bm.getAccount(0);
        assertTrue(firstElem.getNumber().equals("f"));

        bm.sortByDateOpened(false);
        firstElem = bm.getAccount(0);
        assertTrue(firstElem.getNumber().equals("z"));
    }

    //Test the header resolving functionality of the BankModel
    @Test
    public void testHeaderResolution(){
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("a"));
        bm.addAccount(new CheckingAccount("b"));
        HeaderName[] headers = bm.getHeaders();

        assertEquals(headers[0].getFieldName(),
                    Account.defaultDataHeaders[0].getFieldName());
        assertEquals(headers[1].getFullName(),
                    Account.defaultDataHeaders[1].getFullName());
        assertEquals(headers[2], Account.defaultDataHeaders[2]);
        assertEquals(headers[3], Account.defaultDataHeaders[3]);

        assertEquals(8, headers.length);
    }

    //Test the recursive header resolving method
    @Test
    public void testResolveRecursive(){
        HeaderName[][] h1 = {
            new HeaderName[]{
                new HeaderName(Account.class, "1"),
                new HeaderName(Account.class, "2"),
                new HeaderName(Account.class, "4"),
                new HeaderName(Account.class, "5"),
                new HeaderName(Account.class, "7")},
            new HeaderName[]{
                new HeaderName(Account.class, "3"),
                new HeaderName(Account.class, "4")},
            new HeaderName[]{
                new HeaderName(Account.class, "5"),
                new HeaderName(Account.class, "6"),
                new HeaderName(Account.class, "7")}
        };
        HeaderName[] resolved = BankModel.resolveHeadersRecurse(h1);

        for (int i = 1; i < 7; i++){
            boolean found = false;
            for (int j = 0; j < 7; j++){
                if (resolved[j].getFieldName().equals("" + i))
                    found = true;
            }
            assertTrue(found);
        }
    }

    //Test the equals() method of BankModel
    @Test
    public void testBankModelEquals(){
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d"));
        bm.addAccount(new SavingsAccount("a"));

        BankModel bm2 = new BankModel();
        bm2.addAccount(new SavingsAccount("b"));
        bm2.addAccount(new SavingsAccount("c"));
        bm2.addAccount(new CheckingAccount("d"));
        bm2.addAccount(new SavingsAccount("a"));

        assertEquals(bm, bm2);

        bm2.sortByAccountNumber();
        assertNotEquals(bm, bm2);

        bm.sortByAccountNumber();
        assertEquals(bm, bm2);

        bm2.removeAccount(0);
        assertNotEquals(bm, bm2);
    }

    //Test saving and loading to a binary file
    @Test
    public void testSaveToBinaryFile() throws IOException{
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d"));
        bm.addAccount(new SavingsAccount("a"));

        bm.saveToBinaryFile("test.bnk");

        BankModel newBM = new BankModel();
        newBM.loadFromBinaryFile("test.bnk");

        assertEquals(bm, newBM);
    }

    //Test saving and loading to a text file
    @Test
    public void testSaveToTextFile() throws IOException{
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d"));
        bm.addAccount(new SavingsAccount("a"));

        bm.saveToTextFile("test.txt");

        BankModel newBM = new BankModel();
        newBM.loadFromTextFile("test.txt");
        assertEquals(bm, newBM);
    }

    //Test saving and loading to an XML file
    @Test
    public void testSaveToXMLFile() throws IOException {
        BankModel bm = new BankModel();
        bm.addAccount(new SavingsAccount("b", "Sully"));
        bm.addAccount(new SavingsAccount("c"));
        bm.addAccount(new CheckingAccount("d", "Andrew"));
        bm.addAccount(new SavingsAccount("a"));

        bm.saveToXMLFile("test.xml");

        BankModel newBM = new BankModel();
        newBM.loadFromXMLFile("test.xml");

        assertEquals(bm, newBM);
    }

    //Test saving and loading a blank model to a text file
    @Test
    public void testEmptySaveToTextFile() throws IOException{
        BankModel bm = new BankModel();

        bm.saveToTextFile("test2.txt");

        BankModel newBM = new BankModel();
        newBM.loadFromTextFile("test2.txt");
        assertEquals(bm, newBM);
    }

    //Test saving and loading a blank model to an XML file
    @Test
    public void testEmptySaveToXMLFile() throws IOException {
        BankModel bm = new BankModel();

        bm.saveToXMLFile("test2.xml");

        BankModel newBM = new BankModel();
        newBM.loadFromXMLFile("test2.xml");

        assertEquals(bm, newBM);
    }
}