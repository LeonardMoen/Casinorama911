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
  
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root, 1500, 1000, Color.BISQUE);

        int col1YPos = 10;
        int col2YPos = 60;
        int col3YPos = 110;

        for (int i = 0; i < 12; i++) {

            Rectangle a = new Rectangle(50, col1YPos, 50, 50);
            a.setOnMousePressed(rectOnClickAction);

            Rectangle b = new Rectangle(110, col2YPos, 50, 50);
            b.setOnMousePressed(rectOnClickAction);

            Rectangle c = new Rectangle(170, col3YPos, 50, 50);
            c.setOnMousePressed(rectOnClickAction);

            root.getChildren().add(a);
            root.getChildren().add(b);
            root.getChildren().add(c);

            col1YPos += 60;
            col2YPos += 60;
            col3YPos += 60;

        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    EventHandler<Event> rectOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            temp.setFill(Color.CORAL);

        }

    };
}
