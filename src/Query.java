import java.util.ArrayList;

public class Query {
    private ArrayList<Event> events;
    private ArrayList<Customer> customers;
    private ArrayList<Booking> activeBookings;

    public Query(ArrayList<Event> events, ArrayList<Customer> customers, ArrayList<Booking> bookings) {
        this.events = deepCopyEvents(events); //I do now want to modify them, so deep copying
        this.customers = deepCopyCustomer(customers);
        this.activeBookings = deepCopyActiveBookings(bookings);
    }

    public void printMostPopularEvent() {
        if (activeBookings.isEmpty()) {
            System.err.println("No ACTIVE bookings!");
            return;
        }

        int mostNumReserved = Integer.MIN_VALUE;
        Event mostPopularEvent = events.getFirst();

        for (int i=0; i<events.size(); i++) {
            int reservedSeats = events.get(i).getReservedTickets();

            if (reservedSeats > mostNumReserved) {
                mostNumReserved = events.get(i).getReservedTickets();
                mostPopularEvent = events.get(i);
            } else if (reservedSeats == mostNumReserved) {
                //choose the smallest
                if (mostPopularEvent != null && events.get(i).getEventID() < mostPopularEvent.getEventID()) {
                    mostPopularEvent = events.get(i);
                }
            }
        }

        System.out.printf("Q1: %s | %d tickets\n", mostPopularEvent.getEventName(), mostPopularEvent.getReservedTickets());
    }

    public void printHighestRevenueEvent() {
        if (activeBookings.isEmpty()) {
            System.err.println("No ACTIVE bookings!");
            return;
        }

        double highestRevenue = Integer.MIN_VALUE;
        Event chosenEvent = null;

        for (int i=0; i<activeBookings.size(); i++) {
            Event event = activeBookings.get(i).getEvent();
            double eventRevenue = 0;
            for (Booking booking : activeBookings) {
                if (booking.getEvent().equals(event)) {
                    eventRevenue += booking.getFinalPaidAmount();
                }
            }

            if (eventRevenue > highestRevenue) {
                highestRevenue = eventRevenue;
                chosenEvent = event;
            } else if (eventRevenue == highestRevenue) {
                if (chosenEvent != null && event.getEventID() < chosenEvent.getEventID()) { //short circuit
                    chosenEvent = event;
                }
            }
        }

        System.out.printf("Q2: %s | %.2f\n", chosenEvent.getEventName(), highestRevenue);
    }

    public void printTopCustomer() {
        // defensive programming
        if (activeBookings.size() == 0) {
            System.err.println("No ACTIVE bookings!");
            return;
        }

        int maxReservedTickets = Integer.MIN_VALUE;
        Customer chosenCustomer = null;

        for (int i=0; i<activeBookings.size(); i++) {
            Customer customer = activeBookings.get(i).getCustomer();
            int reservedTickets = 0;
            for (Booking booking : activeBookings) {
                if (booking.getCustomer().equals(customer)) {
                    reservedTickets += booking.getTicketQuantity();
                }
            }

            if (reservedTickets > maxReservedTickets) {
                maxReservedTickets = reservedTickets;
                chosenCustomer = customer;
            } else if (reservedTickets == maxReservedTickets) {
                if (chosenCustomer != null && customer.getCustomerID() < chosenCustomer.getCustomerID()) { //short circuit
                    chosenCustomer = customer;
                }
            }
        }

            //Note that .getSimpleName() returns to the Customer or PremiumCustomer
            String membershipType = chosenCustomer.getClass().getSimpleName().equals("PremiumCustomer") ? "Premium" : "Regular";
            System.out.printf("Q3: %s | %s | %d tickets\n", chosenCustomer.getCustomerName(), membershipType, maxReservedTickets);
    }

    public void printTicketsByCategory() {
        int totalConcert = 0;
        int totalConference = 0;
        int totalSports = 0;

        for (Booking booking : activeBookings) {
            switch (booking.getEvent().getEventCategory()) {
                case CONCERT:
                    totalConcert += booking.getTicketQuantity();
                    break;
                case CONFERENCE:
                    totalConference += booking.getTicketQuantity();
                    break;
                case SPORTS:
                    totalSports += booking.getTicketQuantity();
                    break;
            }
        }

        System.out.printf("Q4: CONCERT: %d | CONFERENCE: %d | SPORTS: %d\n", totalConcert, totalConference, totalSports);
    }

    public void executeQueriesInOrder() {
        System.out.println("========== QUERIES ==========");
        // Follows the order (from Homework Description)
        printMostPopularEvent();
        printHighestRevenueEvent();
        printTopCustomer();
        printTicketsByCategory();
    }

    public static ArrayList<Event> deepCopyEvents(ArrayList<Event> events) {
        ArrayList<Event> copiedEvents = new ArrayList<>();

        for (Event event: events) {
            copiedEvents.add(new Event(event));
        }

        return copiedEvents;
    }

    public static ArrayList<Customer> deepCopyCustomer(ArrayList<Customer> customers) {
        ArrayList<Customer> copiedCustomers = new ArrayList<>();

        for (Customer customer : customers) {
            copiedCustomers.add(new Customer(customer));
        }

        return copiedCustomers;
    }

    public static ArrayList<Booking> deepCopyActiveBookings(ArrayList<Booking> bookings) {
        ArrayList<Booking> copiedActiveBookings = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.ACTIVE) {
                copiedActiveBookings.add(new Booking(booking));
            }
        }

        return copiedActiveBookings;
    }
}
