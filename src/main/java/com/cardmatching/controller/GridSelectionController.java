package com.cardmatching.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class GridSelectionController {
    
    @FXML private VBox grid3x4;
    @FXML private VBox grid4x4;
    @FXML private VBox grid5x4;
    @FXML private Button startButton;
    @FXML private Button goBackButton;
    
    private int selectedGridSize = 0;
    private int selectedGridRows = 0;
    
    @FXML
    private void handleGridSelection(MouseEvent event) {
        VBox selectedGrid = (VBox) event.getSource();
        
        // Önceki seçimi temizle
        grid3x4.getStyleClass().remove("selected");
        grid4x4.getStyleClass().remove("selected");
        grid5x4.getStyleClass().remove("selected");
        
        // Yeni seçimi işaretle
        selectedGrid.getStyleClass().add("selected");
        
        // Grid boyutunu belirle
        if (selectedGrid == grid3x4) {
            selectedGridSize = 4;
            selectedGridRows = 3;
        } else if (selectedGrid == grid4x4) {
            selectedGridSize = 4;
            selectedGridRows = 4;
        } else if (selectedGrid == grid5x4) {
            selectedGridSize = 5;
            selectedGridRows = 4;
        }
        
        startButton.setDisable(false);
    }
    
    @FXML
    private void handleStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayerNicknameView.fxml"));
            Parent root = loader.load();
            
            PlayerNicknameController controller = loader.getController();
            controller.setGridSize(selectedGridSize, selectedGridRows);
            
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 650));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
	private void handleBack() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenuView.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) goBackButton.getScene().getWindow();
			stage.setScene(new Scene(root, 800, 650));
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
} 