/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author laua8572
 */
public class RouletteGraphics extends Application {

    private Roulette roulette = new Roulette();
    private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Rectangle> outsideBets = new ArrayList<Rectangle>();
    

    public RouletteGraphics(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Group root = new Group();
        Scene scene = new Scene(root, 1500, 1000, Color.GREEN);

        drawBoard(root);

        for (int i = 0; i < players.size(); i++) {
            System.out.println("Pick an a type of bet");
            
        }

        setMousePress(rects);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMousePress(ArrayList<Rectangle> rects) {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).setOnMousePressed(rectOnClickAction);
        }
    }

    public void drawBoard(Group root) {
        int col1YPos = 70;
        int col2YPos = 70;
        int col3YPos = 70;

        for (int i = 0; i < 12; i++) {

            Rectangle zero = new Rectangle(210, 10, 50, 50);
            zero.setFill(Color.GREENYELLOW);
            rects.add(zero);
            root.getChildren().add(zero);

            Rectangle a = new Rectangle(150, col1YPos, 50, 50);
            rects.add(a);

            Rectangle b = new Rectangle(210, col2YPos, 50, 50);
            rects.add(b);

            Rectangle c = new Rectangle(270, col3YPos, 50, 50);
            rects.add(c);

            root.getChildren().add(a);
            root.getChildren().add(b);
            root.getChildren().add(c);

            col1YPos += 60;
            col2YPos += 60;
            col3YPos += 60;

        }
    }

    EventHandler<Event> rectOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            temp.setFill(Color.CORAL);

        }

    };
    
    public void colourBlack(){
        int [] blackNums = roulette.getBlackNums();
        for (int i = 1; i <= 36; i++) {
//            if(blackNums.contains(rects.get(i))){
//                rects.get(i).setFill(Color.GRAY);
//            }
//            else{
//                rects.get(i).setFill(Color.RED);
//            }
        }
    }
    
    public void drawOutsideeBets(){
        for (int i = 0; i < 3; i++) {
      //      Rectangle twelve1 = new Rectangle(90, , , i)
        }
    }
}
