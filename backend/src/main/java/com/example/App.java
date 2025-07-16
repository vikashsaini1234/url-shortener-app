package com.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        DBUtil.init(); // initialize H2
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/shorten", new UrlShortenerHandler());
        server.createContext("/r", new RedirectHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8000");
    }
}
