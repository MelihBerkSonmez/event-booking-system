package com.event_booking_system.classes;

import com.event_booking_system.enums.BookingStatus;

public class Booking {
    private String bookingID; //will use a clever way to create a unique id
    private Customer customer;
    private Event event;
    private int ticketQuantity;
    private double originalTotalPrice;
    private double finalPaidAmount;
    private BookingStatus bookingStatus; // Declaring all the fields of the Booking class

    public Booking(String bookingID, Customer customer, Event event, int ticketQuantity, double originalTotalPrice, double finalPaidAmount, BookingStatus bookingStatus) {
        // Constructor with parameters to initialize the fields of the Booking class
        this.bookingID = bookingID;
        this.customer = customer;
        this.event = event;
        this.ticketQuantity = ticketQuantity;
        this.originalTotalPrice = originalTotalPrice;
        this.finalPaidAmount = finalPaidAmount;
        this.bookingStatus = bookingStatus;
    }

    public Booking(Booking other) {
        //Copy constructor for the booking class
        this.bookingID = other.bookingID;
        this.customer = new Customer(other.customer); // Assuming Customer class has a copy constructor so it can have a deep copy
        this.event = new Event(other.event);
        this.ticketQuantity = other.ticketQuantity;
        this.originalTotalPrice = other.originalTotalPrice;
        this.finalPaidAmount = other.finalPaidAmount;
        this.bookingStatus = other.bookingStatus;
    }

    //Accessor methods (getters) for each field of the Booking class
    public String getBookingId() {
        return bookingID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Event getEvent() {
        return event;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public double getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public double getFinalPaidAmount() {
        return finalPaidAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    // Mutator methods (setters)
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public void setFinalPaidAmount(double finalPaidAmount) {
        this.finalPaidAmount = finalPaidAmount;
    }

    @Override
    public boolean equals(Object obj) { //Overriding equals method to compare two Booking objects based on their unique bookingId
        if (this == obj) return true; //Are they the same object in memory?
        if (obj ==null || getClass() != obj.getClass()) return false; //Are they of the same class type?
        Booking other = (Booking) obj; //Turning the obj into a Booking object so we can access its fields

        return bookingID.equals(other.bookingID); //Comparing the unique bookingId of both objects to determine equality
    }
}