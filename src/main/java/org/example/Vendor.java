package org.example;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final String name;
    private final int ticketReleaseRate;

    // Constructor updated to accept ticket release rate
    public Vendor(TicketPool ticketPool, String name, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.name = name;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Simulate releasing a ticket with a delay based on release rate
                Thread.sleep(ticketReleaseRate);

                // Create a new ticket with a String ticket ID and add it to the pool
                Ticket ticket = new Ticket("Ticket-" + System.currentTimeMillis(), "Event", 100.0);
                ticketPool.addTicket(ticket); // Add the ticket to the pool
                System.out.println(name + " added a ticket.");
            }
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted: " + e.getMessage());
        }
    }
}
