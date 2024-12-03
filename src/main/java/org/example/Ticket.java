package org.example;

public class Ticket {
    private final int ticketId;
    private final String eventName;
    private final double ticketPrice;

    public Ticket(int ticketId, String eventName, double ticketPrice) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{ticketId=" + ticketId +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
