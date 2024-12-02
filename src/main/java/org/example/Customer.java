package org.example;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate; // Now an int

    // Constructor updated to accept int
    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    @Override
    public void run() {
        // Customer ticket retrieval logic
        while (true) {
            ticketPool.retrieveTicket();
            try {
                Thread.sleep(customerRetrievalRate); // Simulate time taken to retrieve tickets
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
