package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private int totalTickets = 100; // Default value
    private int ticketReleaseRate = 1000; // Default value
    private int customerRetrievalRate = 1000; // Default value
    private int maxTicketCapacity = 10; // Default value

    // Getters and setters
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

    // Load configuration from file
    public static Configuration loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return new Configuration(); // Return default configuration if file does not exist
        }
    }

    // Save configuration to file
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Prompt user to configure system
    public static Configuration configureSystem() {
        Configuration config = new Configuration();

        // Use scanner or another method to get user input for the configuration
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter total number of tickets: ");
        config.setTotalTickets(scanner.nextInt());

        System.out.print("Enter ticket release rate (milliseconds): ");
        config.setTicketReleaseRate(scanner.nextInt());

        System.out.print("Enter customer retrieval rate (milliseconds): ");
        config.setCustomerRetrievalRate(scanner.nextInt());

        System.out.print("Enter maximum ticket capacity: ");
        config.setMaxTicketCapacity(scanner.nextInt());

        return config;
    }

    // Print current configuration
    public void printConfiguration() {
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }
}
