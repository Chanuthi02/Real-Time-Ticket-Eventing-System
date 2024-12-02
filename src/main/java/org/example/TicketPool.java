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
            wait();
        }
        tickets.add(ticket);
        System.out.println("Vendor added a ticket to the Pool. Current size is " + tickets.size());
        notifyAll();
    }

    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            wait();
        }
        Ticket ticket = tickets.poll();
        System.out.println("Customer has bought a ticket from the pool. Current size is " + tickets.size());
        notifyAll();
        return ticket;
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}

