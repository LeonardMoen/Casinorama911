package casino;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PokerGraphics {

    static Pane roop = new Pane();
    static Pane root = new Pane();
    static Pane rootPane = new Pane();

    //etc on table
    static Pane btns = new Pane();
    static Pane burnPile = new Pane();
    static HBox commCards = new HBox();

    static Random rand = new Random();

    static ArrayList<Card> communityCards = new ArrayList<Card>();

    //positions
    public static double deckX = 850 - 63, deckY1 = 295, deckY2 = 295 + 7,
            flopX = 430, flopY = 295, burnX = 860,
            cardScale = 0.6,
            p1x = 580, p1y = 450,
            p2x = 330, p2y = 450,
            p3x = 150, p3y = 295,
            p4x = 330, p4y = 140,
            p5x = 580, p5y = 140,
            p6x = 830, p6y = 140,
            p7x = 830 + 180, p7y = 295,
            p8x = 830, p8y = 450;

    ImagePattern ip = null;

    public PokerGraphics() {

        //setting up position of players

        ip = new ImagePattern(ImageBuffer.pokerTable);
        ImageView pTable = new ImageView();
        pTable.setImage(ImageBuffer.pokerTable);
        pTable.setFitHeight(768);
        pTable.setFitWidth(1366);
        pTable.setX(-40);
        pTable.setY(-80);
        Scene pokerScene = new Scene(rootPane, 1920, 1080);
        rootPane.getChildren().add(pTable);
        Casino.primaryStage.setScene(pokerScene);
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 1; i <= 8; i++) {
            String name = "Player " + i;
            players.add(new Player(name, i));
        }
        
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                players.get(i).setAi(false);
                players.get(i).setBlind(new Blind(0, "small"));
            } else if (i == 1) {
                players.get(i).setBlind(new Blind(0, "big"));
                players.get(i).setAi(true);
            } else {
                players.get(i).setAi(true);
            }
        }

        for (Player player : players) {
            addPlayerInfo(player);
        }
        for (Player player : players) {
            rootPane.getChildren().add(player.getPane());
        }
        
        
        Deck deck = new Deck();
        displayDeck(deck);
        deck.shuffle();
        dealPlayers(players, deck);
        displayDealPlayers(players);
        displayAllCards(players);
        displayBurn(deck);
        cCards(deck);
        displayFlop(communityCards);
        displayTurn(communityCards);
        displayRiver(communityCards);
        //displayShuffle(deck);


        Scene menu = new Scene(roop, 1920, 1080);
        rootPane.getChildren().add(btns);

        Font game = new Font("Times New Roman", 35);
        Font f = new Font("Times New Roman", 16);

        VBox pokerBtns = new VBox();
//
//        //poker scene
        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> Casino.primaryStage.setScene(menu));
        rootPane.getChildren().add(backBtn);
