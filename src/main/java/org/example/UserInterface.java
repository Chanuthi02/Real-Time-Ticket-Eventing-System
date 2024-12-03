package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private TicketPool ticketPool;

    @Override
    public void start(Stage primaryStage) {
        ticketPool = new TicketPool(Configuration.MAX_TICKET_CAPACITY);

        Button startButton = new Button("Start System");
        Button stopButton = new Button("Stop System");
        stopButton.setDisable(true);

        Label ticketCountLabel = new Label("Tickets Available: 0");

        VBox layout = new VBox(10, startButton, stopButton, ticketCountLabel);
        Scene scene = new Scene(layout, 400, 300);

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

        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Ticketing System");
        primaryStage.show();
    }

    private void startSystem() {
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
