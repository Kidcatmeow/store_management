package com.example.store_management_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    AnchorPane mainmenuPane;
    @FXML
    Button employeeBtn;
    @FXML
    Button salesBtn;
    @FXML
    Button ordersBtn;
    @FXML
    Button stockBtn;

//    public void gotoEmployeePage() throws IOException {
//        AnchorPane mainpane = FXMLLoader.load(getClass().getResource(".fxml"));
//        mainmenuPane.getChildren().setAll(mainpane);
//    }

    public void gotoSalesPage() throws IOException{
        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("sales-page.fxml"));
        mainmenuPane.getChildren().setAll(mainpane);
    }

//    public void gotoOrdersPage() throws IOException{
//        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("ChangeAppointment.fxml"));
//        mainmenuPane.getChildren().setAll(mainpane);
//    }
//
//    public void gotoStockPage() throws IOException{
//        AnchorPane mainpane = FXMLLoader.load(getClass().getResource("ChangeAppointment.fxml"));
//        mainmenuPane.getChildren().setAll(mainpane);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
