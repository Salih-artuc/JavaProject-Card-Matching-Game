package com.cardmatchinggame.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MainMenuController {
    
    @FXML private Button playButton;
    @FXML private Button scoresButton;
    @FXML private Button logOutButton;
    
    @FXML
    private void handlePlay() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cardmatchinggame/views/GridSelectionView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) playButton.getScene().getWindow();
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
 
    @FXML
	private void handleLogOut() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cardmatchinggame/views/LoginView.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) logOutButton.getScene().getWindow();
			stage.setScene(new Scene(root, 800, 650));
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}