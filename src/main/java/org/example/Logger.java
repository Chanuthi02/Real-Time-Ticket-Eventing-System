package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE = "system.log";

    // Synchronized method for logging events
    public static synchronized void logEvent(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message + "\n");
            System.out.println("Log: " + message);  // Also print to console
        } catch (IOException e) {
            System.out.println("Logging Error: " + e.getMessage());
        }
    }
}
