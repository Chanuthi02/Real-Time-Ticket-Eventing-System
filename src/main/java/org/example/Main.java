package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Load configuration from the JSON file
        Configuration.loadConfiguration();

        // Initialize the TicketPool with the configured maximum capacity
        TicketPool ticketPool = new TicketPool(Configuration.MAX_TICKET_CAPACITY);

        // Create an ExecutorService to manage threads for vendors and customers
        ExecutorService executor = Executors.newCachedThreadPool();

        // Start multiple vendor threads
        for (int i = 1; i <= 3; i++) { // Adjust number of vendors as needed
            executor.execute(new Vendor(ticketPool, "Vendor-" + i, Configuration.TICKET_RELEASE_RATE));
        }

        // Start multiple customer threads
        for (int i = 1; i <= 5; i++) { // Adjust number of customers as needed
            executor.execute(new Customer(ticketPool, "Customer-" + i));
        }

        // Let the system run for a configured amount of time (e.g., 10 seconds)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

        // Gracefully shut down the ExecutorService and terminate all threads
        System.out.println("Shutting down system...");
        executor.shutdownNow();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in the specified time.");
            } else {
                System.out.println("System stopped successfully.");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for executor shutdown: " + e.getMessage());
        }
    }
}
