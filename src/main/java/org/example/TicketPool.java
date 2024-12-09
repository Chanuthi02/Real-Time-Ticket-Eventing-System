package org.example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add a ticket to the pool
    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            wait();
        }
        tickets.add(ticket);
        notifyAll();
    }

    // Remove a ticket from the pool
    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            wait();
        }
        Ticket ticket = tickets.poll();
        notifyAll();
        return ticket;
    }

    // Cancel a ticket with a specific ticket ID
    public synchronized Ticket cancelTicket(int ticketId) {
        Iterator<Ticket> iterator = tickets.iterator();
        while (iterator.hasNext()) {
            Ticket ticket = iterator.next();
            if (ticket.getTicketId() == ticketId) {
                iterator.remove();
                notifyAll(); // Notify producers that a ticket slot is now free
                return ticket;
            }
        }
        return null; // Return null if no matching ticket is found
    }

    // Get the current ticket count
    public synchronized int getTicketCount() {
        return tickets.size();
    }

    // Method to re-add a canceled ticket to the pool
    public synchronized void cancelTicket(Ticket ticket) {
        tickets.add(ticket);
        notifyAll(); // Notify consumers that a ticket is available again
    }
}
