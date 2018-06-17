package casino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BlackJackGraphics extends Application {

    private Player currentPlayer;
    private Button stay = new Button("Stay");
    private Button dDown = new Button("Double Down");
    private Button split = new Button("Split");
    private Button hit = new Button("Hit");
    private Group root = new Group();
    private int round = 1;

    public static void main(String[] args) {
        launch(args);
    }

    public void setButtons(int handNum) {
        if (round == 1) {
            if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                currentPlayer.setNaturalBlackJack(true);
                currentPlayer.setStay(true);
                currentPlayer.setChips((int) (currentPlayer.getBet() * 1.5 + currentPlayer.getChips() + currentPlayer.getBet()));
            } else if (currentPlayer.getTotal(handNum) >= 9 && currentPlayer.getTotal(handNum) <= 11) {
                if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                    root.getChildren().addAll(hit, stay, split, dDown);
                } else {
                    root.getChildren().addAll(hit, stay, dDown);
                }
            } else if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                root.getChildren().addAll(hit, stay, split);
            } else {
                root.getChildren().addAll(hit, stay);
            }
        } else {
            if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                currentPlayer.setStay(true);
            } else {
                root.getChildren().addAll(hit, stay);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        hit.setLayoutX(10);
        hit.setLayoutY(10);
        stay.setLayoutX(10);
        stay.setLayoutY(40);
        split.setLayoutX(10);
        split.setLayoutY(70);
        dDown.setLayoutX(10);
        dDown.setLayoutY(100);
        hit.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerHit(currentPlayer, 0);
            } catch (IOException ex) {
                Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stay.setOnAction((ActionEvent event) -> {
            currentPlayer.setStay(true);
            if (root.getChildren().contains(split)) {
                root.getChildren().remove(split);
            }
            if (root.getChildren().contains(dDown)) {
                root.getChildren().remove(dDown);
            }
            root.getChildren().removeAll(stay, hit);
        });
        split.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerSplit(currentPlayer, 0);
            } catch (InterruptedException ex) {
                Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dDown.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerDD(currentPlayer, 0);
            } catch (IOException ex) {
                Logger.getLogger(BlackJackGraphics.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentPlayer.setStay(true);
            if (root.getChildren().contains(split)) {
                root.getChildren().remove(split);
            }
            if (root.getChildren().contains(dDown)) {
                root.getChildren().remove(dDown);
            }
            root.getChildren().removeAll(stay, hit);
        });
        currentPlayer = BlackjackJAVA.numOfPlayers.get(0);
        setButtons(0);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("BLACKJACK!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
