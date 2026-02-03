package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if (!isAmountValid(startingBalance) && startingBalance != 0.0) {
            throw new IllegalArgumentException("Starting balance is invalid.");
        }

        this.email = email;
        this.balance = startingBalance;
    }


    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * Checks if the amount is positive and has no more than 2 decimals
     * 
     * @param amount the amount of money to check
     * @return true if amount is valid, false otherwise
     */
    public static boolean isAmountValid(double amount) {
        if (amount <= 0) {
            return false;
        }

        double cents = amount * 100;
        long rounded = Math.round(cents);

        if (Math.abs(cents - rounded) < 0.000000001) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Withdraws money from this account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is negative
     * @throws InsufficientFundsException if amount is greater than the current balance
     * @post If amount is between 0 and balance (inclusive), balance is reduced by amount.
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount == 0.0) {
            return;
        }

        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdraw amount.");
        }

        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }



    public static boolean isEmailValid(String email){
        // If no email provided, return false
        if (email == null){
            return false;
        }

        // Check email format and special character usage for if it is valid
        String emailRegex = "^[A-Za-z0-9]+([._-][A-Za-z0-9]+)*@[A-Za-z0-9]+(-[A-Za-z0-9]+)*(\\.[A-Za-z0-9]+(-[A-Za-z0-9]+)*)+$";
        if (!email.matches(emailRegex)) {
            return false;
        }

        // Find the domain (after the "@")
        String domain = email.substring(email.indexOf('@') + 1);
        int lastDot = domain.lastIndexOf('.');

        // Find TLD
        if (lastDot == -1 || lastDot == domain.length() - 1){
            return false; // No TLD
        }
        String tld = domain.substring(lastDot + 1);

        // Check if the TLD is at least 2 characters and contains no illegal characters
        return tld.matches("[A-Za-z]{2,}");
    }

    /**
     * Deposits money into this account.
     *
     * @param amount the amount of money to deposit
     * @throws IllegalArgumentException if amount is invalid being negative or more than 2 decimal places
     * @post if amount is valid and greater than 0, balance increases by that amount
     */
    public void deposit(double amount) {
        if (amount == 0.0) {
            return;
        }

        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount.");
        }

        balance += amount;
    }


    /**
     * Transfers money from this account to another account
     *
     * @param toAccount the account that is receiving the money
     * @param amount the amount of money to transfer
     * @throws IllegalArgumentException if toAccount is null or amount is invalid being negative or more than 2 decimal places
     * @throws InsufficientFundsException if amount is greater than the current balance
     * @post if success, this balance decreases by the amount and toAccount balance increases by the same amount.
     */
    public void transfer(BankAccount toAccount, double amount) throws InsufficientFundsException {
    //     // if (toAccount == null) {
    //     //     throw new IllegalArgumentException("toAccount cannot be null.");
    //     // }

    //     // if (amount == 0.0) {
    //     //     return;
    //     // }

    //     // if (!isAmountValid(amount)) {
    //     //     throw new IllegalArgumentException("Invalid transfer amount.");
    //     // }

    //     // this.withdraw(amount);
    //     // toAccount.deposit(amount);
    }


}
