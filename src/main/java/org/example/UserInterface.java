package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class UserInterface extends Application {
    private Stage primaryStage;

    // Declare vendorDetails properly
    private final List<String> vendorDetails = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Welcome Label
        Label welcomeLabel = new Label("Welcome to the Ticketing System");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: darkblue;");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        VBox layout = new VBox(20, welcomeLabel, loginButton, signupButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        Scene mainScene = new Scene(layout, 400, 300);

        // Login Button Action
        loginButton.setOnAction(e -> openLoginPage(mainScene));

        // Signup Button Action
        signupButton.setOnAction(e -> openSignupPage(mainScene));

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Ticketing System");
        primaryStage.show();
    }

    private void openLoginPage(Scene previousScene) {
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, loginLabel, usernameField, passwordField, loginBtn, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        Scene loginScene = new Scene(layout, 400, 300);

        loginBtn.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        primaryStage.setScene(loginScene);
    }

    private void openSignupPage(Scene previousScene) {
        Label signupLabel = new Label("Sign Up");
        signupLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Admin", "Vendor", "Customer");
        roleChoiceBox.setValue("Customer");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, signupLabel, usernameField, emailField, passwordField, roleChoiceBox, signupBtn, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        Scene signupScene = new Scene(layout, 400, 400);

        signupBtn.setOnAction(e -> handleSignup(usernameField.getText(), emailField.getText(), passwordField.getText(), roleChoiceBox.getValue()));
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        primaryStage.setScene(signupScene);
    }

    private void handleLogin(String username, String password) {
        // Placeholder logic for login
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Admin logged in!");
            openAdminDashboard();
        } else if (username.equals("vendor") && password.equals("vendor")) {
            System.out.println("Vendor logged in!");
            // Open Vendor Page
        } else if (username.equals("customer") && password.equals("customer")) {
            System.out.println("Customer logged in!");
            // Open Customer Page
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private void handleSignup(String username, String email, String password, String role) {
        System.out.println("Signed up: " + username + ", Role: " + role);
        primaryStage.setScene(primaryStage.getScene()); // Go back to main
    }

    private void openAdminDashboard() {
        Label adminLabel = new Label("Admin Dashboard");
        adminLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, adminLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        backButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));

        Scene adminScene = new Scene(layout, 400, 300);
        primaryStage.setScene(adminScene);
    }

    public List<String> getVendorDetails() {
        return vendorDetails;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
