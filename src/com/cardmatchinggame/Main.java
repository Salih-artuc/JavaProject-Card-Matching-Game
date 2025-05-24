package com.cardmatchinggame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Login sayfasını yükle
        Parent root = FXMLLoader.load(getClass().getResource("/com/cardmatchinggame/views/LoginView.fxml"));
        primaryStage.setTitle("Card Matching Game");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 