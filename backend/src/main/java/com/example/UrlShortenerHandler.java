package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.sql.*;
import java.util.*;

public class UrlShortenerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String body = new BufferedReader(new InputStreamReader(is)).lines().reduce("", (a, b) -> a + b);

            String originalUrl = extractValue(body, "url");
            String shortCode = generateShortCode();

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO urls (short_code, original_url) VALUES (?, ?)")) {
                stmt.setString(1, shortCode);
                stmt.setString(2, originalUrl);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String response = "Shortened URL: http://localhost:8000/r?code=" + shortCode;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private String extractValue(String body, String key) {
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2 && kv[0].equals(key)) return kv[1];
        }
        return null;
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
