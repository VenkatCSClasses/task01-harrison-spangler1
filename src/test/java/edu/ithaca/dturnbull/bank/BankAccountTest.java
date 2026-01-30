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
        assertTrue(BankAccount.isEmailValid( "a@b.com")); // valid email address (equivalence class of a valid email)
        
        assertFalse( BankAccount.isEmailValid("")); // empty string, is a border case showing how an empty string should be handled.

        // These belong in the equivalence class that an underscore, peroid or dash must be followed by an alphanumeric character
        assertFalse( BankAccount.isEmailValid("abc-@mail.com")); // Prefix cannot end with a "-", (border case)
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com")); // Cannot have two "." in a row
        assertFalse( BankAccount.isEmailValid(".abc@mail.com")); // Cannot start with a "." (border case)
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com")); // Illegal special character
        
        // These belong in the equivalence class that state what characters are allowed in the domain
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c")); // The TLD must be at least 2 chars, border case
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); // Domains cannot contain special characters other than dashes
        assertFalse( BankAccount.isEmailValid("abc.def@mail")); // A TLD is required
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com")); // Cannot have two "." in a row like that (border case)

        /* 
        Notes:
        I would add more checks for in the equivalence class of valid email prefixes including the cases of:
            - Valid use of dashes in the email prefix
            - Valid use of "." in the email prefix
            - Valid use of "_" in the email prefix

        Also I would make checks in the equivalence class of valid email domains including:
            - Two letter TLDs (border case)
            - Dashes in the domain
        */
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