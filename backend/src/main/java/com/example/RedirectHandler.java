package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URI;
import java.sql.*;

public class RedirectHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();
        String code = query != null && query.startsWith("code=") ? query.substring(5) : null;

        if (code != null) {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT original_url FROM urls WHERE short_code = ?")) {
                stmt.setString(1, code);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String originalUrl = rs.getString("original_url");
                    exchange.getResponseHeaders().add("Location", originalUrl);
                    exchange.sendResponseHeaders(302, -1);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String response = "Invalid short code";
        exchange.sendResponseHeaders(404, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
