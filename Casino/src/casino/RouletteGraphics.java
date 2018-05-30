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
    ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

    public RouletteGraphics() {

    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Group root = new Group();
        Scene scene = new Scene(root, 1500, 1000, Color.BISQUE);
        
        drawBoard(root);

       //put game logic here, roulette class cant be called?
        
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

            Rectangle zero = new Rectangle(110, 10, 50, 50);

            rects.add(zero);
            root.getChildren().add(zero);

            Rectangle a = new Rectangle(50, col1YPos, 50, 50);
            rects.add(a);

            Rectangle b = new Rectangle(110, col2YPos, 50, 50);
            rects.add(b);

            Rectangle c = new Rectangle(170, col3YPos, 50, 50);
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
}
