package com.example.store_management_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private AnchorPane loginPane;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1";

    @FXML
    public void initialize() {
        usernameTextField.setPromptText("Enter your username");
        passwordTextField.setPromptText("Enter your password");
    }

    @FXML
    public void loginButtonClicked() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


//        AnchorPane newpage = FXMLLoader.load(getClass().getResource("sales-page.fxml"));
//        loginPane.getChildren().setAll(newpage);

        // Validate the login credentials against the database
//        if (validateLogin(username, password)) {
//            // Login successful
//            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
            AnchorPane newpage = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
            loginPane.getChildren().setAll(newpage);
//
//        } else {
//            // Login failed
//            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
//        }
    }

    public void gotonextpage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("sales-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1381, 835);
        stage.setTitle("Total Sales Page");
        stage.setScene(scene);
        stage.show();
    }

    public boolean validateLogin(String username, String password) {
        // Establish database connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Prepare the SQL statement to check if the username and password exist
            String query = "SELECT username,password FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

//            System.out.println(username);
//            System.out.println(password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if a row was returned
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
