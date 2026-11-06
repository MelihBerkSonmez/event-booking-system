
public class PremiumCustomer extends Customer {

    private double PremiumDiscount = 0.05;

    public PremiumCustomer(int customerID, String customerName,
                           double balance) {
        super(customerID, customerName, balance);
    }
    public PremiumCustomer(PremiumCustomer other) {
        super(other);
    }

    @Override
    public void buyTicket(double ticketPrice) {

        double discountedPrice = ticketPrice * (1 - PremiumDiscount);

        super.buyTicket(discountedPrice);
    }
}
