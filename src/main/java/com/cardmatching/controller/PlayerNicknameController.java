package com.cardmatching.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlayerNicknameController {
    
    @FXML
    private TextField player1Field;
    
    @FXML
    private TextField player2Field;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button goBackButton;
    
    @FXML
    private Label messageLabel;
    
    private int gridSize;
    private int gridRows;
    
    @FXML
    public void initialize() {
        // Metin alanı değişikliklerini dinle
        player1Field.textProperty().addListener((observable, oldValue, newValue) -> {
            validateNicknames();
        });
        
        player2Field.textProperty().addListener((observable, oldValue, newValue) -> {
            validateNicknames();
        });
    }
    
    public void setGridSize(int size, int rows) {
        this.gridSize = size;
        this.gridRows = rows;
    }
    
    @FXML
    private void validateNicknames() {
        String nickname1 = player1Field.getText().trim();
        String nickname2 = player2Field.getText().trim();
        
        boolean isValid = nickname1.length() >= 3 && nickname1.length() <= 10 &&
                         nickname2.length() >= 3 && nickname2.length() <= 10 &&
                         !nickname1.equals(nickname2);
        
        startButton.setDisable(!isValid);
    }
    
    @FXML
    private void handleStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
            Parent root = loader.load();
            
            GameController controller = loader.getController();
            controller.initializeGame(gridSize, gridRows, player1Field.getText().trim(), player2Field.getText().trim());
            
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
	private void handleBack() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GridSelectionView.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) goBackButton.getScene().getWindow();
			stage.setScene(new Scene(root, 800, 650));
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
} 