package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface extends Application {
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<String> customerDetails = new ArrayList<>();
    private final List<String> vendorDetails = new ArrayList<>();
    private TicketPool ticketPool;

    private final Map<String, String> userCredentials = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // Input fields
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

        // Buttons
        Label statusLabel = new Label("System Status: Waiting for Input");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");

        Button submitButton = new Button("Submit Parameters");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button startButton = new Button("Start System");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        startButton.setDisable(true);

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

        Button cancelTicketButton = new Button("Cancel Ticket");
        cancelTicketButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-size: 14px;");

        // Layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f9; -fx-padding: 20;");
        layout.getChildren().addAll(
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                submitButton, statusLabel, startButton, stopButton,
                viewCustomersButton, viewVendorsButton, loginButton, signUpButton, cancelTicketButton
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
                statusLabel.setText("Error: " + ex.getMessage());
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
            System.out.println("Customer Details:");
            customerDetails.forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details:");
            vendorDetails.forEach(System.out::println);
        });

        cancelTicketButton.setOnAction(e -> openCancelTicketWindow());

        loginButton.setOnAction(e -> openLoginWindow());
        signUpButton.setOnAction(e -> openSignUpWindow());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System - GUI");
        primaryStage.show();
    }

    // Enhanced Login Window
    private void openLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        // Create VBox for layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-background-radius: 10px;");

        // Title Label
        Label titleLabel = new Label("Login to Your Account");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #333;");

        // Username and Password Fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        // Login Button with hover effect
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));

        // Status label for login
        Label loginStatus = new Label();
        loginStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Action for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                loginStatus.setText("Login successful!");
            } else {
                loginStatus.setText("Invalid username or password.");
            }
        });

        // Add elements to layout
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, loginStatus);

        // Set scene and show
        loginStage.setScene(new Scene(layout, 300, 250));
        loginStage.show();
    }

    // Enhanced Sign Up Window
    private void openSignUpWindow() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        // Create VBox for layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-background-radius: 10px;");

        // Title Label
        Label titleLabel = new Label("Create Your Account");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #333;");

        // Input fields for username, email, and password
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        // Sign-up Button with hover effect
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));

        // Status label for signup
        Label signUpStatus = new Label();
        signUpStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Action for sign-up button
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                signUpStatus.setText("All fields are required.");
            } else if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
                signUpStatus.setText("Invalid email format.");
            } else if (userCredentials.containsKey(username)) {
                signUpStatus.setText("Username already exists.");
            } else {
                userCredentials.put(username, password);
                signUpStatus.setText("Sign-up successful!");
            }
        });

        // Add elements to layout
        layout.getChildren().addAll(titleLabel, usernameField, emailField, passwordField, signUpButton, signUpStatus);

        // Set scene and show
        signUpStage.setScene(new Scene(layout, 300, 300));
        signUpStage.show();
    }

    private void openCancelTicketWindow() {
        // Create a new stage (window) for the Cancel Ticket page
        Stage cancelStage = new Stage();
        cancelStage.setTitle("Cancel Ticket");

        // Create a VBox layout for the cancel ticket form
        VBox cancelLayout = new VBox(10);
        cancelLayout.setAlignment(Pos.CENTER);
        cancelLayout.setStyle("-fx-background-color: #fff; -fx-padding: 20; -fx-background-radius: 10px;");

        // Title for the cancel ticket page
        Label titleLabel = new Label("Cancel Your Ticket");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #333;");

        // Vendor ID input field
        TextField vendorIdField = new TextField();
        vendorIdField.setPromptText("Enter Vendor ID");
        vendorIdField.setStyle("-fx-border-color: #2196F3; -fx-background-color: #f0f8ff; -fx-padding: 8px; -fx-font-size: 14px;");

        // Ticket ID input field
        TextField ticketIdField = new TextField();
        ticketIdField.setPromptText("Enter Ticket ID to Cancel");
        ticketIdField.setStyle("-fx-border-color: #2196F3; -fx-background-color: #f0f8ff; -fx-padding: 8px; -fx-font-size: 14px;");

        // Cancel button with hover effect
        Button cancelButton = new Button("Cancel Ticket");
        cancelButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #FF7043; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;"));

        // Status label for cancel status
        Label cancelStatus = new Label();
        cancelStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

        // Action for the cancel button
        cancelButton.setOnAction(e -> {
            try {
                int ticketId = Integer.parseInt(ticketIdField.getText().trim());
                int vendorId = Integer.parseInt(vendorIdField.getText().trim()); // Get vendor ID input

                // Process the cancellation
                Ticket canceledTicket = cancelTicket(ticketId);
                if (canceledTicket != null) {
                    cancelStatus.setText("Ticket " + ticketId + " canceled successfully by Vendor ID: " + vendorId);
                    Logger.logEvent("Vendor ID: " + vendorId + " canceled Ticket " + ticketId);
                } else {
                    cancelStatus.setText("Ticket " + ticketId + " not found or already canceled.");
                }
            } catch (NumberFormatException ex) {
                cancelStatus.setText("Invalid input. Please enter valid numbers for Ticket ID and Vendor ID.");
            }
        });

        // Add all elements to the cancelLayout
        cancelLayout.getChildren().addAll(titleLabel, vendorIdField, ticketIdField, cancelButton, cancelStatus);

        // Create a scene for the cancel window and show it
        Scene cancelScene = new Scene(cancelLayout, 400, 250);
        cancelStage.setScene(cancelScene);
        cancelStage.show();
    }

    private Ticket cancelTicket(int ticketId) {
        // For demo purposes, assume ticket ID 1 is valid and others are not
        if (ticketId == 1) {
            return new Ticket(ticketId, "Sample Event", 100.0);
        }
        return null;
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
    }

    private void stopSystem() {
        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);
        System.out.println("System Stopped.");
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
