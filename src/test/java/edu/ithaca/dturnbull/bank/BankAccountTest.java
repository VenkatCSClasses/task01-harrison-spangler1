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
    void getBalance_zero_boundary() {
        // Equivalence class of balance == 0, border
        BankAccount acct = new BankAccount("a@b.com", 0.0);
        assertEquals(0.0, acct.getBalance(), 0.001);
    }

    @Test
    void getBalance_negative_middle() {
        // Equivalence class of balance < 0, middle
        BankAccount acct = new BankAccount("a@b.com", -50.0);
        assertEquals(-50.0, acct.getBalance(), 0.001);
    }

    @Test
    void getBalance_updates_after_valid_withdraw() throws InsufficientFundsException {
        // Equivalence class of balance reflects state change after successful withdraw
        BankAccount acct = new BankAccount("a@b.com", 200.0);
        acct.withdraw(25.0);
        assertEquals(175.0, acct.getBalance(), 0.001);
    }

    @Test
    void getBalance_unchanged_after_failed_withdraw() {
        // Equivalence classof  balance unchanged when withdraw throws exception
        BankAccount acct = new BankAccount("a@b.com", 200.0);

        assertThrows(InsufficientFundsException.class, () -> acct.withdraw(1000.0));
        assertEquals(200.0, acct.getBalance(), 0.001);
    }

    @Test
    void withdraw_middle_valid() throws InsufficientFundsException {
        // Equivalence class of 0 < amount < balance, middle
        BankAccount acct = new BankAccount("a@b.com", 200.0);
        acct.withdraw(100.0); // middle
        assertEquals(100.0, acct.getBalance(), 0.001);
    }

    @Test
    void withdraw_zero_boundary() throws InsufficientFundsException {
        // Equivalence class of amount == 0, border
        BankAccount acct = new BankAccount("a@b.com", 200.0);
        acct.withdraw(0.0); // border
        assertEquals(200.0, acct.getBalance(), 0.001);
    }

    @Test
    void withdraw_equals_balance_boundary() throws InsufficientFundsException {
        // Equivalence class of amount == balance, border
        BankAccount acct = new BankAccount("a@b.com", 200.0);
        acct.withdraw(200.0); // border
        assertEquals(0.0, acct.getBalance(), 0.001);
    }

    @Test
    void withdraw_greater_than_balance_invalid() {
        // Equivalence class of amount > balance, border
        BankAccount acct = new BankAccount("a@b.com", 200.0);

        assertThrows(InsufficientFundsException.class, () -> acct.withdraw(200.01)); // border
        assertEquals(200.0, acct.getBalance(), 0.001); // balance unchanged
    }

    @Test
    void withdraw_negative_invalid() {
        // Equivalence class of amount < 0, border
        BankAccount acct = new BankAccount("a@b.com", 200.0);

        assertThrows(IllegalArgumentException.class, () -> acct.withdraw(-0.01)); // border
        assertEquals(200.0, acct.getBalance(), 0.001); // balance unchanged
    }
    
    @Test
    void withdraw_negative_middle_invalid() {
        // Equivalence class of amount < 0, middle
        BankAccount acct = new BankAccount("a@b.com", 200.0);
        assertThrows(IllegalArgumentException.class, () -> acct.withdraw(-50.0)); // middle
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


    @Test
    void isAmountValidTest(){
        // Equivalence class of valid amounts
        assertTrue(BankAccount.isAmountValid(100)); // whole number, middle
        assertTrue(BankAccount.isAmountValid(999999999)); // large whole number, border
        assertTrue(BankAccount.isAmountValid(50)); // whole number, middle
        assertTrue(BankAccount.isAmountValid(1)); // small whole number, border
        assertTrue(BankAccount.isAmountValid(10)); // whole number, middle

        // Equivalence class of amounts with decimals
        assertTrue(BankAccount.isAmountValid(99.99)); // two decimal places, border
        assertTrue(BankAccount.isAmountValid(0.01)); // two decimal places, middle
        assertTrue(BankAccount.isAmountValid(50.00)); // two decimal places, middle
        assertTrue(BankAccount.isAmountValid(100000000000.10)); // two decimal place, border
        assertTrue(BankAccount.isAmountValid(25.50)); // two decimal place, middle
        assertTrue(BankAccount.isAmountValid(12.3));  // one decimal place, middle 
        assertTrue(BankAccount.isAmountValid(5.5));  // one decimal place, middle
        assertTrue(BankAccount.isAmountValid(1.23)); // two decimal places, border
        assertTrue(BankAccount.isAmountValid(0.1)); // one decimal place, border

        // Equivalence class of invalid amounts
        assertFalse(BankAccount.isAmountValid(0)); // zero amount, border
        assertFalse(BankAccount.isAmountValid(-100)); // negative whole number, middle
        assertFalse(BankAccount.isAmountValid(-50)); // negative whole number, middle
        assertFalse(BankAccount.isAmountValid(-9999999)); // large negative whole number, border
        assertFalse(BankAccount.isAmountValid(-5000)); // negative whole number, middle
        assertFalse(BankAccount.isAmountValid(-1)); // negative whole number, border

        // Equivalence class of invalid amounts with decimal places
        assertFalse(BankAccount.isAmountValid(25.999)); // three decimal places, middle
        assertFalse(BankAccount.isAmountValid(0.001)); // three decimal places, border
        assertFalse(BankAccount.isAmountValid(100.23444444444)); // many decimal places, middle
        assertFalse(BankAccount.isAmountValid(50.5555555)); // many decimal places, middle
        assertFalse(BankAccount.isAmountValid(-0.001)); // three decimal places negative, border
        assertFalse(BankAccount.isAmountValid(-100.12333333)); // many decimal places negative, middle
        assertFalse(BankAccount.isAmountValid(-0.01)); // two decimal places negative, border
        assertFalse(BankAccount.isAmountValid(0.0)); // zero with decimal, border
        assertFalse(BankAccount.isAmountValid(-0.0)); // zero with decimal, border
        assertFalse(BankAccount.isAmountValid(1.234)); // three decimal places, invalid
    }
}