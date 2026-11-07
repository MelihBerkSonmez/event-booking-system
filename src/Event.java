public class Event {
    //fields
    private final int eventID; //This class cannot guarantee a unique ID, uniqueness must be checked by the system managing the events.
    private final String eventName;
    private final EventCategory eventCategory;
    private final int capacity;
    private final double ticketPrice;
    private int reservedTickets;

    //Only a parameterized constructor is provided
    //Creates an event with the given information.
    public Event(int eventID, String eventName, EventCategory eventCategory, int capacity, double ticketPrice){
        if (capacity <= 0){throw new IllegalArgumentException("Capacity must be positive.");}
        if (ticketPrice < 0){throw new IllegalArgumentException("Ticket price must be non-negative.");}
        if (eventCategory == null){throw new IllegalArgumentException("Event category cannot be null.");}
        if (eventName == null){throw new IllegalArgumentException("Event name cannot be null.");} //empty string is allowed

        this.eventID = eventID;
        this.eventName = eventName;
        this.eventCategory = eventCategory;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
        reservedTickets = 0;
    }

    //Creates a copy of the event, including its current reservation count.
    public Event(Event other){
        if (other == null){throw new IllegalArgumentException("Event to copy cannot be null.");}

        this.eventID = other.eventID;
        this.eventName = other.eventName;
        this.eventCategory = other.eventCategory;
        this.capacity = other.capacity;
        this.ticketPrice = other.ticketPrice;
        this.reservedTickets = other.reservedTickets;
    }

    //Accessors
    public int getEventID(){
        return eventID;
    }

    public String getEventName(){
        return eventName;
    }

    public EventCategory getEventCategory(){
        return eventCategory;
    }

    public int getCapacity(){
        return capacity;
    }

    public double getTicketPrice(){
        return ticketPrice;
    }

    public int getAvailableTickets(){
        return capacity - reservedTickets;
    }

    public int getReservedTickets() {
        return reservedTickets;
    }

    //Reserves the requested number of tickets if the amount is valid and capacity allows
    public void reserveTicket(int count){
        if (count < 0 || count > capacity - reservedTickets){return;}
        reservedTickets += count;
    }

    //Releases the requested number of reserved tickets if the amount is valid
    public void releaseReservedTickets(int count){
        if (count < 0 || count > reservedTickets){return;}
        reservedTickets -= count;
    }

    //Compares events by their event IDs
    @Override
    public boolean equals(Object object){
    if (object == null){return false;}
    if (!(object instanceof Event)){return false;}

    Event other = (Event) object;
    return this.eventID == other.eventID;
    }
    
    //Returns event information for debugging
    @Override 
    public String toString(){
        return "{[Event: " + eventID + "] "+
         "Name: " + eventName + ", " +
         "Category: " + eventCategory.name() + ", " +
         "Capacity: " + capacity + ", " +
         "Price: " + ticketPrice + ", " +
         "Reserve: " + reservedTickets + "}";
    }
}