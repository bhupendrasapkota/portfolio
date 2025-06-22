package com.bhupendrasapkota.portfolio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AppConfig {
    private static final Logger logger = Logger.getLogger(AppConfig.class.getName());
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
                logger.info("Application properties loaded successfully");
            } else {
                logger.warning("application.properties not found, using defaults");
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error loading application.properties: " + e.getMessage(), e);
        }
    }
    
    public static String getProperty(String key) {
        return getProperty(key, null);
    }
    
    public static String getProperty(String key, String defaultValue) {
        // Try environment variable first
        String envValue = System.getenv(key.toUpperCase().replace(".", "_"));
        if (envValue != null && !envValue.trim().isEmpty()) {
            return envValue;
        }
        
        // Try system property
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.trim().isEmpty()) {
            return sysValue;
        }
        
        // Try application.properties
        String propValue = properties.getProperty(key);
        if (propValue != null && !propValue.trim().isEmpty()) {
            return propValue;
        }
        
        return defaultValue;
    }
    
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warning("Invalid integer value for property " + key + ": " + value);
            return defaultValue;
        }
    }
} 