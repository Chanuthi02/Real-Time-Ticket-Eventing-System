package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private TicketPool ticketPool;

    private TextField totalTicketsField;
    private TextField maxCapacityField;
    private TextField releaseRateField;
    private TextField retrievalRateField;
    private Label ticketCountLabel;
    private Label errorLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize UI components
        totalTicketsField = new TextField();
        totalTicketsField.setPromptText("Total Tickets");

        maxCapacityField = new TextField();
        maxCapacityField.setPromptText("Max Capacity");

        releaseRateField = new TextField();
        releaseRateField.setPromptText("Release Rate (ms)");

        retrievalRateField = new TextField();
        retrievalRateField.setPromptText("Retrieval Rate (ms)");

        errorLabel = new Label();
        ticketCountLabel = new Label("Tickets Available: 0");

        Button startButton = new Button("Start System");
        Button stopButton = new Button("Stop System");
        stopButton.setDisable(true);

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                totalTicketsField, maxCapacityField, releaseRateField, retrievalRateField,
                errorLabel, ticketCountLabel, startButton, stopButton
        );

        Scene scene = new Scene(layout, 400, 300);

        // Event handlers
        startButton.setOnAction(e -> {
            if (validateInputs()) {
                startSystem();
                startButton.setDisable(true);
                stopButton.setDisable(false);
                errorLabel.setText("");
            } else {
                errorLabel.setText("Invalid input. Please check values.");
            }
        });

        stopButton.setOnAction(e -> {
            stopSystem();
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });

        // Set up stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ticketing System");
        primaryStage.show();
    }

    private boolean validateInputs() {
        try {
            int totalTickets = Integer.parseInt(totalTicketsField.getText());
            int maxCapacity = Integer.parseInt(maxCapacityField.getText());
            int releaseRate = Integer.parseInt(releaseRateField.getText());
            int retrievalRate = Integer.parseInt(retrievalRateField.getText());

            if (totalTickets <= 0 || maxCapacity <= 0 || releaseRate <= 0 || retrievalRate <= 0) {
                return false;
            }

            Configuration.MAX_TICKET_CAPACITY = maxCapacity;
            Configuration.TICKET_RELEASE_RATE = releaseRate;

            ticketPool = new TicketPool(maxCapacity);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void startSystem() {
        int totalTickets = Integer.parseInt(totalTicketsField.getText());

        for (int i = 1; i <= 3; i++) {
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, Configuration.TICKET_RELEASE_RATE);
            vendors.add(vendor);
            new Thread(vendor).start();
        }

        for (int i = 1; i <= 5; i++) {
            Customer customer = new Customer(ticketPool, "Customer-" + i);
            customers.add(customer);
            new Thread(customer).start();
        }

        // Simulate ticket generation
        new Thread(() -> {
            while (ticketPool.getTicketCount() < totalTickets) {
                ticketCountLabel.setText("Tickets Available: " + ticketPool.getTicketCount());
                try {
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
}
