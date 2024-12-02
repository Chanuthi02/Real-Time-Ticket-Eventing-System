package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;

    // Constructor to initialize the TicketPool with a maximum capacity
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add a ticket to the pool
    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            wait();  // Wait if the pool is full
        }
        tickets.add(ticket);
        System.out.println("Vendor added ticket: " + ticket);
        notifyAll();  // Notify consumers
    }

    // Retrieve a ticket from the pool (now accepts customer name)
    public synchronized void retrieveTicket(String customerName) throws InterruptedException {
        while (tickets.isEmpty()) {
            wait();  // Wait if the pool is empty
        }
        Ticket ticket = tickets.poll();  // Get the ticket from the pool
        System.out.println(customerName + " retrieved a ticket: " + ticket);
        notifyAll();  // Notify vendors to add more tickets
    }

    // Get the current number of tickets in the pool
    public synchronized int getTicketCount() {
        return tickets.size();
    }
}
