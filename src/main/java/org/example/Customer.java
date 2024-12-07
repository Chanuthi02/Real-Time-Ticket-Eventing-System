package org.example;

import java.util.ArrayList;
import java.util.List;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final String customerId;
    private final List<Ticket> purchasedTickets = new ArrayList<>();
    private volatile boolean running = true;

    public Customer(TicketPool ticketPool, String customerId) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId; // Returns the customer's ID
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets; // Returns the list of purchased tickets
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Ticket ticket = ticketPool.removeTicket();
                purchasedTickets.add(ticket);  // Add the ticket to the purchased list
                System.out.println("Ticket bought by " + customerId + ". Ticket is " + ticket);
                Thread.sleep(700); // Simulate customer purchase interval

                // Simulate the customer deciding to cancel the ticket after some time
                if (Math.random() < 0.5) { // 50% chance to cancel
                    cancelTicket(ticket);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    // Method for customer to cancel a ticket
    public void cancelTicket(Ticket ticket) {
        System.out.println("Customer " + customerId + " is canceling ticket: " + ticket);
        ticketPool.cancelTicket(ticket); // Return the ticket to the pool
    }
}
