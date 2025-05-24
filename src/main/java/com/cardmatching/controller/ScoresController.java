package com.cardmatching.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import javafx.beans.property.*;

public class ScoresController {
    
    @FXML private TableView<GameScore> scoresTable;
    @FXML private TableColumn<GameScore, String> player1Column;
    @FXML private TableColumn<GameScore, Integer> score1Column;
    @FXML private TableColumn<GameScore, String> player2Column;
    @FXML private TableColumn<GameScore, Integer> score2Column;
    @FXML private TableColumn<GameScore, String> timeColumn;
    
    private static final String SCORES_FILE = "scores.txt";
    
    @FXML
    public void initialize() {
        // Tablo sütunlarını ayarla
        player1Column.setCellValueFactory(cellData -> cellData.getValue().player1Property());
        score1Column.setCellValueFactory(cellData -> cellData.getValue().score1Property().asObject());
        player2Column.setCellValueFactory(cellData -> cellData.getValue().player2Property());
        score2Column.setCellValueFactory(cellData -> cellData.getValue().score2Property().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        scoresTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        
        loadScores();
    }
    
    private void loadScores() {
        scoresTable.getItems().clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    GameScore score = new GameScore(
                        parts[0], Integer.parseInt(parts[1]),
                        parts[2], Integer.parseInt(parts[3]),
                        parts[4]
                    );
                    scoresTable.getItems().add(score);
                }
            }
        } catch (IOException e) {
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) scoresTable.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Oyun skoru sınıfı
    public static class GameScore {
        private final String player1;
        private final int score1;
        private final String player2;
        private final int score2;
        private final String time;
        
        public GameScore(String player1, int score1, String player2, int score2, String time) {
            this.player1 = player1;
            this.score1 = score1;
            this.player2 = player2;
            this.score2 = score2;
            this.time = time;
        }
        
        public StringProperty player1Property() {
            return new SimpleStringProperty(player1);
        }
        
        public IntegerProperty score1Property() {
            return new SimpleIntegerProperty(score1);
        }
        
        public StringProperty player2Property() {
            return new SimpleStringProperty(player2);
        }
        
        public IntegerProperty score2Property() {
            return new SimpleIntegerProperty(score2);
        }
        
        public StringProperty timeProperty() {
            return new SimpleStringProperty(time);
        }
    }
} 