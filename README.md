# Java URL Shortener

A minimal URL shortener built using:
- Java + `com.sun.net.httpserver.HttpServer`
- H2 Database (via JDBC)
- SLF4J for logging
- HTML, CSS, JS frontend
- JUnit 5 + Mockito for testing

## ✨ Features
- Shorten URLs anonymously
- Register/Login to create **custom short URLs**
- H2 in-memory DB
- CI with GitHub Actions (build + test)

## 🚀 How to Run

### Backend
```bash
cd backend
javac -cp ".:h2.jar:slf4j-api.jar:slf4j-simple.jar" com/example/*.java
java -cp ".:h2.jar:slf4j-api.jar:slf4j-simple.jar" com.example.App
```

### Frontend
Open `frontend/index.html` or `frontend/login.html` in your browser.

## 🧪 Run Tests
```bash
cd backend
# Use JUnit platform console launcher or IntelliJ
```

## ⚙️ Tech Stack
- Java 17+
- HTML/CSS/JS
- H2 DB
- SLF4J
- JUnit 5, Mockito
- GitHub Actions

## 📁 GitHub Workflow
- Issues to track features
- Branch per feature
- PR for review
- GitHub Actions run build/tests
