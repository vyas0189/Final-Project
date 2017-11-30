public class Account {

    private String accountNumber;
    private String lastName;
    private String firstName;
    private double balance;
    private String status;

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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void transfer(double amountValue) {
        balance = balance + amountValue;
    }
}