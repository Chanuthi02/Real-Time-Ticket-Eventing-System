package org.example;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String name;
    private final int retrievalRate;  // Rate at which customers retrieve tickets

    // Constructor modified to accept retrieval rate
    public Customer(TicketPool ticketPool, String name, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.name = name;
        this.retrievalRate = retrievalRate;  // Initialize retrieval rate
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Simulate customer retrieval rate (wait for a while based on retrieval rate)
                Thread.sleep(retrievalRate);

                // Simulate retrieving a ticket from the pool
                ticketPool.retrieveTicket(name);

                // Print message when a customer retrieves a ticket
                System.out.println(name + " retrieved a ticket!");
            }
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted: " + e.getMessage());
        }
    }
}
