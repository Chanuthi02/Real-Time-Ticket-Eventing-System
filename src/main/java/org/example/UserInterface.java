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
    private final List<String> customerDetails = new ArrayList<>(); // To store customer details
    private final List<String> vendorDetails = new ArrayList<>(); // To store vendor details
    private TicketPool ticketPool;

    @Override
    public void start(Stage primaryStage) {
        ticketPool = new TicketPool(Configuration.MAX_TICKET_CAPACITY);

        // GUI elements
        Button startButton = new Button("Start System");
        Button stopButton = new Button("Stop System");
        stopButton.setDisable(true);

        Button viewCustomersButton = new Button("View Customers");
        Button viewVendorsButton = new Button("View Vendors");

        Label ticketCountLabel = new Label("Tickets Available: 0");

        VBox layout = new VBox(10, startButton, stopButton, viewCustomersButton, viewVendorsButton, ticketCountLabel);
        Scene scene = new Scene(layout, 400, 400);

        // Button Actions
        startButton.setOnAction(e -> {
            startSystem();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });

        stopButton.setOnAction(e -> {
            stopSystem();
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });

        viewCustomersButton.setOnAction(e -> {
            System.out.println("Customer Details:");
            customerDetails.forEach(System.out::println);
        });

        viewVendorsButton.setOnAction(e -> {
            System.out.println("Vendor Details:");
            vendorDetails.forEach(System.out::println);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System");
        primaryStage.show();
    }

    private void startSystem() {
        for (int i = 1; i <= 3; i++) { // Add Vendors
            Vendor vendor = new Vendor(ticketPool, "Vendor-" + i, Configuration.TICKET_RELEASE_RATE);
            vendors.add(vendor);
            vendorDetails.add("Vendor ID: Vendor-" + i);
            new Thread(vendor).start();
        }

        for (int i = 1; i <= 5; i++) { // Add Customers
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
