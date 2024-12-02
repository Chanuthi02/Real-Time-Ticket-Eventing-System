import com.google.gson.Gson;
import java.io.*;
import java.util.Scanner;

public class Configuration {
    // Configuration parameters
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Constructor
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Getter methods
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    // Load configuration from a file
    public static Configuration loadConfiguration() {
        try {
            FileReader reader = new FileReader("config.json");
            Gson gson = new Gson();
            return gson.fromJson(reader, Configuration.class);
        } catch (FileNotFoundException e) {
            System.out.println("No saved configuration found. Using default values.");
            return new Configuration(100, 10, 5, 50); // Default values
        }
    }

    // Save configuration to a file
    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("Error saving configuration.");
        }
    }

    // Print the configuration
    public void printConfiguration() {
        System.out.println("Configuration Loaded:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }

    // Validate user input and prompt again if invalid
    public static int promptForValidInput(String promptMessage) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        while (input <= 0) {
            System.out.print(promptMessage);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input <= 0) {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        return input;
    }

    // Method to configure system settings via CLI
    public static Configuration configureSystem() {
        int totalTickets = promptForValidInput("Enter total number of tickets: ");
        int ticketReleaseRate = promptForValidInput("Enter ticket release rate: ");
        int customerRetrievalRate = promptForValidInput("Enter customer retrieval rate: ");
        int maxTicketCapacity = promptForValidInput("Enter maximum ticket capacity: ");

        return new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }
}
