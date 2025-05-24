package com.cardmatchinggame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameOverController {
    
    @FXML private Text winnerText;
    @FXML private Text scoreText;
    @FXML private Button menuButton;
    @FXML private Button scoresButton;
    
    private static final String SCORES_FILE = "scores.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public void setGameResult(String player1, int score1, String player2, int score2) {
        // Kazananı belirle
        String winner;
        int scoreHigh;
        int scoreLow;
        if (score1 > score2) {
            winner = player1;
            scoreHigh = score1;
            scoreLow = score2;
        } else if (score2 > score1) {
            winner = player2;
            scoreHigh = score2;
            scoreLow = score1;
        } else {
            winner = "Dostluk kazandı. Berabere";
            scoreHigh = score1;
            scoreLow = score2;
        }
        
        if(score1 == score2) {
            winnerText.setText(winner);
            scoreText.setText("Skor: " + scoreHigh + " - " + scoreLow);
        } else {
        	winnerText.setText("Kazanan: " + winner);
        	scoreText.setText("Skor: " + score1 + " - " + score2);
        }
        
        // Skorları kaydet
        saveScore(player1, score1, player2, score2);
    }
    
    private void saveScore(String player1, int score1, String player2, int score2) {
        try (FileWriter fw = new FileWriter(SCORES_FILE, true);
             PrintWriter out = new PrintWriter(fw)) {
            
            String timestamp = LocalDateTime.now().format(formatter);
            String scoreLine = String.format("%s,%d,%s,%d,%s",
                player1, score1, player2, score2, timestamp);
            
            out.println(scoreLine);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cardmatchinggame/views/MainMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleScores() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cardmatchinggame/views/ScoresView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) scoresButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 