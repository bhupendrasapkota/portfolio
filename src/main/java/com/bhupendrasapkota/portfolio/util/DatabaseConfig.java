package com.bhupendrasapkota.portfolio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                // Default values if properties file is not found
                properties.setProperty("db.url", "jdbc:mysql://127.0.0.1:3306/portfolio");
                properties.setProperty("db.username", "root");
                properties.setProperty("db.password", "idontcare");
                properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static String getDriver() {
        return properties.getProperty("db.driver");
    }
}
