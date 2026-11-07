import java.util.ArrayList;

public class BookingSystem {
    private ArrayList<Event> events;
    private ArrayList<Customer> customers;
    private ArrayList<Booking> bookings;
    private EventCategory restrictedCategory; //if null no group-specific rule

    //Parameterized Constructor
    public BookingSystem(ArrayList<Event> events, ArrayList<Customer> customers) {
        // we do want to directly modify the actual given objects
        this.events = events;
        this.customers = customers;
        bookings = new ArrayList<>();
    }

    // A constructor for group specific-rule
    public BookingSystem(ArrayList<Event> events, ArrayList<Customer> customers, EventCategory restrictedCategory) {
        this(events, customers);
        this.restrictedCategory = restrictedCategory;
    }

    /**
        Prints the total event, customer, and premium customer counts.
     */
    public void printSystemInfo() {
        // Getting the event and customer count is easy, just use .size()
        // to get the total premium customer count use instanceof or getClass()
        int totalEvents = events.size();
        int totalCustomers = customers.size();
        int totalPremiumCustomers = 0;

        for (Customer customer : customers) {
            if (customer instanceof PremiumCustomer) {
                totalPremiumCustomers++;
            }
        }

        //Printing
        System.out.println("========== SYSTEM INFORMATION ==========");
        System.out.println("Events: " + totalEvents);
        System.out.println("Customers: " + totalCustomers);
        System.out.println("Premium Customers: " + totalPremiumCustomers);
    }

    public void executeCommonOperations() {

        OperationAttempt b1 = createBooking(getCustomerFromID(201), getEventFromID(101), 1);
        OperationAttempt b2 = createBooking(getCustomerFromID(202), getEventFromID(103), 1);
        OperationAttempt b3 = createBooking(getCustomerFromID(205), getEventFromID(104), 6);
        OperationAttempt b4 = createBooking(getCustomerFromID(203), getEventFromID(101), 1);
        OperationAttempt b5 = createBooking(getCustomerFromID(201), getEventFromID(102), 0);
        OperationAttempt b6 = createBooking(getCustomerFromID(205), getEventFromID(102), 2);

        // Printing SUCCESS or FAILED
        System.out.println("========== COMMON OPERATIONS ==========");

        OperationAttempt[] operationAttempts = {b1, b2, b3, b4, b5, b6};

        for (int i=0; i<operationAttempts.length; i++) {
            if (operationAttempts[i] == null) {
                continue;
            } else if (operationAttempts[i].isSuccessful()) {
                System.out.printf("B%d: SUCCESS | Paid: %.2f\n", (i+1), operationAttempts[i].getBooking().getFinalPaidAmount());
            } else {
                System.out.printf("B%d: FAILED | %s\n", (i+1), operationAttempts[i].getFailReason());
            }
        }

        // C1 & C2
        Booking b1Booking = b1.getBooking();
        OperationAttempt c1 = cancelBooking(b1Booking);
        OperationAttempt c2 = cancelBooking(b1Booking);

        OperationAttempt[] operationAttempts2 = {c1, c2};

        for (int i=0; i<operationAttempts2.length; i++) {
            if (operationAttempts[i] == null) {
                continue;
            } else if (operationAttempts[i].isSuccessful()) {
                System.out.printf("C%d: SUCCESS | Paid: %.2f\n", (i+1), operationAttempts[i].getBooking().getFinalPaidAmount());
            } else {
                System.out.printf("C%d: FAILED | %s\n", (i+1), operationAttempts[i].getFailReason());
            }
        }

    }

