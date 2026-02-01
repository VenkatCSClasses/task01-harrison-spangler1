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

    // Equivalence class of valid email prefixes 
    assertTrue(BankAccount.isEmailValid("abc-def@mail.com")); // valid use of "-" in local part, middle
    assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // valid use of "." in local part, middle
    assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // valid use of "_" in local part, middle

    // Equivalence class of valid email domains 
    assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // valid "-" in domain label, middle
    assertTrue(BankAccount.isEmailValid("abc.def@mail.co")); // valid 2-letter TLD, border

    // Equivalence class of invalid inputs
    assertFalse(BankAccount.isEmailValid(null)); // null string should be invalid, border

    // Equivalence class of '@' symbol rules
    assertFalse(BankAccount.isEmailValid("abcdef.mail.com")); // missing '@', middle
    assertFalse(BankAccount.isEmailValid("abc@@mail.com")); // more than one '@', border
    assertFalse(BankAccount.isEmailValid("@mail.com")); // missing local part, border
    assertFalse(BankAccount.isEmailValid("abc@")); // missing domain part, border

    // Border cases of . in local part
    assertFalse(BankAccount.isEmailValid(".a@mail.com")); // invalid use of '.'', border
    assertTrue(BankAccount.isEmailValid("a.b@mail.com")); // valid use of '.'', border

    // Border cases of - in local part
    assertFalse(BankAccount.isEmailValid("a-@mail.com")); // invalid use of '-', border
    assertTrue(BankAccount.isEmailValid("a-b@mail.com")); // valid use of '-', border

    // Border case of consecutive dots in local part
    assertFalse(BankAccount.isEmailValid("ab..c@mail.com")); // invalid use of '..', border
    assertTrue(BankAccount.isEmailValid("ab.c@mail.com")); // valid use of '.', other border

    // Border cases of domain must contain a dot
    assertFalse(BankAccount.isEmailValid("abc@mail")); // invalid, missing '.' in domain, border
    assertTrue(BankAccount.isEmailValid("abc@mail.com")); // valid use of '.' in domain, border

    // Border cases of consecutive dots in domain
    assertFalse(BankAccount.isEmailValid("abc@mail..com")); // invalid use of '..' in domain, border
    assertTrue(BankAccount.isEmailValid("abc@mail.com")); // valid use of '.' in domain, other border

    // Equivalence class of domain label cannot start/end with a -
    assertFalse(BankAccount.isEmailValid("abc@-mail.com")); // invalid use of '-', border
    assertFalse(BankAccount.isEmailValid("abc@mail-.com")); // invalid use of '-', border
    assertTrue(BankAccount.isEmailValid("abc@mail-archive.com")); // valid use of '-' in domain label, other border

    // Equivalence class of domain cannot start/end with a .
    assertFalse(BankAccount.isEmailValid("abc@.mail.com")); // invalid use of '.', border
    assertFalse(BankAccount.isEmailValid("abc@mail.com.")); // invalid use of '.', border
    assertTrue(BankAccount.isEmailValid("abc@mail.com")); // valid use of '.', other border

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