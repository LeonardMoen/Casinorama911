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
import static javafx.application.Application.STYLESHEET_CASPIAN;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMousePress(ArrayList<Rectangle> rects) {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).setOnMousePressed(rectOnClickAction);
        }
    }

    public void drawBoard(Group root) {

        int counter = 1;
        int colxPos = 180;

        Rectangle outline = new Rectangle(175, 145, 905, 170);
        outline.setFill(Color.WHITE);
        root.getChildren().add(outline);
        Rectangle outline2 = new Rectangle(100, 200, 75, 60);
        outline2.setFill(Color.WHITE);
        root.getChildren().add(outline2);

        Font f = new Font(STYLESHEET_CASPIAN, 35);

        for (int i = 0; i < 12; i++) {

            Rectangle zero = new Rectangle(105, 205, 70, 50);
            zero.setFill(Color.GREEN);
            root.getChildren().add(zero);

            Text t0 = new Text(125, 240, "0");
            t0.setFill(Color.WHITE);
            t0.setFont(f);

            Rectangle a = new Rectangle(colxPos, 150, 70, 50);
            a.setFill(Color.RED);
            rects.add(a);

            Text t1 = new Text(colxPos + 15, 185, Integer.toString(counter));
            t1.setFill(Color.WHITE);
            t1.setFont(f);

            Rectangle b = new Rectangle(colxPos, 205, 70, 50);
            b.setFill(Color.RED);
            rects.add(b);

            Text t2 = new Text(colxPos + 15, 240, Integer.toString(counter + 1));
            t2.setFill(Color.WHITE);
            t2.setFont(f);

            Rectangle c = new Rectangle(colxPos, 260, 70, 50);
            c.setFill(Color.RED);
            rects.add(c);

            Text t3 = new Text(colxPos + 15, 295, Integer.toString(counter + 2));
            t3.setFill(Color.WHITE);
            t3.setFont(f);

            root.getChildren().add(a);
            root.getChildren().add(b);
            root.getChildren().add(c);

            root.getChildren().add(t0);
            root.getChildren().add(t1);
            root.getChildren().add(t2);
            root.getChildren().add(t3);

            colxPos += 75;
            counter += 3;

        }
        fillBlackNums();
    }

    EventHandler<Event> rectOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            temp.setFill(Color.CORAL);

        }

    };

    public void fillBlackNums() {
        int[] blackNums = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};

        for (int i = 0; i < rects.size(); i++) {
            for (int j = 0; j < blackNums.length; j++) {

                if (i == blackNums[j] - 1) {
                    rects.get(i).setFill(Color.BLACK);
                }

            }

        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
