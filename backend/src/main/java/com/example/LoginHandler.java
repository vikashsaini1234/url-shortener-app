package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.*;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is)).lines().reduce("", (a, b) -> a + b);

            String username = extractValue(body, "username");
            String password = extractValue(body, "password");

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    respond(exchange, "Login successful");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            respond(exchange, "Invalid credentials");
        }
    }

    private String extractValue(String body, String key) {
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2 && kv[0].equals(key)) return kv[1];
        }
        return null;
    }

    private void respond(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
