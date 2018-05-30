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
        Scene scene = new Scene(root, 1000, 700, new Color(0.5, 0.3, 0.6, 0.5));

        Rectangle rect = new Rectangle(5, 5, 10, 10);
        root.getChildren().add(rect);
        
        Polygon [] col1 = new Polygon[12];
        Polygon [] col2 = new Polygon[12];
        Polygon [] col3 = new Polygon[12];

        for (int i = 0; i < 12; i++) {
            col1[i] = new Rectangle(5, 5, 10, 10);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
