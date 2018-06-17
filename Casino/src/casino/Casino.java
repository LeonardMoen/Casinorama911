package casino;

import java.io.*;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Casino extends Application { //<--- extends Application for javaFX

    static PokerGraphics pokerGraphics;
    static Stage primaryStage;
    //Pane rootPane = new Pane();
    Pane roop = new Pane();
    static Scene menu;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        ImageView menuImage = new ImageView();
        menuImage.setImage(ImageBuffer.menu);
        menuImage.setFitHeight(768);
        menuImage.setFitWidth(1366);
        menuImage.setX(-45);
        menuImage.setY(-40);
        roop.getChildren().add(menuImage);

        menu = new Scene(roop, 1920, 1080);
        Font titleF = new Font("Times New Roman", 120);
        Font game = new Font("Times New Roman", 35);
        Font f = new Font("Times New Roman", 16);

        //menu scene
        //<editor-fold defaultstate="collapsed" desc="display menu buttons/title">
        double buttonsX = 430, buttonsY = 130;
        double titleX = 250, titleY = 20;

        Pane tPane = new Pane();
        Rectangle bck = new Rectangle(780, 100);
        bck.setFill(Color.rgb(232, 173, 12, 0.75));
        bck.setArcHeight(45);
        bck.setArcWidth(45);
        tPane.getChildren().add(bck);

        Text title = new Text("Casinorama 911");
        title.setFont(titleF);
        title.setX(2);
        title.setY(90);
        Color blue = Color.rgb(24, 12, 232, 1);
        title.setFill(blue);
        tPane.getChildren().add(title);

        tPane.setTranslateX(titleX);
        tPane.setTranslateY(titleY);

        roop.getChildren().add(tPane);

        //button Poker
        Button btnPoker = new Button();
        btnPoker.setText("Poker");
        btnPoker.setFont(game);
        btnPoker.setMinSize(200, 200);
        btnPoker.setTranslateX(buttonsX);
        btnPoker.setTranslateY(buttonsY);
        btnPoker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pokerGraphics = new PokerGraphics();
                PokerGraphics.pokerSetUp();
            }
        });
        roop.getChildren().add(btnPoker);

        //button Black Jack
        Button btnBJ = new Button();
        btnBJ.setText("Black\nJack");
        btnBJ.setFont(game);
        btnBJ.setMinSize(200, 200);
        btnBJ.setTranslateX(buttonsX);
        btnBJ.setTranslateY(buttonsY + 220);
        btnBJ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Black Jack Game");
                try {
                    BlackJackGraphics.begin();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        roop.getChildren().add(btnBJ);

        //button Roulette
        Button btnRoulette = new Button();
        btnRoulette.setText("Roulette");
        btnRoulette.setFont(game);
        btnRoulette.setMinSize(200, 200);
        btnRoulette.setTranslateX(buttonsX + 220);
        btnRoulette.setTranslateY(buttonsY);
        btnRoulette.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Roulette Game");
            }
        });
        roop.getChildren().add(btnRoulette);

        //button Roulette
        Button btnRules = new Button();
        btnRules.setText("Rules");
        btnRules.setFont(game);
        btnRules.setMinSize(200, 200);
        btnRules.setTranslateX(buttonsX + 220);
        btnRules.setTranslateY(buttonsY + 220);
        btnRules.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Rules/Instructions");
            }
        });
        roop.getChildren().add(btnRules);
//</editor-fold>

        primaryStage.setScene(menu);
        primaryStage.show();
    }

    public static PokerGraphics getPokerGraphics() {
        return pokerGraphics;
    }

}