//
        deck.getdPane().setTranslateX(deckX);
        deck.getdPane().setTranslateY(deckY1);
        commCards.setTranslateX(flopX);
        commCards.setTranslateY(flopY);
        burnPile.setTranslateX(burnX);
        burnPile.setTranslateY(deckY1);
        rootPane.getChildren().addAll(burnPile, commCards, deck.getdPane());
        //<editor-fold defaultstate="collapsed" desc="displaying all cards and buttons">

        //button call
        Button btnCall = new Button();
        btnCall.setText("Call");
        btnCall.setFont(f);
        btnCall.setMinSize(70, 70);
        btnCall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Call");
            }
        });
        pokerBtns.getChildren().add(btnCall);

        //button raise
        Button btnRaise = new Button();
        btnRaise.setText("Bet");
        btnRaise.setFont(f);
        btnRaise.setMinSize(70, 70);
        btnRaise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Bet");
            }
        });
        pokerBtns.getChildren().add(btnRaise);

        //button fold
        Button btnFold = new Button();
        btnFold.setText("Fold");
        btnFold.setFont(f);
        btnFold.setMinSize(70, 70);
        btnFold.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Fold");
            }
        });
        pokerBtns.getChildren().add(btnFold);

        rootPane.getChildren().add(pokerBtns);

        //button check
        Button btnCheck = new Button();
        btnCheck.setText("Check");
        btnCheck.setFont(f);
        btnCheck.setMinSize(70, 70);
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Check");
            }
        });
        pokerBtns.setTranslateY(370);
        pokerBtns.getChildren().add(btnCheck);
    }

    public static void addPlayerInfo(Player player) {
        String blind;

        Pane playerInfo = new Pane();

        VBox vbox = new VBox();
//        vbox.setPadding(new Insets(5));
        vbox.setSpacing(0);

        //setting blind names
        if (player.getBlind().getTypeBlind().equalsIgnoreCase("big")) {
            blind = "Big Blind";
        } else if (player.getBlind().getTypeBlind().equalsIgnoreCase("small")) {
            blind = "Small Blind";
        } else {
            blind = "";
        }

        Rectangle infoBck = new Rectangle(140, 70);
        infoBck.setFill(Color.rgb(255, 255, 255, 0.2));
        infoBck.setArcHeight(25);
        infoBck.setArcWidth(25);
        infoBck.setX(-7);
        infoBck.setY(-4);

        if (player.getPlayerNum() == 1 || player.getPlayerNum() == 2 || player.getPlayerNum() == 8) { //places info on bottom
            playerInfo.setTranslateY(95);
        } else if (player.getPlayerNum() == 3) { //places info on leftside
            playerInfo.setTranslateX(-80);
            playerInfo.setTranslateY(-50);
        } else if (player.getPlayerNum() == 7) { //places info on rightside
            playerInfo.setTranslateX(80);
            playerInfo.setTranslateY(-50);
        } else if (player.getPlayerNum() == 4 || player.getPlayerNum() == 5 || player.getPlayerNum() == 6) { //places info on bottom
            playerInfo.setTranslateY(-80);
        }

        Text title = new Text(player.getName());
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Text options[] = new Text[]{
            new Text("Chips: " + player.getChips()),
            new Text("Bet: " + player.getBet()),
            new Text(blind)};

        for (int i = 0; i < 3; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, -2, 5));
            vbox.getChildren().add(options[i]);
        }
        playerInfo.getChildren().addAll(infoBck, vbox);
        player.getPane().getChildren().add(playerInfo);
    }

    public static Card displayCard(Card card) {
        Image image = null;
        ImagePattern ip = null;
        Pane cPane = new Pane();

        String cardSuit = card.getSuit();
        int cardValue = card.getValue();

        if (card.isFaceUp() == false) {
            image = ImageBuffer.back1;
        } else {
            card.setFaceUp(true);
            //<editor-fold defaultstate="collapsed" desc="Setting the image for each suit/value">
            if (cardSuit.equalsIgnoreCase("Spade")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.spadeA;
                        break;
                    case 2:
                        image = ImageBuffer.spade2;
                        break;
                    case 3:
                        image = ImageBuffer.spade3;
                        break;
                    case 4:
                        image = ImageBuffer.spade4;
                        break;
                    case 5:
                        image = ImageBuffer.spade5;
                        break;
                    case 6:
                        image = ImageBuffer.spade6;
                        break;
                    case 7:
                        image = ImageBuffer.spade7;
                        break;
                    case 8:
                        image = ImageBuffer.spade8;
                        break;
                    case 9:
                        image = ImageBuffer.spade9;
                        break;
                    case 10:
                        image = ImageBuffer.spade10;
                        break;
                    case 11:
                        image = ImageBuffer.spadeJ;
                        break;
                    case 12:
                        image = ImageBuffer.spadeQ;
                        break;
                    case 13:
                        image = ImageBuffer.spadeK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Club")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.clubsA;
                        break;
                    case 2:
                        image = ImageBuffer.clubs2;
                        break;
                    case 3:
                        image = ImageBuffer.clubs3;
                        break;
                    case 4:
                        image = ImageBuffer.clubs4;
                        break;
                    case 5:
                        image = ImageBuffer.clubs5;
                        break;
                    case 6:
                        image = ImageBuffer.clubs6;
                        break;
                    case 7:
                        image = ImageBuffer.clubs7;
                        break;
                    case 8:
                        image = ImageBuffer.clubs8;
                        break;
                    case 9:
                        image = ImageBuffer.clubs9;
                        break;
                    case 10:
                        image = ImageBuffer.clubs10;
                        break;
                    case 11:
                        image = ImageBuffer.clubsJ;
                        break;
                    case 12:
                        image = ImageBuffer.clubsQ;
                        break;
                    case 13:
                        image = ImageBuffer.clubsK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Diamond")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.diamondA;
                        break;
                    case 2:
                        image = ImageBuffer.diamond2;
                        break;
                    case 3:
                        image = ImageBuffer.diamond3;
                        break;
                    case 4:
                        image = ImageBuffer.diamond4;
                        break;
                    case 5:
                        image = ImageBuffer.diamond5;
                        break;
                    case 6:
                        image = ImageBuffer.diamond6;
                        break;
                    case 7:
                        image = ImageBuffer.diamond7;
                        break;
                    case 8:
                        image = ImageBuffer.diamond8;
                        break;
                    case 9:
                        image = ImageBuffer.diamond9;
                        break;
                    case 10:
                        image = ImageBuffer.diamond10;
                        break;
                    case 11:
                        image = ImageBuffer.diamondJ;
                        break;
                    case 12:
                        image = ImageBuffer.diamondQ;
                        break;
                    case 13:
                        image = ImageBuffer.diamondK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else if (cardSuit.equalsIgnoreCase("Heart")) {
                switch (cardValue) {
                    case 1:
                        image = ImageBuffer.heartA;
                        break;
                    case 2:
                        image = ImageBuffer.heart2;
                        break;
                    case 3:
                        image = ImageBuffer.heart3;
                        break;
                    case 4:
                        image = ImageBuffer.heart4;
                        break;
                    case 5:
                        image = ImageBuffer.heart5;
                        break;
                    case 6:
                        image = ImageBuffer.heart6;
                        break;
                    case 7:
                        image = ImageBuffer.heart7;
                        break;
                    case 8:
                        image = ImageBuffer.heart8;
                        break;
                    case 9:
                        image = ImageBuffer.heart9;
                        break;
                    case 10:
                        image = ImageBuffer.heart10;
                        break;
                    case 11:
                        image = ImageBuffer.heartJ;
                        break;
                    case 12:
                        image = ImageBuffer.heartQ;
                        break;
                    case 13:
                        image = ImageBuffer.heartK;
                        break;
                    default:
                        image = ImageBuffer.blank;
                        break;
                }
            } else {
                image = ImageBuffer.blank;
            }
//</editor-fold>
        }
        ip = new ImagePattern(image);
        //card.setIp(ip);
        card.setFill(ip);

        return card;
    }

    public static void dealCard(double x, double y, Card card, Player player) {
        Pane pane = new Pane();

        Path path = new Path();
        path.getElements().add(new MoveTo(card.getX(), card.getY()));
        path.getElements().add(new LineTo(x, y));

        PathTransition pt = new PathTransition();
        pt.setNode(card);
        pt.setPath(path);
        pt.setDuration(Duration.seconds(1));
        pt.play();

        player.getPane().getChildren().add(pane);
    }

    public static void displayShuffle(Deck deck) {
        ImagePattern ip = new ImagePattern(ImageBuffer.back1);
        deck.getdPane().getChildren().clear();
        for (int i = 0; i < deck.getDeck().size() - 1; i++) {
            Card card = deck.getDeck().get(i);
            card.setFill(ip);
            Path cpath = new Path();
            cpath.getElements().add(new MoveTo(0, 0));
            cpath.getElements().add(new CubicCurveTo(-rand.nextInt(500) + 100, -rand.nextInt(500) + 200, -rand.nextInt(500) + 200, rand.nextInt(200) + 100, rand.nextInt(500) + 100, rand.nextInt(200) + 100));
            cpath.getElements().add(new CubicCurveTo(rand.nextInt(500) + 100, rand.nextInt(500) + 200, rand.nextInt(500) + 200, -rand.nextInt(200) + 100, 0, 0));
            cpath.getElements().add(new MoveTo(0, 0));

            PathTransition cPT = new PathTransition();
            cPT.setNode(card);
            cPT.setPath(cpath);
            cPT.setDuration(Duration.seconds(2));
            cPT.play();
            deck.getdPane().getChildren().add(card);
            cPT.setDelay(Duration.seconds(2));
        }
        displayDeck(deck);
    }

    public static void displayDeck(Deck deck) {
        deck.getdPane().getChildren().clear();
        ImagePattern ip = new ImagePattern(ImageBuffer.back1);
        for (int i = 0; i < deck.getDeck().size() - 1; i++) {
            Card card = deck.getDeck().get(i);
            card.setX(0);
            card.setY(0 - (i * 0.25));
            deckY2 = deckY1 - (i * 0.25);
            deck.getdPane().getChildren().add(displayCard(card));
        }
    }

    public static void displayBurn(Deck deck) {
        Card card = deck.getDeck().get(0);
        card.setFaceUp(false);
        burnPile.getChildren().add(displayCard(card));
    }

    public static void displayFlop(ArrayList<Card> communityCards) {
        for (int i = 0; i < 3; i++) {
            Card card = communityCards.get(i);
            card.setFaceUp(true);
            commCards.getChildren().add(displayCard(card));
        }
    }

    public static void displayTurn(ArrayList<Card> communityCards) {
        Card card = communityCards.get(3);
        card.setFaceUp(true);
        commCards.getChildren().add(displayCard(card));
    }

    public static void displayRiver(ArrayList<Card> communityCards) {
        Card card = communityCards.get(4);
        card.setFaceUp(true);
        commCards.getChildren().add(displayCard(card));
    }

    public static void displayAllCards(ArrayList<Player> playersInRound) {
        for (Player player : playersInRound) {
            HBox pocketCards = new HBox();
            player.getPane().getChildren().clear();
            addPlayerInfo(player);
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);
                if (!card.isFaceUp()) {
                    card.setFaceUp(true);
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
    }

    public static void cCards(Deck deck) {
        communityCards.clear();
        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.getDeck().get(0));
            deck.getDeck().remove(0);
        }
    }

    public static void displayFold(Player player) {
        player.getPane().getChildren().clear();
        addPlayerInfo(player);

//            Path path = new Path();
//            path.getElements().add(new MoveTo(card.getX(), card.getY()));
//            path.getElements().add(new LineTo(burnX, deckY1));
//
//            PathTransition pt = new PathTransition();
//            pt.setNode(card);
//            pt.setPath(path);
//            pt.setDuration(Duration.seconds(1));
//            pt.play();
//            root.getChildren().add(card);
    }

    public static void displayDealPlayers(ArrayList<Player> players) {
        Image image = null;
        double cardScale = .6;

        for (Player player : players) {
            HBox pocketCards = new HBox();
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);

                //is it computer or person to hide cards or not to hide cards
                if (player.isAi()) {
                    card.setFaceUp(false);
                    cardScale = 0.6;
                    card.setScaleX(cardScale);
                    card.setScaleY(cardScale);
                } else {
                    card.setFaceUp(true);
                    cardScale = 1;
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
    }

    public static void dealPlayers(ArrayList<Player> players, Deck deck) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getPocketHand().getPocketHand().add(deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
        }
    }

}
