package org.example;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    public static int MAX_TICKET_CAPACITY = 10;
    public static int TICKET_RELEASE_RATE = 5;

    public static void loadConfiguration() {
        try (FileReader reader = new FileReader("config.json")) {
            Configuration config = new Gson().fromJson(reader, Configuration.class);
            MAX_TICKET_CAPACITY = config.MAX_TICKET_CAPACITY;
            TICKET_RELEASE_RATE = config.TICKET_RELEASE_RATE;
            System.out.println("Configuration loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
}

