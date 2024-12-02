package org.example;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String customerName;  // Customer's name (String)
    private final int customerRetrievalRate; // Customer's retrieval rate (milliseconds)

    // Constructor updated to accept the retrieval rate (int) and name (String)
    public Customer(TicketPool ticketPool, String customerName, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerName = customerName;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Simulate customer retrieving a ticket with a delay based on retrieval rate
                Thread.sleep(customerRetrievalRate);  // Use the configured retrieval rate

                // Simulate retrieving a ticket from the pool
                ticketPool.retrieveTicket(customerName);

                // Print message when a customer retrieves a ticket
                System.out.println(customerName + " retrieved a ticket!");
            }
        } catch (InterruptedException e) {
            System.err.println(customerName + " was interrupted: " + e.getMessage());
        }
    }
}
