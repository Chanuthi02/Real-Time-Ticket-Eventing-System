package org.example;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String customerId;
    private volatile boolean running = true;

    public Customer(TicketPool ticketPool, String customerId) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
    }

    public void stop() {
        running = false;
    }

    // This method will cancel a ticket with a specific ticketId
    public void cancelTicket(int ticketId) {
        Ticket canceledTicket = ticketPool.cancelTicket(ticketId);  // Correct call with ticketId
        if (canceledTicket != null) {
            Logger.logEvent("Customer " + customerId + " canceled ticket: " + canceledTicket);
        } else {
            Logger.logEvent("Customer " + customerId + " attempted to cancel but no tickets available with ID " + ticketId);
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                Ticket ticket = ticketPool.removeTicket();
                System.out.println(customerId + " bought ticket: " + ticket);
                Thread.sleep(700);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
