package com.gomabot.utils;

import com.gomabot.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSupplier {
    private final String url;
    private final String user;
    private final String password;

    public ConnectionSupplier(final DatabaseConfig config) {
        this.url = config.getUrl();
        this.user = config.getUser();
        this.password = config.getPassword();
    }

    public Connection getNewConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
