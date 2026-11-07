public class OperationAttempt {
    private Booking booking;
    private boolean isSuccessful;
    private String failReason;

    public OperationAttempt(Booking booking, boolean isSuccessful, String failReason) {
        this.booking = booking;
        this.isSuccessful = isSuccessful;
        this.failReason = failReason;
    }

    //Getters
    public Booking getBooking() {
        return booking;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getFailReason() {
        return failReason;
    }
}
