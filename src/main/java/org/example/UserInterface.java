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

        // Example output based on configuration (you can replace this with actual ticket system logic)
        // Initialize Ticketing System with loaded settings
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        Vendor vendor = new Vendor(ticketPool, "Vendor-1", config.getTicketReleaseRate());
        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());

        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();
    }
}
