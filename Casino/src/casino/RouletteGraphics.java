/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author laua8572
 */
public class RouletteGraphics extends Application {

    private ArrayList<Rectangle> rects = new ArrayList<>();
    private ArrayList<Rectangle> outsideBets = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Object> playerSquares = new ArrayList<>();
    private ArrayList<Rectangle> playerSquareCovers = new ArrayList<>();
    private ArrayList<Bet> bets = new ArrayList<Bet>();
    private boolean clickedSingleBetButton = false;
    private boolean clickedDoneBotton = false;
    private Button next = new Button();

    Circle wheel = new Circle(400, 500, 100);

    public void RouletteTest(ArrayList<Player> players) {
        this.players = players;
    }

    @Override

    public void start(Stage primaryStage) throws IOException, FileNotFoundException, InterruptedException {
        int xLoc = -325;

        players.add(new Player("ASDHGJSKSDJKLS", 0));
        players.add(new Player("dsfhek", 1));
        players.add(new Player("djwkalsdj", 2));
        players.add(new Player("JKKJS", 3));
        players.add(new Player("SHJKE", 4));
        players.add(new Player("Bob", 5));
        players.add(new Player("SJHKM<D", 6));
        players.add(new Player("ASLJDHJSSKJDA", 7));
        players.get(0).setChips(123456);

        Label label1 = new Label("Name:");

        Group root = new Group();
        Scene scene = new Scene(root, 1700, 1000, Color.GREEN);

        drawBoard(root);
        setClickNums();

        drawOutsideeBets(root);
        setClickOutside();

        drawInsideBets(root);

        drawWheel(root, 2000);
        rotateWheel(root, 2000);

        drawPlayers(root);   //moves player squares
        for (int i = 0; i < players.size() - 4; i++) {
            movePlayers(root, xLoc);
            xLoc -= 325;
        }
        flyingChip(root, primaryStage, scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setClickNums() {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).setOnMousePressed(rectOnClickAction);
        }
    }

    public void removeClickNums() {
        for (int i = 0; i < rects.size(); i++) {
            rects.get(i).removeEventHandler(EventType.ROOT, rectOnClickAction);
        }
    }

    public void setClickOutside() {
        for (int i = 0; i < outsideBets.size(); i++) {
            outsideBets.get(i).setOnMousePressed(outsideOnClickAction);

        }
    }

    public void removeClickOutside() {
        for (int i = 0; i < outsideBets.size(); i++) {
            outsideBets.get(i).removeEventHandler(EventType.ROOT, outsideOnClickAction);
        }
    }

