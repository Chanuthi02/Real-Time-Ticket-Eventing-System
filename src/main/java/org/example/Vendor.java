package org.example;

import java.util.List;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final String vendorId;
    private final int ticketsPerRelease;
    private volatile boolean running = true;
    private final UserInterface userInterface; // Reference to UserInterface

    public Vendor(TicketPool ticketPool, String vendorId, int ticketsPerRelease, UserInterface userInterface) {
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.userInterface = userInterface; // Assign reference
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            int ticketId = 1;
            while (running) {
                for (int i = 0; i < ticketsPerRelease; i++) {
                    if (!running) break;

                    Ticket ticket = new Ticket(ticketId++, "Event Simple", 1000.0);
                    ticketPool.addTicket(ticket);

                    // Add ticket information to vendorDetails
                    synchronized (userInterface) {
                        List<String> vendorDetailsList = userInterface.getVendorDetails();
                        if (vendorDetailsList != null) { // Ensure it's not null
                            vendorDetailsList.add("Vendor ID: " + vendorId + " - Released Ticket: " + ticket.toString());
                        }
                    }
                    System.out.println("Vendor-" + vendorId + " has added a ticket to the Pool. Current size is " + ticketPool.getTicketCount());
                    Thread.sleep(500); // Simulate ticket generation time
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt(); // Re-interrupt the thread
        }
    }
}
