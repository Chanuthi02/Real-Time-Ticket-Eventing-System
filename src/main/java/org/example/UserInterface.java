package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<String> customerDetails = new ArrayList<>();
    private final List<String> vendorDetails = new ArrayList<>();
    private TicketPool ticketPool;

    // ComboBox to select customer and ticket
    private ComboBox<String> customerComboBox;
    private ComboBox<String> ticketComboBox;
    private Label cancelStatusLabel; // Label to display cancellation status

    @Override
    public void start(Stage primaryStage) {
        // Input fields for system parameters
        TextField totalTicketsField = new TextField();
        totalTicketsField.setPromptText("Total Tickets");
        totalTicketsField.setStyle("-fx-padding: 10; -fx-font-size: 14px;");

        TextField maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Max Capacity");
        maxCapacityField.setStyle("-fx-padding: 10; -fx-font-size: 14px;");

        TextField releaseRateField = new TextField();
        releaseRateField.setPromptText("Release Rate (ms)");
        releaseRateField.setStyle("-fx-padding: 10; -fx-font-size: 14px;");

        TextField retrievalRateField = new TextField();
        retrievalRateField.setPromptText("Retrieval Rate (ms)");
        retrievalRateField.setStyle("-fx-padding: 10; -fx-font-size: 14px;");

        // Labels and buttons
        Label statusLabel = new Label("System Status: Waiting for Input");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");

        Button submitButton = new Button("Submit Parameters");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button startButton = new Button("Start System");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        startButton.setDisable(true);  // Disabled initially

        Button stopButton = new Button("Stop System");
        stopButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        stopButton.setDisable(true);

        Button cancelTicketButton = new Button("Cancel Ticket");
        cancelTicketButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px;");
        cancelTicketButton.setDisable(false);  // Enabled initially, as ticket can be canceled at any time

        Button viewCustomersButton = new Button("View Customers");
        viewCustomersButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button viewVendorsButton = new Button("View Vendors");
        viewVendorsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        // ComboBoxes for selecting customer and ticket
        customerComboBox = new ComboBox<>();
        ticketComboBox = new ComboBox<>();

        // Label to display the cancel ticket status
        cancelStatusLabel = new Label();
        cancelStatusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");

        // Layout for UI components
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f9; -fx-padding: 20;");
        layout.getChildren().addAll(
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                submitButton, statusLabel, startButton, stopButton, cancelTicketButton,
                customerComboBox, ticketComboBox, cancelStatusLabel, viewCustomersButton, viewVendorsButton
        );

        Scene scene = new Scene(layout, 500, 600);

        // Button Actions
        submitButton.setOnAction(e -> {
            try {
                int totalTickets = Integer.parseInt(totalTicketsField.getText());
                int maxCapacity = Integer.parseInt(maxCapacityField.getText());
                int releaseRate = Integer.parseInt(releaseRateField.getText());
                int retrievalRate = Integer.parseInt(retrievalRateField.getText());

                Configuration.MAX_TICKET_CAPACITY = maxCapacity;
                Configuration.TICKET_RELEASE_RATE = releaseRate;
                ticketPool = new TicketPool(maxCapacity);

                startButton.setDisable(false);
                statusLabel.setText("Parameters Submitted. Ready to Start.");
            } catch (Exception ex) {
                statusLabel.setText("Invalid Input: " + ex.getMessage());
            }
        });

        startButton.setOnAction(e -> {
            startSystem(Integer.parseInt(totalTicketsField.getText()),
                    Integer.parseInt(releaseRateField.getText()),
                    Integer.parseInt(retrievalRateField.getText()));
            startButton.setDisable(true);
            stopButton.setDisable(false);
            statusLabel.setText("System Started!");
        });

        stopButton.setOnAction(e -> {
            stopSystem();
            startButton.setDisable(false);
            stopButton.setDisable(true);
            statusLabel.setText("System Stopped.");
        });

        // Populate tickets dynamically when a customer is selected
        customerComboBox.setOnAction(e -> populateTickets());

        cancelTicketButton.setOnAction(e -> cancelTicket());

        viewCustomersButton.setOnAction(e -> {
            System.out.println("Customer Details and Purchased Tickets:");
            getCustomerDetails().forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details and Released Tickets:");
            getVendorDetails().forEach(System.out::println);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System");
        primaryStage.show();
    }

    private void populateTickets() {
        String selectedCustomerId = customerComboBox.getValue();
        if (selectedCustomerId == null) {
            return;
        }

        // Find the selected customer
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(selectedCustomerId)) {
                ticketComboBox.getItems().clear();
                customer.getPurchasedTickets().forEach(ticket -> ticketComboBox.getItems().add(ticket.toString()));
                ticketComboBox.setPromptText("Select Ticket to Cancel");
                break;
            }
        }
    }

    private void cancelTicket() {
        String selectedCustomerId = customerComboBox.getValue();
        if (selectedCustomerId == null) {
            cancelStatusLabel.setText("Please select a customer.");
            return;
        }

        Customer selectedCustomer = null;
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(selectedCustomerId)) {
                selectedCustomer = customer;
                break;
            }
        }

        if (selectedCustomer == null) {
            cancelStatusLabel.setText("Invalid customer selection.");
            return;
        }

        String selectedTicketInfo = ticketComboBox.getValue();
        if (selectedTicketInfo == null) {
            cancelStatusLabel.setText("Please select a ticket.");
            return;
        }

        Ticket selectedTicket = null;
        for (Ticket ticket : selectedCustomer.getPurchasedTickets()) {
            if (selectedTicketInfo.equals(ticket.toString())) {
                selectedTicket = ticket;
                break;
            }
        }

        if (selectedTicket == null) {
            cancelStatusLabel.setText("Invalid ticket selection.");
            return;
        }

        selectedCustomer.cancelTicket(selectedTicket);
        cancelStatusLabel.setText("Ticket canceled: " + selectedTicket);
        populateTickets();
    }

    private void startSystem(int totalTickets, int releaseRate, int retrievalRate) {
        for (int i = 1; i <= 10; i++) {
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, releaseRate, this);
            vendors.add(vendor);
            new Thread(vendor).start();
        }

        for (int i = 1; i <= 20; i++) {
            Customer customer = new Customer(ticketPool, "Customer-" + i);
            customers.add(customer);
            new Thread(customer).start();
        }

        customerComboBox.getItems().clear();
        customers.forEach(customer -> customerComboBox.getItems().add(customer.getCustomerId()));
        customerComboBox.setPromptText("Select Customer");

        ticketComboBox.getItems().clear();
    }

    private void stopSystem() {
        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public List<String> getCustomerDetails() {
        return customerDetails;
    }

    public List<String> getVendorDetails() {
        return vendorDetails;
    }
}
