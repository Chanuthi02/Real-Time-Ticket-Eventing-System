package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<String> customerDetails = new ArrayList<>();
    private final List<String> vendorDetails = new ArrayList<>();
    private TicketPool ticketPool;

    @Override
    public void start(Stage primaryStage) {
        // Input fields for system parameters
        TextField totalTicketsField = new TextField();
        totalTicketsField.setPromptText("Total Tickets");
        TextField maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Max Capacity");
        TextField releaseRateField = new TextField();
        releaseRateField.setPromptText("Release Rate (ms)");
        TextField retrievalRateField = new TextField();
        retrievalRateField.setPromptText("Retrieval Rate (ms)");

        // Labels and buttons
        Label statusLabel = new Label("Tickets Available: 0");
        Button startButton = new Button("Start System");
        Button stopButton = new Button("Stop System");
        Button viewCustomersButton = new Button("View Customers");
        Button viewVendorsButton = new Button("View Vendors");

        // Initial state for buttons
        stopButton.setDisable(true);

        // Layout for UI components
        VBox layout = new VBox(10,
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                statusLabel, startButton, stopButton, viewCustomersButton, viewVendorsButton);
        Scene scene = new Scene(layout, 400, 500);

        // Button Actions
        startButton.setOnAction(e -> {
            try {
                // Validate and parse inputs
                int totalTickets = Integer.parseInt(totalTicketsField.getText().trim());
                int maxCapacity = Integer.parseInt(maxCapacityField.getText().trim());
                int releaseRate = Integer.parseInt(releaseRateField.getText().trim());
                int retrievalRate = Integer.parseInt(retrievalRateField.getText().trim());

                // Update configuration and initialize the ticket pool
                Configuration.MAX_TICKET_CAPACITY = maxCapacity;
                Configuration.TICKET_RELEASE_RATE = releaseRate;
                ticketPool = new TicketPool(maxCapacity);

                startSystem(totalTickets, releaseRate, retrievalRate);
                startButton.setDisable(true);
                stopButton.setDisable(false);
                statusLabel.setText("System started successfully.");
            } catch (NumberFormatException ex) {
                statusLabel.setText("Invalid input. Please check values.");
            }
        });

        stopButton.setOnAction(e -> {
            stopSystem();
            startButton.setDisable(false);
            stopButton.setDisable(true);
            statusLabel.setText("System stopped.");
        });

        viewCustomersButton.setOnAction(e -> {
            System.out.println("Customer Details:");
            customerDetails.forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details:");
            vendorDetails.forEach(System.out::println);
        });

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System");
        primaryStage.show();
    }

    private void startSystem(int totalTickets, int releaseRate, int retrievalRate) {
        // Initialize vendors
        for (int i = 1; i <= 3; i++) {
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, releaseRate);
            vendors.add(vendor);
            vendorDetails.add("Vendor ID: Vendor-" + i);
            new Thread(vendor).start();
        }

        // Initialize customers
        for (int i = 1; i <= 5; i++) {
            Customer customer = new Customer(ticketPool, "Customer-" + i);
            customers.add(customer);
            customerDetails.add("Customer ID: Customer-" + i);
            new Thread(customer).start();
        }
    }

    private void stopSystem() {
        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);
        System.out.println("System Stopped.");
    }

    public static void main(String[] args) {
        Configuration.loadConfiguration();
        launch(args);
    }
}
