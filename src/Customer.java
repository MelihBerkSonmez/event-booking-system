
import java.util.Objects;

public class Customer {

    
    private int customerID;
    private String customerName;
    private double balance;

    
    public Customer(int customerID, String customerName, double balance) {
        this.customerID = customerID;
        this.customerName = customerName;
        setBalance(balance);
    }

    public Customer(Customer other) {
        this(other.customerID, other.customerName, other.balance);
    }

    
    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    //for refund when canceled
    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException(
                "Balance cannot be negative."
            );
        }
    }

    public boolean canBuy(double amount) {
        return amount >= 0 && balance >= amount;
    }

    public void buyTicket(double ticketPrice) {
        if (canBuy(ticketPrice)) {
            balance -= ticketPrice;
        }
    }

    //not sure wheter its needed or not
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Customer))
            return false;

        Customer other = (Customer) obj;

        return customerID == other.customerID;
    }

   
}
