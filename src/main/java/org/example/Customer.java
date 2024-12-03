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

    @Override
    public void run() {
        try {
            while (running) {
                Ticket ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    System.out.println("Ticket bought by " + customerId + ". Ticket is " + ticket);
                }
                Thread.sleep(700); // Simulate customer purchase interval
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
