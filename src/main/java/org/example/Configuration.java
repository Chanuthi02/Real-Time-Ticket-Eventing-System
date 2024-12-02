package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets = 100; // Default value
    private int ticketReleaseRate = 1000; // Default value (milliseconds)
    private int customerRetrievalRate = 1000; // Default value (milliseconds)
    private int maxTicketCapacity = 10; // Default value

    // Getters and setters for the properties
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Load configuration from file (JSON format)
    public static Configuration loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return new Configuration(); // Return default configuration if file does not exist
        }
    }

    // Save configuration to file in JSON format
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Prompt user to configure the system via CLI input
    public static Configuration configureSystem() {
        Configuration config = new Configuration();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.println("Please enter the following system configuration:");

        // Getting user input for the total number of tickets
        System.out.print("Enter total number of tickets: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number for total tickets.");
            scanner.next(); // consume the invalid input
        }
        config.setTotalTickets(scanner.nextInt());

        // Getting user input for the ticket release rate
        System.out.print("Enter ticket release rate (milliseconds): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number for ticket release rate.");
            scanner.next(); // consume the invalid input
        }
        config.setTicketReleaseRate(scanner.nextInt());

        // Getting user input for the customer retrieval rate
        System.out.print("Enter customer retrieval rate (milliseconds): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number for customer retrieval rate.");
            scanner.next(); // consume the invalid input
        }
        config.setCustomerRetrievalRate(scanner.nextInt());

        // Getting user input for the maximum ticket capacity
        System.out.print("Enter maximum ticket capacity: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number for maximum ticket capacity.");
            scanner.next(); // consume the invalid input
        }
        config.setMaxTicketCapacity(scanner.nextInt());

        return config;
    }

    // Print the current configuration to the console
    public void printConfiguration() {
        System.out.println("\nSystem Configuration:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate + " ms");
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate + " ms");
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }
}
