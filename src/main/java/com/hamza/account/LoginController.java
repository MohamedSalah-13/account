package com.hamza.account;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    @FXML
    protected void onLoginButtonClick() {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            showAlert("خطا", "من فضلك ادخل اسم المستخدم وكلمة المرور");
            return;
        }

        String sql = "Select * from users where username = ? and password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                showAlert("نجاح", "اهلا بك يا" + fullName);
            } else {
                showAlert("خطأ", "اسم المستخدم أو كلمة المرور غير صحيحة");
                txtUserName.clear();
                txtPassword.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("خطأ", "حدث خطأ أثناء تسجيل الدخول");
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
