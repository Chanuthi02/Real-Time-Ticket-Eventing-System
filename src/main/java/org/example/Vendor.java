package org.example;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final String vendorId;
    private final int ticketsPerRelease;
    private volatile boolean running = true;
    private final UserInterface userInterface;  // Add reference to UserInterface

    // Modify the constructor to take UserInterface as a parameter
    public Vendor(TicketPool ticketPool, String vendorId, int ticketsPerRelease, UserInterface userInterface) {
        this.ticketPool = ticketPool;
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.userInterface = userInterface;  // Initialize the reference to UserInterface
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

                    // Add ticket information to vendorDetails using the reference
                    synchronized (userInterface.getVendorDetails()) {  // Use the existing UI instance
                        userInterface.getVendorDetails().add("Vendor ID: " + vendorId + " - Released Ticket: " + ticket);
                    }

                    System.out.println("Vendor-" + vendorId + " has added a ticket to the Pool. Current size is " + ticketPool.getTicketCount());
                    Thread.sleep(500); // Simulate ticket generation time
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
