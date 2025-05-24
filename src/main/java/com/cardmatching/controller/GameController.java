package com.cardmatching.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;
import javafx.application.Platform;

public class GameController {
    
    @FXML private GridPane gameGrid;
    @FXML private Text player1Name;
    @FXML private Text player1Score;
    @FXML private Text player2Name;
    @FXML private Text player2Score;
    
    private int gridSize;
    private int gridRows;
    private String player1;
    private String player2;
    private int currentPlayer = 1;
    private int score1 = 0;
    private int score2 = 0;
    private List<Card> cards = new ArrayList<>();
    private Card firstCard = null;
    private Card secondCard = null;
    private Button firstButton = null;
    private Button secondButton = null;
    private boolean isProcessing = false;
    
    public void initializeGame(int size, int rows, String p1, String p2) {

    	this.gridSize = size;
    	this.gridRows = rows;

        this.player1 = p1;
        this.player2 = p2;
        
        player1Name.setText(p1);
        player2Name.setText(p2);
        updateScores();
        
        // İlk oyuncunun sırası
        currentPlayer = 1;
        
        createCards();
        shuffleCards();
        placeCards();
        
        // Scene oluşturulduktan sonra arka plan rengini güncelle
        Platform.runLater(this::updateBackgroundColor);
    }
    
    private void createCards() {
        // Programlama dili resimleri için kartlar oluştur
        String[] languages = {"Java", "Python", "C++", "C#", "Ruby", "PHP", 
                            "Swift", "Kotlin", "Go", "Rust"};
        
        // kart sayısı hesap
        int totalCards;
        totalCards = gridSize * gridRows;
        
        int pairsNeeded = totalCards / 2;
        
        for (int i = 0; i < pairsNeeded; i++) {
            cards.add(new Card(languages[i]));
            cards.add(new Card(languages[i]));
        }
    }
    
    private void shuffleCards() {
        Collections.shuffle(cards);
    }
    
    private void placeCards() {
        gameGrid.getChildren().clear();
        int index = 0;

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (index < cards.size()) {
                    Card card = cards.get(index++);
                    Button cardButton = createCardButton(card);
                    gameGrid.add(cardButton, col, row);
                }
            }
        }
    }
    
    private Button createCardButton(Card card) {
        Button button = new Button("?");
        button.setPrefSize(100, 100);
        button.getStyleClass().add("card-button");
        
        button.setOnAction(e -> handleCardClick(card, button));
        return button;
    }
    
    private void handleCardClick(Card card, Button button) {
        // Eğer kart işlemi devam ediyorsa veya kart zaten açıksa, tıklamayı engelle
        if (isProcessing || button.getText().equals(card.getValue())) {
            return;
        }
        
        if (firstCard == null) {
            // İlk kart seçildi
            firstCard = card;
            firstButton = button;
            button.setText(card.getValue());
            button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        } else if (secondCard == null) {
            // İkinci kart seçildi
            secondCard = card;
            secondButton = button;
            button.setText(card.getValue());
            button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            
            // Kartları kontrol et
            isProcessing = true;
            
            // Kısa bir gecikme
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 1 saniye bekle
                    
                    // JavaFX thread'inde UI güncellemesi yap
                    javafx.application.Platform.runLater(() -> {
                        if (firstCard.getValue().equals(secondCard.getValue())) {
                            // Eşleşme bulundu
                            if (currentPlayer == 1) {
                                score1++;
                            } else {
                                score2++;
                            }
                            updateScores();
                            
                            //eşleşirse yeşil renk
                            firstButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
                            secondButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
                            
                            // Tüm kartlar eşleşti mi kontrol et
                            checkGameOver();
                        } else {
                            // Eşleşme bulunamadı
                            currentPlayer = currentPlayer == 1 ? 2 : 1;
                            
                            // Kartları geri çevir
                            firstButton.setText("?");
                            firstButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
                            secondButton.setText("?");
                            secondButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
                            
                            // Arka plan rengini güncelle
                            updateBackgroundColor();
                        }
                        
                        // Kartları sıfırla
                        firstCard = null;
                        secondCard = null;
                        firstButton = null;
                        secondButton = null;
                        isProcessing = false;
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    
    private void updateScores() {
        player1Score.setText(String.valueOf(score1));
        player2Score.setText(String.valueOf(score2));
    }
    
    private void checkGameOver() {
        // Tüm kartların açık olup olmadığını kontrol et
        boolean allMatched = true;
        for (int i = 0; i < gameGrid.getChildren().size(); i++) {
            Button button = (Button) gameGrid.getChildren().get(i);
            if (button.getText().equals("?")) {
                allMatched = false;
                break;
            }
        }
        
        if (allMatched) {
            // Oyun bitti, sonuç ekranını göster
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameOverView.fxml"));
                Parent root = loader.load();
                
                GameOverController controller = loader.getController();
                controller.setGameResult(player1, score1, player2, score2);
                
                Stage stage = (Stage) gameGrid.getScene().getWindow();
                stage.setScene(new Scene(root, 800,650));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateBackgroundColor() {
        if (gameGrid.getScene() != null) {
            VBox root = (VBox) gameGrid.getScene().getRoot();
            root.getStyleClass().removeAll("player1-turn", "player2-turn");
            root.getStyleClass().add(currentPlayer == 1 ? "player1-turn" : "player2-turn");
        }
    }
    
    @FXML
    private void handleRestart() {
        initializeGame(gridSize, gridRows, player1, player2);
    }
    
    @FXML
    private void handleExit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gameGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Kart sınıfı
    private static class Card {
        private String value;
        
        public Card(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
} 