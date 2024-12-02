package org.example;

public class Ticket {
    private final String ticketId;  // Changed to String
    private final String eventName;
    private final double ticketPrice;

    // Constructor updated to accept String for ticketId
    public Ticket(String ticketId, String eventName, double ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{ticketId='" + ticketId + "', eventName='" + eventName + "', ticketPrice=" + ticketPrice + "}";
    }
}
