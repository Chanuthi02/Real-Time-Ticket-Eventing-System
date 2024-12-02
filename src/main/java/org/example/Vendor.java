package org.example;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private String name;
    private int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, String name, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.name = name;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        // Vendor ticket releasing logic
        while (true) {
            ticketPool.retrieveTicket();
            try {
                Thread.sleep(ticketReleaseRate); // Simulate time taken to release a ticket
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
