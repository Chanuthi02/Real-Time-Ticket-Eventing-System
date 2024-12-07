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

        Button viewCustomersButton = new Button("View Customers");
        viewCustomersButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button viewVendorsButton = new Button("View Vendors");
        viewVendorsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        // Layout for UI components
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f9; -fx-padding: 20;");
        layout.getChildren().addAll(
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                submitButton, statusLabel, startButton, stopButton,
                viewCustomersButton, viewVendorsButton, loginButton, signUpButton
        );

        Scene scene = new Scene(layout, 500, 600);

        // Button Actions
        submitButton.setOnAction(e -> {
            try {
                int totalTickets = Integer.parseInt(totalTicketsField.getText().trim());
                int maxCapacity = Integer.parseInt(maxCapacityField.getText().trim());
                int releaseRate = Integer.parseInt(releaseRateField.getText().trim());
                int retrievalRate = Integer.parseInt(retrievalRateField.getText().trim());

                if (totalTickets < 1 || totalTickets > 100000) {
                    throw new IllegalArgumentException("Total Tickets must be between 1 and 100,000.");
                }
                if (maxCapacity < 1 || maxCapacity > totalTickets) {
                    throw new IllegalArgumentException("Max Capacity must be between 1 and Total Tickets.");
                }
                if (releaseRate < 100 || releaseRate > 60000) {
                    throw new IllegalArgumentException("Release Rate must be between 100 ms and 60,000 ms.");
                }
                if (retrievalRate < 100 || retrievalRate > 60000) {
                    throw new IllegalArgumentException("Retrieval Rate must be between 100 ms and 60,000 ms.");
                }

                Configuration.MAX_TICKET_CAPACITY = maxCapacity;
                Configuration.TICKET_RELEASE_RATE = releaseRate;
                ticketPool = new TicketPool(maxCapacity);

                startButton.setDisable(false);
                statusLabel.setText("Parameters Submitted. Ready to Start.");
            } catch (NumberFormatException ex) {
                statusLabel.setText("Invalid input. Please enter numeric values.");
            } catch (IllegalArgumentException ex) {
                statusLabel.setText(ex.getMessage());
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

        viewCustomersButton.setOnAction(e -> {
            System.out.println("Customer Details and Purchased Tickets:");
            getCustomerDetails().forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details and Released Tickets:");
            getVendorDetails().forEach(System.out::println);
        });

        loginButton.setOnAction(e -> openLoginWindow());
        signUpButton.setOnAction(e -> openSignUpWindow());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System - GUI");
        primaryStage.show();
    }

    private void startSystem(int totalTickets, int releaseRate, int retrievalRate) {
        for (int i = 1; i <= 10; i++) {
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, releaseRate, this);
            vendors.add(vendor);
            vendorDetails.add("Vendor ID: Vendor-" + i + " - Released Tickets: ");
            new Thread(vendor).start();
        }

        for (int i = 1; i <= 20; i++) {
            Customer customer = new Customer(ticketPool, "Customer-" + i);
            customers.add(customer);
            customerDetails.add("Customer ID: Customer-" + i + " - Purchased Tickets: ");
            new Thread(customer).start();
        }
    }

    private void stopSystem() {
        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);
        System.out.println("System Stopped.");
    }

    private void openLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        TextField usernameField = new TextField("Enter your username");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> loginStage.close());

        VBox layout = new VBox(10, usernameField, passwordField, loginButton);
        layout.setAlignment(Pos.CENTER);
        loginStage.setScene(new Scene(layout, 300, 200));
        loginStage.show();
    }

    private void openSignUpWindow() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        TextField usernameField = new TextField("Enter your username");
        TextField emailField = new TextField("Enter your email");
        PasswordField passwordField = new PasswordField();
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> signUpStage.close());

        VBox layout = new VBox(10, usernameField, emailField, passwordField, signUpButton);
        layout.setAlignment(Pos.CENTER);
        signUpStage.setScene(new Scene(layout, 300, 250));
        signUpStage.show();
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
