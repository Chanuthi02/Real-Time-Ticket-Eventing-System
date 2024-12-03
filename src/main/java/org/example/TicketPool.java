package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            wait(); // Wait if the pool is full
        }
        tickets.add(ticket);
        notifyAll(); // Notify consumers
    }

    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            wait(); // Wait if the pool is empty
        }
        Ticket ticket = tickets.poll();
        notifyAll(); // Notify producers
        return ticket;
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}
