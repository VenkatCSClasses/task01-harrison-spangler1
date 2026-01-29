package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        // An underscore, peroid or dash must be followed by an alphanumeric character
        assertFalse( BankAccount.isEmailValid("abc-@mail.com")); 
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com")); 
        assertFalse( BankAccount.isEmailValid(".abc@mail.com")); 
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com")); // Illegal special character
        
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c")); // The TLD must be at least 2 chars
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); // Domains cannot contain special characters other than dashes
        assertFalse( BankAccount.isEmailValid("abc.def@mail")); // A TLD is required
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); // Cannot have two "." in a row like that
    }
    
    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}