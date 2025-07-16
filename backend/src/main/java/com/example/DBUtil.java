package com.example;

import java.sql.*;

public class DBUtil {
    private static final String DB_URL = "jdbc:h2:mem:urlshortener";
    private static Connection conn;

    public static void init() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))");
                stmt.execute("CREATE TABLE IF NOT EXISTS urls (id INT AUTO_INCREMENT PRIMARY KEY, short_code VARCHAR(255), original_url VARCHAR(2048), user_id INT)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return conn;
    }
}