    public void executeGroupSpecificOperations() {
        if (restrictedCategory == null) {
            throw new IllegalArgumentException("[BookingSystem, executeGroupSpecificOperations] restrictedCategory is not defined!");
        }

        // selecting eventId
        int selectedEventID = 0;
        switch (restrictedCategory) {
            case CONCERT:
                selectedEventID = 101;
                break;
            case CONFERENCE:
                selectedEventID = 102;
                break;
            case SPORTS:
                selectedEventID = 103;
                break;
        }

        System.out.println("========== GROUP SPECIFIC POLICIES ==========");
        System.out.println("Restricted Category: " + restrictedCategory);
        System.out.println("Selected EventID: " + selectedEventID);

        OperationAttempt r1 = createBooking(getCustomerFromID(201), getEventFromID(selectedEventID), 1);
        OperationAttempt r2 = createBooking(getCustomerFromID(202), getEventFromID(selectedEventID), 1);

        OperationAttempt[] operationAttempts = {r1, r2};
        for (int i=0; i<operationAttempts.length; i++) {
            if (operationAttempts[i] == null) {
                continue;
            } else if (operationAttempts[i].isSuccessful()) {
                System.out.printf("R%d: SUCCESS | Paid: %.2f\n", i+1, operationAttempts[i].getBooking().getFinalPaidAmount());
            } else {
                System.out.printf("R%d: FAILED | %s\n", i+1, operationAttempts[i].getFailReason());
            }
        }
    }

    //Helpers & Also can be used outside the class
    public Customer getCustomerFromID(int customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                return customer; // copy of the object, cannot modify the actual object
            }
        }

        return null;
    }

    public Event getEventFromID(int eventID) {
        for (Event event : events) {
            if (event.getEventID() == eventID) {
                return event; // reference to the object, can modify the actual object
            }
        }

        return null;
    }

    //Getters
    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public EventCategory getRestrictedCategory() {
        return restrictedCategory;
    }

    /**
     If the conditions are satisfied; creates an ACTIVE booking, adds it to the bookings list.
     Always returns to a OperationAttempt (whether it is successful or not)
     */
    public OperationAttempt createBooking(Customer customer, Event event, int ticketQuantity) {
        // Booking conditions
        if (customer == null) {
            return new OperationAttempt(null, false, "Customer Not Found");
        }

        if (event == null) {
            return new OperationAttempt(null, false, "Event Not Found");
        }

        if (!(ticketQuantity > 0)) {
            return new OperationAttempt(null, false, "Invalid Quantity");
        }

        if (event.getAvailableTickets() < ticketQuantity) {
            return new OperationAttempt(null, false, "Insufficient Capacity");
        }

        double originalTotalPrice = ticketQuantity * event.getTicketPrice();
        if (!customer.canBuy(originalTotalPrice)) { // premium now checks the discounted price
            return new OperationAttempt(null, false, "Insufficient Balance");
        }

        // Group-specific rule
        if (restrictedCategory != null && customer.getClass() == PremiumCustomer.class && event.getEventCategory() == restrictedCategory) {
            return new OperationAttempt(null, false, "Event category " + restrictedCategory + " is restricted for premium customers.");
        }

        // If did not return null until here, then booking is successful
        double finalPaidAmount = customer.buyTicket(originalTotalPrice);
        event.reserveTicket(ticketQuantity);

        String bookingID = createBookingID(customer, event);
        Booking booking = new Booking(bookingID, customer, event, ticketQuantity, originalTotalPrice, finalPaidAmount, BookingStatus.ACTIVE);

        bookings.add(booking);

        return new OperationAttempt(booking, true, null);
    }

    public OperationAttempt cancelBooking(Booking booking) {
        if (booking == null) {
            System.err.println("[BookingSystem, cancelBooking] booking argument cannot be null");
            return null;
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            return new OperationAttempt(booking, false, "Already Cancelled");
        }

        // if come here then successful
        booking.getEvent().releaseReservedTickets(booking.getTicketQuantity()); //release seats
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.getCustomer().refund(booking.getFinalPaidAmount());

        return new OperationAttempt(booking, true, null);
    }

    public static String createBookingID(Customer customer, Event event) {
        return customer.getCustomerID() + "_" + event.getEventID();
    }

}
