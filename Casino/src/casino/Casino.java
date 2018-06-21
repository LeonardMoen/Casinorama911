package casino;

import java.io.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Casino extends Application { //<--- extends Application for javaFX

    private static ArrayList <Player> players = new ArrayList<>();
    private static PokerGraphics pokerGraphics;
    private static Poker poker;
    static Stage primaryStage;
    private static Player mainPlayer;
    //Pane rootPane = new Pane();
    private static Pane roop = new Pane();
    static Scene menu;
    static String name;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        menu();
    }
    
    public static void menu(){
        mainPlayer = new Player("Player 1", 1);
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
        double titleX = 350, titleY = 20;

        Pane tPane = new Pane();
        Rectangle bck = new Rectangle(580, 100);
        bck.setFill(Color.rgb(232, 173, 12, 0.75));
        bck.setArcHeight(45);
        bck.setArcWidth(45);
        tPane.getChildren().add(bck);

        Text title = new Text("Casinorama");
        title.setFont(titleF);
        title.setX(2);
        title.setY(90);
        Color blue = Color.rgb(24, 12, 232, 1);
        title.setFill(blue);
        tPane.getChildren().add(title);

        tPane.setTranslateX(titleX);
        tPane.setTranslateY(titleY);

        roop.getChildren().add(tPane);

        Text label1 = new Text("Name:");
        label1.setFont(new Font(18));
        label1.setFill(Color.WHITE);
        TextField nameInput = new TextField();
        Button submit = new Button("Submit");
        HBox hb = new HBox(10);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((nameInput.getText() != null && !nameInput.getText().isEmpty())) {
                    mainPlayer.setName(nameInput.getText());
                    name = nameInput.getText();
                    roop.getChildren().remove(hb);
                    Text text = new Text(550, 600, "Welcome "+name);
                    text.setFont(new Font(30));
                    roop.getChildren().add(text);
                } else {
                    nameInput.setText("You have not entered a name.");
                }
            }
        });
        
        hb.setTranslateY(580);
        hb.setTranslateX(500);
        hb.getChildren().addAll(label1, nameInput, submit);
        roop.getChildren().add(hb);

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
                    BlackJackGraphics.begin(name);
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
                RouletteGraphics rouletteGraphics = new RouletteGraphics();
                rouletteGraphics.addPlayerList(players);
                try {
                    rouletteGraphics.rouletteSetUp();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    public static Poker getPoker() {
        return poker;
    }

    public static void setPoker(Poker poker) {
        Casino.poker = poker;
    }
    
    

    public static Player getMainPlayer() {
        return mainPlayer;
    }
    
    

}
