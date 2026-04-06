package com.hamza.account.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();
    private static HikariDataSource dataSource;

    static {
        loadProperties();
        initializeDataSource();
    }

    private DatabaseConfig() {
    }

    private static void loadProperties() {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("لم يتم العثور على الملف");
            }

            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("خطا فى قراءة ملف الاعدادت");
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();

        // إعدادات الاتصال الاساسية
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        // إعدادات pool
        config.setMaximumPoolSize(Integer.parseInt(
                properties.getProperty("db.pool.size", "10")));
        config.setMinimumIdle(Integer.parseInt(
                properties.getProperty("db.pool.minIdle", "5")));
        config.setIdleTimeout(Long.parseLong(
                properties.getProperty("db.pool.idleTimeout", "300000")));
        config.setConnectionTimeout(Long.parseLong(
                properties.getProperty("db.pool.connectionTimeout", "20000")));
        config.setMaxLifetime(Long.parseLong(
                properties.getProperty("db.pool.maxLifetime", "1200000")));

        // إعدادات إضافية للأداء
        config.setPoolName("SalesAppPool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        dataSource = new HikariDataSource(config);
        System.out.println("✅ HikariCP Connection Pool initialized successfully");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // إغلاق ال pool عند اغلاق البرنامج
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
            System.out.println("Connection Closed");
        }
    }

}
