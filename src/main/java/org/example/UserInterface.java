package org.example;

public class UserInterface {
    public static void main(String[] args) {
        // Load existing configuration, if any
        Configuration config = Configuration.loadConfiguration();

        // Ask user to configure the system if no saved configuration exists
        if (config.getTotalTickets() == 100) {
            System.out.println("No configuration found. Please enter system configuration.");
            config = Configuration.configureSystem();
            config.saveConfiguration(); // Save the configuration for future runs
        }

        // Print the loaded or configured settings
        config.printConfiguration();

        // Initialize Ticketing System with loaded settings
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Create and start vendor threads
        Vendor vendor = new Vendor(ticketPool, "Vendor-1", config.getTicketReleaseRate());
        Thread vendorThread = new Thread(vendor);

        // Create and start customer threads
        Customer customer = new Customer(ticketPool, "Customer-1", config.getCustomerRetrievalRate());
        Thread customerThread = new Thread(customer);

        // Start the vendor and customer threads
        vendorThread.start();
        customerThread.start();
    }
}
