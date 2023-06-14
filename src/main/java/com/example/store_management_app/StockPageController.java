package com.example.store_management_app;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StockPageController {
    // Database connection details
    public static final String DB_URL = "jdbc:mysql://localhost:1234/storemanagement";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "1";

    @FXML
    private AnchorPane stockPane;
    @FXML
    private TextField inputID;
    @FXML
    private TextField inputItemName;
    @FXML
    private TextField inputQuantity;

    // Stock table
    @FXML
    private TableView<SalesPageController.DataRow> stockTable;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> stockID;
    @FXML
    private TableColumn<SalesPageController.DataRow, String> itemName_s;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> quantity;

    // Out of stock table
    @FXML
    private TableView<SalesPageController.DataRow> outOfSockTable;
    @FXML
    private TableColumn<SalesPageController.DataRow, Integer> outOfStockID;
    @FXML
    private TableColumn<SalesPageController.DataRow, String> itemName_ofs;

    @FXML
    public void addButtonClicked() throws IOException {
    }
    @FXML
    public void deleteButtonClicked() throws IOException {
    }
    @FXML
    public void updateButtonClicked() throws IOException {
    }
    @FXML
    public void clearButtonClicked() throws IOException {
    }

}
