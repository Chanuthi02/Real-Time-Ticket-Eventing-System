package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE = "system.log";

    public static synchronized void logEvent(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + "\n");
            System.out.println("Log: " + message);
        } catch (IOException e) {
            System.out.println("Logging Error: " + e.getMessage());
        }
    }
}

