package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserInterface extends Application {
    private Stage primaryStage;

    // List to store user details (Username, Email, Password, Role)
    private final List<User> users = new ArrayList<>();
    private final List<String> vendorDetails = new ArrayList<>(); // List to store vendor details (released tickets)

    private Scene mainScene;  // Store the main scene for easy navigation

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Main page setup
        Label welcomeLabel = new Label("Welcome to the Ticketing System");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: darkblue;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        VBox layout = new VBox(20, welcomeLabel, loginButton, signupButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        mainScene = new Scene(layout, 400, 300);

        // Button actions
        loginButton.setOnAction(e -> openLoginPage(mainScene));
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

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        VBox layout = new VBox(15, loginLabel, usernameField, passwordField, loginBtn, backButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        Scene loginScene = new Scene(layout, 400, 300);

        loginBtn.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), messageLabel));
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

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        VBox layout = new VBox(15, signupLabel, usernameField, emailField, passwordField, roleChoiceBox, signupBtn, backButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        Scene signupScene = new Scene(layout, 400, 400);

        signupBtn.setOnAction(e -> handleSignup(usernameField.getText(), emailField.getText(), passwordField.getText(), roleChoiceBox.getValue(), messageLabel));
        backButton.setOnAction(e -> primaryStage.setScene(previousScene));

        primaryStage.setScene(signupScene);
    }

    private void handleLogin(String username, String password, Label messageLabel) {
        // Check if user exists in the list
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                messageLabel.setText("");
                System.out.println(user.getRole() + " logged in!");
                switch (user.getRole()) {
                    case "Admin" -> openAdminDashboard();
                    case "Vendor" -> openVendorPage();
                    case "Customer" -> openCustomerPage();
                }
                return;
            }
        }
        messageLabel.setText("Invalid credentials!");
    }

    private void handleSignup(String username, String email, String password, String role, Label messageLabel) {
        // Validate email format
        if (!isValidEmail(email)) {
            messageLabel.setText("Invalid email format.");
            return;
        }

        // Validate fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
        } else {
            // Add user to the list
            users.add(new User(username, email, password, role));
            System.out.println("Sign Up Successful: " + username + ", Role: " + role);
            primaryStage.setScene(primaryStage.getScene()); // Go back to main
        }
    }

    private void openAdminDashboard() {
        Label adminLabel = new Label("Admin Dashboard");
        adminLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, adminLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        Scene adminScene = new Scene(layout, 400, 300);
        primaryStage.setScene(adminScene);
    }

    private void openVendorPage() {
        Label vendorLabel = new Label("Vendor Page");
        vendorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, vendorLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        Scene vendorScene = new Scene(layout, 400, 300);
        primaryStage.setScene(vendorScene);
    }

    private void openCustomerPage() {
        Label customerLabel = new Label("Customer Page");
        customerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        VBox layout = new VBox(15, customerLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f9;");

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        Scene customerScene = new Scene(layout, 400, 300);
        primaryStage.setScene(customerScene);
    }

    private boolean isValidEmail(String email) {
        // Email validation regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    // Method to get vendor details
    public List<String> getVendorDetails() {
        return vendorDetails;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Class to store user details
class User {
    private final String username;
    private final String email;
    private final String password;
    private final String role;

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
