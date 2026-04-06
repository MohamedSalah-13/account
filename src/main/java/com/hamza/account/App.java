package com.hamza.account;

import com.hamza.account.config.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("شاشة الدخول");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop(){
        // عند إنهاء التطبيق يتم إغلاق ال connection pool
        DatabaseConfig.closePool();
    }
}
