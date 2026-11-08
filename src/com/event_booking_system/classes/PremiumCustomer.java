package com.event_booking_system.classes;

public class PremiumCustomer extends Customer {
    private static final double PREMIUM_DISCOUNT = 0.05;

    public PremiumCustomer(int customerID, String customerName, double balance) {
        super(customerID, customerName, balance);
    }

    public PremiumCustomer(PremiumCustomer other) {
        super(other);
    }

    @Override
    public boolean canBuy(double amount) {
        return amount >= 0 && getBalance() * (1 - PREMIUM_DISCOUNT) >= amount;
    }

    @Override
    // Returns to the paid amount
    public double buyTicket(double ticketPrice) {
        double discountedPrice = ticketPrice * (1 - PREMIUM_DISCOUNT);

        super.buyTicket(discountedPrice);

        return discountedPrice;
    }
}
