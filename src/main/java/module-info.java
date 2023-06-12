module com.example.store_management_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.store_management_app to javafx.fxml;
    exports com.example.store_management_app;
}