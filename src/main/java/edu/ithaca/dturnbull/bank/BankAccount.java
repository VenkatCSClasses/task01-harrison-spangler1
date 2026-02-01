package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * Withdraws money from this account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is negative
     * @throws InsufficientFundsException if amount is greater than the current balance
     * @post If amount is between 0 and balance (inclusive), balance is reduced by amount.
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
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
}
