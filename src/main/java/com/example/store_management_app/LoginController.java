package com.example.store_management_app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:1234/test-docker-connection";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1";

    @FXML
    public void initialize() {
        usernameTextField.setPromptText("Enter your username");
        passwordTextField.setPromptText("Enter your password");
    }

    @FXML
    public void loginButtonClicked() {
        String username = usernameTextField.getText();
        String password = usernameTextField.getText();

        // Validate the login credentials against the database
        if (validateLogin(username, password)) {
            // Login successful
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            // Login failed
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean validateLogin(String username, String password) {
        // Establish database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Prepare the SQL statement to check if the username and password exist
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if a row was returned
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
