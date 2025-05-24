package com.cardmatchinggame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    
    private static final String USER_DATA_FILE = "userdata.txt";
    private Map<String, String> users = new HashMap<>();
    
    @FXML
    public void initialize() {
        loadUsers();
        
        // Kullanıcı adı için karakter sınırı
        usernameField.setTextFormatter(new TextFormatter<String>((Change change) -> {
            if (change.getControlNewText().length() <= 8) {
                return change;
            }
            return null;
        }));
        
        // Şifre için karakter sınırı
        passwordField.setTextFormatter(new TextFormatter<String>((Change change) -> {
            if (change.getControlNewText().length() <= 9) {
                return change;
            }
            return null;
        }));
    }
    
    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if ((username.length() < 5 || username.length() > 8) || (password.length() < 6 || password.length() > 9)) {
            messageLabel.setText("Kullanıcı adı 5-8 karakter olmalıdır, şifre 6-9 karakter olmalıdır!");
            return;
        }
        
        if (users.containsKey(username)) {
            messageLabel.setText("Bu kullanıcı adı zaten kayıtlı!");
            return;
        }
        
        users.put(username, password);
        saveUsers();
        messageLabel.setText("Kayıt başarılı! Giriş yapabilirsiniz.");
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (!users.containsKey(username)) {
            messageLabel.setText("Kullanıcı bulunamadı!");
            return;
        }
        
        if (!users.get(username).equals(password)) {
            messageLabel.setText("Hatalı şifre!");
            return;
        }
        
        try {
            // Ana menüye yönlendir
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cardmatchinggame/views/MainMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Bir hata oluştu!");
        }
    }
    
}