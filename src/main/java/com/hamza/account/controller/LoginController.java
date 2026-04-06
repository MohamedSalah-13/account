package com.hamza.account.controller;

import com.hamza.account.config.DatabaseConnection;
import com.hamza.account.dao.UserDao;
import com.hamza.account.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    private UserDao userDao = new UserDao();

    @FXML
    protected void onLoginButtonClick() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("خطا", "من فضلك ادخل اسم المستخدم وكلمة المرور");
            return;
        }

        Optional<User> userOptional = userDao.findByCredentials(userName, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            showAlert("نجاح", "اهلا بك يا" + user.getFullName());
        }else {
            showAlert("خطأ", "اسم المستخدم أو كلمة المرور غير صحيحة");
            txtUserName.clear();
            txtPassword.clear();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
