module com.hamza.account {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.hamza.account to javafx.fxml;
    exports com.hamza.account;
}