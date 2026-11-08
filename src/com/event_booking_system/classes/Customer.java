package com.event_booking_system.classes;

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
        if (other == null) {
            throw new IllegalArgumentException("[Customer, Copy Constructor] customer argument cannot be null!");
        }
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

    // Returns to the paid amount
    public double buyTicket(double ticketPrice) {
        if (canBuy(ticketPrice)) {
            setBalance(balance - ticketPrice);
            return ticketPrice;
        }
        return -1; //special error case
    }

    public void refund(double amount) {
        if (amount < 0) {
            System.err.println("[Customer] amount argument cannot be less than zero!");
            return;
        }

        setBalance(balance + amount);
    }

    //not sure whether it's needed or not
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
