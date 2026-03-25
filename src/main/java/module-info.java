module com.hamza.account {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;


    opens com.hamza.account to javafx.fxml;
    exports com.hamza.account;
    exports com.hamza.account.config;
    opens com.hamza.account.config to javafx.fxml;
    exports com.hamza.account.controller;
    opens com.hamza.account.controller to javafx.fxml;
}