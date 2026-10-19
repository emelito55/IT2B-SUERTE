
package com.suerte.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:scenario.db";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);

        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON;");
        }

        return connection;
    }

    public static void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS assets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    device_name TEXT NOT NULL,
                    assigned_to TEXT NOT NULL,
                    serial_no TEXT NOT NULL UNIQUE,
                    condition TEXT NOT NULL
                        CHECK (condition IN (
                            'NEW', 'GOOD', 'REPAIR_NEEDED'
                        ))
                );
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Database connected successfully.");

        } catch (SQLException e) {
            System.out.println(
                    "Database initialization error: " + e.getMessage()
            );
        }
    }
}
