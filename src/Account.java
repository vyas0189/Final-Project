public class Account {

    private String accountNumber;
    private String lastName;
    private String firstName;
    private double balance;
    private String status;
    double amountNumber;

    public Account(String accountNumber, String lastName, String firstName, double balance, String status) {
        this.accountNumber = accountNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void transfer(double amountValue) {
        amountNumber = amountValue;
        balance = balance + amountNumber;
    }

    public double setCurrentBalance() {
        return balance;
    }

}