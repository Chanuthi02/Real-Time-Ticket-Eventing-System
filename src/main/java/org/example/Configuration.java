package org.example;

import com.google.gson.Gson;  // Gson import should resolve after syncing
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {
    private static final String CONFIG_FILE = "config.json";

    public static int MAX_TICKET_CAPACITY = 10;
    public static int TICKET_RELEASE_RATE = 5;

    public static Configuration loadConfiguration() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return new Gson().fromJson(reader, Configuration.class);  // Gson usage
        } catch (IOException e) {
            System.err.println("Error loading configuration. Using default values: " + e.getMessage());
            return new Configuration();
        }
    }

    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            new Gson().toJson(this, writer);
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }
}