    EventHandler<Event> rectOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            Paint p = temp.getFill();
            temp.setFill(Color.BLUEVIOLET);
            System.out.println(rects.indexOf(temp));

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    temp.setFill(p);
                }
            },
                    300
            );
        }

    };

    EventHandler<Event> outsideOnClickAction = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Rectangle temp = (Rectangle) event.getSource();
            Paint p = temp.getFill();
            temp.setFill(Color.BLUEVIOLET);
            System.out.println(rects.indexOf(temp));

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    temp.setFill(p);
                }
            },
                    300
            );
        }

    };

    public void drawBoard(Group root) {

        int counter = 1;
        int colxPos = 180;

        Rectangle outline = new Rectangle(175, 145, 1000, 280); //for white outline
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

    public void drawOutsideeBets(Group root) {
        Font f1 = new Font(STYLESHEET_CASPIAN, 25);

        Rectangle coverWhiteArea = new Rectangle(1080, 315, 95, 165);
        coverWhiteArea.setFill(Color.GREEN);
        root.getChildren().add(coverWhiteArea);

        int yPos = 150;
        for (int i = 0; i < 3; i++) {
            Rectangle r = new Rectangle(1080, yPos, 90, 50);    //column bets
            r.setFill(Color.GREEN);
            root.getChildren().add(r);
            outsideBets.add(r);

            Text t = new Text(1095, yPos + 32, "Col " + Integer.toString(i + 1));
            t.setFill(Color.WHITE);
            t.setFont(f1);
            root.getChildren().add(t);

            yPos += 55;
        }
        int xPos = 180;
        for (int i = 0; i < 3; i++) {
            Rectangle r = new Rectangle(xPos, 315, 295, 50);    //dozen bets
            r.setFill(Color.GREEN);
            root.getChildren().add(r);
            outsideBets.add(r);

            Text t = new Text(xPos + 110, yPos + 32, "Dozen " + Integer.toString(i + 1));
            t.setFill(Color.WHITE);
            t.setFont(f1);
            root.getChildren().add(t);

            xPos += 300;
        }
        xPos = 180;
        for (int i = 0; i < 12; i++) {
            Rectangle r = new Rectangle(xPos, 370, 145, 50);
            r.setFill(Color.GREEN);
            root.getChildren().add(r);
            outsideBets.add(r);
            xPos += 150;
        }
        xPos = 230;
        yPos = 405;

        Text t1 = new Text(xPos, yPos, "1-18");
        t1.setFill(Color.WHITE);
        t1.setFont(f1);
        root.getChildren().add(t1);

        Text t2 = new Text(xPos + 150, yPos, "Even");
        t2.setFill(Color.WHITE);
        t2.setFont(f1);
        root.getChildren().add(t2);

        Text t3 = new Text(xPos + 150 * 2, yPos, "Red");
        t3.setFill(Color.RED);
        t3.setFont(f1);
        root.getChildren().add(t3);

        Text t4 = new Text(xPos + 150 * 3, yPos, "Black");
        t4.setFill(Color.BLACK);
        t4.setFont(f1);
        root.getChildren().add(t4);

        Text t5 = new Text(xPos + 150 * 4, yPos, "Odd");
        t5.setFill(Color.WHITE);
        t5.setFont(f1);
        root.getChildren().add(t5);

        Text t6 = new Text(xPos + 150 * 5 - 10, yPos, "19-36");
        t6.setFill(Color.WHITE);
        t6.setFont(f1);
        root.getChildren().add(t6);

    }

    public void drawInsideBets(Group root) throws FileNotFoundException {

        Rectangle green = new Rectangle(180, 505, 600, 190);
        green.setFill(Color.GREEN);
        green.setArcWidth(20);
        green.setArcHeight(20);
        root.getChildren().add(green);

        Font f = new Font(STYLESHEET_MODENA, 50);
        Text t = new Text(355, 570, "Inside Bets");
        t.setFill(Color.WHITE);
        t.setFont(f);
        root.getChildren().add(t);

        clickedSingleBetButton = false;

        singleButton(root);

        streetButton(root);

        cornerButton(root);

        splitButton(root);
    }

    public void singleButton(Group root) throws FileNotFoundException {
        if (!clickedSingleBetButton) {
            Button button = new Button();
            button.setTranslateX(200);
            button.setTranslateY(625);
            button.setPadding(Insets.EMPTY);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Rectangle r = new Rectangle(200, 625, 100, 40);
                    r.setOpacity(0.5);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    root.getChildren().add(r);
                    buttonClickColor(root, r);

                    IntStream.range(0, 1).forEach(//automatically clicks button "next"
                            i -> next.fire()
                    );
                }
            });

            Image image = new Image(new FileInputStream("src/Resources/Single.png"), 2000, 2000, true, true);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(40);
            button.setGraphic(imageView);

            root.getChildren().add(button);
        }
    }

    public void cornerButton(Group root) throws FileNotFoundException {
        if (!clickedSingleBetButton) {
            Button button2 = new Button();
            button2.setTranslateX(350);
            button2.setTranslateY(625);
            button2.setPadding(Insets.EMPTY);

            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Rectangle r = new Rectangle(350, 625, 100, 40);
                    r.setOpacity(0.5);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    root.getChildren().add(r);
                    buttonClickColor(root, r);

                    IntStream.range(0, 1).forEach(//automatically clicks button "next"
                            i -> next.fire()
                    );
                }
            });

            Image image2 = new Image(new FileInputStream("src/Resources/Corner.png"), 2000, 2000, true, true);
            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitWidth(100);
            imageView2.setFitHeight(40);
            button2.setGraphic(imageView2);

            root.getChildren().add(button2);
        }
    }

    public void streetButton(Group root) throws FileNotFoundException {
        if (!clickedSingleBetButton) {
            Button button2 = new Button();
            button2.setTranslateX(500);
            button2.setTranslateY(625);
            button2.setPadding(Insets.EMPTY);

            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Rectangle r = new Rectangle(500, 625, 100, 40);
                    r.setOpacity(0.5);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    root.getChildren().add(r);
                    buttonClickColor(root, r);

                    IntStream.range(0, 1).forEach(//automatically clicks button "next"
                            i -> next.fire()
                    );
                }
            });

            Image image2 = new Image(new FileInputStream("src/Resources/Street.png"), 2000, 2000, true, true);
            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitWidth(100);
            imageView2.setFitHeight(40);
            button2.setGraphic(imageView2);

            root.getChildren().add(button2);
        }
    }

    public void splitButton(Group root) throws FileNotFoundException {
        if (!clickedSingleBetButton) {
            Button button2 = new Button();
            button2.setTranslateX(650);
            button2.setTranslateY(625);
            button2.setPadding(Insets.EMPTY);

            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Rectangle r = new Rectangle(650, 625, 100, 40);
                    r.setOpacity(0.5);
                    r.setArcHeight(10);
                    r.setArcWidth(10);
                    root.getChildren().add(r);
                    buttonClickColor(root, r);

                    IntStream.range(0, 1).forEach(//automatically clicks button "next"
                            i -> next.fire()
                    );
                }
            });

            Image image2 = new Image(new FileInputStream("src/Resources/Split.png"), 2000, 2000, true, true);
            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitWidth(100);
            imageView2.setFitHeight(40);
            button2.setGraphic(imageView2);

            root.getChildren().add(button2);
        }
    }

    public void doneButton(Group root) throws FileNotFoundException {
        Button button2 = new Button();
        button2.setTranslateX(1000);
        button2.setTranslateY(625);
        button2.setPadding(Insets.EMPTY);

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Rectangle r = new Rectangle(1000, 625, 200, 50);
                r.setArcHeight(10);
                r.setArcWidth(10);
                r.setOpacity(0.5);
                root.getChildren().add(r);
                buttonClickColor(root, r);
                clickedDoneBotton = true;

                IntStream.range(0, 1).forEach(//automatically clicks button "next"
                        i -> next.fire()
                );
            }
        });

        Image image2 = new Image(new FileInputStream("src/Resources/DoneButton.png"), 2000, 2000, true, true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(200);
        imageView2.setFitHeight(50);
        button2.setGraphic(imageView2);

        root.getChildren().add(button2);
    }

    public void buttonClickColor(Group root, Rectangle r) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                r.setOpacity(0);
            }
        },
                300
        );
    }

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

    public void drawWheel(Group root, int angle) throws FileNotFoundException, InterruptedException {
        Image image = new Image(new FileInputStream("src/Resources/Wheel.png"), 2000, 2000, true, true);
        ImagePattern ip = new ImagePattern(image);
        wheel = new Circle(1425, 275, 200);
        wheel.setFill(Color.GREY);
        wheel.setFill(ip);
        root.getChildren().add(wheel);

    }

    public void rotateWheel(Group root, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(5000), wheel);
        rt.setByAngle(angle);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.play();

        int resetAng = 360 - angle % 360;
        RotateTransition reset = new RotateTransition(Duration.millis(1000), wheel);
        reset.setByAngle(resetAng);
        reset.setCycleCount(1);
        reset.setAutoReverse(false);

        rt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("That sucks");
                }
                reset.play();
            }
        });
    }

    public void drawPlayers(Group root) throws FileNotFoundException {
        int xPos = 150;
        for (int i = 0; i < players.size(); i++) {
            Rectangle r = new Rectangle(xPos, 750, 300, 220);
            r.setFill(Color.WHITE);
            r.setOpacity(0.5);
            r.setArcWidth(20);
            r.setArcHeight(20);
            root.getChildren().add(r);
            playerSquares.add(r);

            Font f = new Font(30);
            Text t = new Text(xPos + 15, 800, players.get(i).getName());
            t.setFill(Color.WHITE);
            t.setFont(f);
            root.getChildren().add(t);
            playerSquares.add(t);

            Image image = new Image(new FileInputStream("src/Resources/Chip1.png"), 2000, 2000, true, true);
            ImagePattern ip = new ImagePattern(image);
            Circle c = new Circle(xPos + 75, 890, 60);
            c.setFill(ip);
            root.getChildren().add(c);
            playerSquares.add(c);

            Font f2 = new Font(45);
            Text chips = new Text(xPos + 145, 900, Integer.toString(players.get(i).getChips()));
            chips.setFill(Color.WHITE);
            chips.setFont(f2);
            root.getChildren().add(chips);
            playerSquares.add(chips);

            Rectangle cover = new Rectangle(xPos, 750, 300, 220);
            cover.setFill(Color.GREY);
            cover.setOpacity(0.5);
            cover.setArcWidth(20);
            cover.setArcHeight(20);
            root.getChildren().add(cover);
            playerSquares.add(cover);
            playerSquareCovers.add(cover);

            xPos += 350;
        }
        playerSquareCovers.get(0).setOpacity(0);
    }

    public void movePlayers(Group root, int xLoc) {
        for (int i = 0; i < playerSquares.size(); i++) {
            TranslateTransition t = new TranslateTransition(new Duration(3000), (Node) playerSquares.get(i));
            t.setToX(xLoc);
            t.play();
        }
    }

    public void flyingChip(Group root, Stage primaryStage, Scene scene) throws FileNotFoundException {
        Cylinder cl = new Cylinder(100, 15);
        cl.setTranslateX(500);
        cl.setTranslateY(500);
        cl.setTranslateZ(5000);

        PerspectiveCamera camera = new PerspectiveCamera(false);
        scene.setCamera(camera);

        cl.setRotationAxis(Rotate.X_AXIS);
        cl.setRotate(90);

        root.getChildren().add(cl);

        PhongMaterial m = new PhongMaterial();
        Image image = new Image(new FileInputStream("src/Resources/Chip1.png"), 5000, 5000, true, true);

        m.setDiffuseMap(image);
        cl.setMaterial(m);

        TranslateTransition t = new TranslateTransition(new Duration(3500), cl);
        t.setToZ(-5000);
        t.play();
        TranslateTransition t2 = new TranslateTransition(new Duration(3500), cl);
        t2.setToX(1000);
        t2.play();

        RotateTransition r = new RotateTransition(new Duration(3000), cl);
        r.setByAngle(3000);
        r.setAxis(new Point3D(20, 20, 20));
        r.play();
    }


}
