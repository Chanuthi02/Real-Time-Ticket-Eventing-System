package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

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
        Label statusLabel = new Label("Tickets Available: 0");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");

        Button startButton = new Button("Start System");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        Button stopButton = new Button("Stop System");
        stopButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        stopButton.setDisable(true);

        Button viewCustomersButton = new Button("View Customers");
        viewCustomersButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button viewVendorsButton = new Button("View Vendors");
        viewVendorsButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        // Add Login and Sign Up buttons
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
                statusLabel, startButton, stopButton, viewCustomersButton, viewVendorsButton,
                loginButton, signUpButton  // Add the Login and Sign Up buttons
        );

        // Add some margin around each component
        VBox.setMargin(totalTicketsField, new javafx.geometry.Insets(10));
        VBox.setMargin(maxCapacityField, new javafx.geometry.Insets(10));
        VBox.setMargin(releaseRateField, new javafx.geometry.Insets(10));
        VBox.setMargin(retrievalRateField, new javafx.geometry.Insets(10));
        VBox.setMargin(statusLabel, new javafx.geometry.Insets(10));
        VBox.setMargin(startButton, new javafx.geometry.Insets(10));
        VBox.setMargin(stopButton, new javafx.geometry.Insets(10));
        VBox.setMargin(viewCustomersButton, new javafx.geometry.Insets(10));
        VBox.setMargin(viewVendorsButton, new javafx.geometry.Insets(10));
        VBox.setMargin(loginButton, new javafx.geometry.Insets(10));
        VBox.setMargin(signUpButton, new javafx.geometry.Insets(10));

        Scene scene = new Scene(layout, 500, 600);

        // Button Actions
        startButton.setOnAction(e -> {
            try {
                // Validate and parse inputs
                int totalTickets = Integer.parseInt(totalTicketsField.getText().trim());
                int maxCapacity = Integer.parseInt(maxCapacityField.getText().trim());
                int releaseRate = Integer.parseInt(releaseRateField.getText().trim());
                int retrievalRate = Integer.parseInt(retrievalRateField.getText().trim());

                // Validate ranges
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

                // Update configuration and initialize the ticket pool
                Configuration.MAX_TICKET_CAPACITY = maxCapacity;
                Configuration.TICKET_RELEASE_RATE = releaseRate;
                ticketPool = new TicketPool(maxCapacity);

                // Start the system if all validations pass
                startSystem(totalTickets, releaseRate, retrievalRate);
                startButton.setDisable(true);
                stopButton.setDisable(false);
                statusLabel.setText("System started successfully.");
            } catch (NumberFormatException ex) {
                statusLabel.setText("Invalid input. Please enter numeric values.");
            } catch (IllegalArgumentException ex) {
                statusLabel.setText(ex.getMessage());
            }
        });


        stopButton.setOnAction(e -> {
            stopSystem();
            startButton.setDisable(false);
            stopButton.setDisable(true);
            statusLabel.setText("System stopped.");
        });

        viewCustomersButton.setOnAction(e -> {
            System.out.println("Customer Details and Purchased Tickets:");
            getCustomerDetails().forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details and Released Tickets:");
            getVendorDetails().forEach(System.out::println);
        });

        // Login and Sign Up Button Actions
        loginButton.setOnAction(e -> {
            System.out.println("Login Button Clicked");
            // Add Login logic (e.g., open login window, validate user credentials)
            openLoginWindow();
        });

        signUpButton.setOnAction(e -> {
            System.out.println("Sign Up Button Clicked");
            // Open the Sign Up form
            openSignUpWindow();
        });

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System");
        primaryStage.show();
    }

    // Function to open the Login window
    private void openLoginWindow() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        // Create login fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Here you can add login logic (e.g., check username/password)
            System.out.println("Username: " + username + ", Password: " + password);
            loginStage.close();  // Close the login window after clicking login
        });

        VBox loginLayout = new VBox(10, usernameField, passwordField, loginBtn);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-padding: 20;");

        Scene loginScene = new Scene(loginLayout, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    // Function to open the Sign Up window
    private void openSignUpWindow() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        // Create sign-up fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        signUpBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            // Here you can add sign-up logic (e.g., save user details)
            System.out.println("Sign Up Details - Username: " + username + ", Email: " + email + ", Password: " + password);
            signUpStage.close();  // Close the sign-up window after clicking sign-up
        });

        VBox signUpLayout = new VBox(10, usernameField, emailField, passwordField, signUpBtn);
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setStyle("-fx-padding: 20;");

        Scene signUpScene = new Scene(signUpLayout, 300, 250);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    private void startSystem(int totalTickets, int releaseRate, int retrievalRate) {
        // Initialize vendors (10 vendors now)
        for (int i = 1; i <= 10; i++) { // Updated to 10 vendors
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, releaseRate);
            vendors.add(vendor);
            vendorDetails.add("Vendor ID: Vendor-" + i + " - Released Tickets: ");
            new Thread(vendor).start();
        }

        // Initialize customers (20 customers now)
        for (int i = 1; i <= 20; i++) { // Updated to 20 customers
            Customer customer = new Customer(ticketPool, "Customer-" + i);
            customers.add(customer);
            customerDetails.add("Customer ID: Customer-" + i + " - Purchased Tickets: ");
            new Thread(customer).start();
        }

        // Simulate ticket generation for totalTickets
        new Thread(() -> {
            int ticketId = 1;
            while (ticketPool.getTicketCount() < totalTickets) {
                Ticket ticket = new Ticket(ticketId++, "Event Simple", 1000.0);
                try {
                    ticketPool.addTicket(ticket);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
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

    // Getter methods to access the private lists
    public List<String> getCustomerDetails() {
        return customerDetails;
    }

    public List<String> getVendorDetails() {
        return vendorDetails;
    }
}
