package com.hamza.account.dao;

import com.hamza.account.config.DatabaseConfig;
import com.hamza.account.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    public Optional<User> findByCredentials(String username, String password) {
        String sql = "Select * from users where username=? and password=?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setRole(rs.getString("role"));
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            System.err.println("خطا فى البحث عن المستخدم" + e.getMessage());
        }
        return Optional.empty();
    }
}
