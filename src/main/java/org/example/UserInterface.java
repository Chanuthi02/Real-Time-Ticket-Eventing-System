package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

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
    private final Map<String, String> userRoles = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // Input fields
        TextField totalTicketsField = new TextField();
        totalTicketsField.setPromptText("Total Tickets");
        totalTicketsField.setStyle("-fx-padding: 12; -fx-font-size: 14px; -fx-border-radius: 15px; -fx-background-color: #e0f7fa; -fx-border-color: #26c6da;");

        TextField maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Max Capacity");
        maxCapacityField.setStyle("-fx-padding: 12; -fx-font-size: 14px; -fx-border-radius: 15px; -fx-background-color: #e0f7fa; -fx-border-color: #26c6da;");

        TextField releaseRateField = new TextField();
        releaseRateField.setPromptText("Release Rate (ms)");
        releaseRateField.setStyle("-fx-padding: 12; -fx-font-size: 14px; -fx-border-radius: 15px; -fx-background-color: #e0f7fa; -fx-border-color: #26c6da;");

        TextField retrievalRateField = new TextField();
        retrievalRateField.setPromptText("Retrieval Rate (ms)");
        retrievalRateField.setStyle("-fx-padding: 12; -fx-font-size: 14px; -fx-border-radius: 15px; -fx-background-color: #e0f7fa; -fx-border-color: #26c6da;");

        // Buttons
        Label statusLabel = new Label("System Status: Waiting for Input");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #00695c; -fx-font-weight: bold;");

        Button submitButton = new Button("Submit Parameters");
        submitButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #004d40; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;"));

        Button startButton = new Button("Start System");
        startButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");
        startButton.setDisable(true);

        Button stopButton = new Button("Stop System");
        stopButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");
        stopButton.setDisable(true);

        Button viewCustomersButton = new Button("View Customers");
        viewCustomersButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");

        Button viewVendorsButton = new Button("View Vendors");
        viewVendorsButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");

        Button cancelTicketButton = new Button("Cancel Ticket");
        cancelTicketButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");

        // Buttons for login and signup in the top-right corner
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 12px;");

        // Layout for top-right buttons
        HBox topRightLayout = new HBox(10);
        topRightLayout.setAlignment(Pos.TOP_RIGHT);
        topRightLayout.setStyle("-fx-padding: 20;");
        topRightLayout.getChildren().addAll(loginButton, signUpButton);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-background-radius: 10px;");
        layout.getChildren().addAll(
                topRightLayout, // Add the top-right layout with buttons
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                submitButton, statusLabel, startButton, stopButton,
                viewCustomersButton, viewVendorsButton, cancelTicketButton
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

        cancelTicketButton.setOnAction(e -> openCancelTicketWindow(primaryStage));

        loginButton.setOnAction(e -> openLoginWindow(primaryStage)); // Fixed Login Button
        signUpButton.setOnAction(e -> openSignUpWindow(primaryStage)); // Fixed Sign-Up Button

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System - GUI");
        primaryStage.show();
    }

    // Cancel Ticket Window with Vendor Details
    private void openCancelTicketWindow(Stage primaryStage) {
        Stage cancelStage = new Stage();
        cancelStage.setTitle("Cancel Ticket");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20;");

        Label titleLabel = new Label("Cancel Ticket");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-text-fill: #333;");

        TextField vendorIdField = new TextField();
        vendorIdField.setPromptText("Enter Vendor ID");
        vendorIdField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        TextField ticketIdField = new TextField();
        ticketIdField.setPromptText("Enter Ticket ID");
        ticketIdField.setStyle("-fx-padding: 8px; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        Button cancelButton = new Button("Cancel Ticket");
        cancelButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        cancelButton.setOnAction(e -> {
            String vendorId = vendorIdField.getText().trim();
            String ticketId = ticketIdField.getText().trim();

            if (vendorId.isEmpty() || ticketId.isEmpty()) {
                statusLabel.setText("Vendor ID and Ticket ID are required!");
                statusLabel.setStyle("-fx-text-fill: red;");
            } else {
                statusLabel.setText("Ticket " + ticketId + " canceled by Vendor " + vendorId + "!");
                statusLabel.setStyle("-fx-text-fill: green;");
                Logger.logEvent("Vendor " + vendorId + " canceled Ticket " + ticketId);
            }
        });

        backButton.setOnAction(e -> cancelStage.close());

        layout.getChildren().addAll(titleLabel, vendorIdField, ticketIdField, cancelButton, backButton, statusLabel);
        cancelStage.setScene(new Scene(layout, 400, 300));
        cancelStage.show();
    }

    // Login Window
    private void openLoginWindow(Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20;");

        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", 20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Admin", "Vendor", "Customer");
        roleChoiceBox.setValue("Customer"); // Default role
        roleChoiceBox.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleChoiceBox.getValue();

            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)
                    && userRoles.get(username).equals(role)) {
                statusLabel.setText("Login successful as " + role + "!");
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                statusLabel.setText("Invalid credentials or role!");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });

        backButton.setOnAction(e -> loginStage.close());

        layout.getChildren().addAll(titleLabel, usernameField, passwordField, roleChoiceBox, loginButton, backButton, statusLabel);
        loginStage.setScene(new Scene(layout, 300, 350));
        loginStage.show();
    }

    // Sign Up Window
    private void openSignUpWindow(Stage primaryStage) {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fff; -fx-padding: 20;");

        Label titleLabel = new Label("Sign Up");
        titleLabel.setFont(Font.font("Arial", 20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-border-color: #2196F3; -fx-background-color: #f0f8ff;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        signUpButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("All fields are required!");
                statusLabel.setStyle("-fx-text-fill: red;");
            } else if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
                statusLabel.setText("Invalid email format!");
                statusLabel.setStyle("-fx-text-fill: red;");
            } else if (userCredentials.containsKey(username)) {
                statusLabel.setText("Username already exists!");
                statusLabel.setStyle("-fx-text-fill: red;");
            } else {
                userCredentials.put(username, password);
                statusLabel.setText("Sign-up successful!");
                statusLabel.setStyle("-fx-text-fill: green;");
            }
        });

        backButton.setOnAction(e -> signUpStage.close());

        layout.getChildren().addAll(titleLabel, usernameField, emailField, passwordField, signUpButton, backButton, statusLabel);
        signUpStage.setScene(new Scene(layout, 300, 350));
        signUpStage.show();
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
