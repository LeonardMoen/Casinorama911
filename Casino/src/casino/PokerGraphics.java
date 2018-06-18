package casino;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PokerGraphics {
    
    static Pane rootPane;

    //etc on table
    static Pane pokerBtns = new VBox(2);
    static Pane burnPile = new Pane();
    static HBox commCards = new HBox();
    static Pane potPane = new Pane();
    
    static Random rand = new Random();
    static int raiseAmount;
    //positions
    final public static double deckX = 850 - 40, deckY = Player.middleY,
            flopX = 430 + 40, flopY = Player.middleY, burnX = 860 + 40, potX = 300 + 30, potY = 320,
            cardScale = 0.7,
            chipSize = 17;
    
    ImagePattern ip = null;
    
    public PokerGraphics() {
        
    }
    
    public static void pokerSetUp() {
        rootPane = new Pane();
        ImagePattern ip = new ImagePattern(ImageBuffer.pokerTable);
        ImageView pTable = new ImageView();
        pTable.setImage(ImageBuffer.pokerTable);
        pTable.setFitHeight(768);
        pTable.setFitWidth(1366);
        pTable.setX(-40);
        pTable.setY(-80);
        Scene pokerScene = new Scene(rootPane, 1366, 768);
        rootPane.getChildren().add(pTable);

        //poker scene back button
        Button backBtn = new Button();
        backBtn.setText("Back");
        backBtn.setOnAction(e -> Casino.primaryStage.setScene(Casino.menu));
        rootPane.getChildren().add(backBtn);
        
        Casino.primaryStage.setScene(pokerScene);
        Poker poker = new Poker();
        ArrayList<Player> players = Poker.getPlayers();
        for (Player player : Poker.getAllPlayers()) {
            addPlayerInfo(player);
            rootPane.getChildren().add(player.getPane());
        }
        //displayShuffle(deck);
        Poker.playPoker();
        
        Font game = new Font("Times New Roman", 35);
        Font f = new Font("Times New Roman", 16);

        //positioning the panes
        Poker.getDeck().getdPane().setTranslateX(deckX);
        Poker.getDeck().getdPane().setTranslateY(deckY);
        commCards.setTranslateX(flopX);
        commCards.setTranslateY(flopY);
        burnPile.setTranslateX(burnX);
        burnPile.setTranslateY(deckY);
        potPane.setTranslateX(potX);
        potPane.setTranslateY(potY);
        rootPane.getChildren().add(potPane);
        rootPane.getChildren().add(commCards);
    }
    
    public void createButtons() {
        pokerBtns = new VBox();
        Font f = new Font("Times New Roman", 16);
        rootPane.getChildren().add(pokerBtns);
        //button call
        Button btnCall = new Button();
        btnCall.setText("Call");
        btnCall.setFont(f);
        btnCall.setMinSize(70, 70);
        btnCall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    if (Poker.getCurrentPlayer().getChipsInCurrent() != requiredChips) {
                        System.out.println("Call");
                        Poker.call(Poker.getCurrentPlayer(), requiredChips);
                        int playerIndex = Poker.getPlayers().indexOf(Poker.getCurrentPlayer());
                        Poker.determiningNextAction(playerIndex);
                    } else {
                        System.out.println("Anton resembelance");
                    }
                }
            }
        });

        //button raise
        Pane raisePane = new HBox();
        HBox hb = new HBox();
        Button btnRaise = new Button();
        btnRaise.setText("Bet");
        btnRaise.setFont(f);
        btnRaise.setMinSize(70, 70);
        raisePane.getChildren().add(btnRaise);
        btnRaise.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    raiseAmount = 0;
                    createChips(hb);
                }
            }
        });
        raisePane.getChildren().add(hb);

        //button fold
        Button btnFold = new Button();
        btnFold.setText("Fold");
        btnFold.setFont(f);
        btnFold.setMinSize(70, 70);
        btnFold.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    ArrayList<Player> players = Poker.getPlayers();
                    System.out.println("Fold");
                    PokerGraphics.displayFold(Poker.getCurrentPlayer());
                    int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                    Poker.getPlayers().remove(Poker.getCurrentPlayer());
                    Poker.determiningNextAction(playerIndex);
                }
            }
        });

        //button check
        Button btnCheck = new Button();
        btnCheck.setText("Check");
        btnCheck.setFont(f);
        btnCheck.setMinSize(70, 70);
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    if (requiredChips == Poker.getCurrentPlayer().getChipsInCurrent()) {
                        System.out.println("Check");
                        int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                        Poker.determiningNextAction(playerIndex);
                    } else {
                        System.out.println("Anton resembelance");
                    }
                }
            }
        });
        pokerBtns.setTranslateY(370);
        pokerBtns.getChildren().addAll(btnCheck, btnCall, btnFold, raisePane);
        //the raising buttons
        //Pane betPane = new HBox();
        //button raise

        //betPane.getChildren().add(btnRaise);
    }
    
    public void createChips(HBox raisePane) {
        System.out.println("raise");
        //button raise
        Font f = new Font("Times New Roman", 16);
        //betPane.getChildren().add(btnRaise);
        //the chip buttons for raising
        double x = chipSize, y = chipSize;
        ip = null;
        
        Pane betChips = new Pane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                Circle c = new Circle(x + (j * chipSize * 2), y + (i * chipSize * 2), chipSize);
                c.setOnMouseEntered(ChipOnMouseEntered);
                c.setOnMouseExited(ChipOnMouseExited);
                if (i == 0) {
                    switch (j) {
                        case 0:
                            if (Poker.getPlayers().get(0).getChips() >= 1000) {
                                ip = new ImagePattern(ImageBuffer.chip1000);
                                c.setOnMouseClicked(chip1000OnClickAction);
                            }
                            break;
                        case 1:
                            if (Poker.getPlayers().get(0).getChips() >= 500) {
                                ip = new ImagePattern(ImageBuffer.chip500);
                                c.setOnMouseClicked(chip500OnClickAction);
                            }
                            break;
                        case 2:
                            if (Poker.getPlayers().get(0).getChips() >= 100) {
                                ip = new ImagePattern(ImageBuffer.chip100);
                                c.setOnMouseClicked(chip100OnClickAction);
                            }
                            break;
                        case 3:
                            if (Poker.getPlayers().get(0).getChips() >= 50) {
                                ip = new ImagePattern(ImageBuffer.chip50);
                                c.setOnMouseClicked(chip50OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                } else if (i == 1) {
                    switch (j) {
                        case 0:
                            if (Poker.getPlayers().get(0).getChips() >= 25) {
                                ip = new ImagePattern(ImageBuffer.chip25);
                                c.setOnMouseClicked(chip25OnClickAction);
                            }
                            break;
                        case 1:
                            if (Poker.getPlayers().get(0).getChips() >= 10) {
                                
                                ip = new ImagePattern(ImageBuffer.chip10);
                                c.setOnMouseClicked(chip10OnClickAction);
                            }
                            break;
                        case 2:
                            if (Poker.getPlayers().get(0).getChips() >= 5) {
                                
                                ip = new ImagePattern(ImageBuffer.chip5);
                                c.setOnMouseClicked(chip5OnClickAction);
                            }
                            break;
                        case 3:
                            if (Poker.getPlayers().get(0).getChips() >= 1) {
                                
                                ip = new ImagePattern(ImageBuffer.chip1);
                                c.setOnMouseClicked(chip1OnClickAction);
                            }
                            break;
                        default:
                            break;
                    }
                }
                c.setFill(ip);
                betChips.getChildren().add(c);
            }
        }
        
        raisePane.getChildren().add(betChips);
        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setFont(f);
        confirm.setMinSize(70, 70);
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    int requiredChips = Poker.getRequiredChips();
                    Poker.call(Poker.getCurrentPlayer(), requiredChips);
                    if (raiseAmount <= Poker.getCurrentPlayer().getChips()) {
                        Poker.raise(Poker.getCurrentPlayer(), requiredChips, raiseAmount);
                        Poker.getCurrentPlayer().setChipsInCurrent(Poker.getCurrentPlayer().getChipsInCurrent() + raiseAmount);
                        Poker.getCurrentPlayer().setChips(Poker.getCurrentPlayer().getChips() - raiseAmount);
                        Poker.getCurrentPlayer().setTotalChipsInPot(Poker.getCurrentPlayer().getTotalChipsInPot() + raiseAmount);
                    } else {
                        Poker.raise(Poker.getCurrentPlayer(), requiredChips, Poker.getCurrentPlayer().getChips());
                        Poker.getCurrentPlayer().setChipsInCurrent(Poker.getCurrentPlayer().getChipsInCurrent() + Poker.getCurrentPlayer().getChips());
                        Poker.getCurrentPlayer().setChips(0);
                        Poker.getCurrentPlayer().setTotalChipsInPot(Poker.getCurrentPlayer().getTotalChipsInPot() + Poker.getCurrentPlayer().getChips());
                    }
                    Poker.requiredChips += raiseAmount;
                    int playerIndex = players.indexOf(Poker.getCurrentPlayer());
                    raisePane.getChildren().clear();
                    Poker.determiningNextAction(playerIndex);
                }
            }
        });
        raisePane.getChildren().add(confirm);
        
        Button reset = new Button();
        reset.setText("Reset");
        reset.setFont(f);
        reset.setMinSize(70, 70);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Player> players = Poker.getPlayers();
                if (!(Poker.getCurrentPlayer() instanceof AI)) {
                    raiseAmount = 0;
                }
            }
        });
        raisePane.getChildren().add(reset);
        
        Text raiseText = new Text();
        raiseText.setText("Raise: " + raiseAmount);
        raiseText.setFill(Color.WHITE);
        raisePane.setMargin(raiseText, new Insets(20, 0, 0, 50));
        raisePane.getChildren().add(raiseText);
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
        infoBck.setFill(Color.rgb(0, 0, 0, 0.3));
        infoBck.setArcHeight(25);
        infoBck.setArcWidth(25);
        infoBck.setX(-7);
        infoBck.setY(-4);
        
        if (player.getPlayerNum() == 1 || player.getPlayerNum() == 2 || player.getPlayerNum() == 8) { //places info on bottom
            playerInfo.setTranslateY(95);
        } else if (player.getPlayerNum() == 3) { //places info on leftside
            playerInfo.setTranslateX(-135);
            playerInfo.setTranslateY(0);
        } else if (player.getPlayerNum() == 7) { //places info on rightside
            playerInfo.setTranslateX(135);
            playerInfo.setTranslateY(0);
        } else if (player.getPlayerNum() == 4 || player.getPlayerNum() == 5 || player.getPlayerNum() == 6) { //places info on top
            playerInfo.setTranslateY(-70);
        }
        
        Text title = new Text(player.getName());
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        
        Text options[] = new Text[]{
            new Text("Chips: " + player.getChips()),
            new Text("Bet: " + player.getChipsInCurrent()),
            new Text(blind)};
        
        for (int i = 0; i < 3; i++) {
            options[i].setFill(Color.WHITESMOKE);
            VBox.setMargin(options[i], new Insets(0, 0, -2, 5));
            vbox.getChildren().add(options[i]);
        }
        playerInfo.getChildren().addAll(infoBck, vbox);
        player.getPane().getChildren().add(playerInfo);
    }
    
    public static Card displayCard(Card card) {
        Image image = null;
        ImagePattern ip = null;
        //Pane cPane = new Pane();

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
        deck.getdPane().setTranslateX(deckX);
        deck.getdPane().setTranslateY(deckY);
        ImagePattern ip = new ImagePattern(ImageBuffer.back1);
        for (int i = 0; i < deck.getDeck().size() - 1; i++) {
            Card card = deck.getDeck().get(i);
            card.setX(0);
            card.setY(0 - (i * 0.25));
            deck.getdPane().getChildren().add(displayCard(card));
        }
        rootPane.getChildren().add(deck.getdPane());
    }
    
    public static void displayBurn() {
        burnPile.setTranslateX(burnX);
        burnPile.setTranslateY(deckY);
        Card card = Poker.getDeck().getDeck().get(0);
        card.setFaceUp(false);
        burnPile.getChildren().add(displayCard(card));
    }
    
    public static void displayFlop(ArrayList<Card> communityCards) {
        commCards.setTranslateX(flopX);
        commCards.setTranslateY(flopY);
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
    
    public static void removeCommunityCard() {
        for (int i = commCards.getChildren().size() - 1; i >= 0; i--) {
            commCards.getChildren().remove(i);
        }
    }
    
    public static void displayAllCards(ArrayList<Player> playersInRound) {
        
        for (Player player : playersInRound) {
            HBox pocketCards = new HBox();
            player.getPane().getChildren().clear();
            addPlayerInfo(player);
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);
                if (!card.isFaceUp()) {
                    System.out.println("flipped");
                    card.setFaceUp(true);
                }
                pocketCards.getChildren().add(displayCard(card));
            }
            player.getPane().getChildren().add(pocketCards);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Poker.distributeWin();
            }
        }));
        timeline.play();
    }
    
    public static void displayFold(Player player) {
        player.getPane().getChildren().clear();
        addPlayerInfo(player);
        for (int i = 0; i < 2; i++) {
            Card card = player.getPocketHand().getPocketHand().get(i);
            Path path = new Path();
            path.getElements().add(new MoveTo(card.getX(), card.getY()));
            path.getElements().add(new LineTo(burnX, deckY));
            PathTransition pt = new PathTransition();
            pt.setNode(card);
            pt.setPath(path);
            pt.setDuration(Duration.seconds(1));
            pt.play();
        }
    }
    
    public static void displayPot() {
        potPane.getChildren().clear();
        
        int totalPot = Poker.pot, k = 0, i;
        double x = 0, y = 0;
        ImagePattern chipImage;
        Pane p1000 = new Pane();
        Pane p500 = new Pane();
        Pane p100 = new Pane();
        Pane p50 = new Pane();
        Pane p25 = new Pane();
        Pane p10 = new Pane();
        Pane p5 = new Pane();
        Pane p1 = new Pane();
        
        if (totalPot > 55000) {
            y = 100;
        }
        p25.setTranslateX(x - chipSize + 3);
        p25.setTranslateY(y);
        p10.setTranslateX(x - chipSize + (chipSize * 2) + (3 * 2));
        p10.setTranslateY(y);
        p5.setTranslateX(x - chipSize + ((chipSize * 2) * 2) + (3 * 3));
        p5.setTranslateY(y);
        p1.setTranslateX(x - chipSize + ((chipSize * 2) * 3) + (3 * 4));
        p1.setTranslateY(y);
        p1000.setTranslateX(x + 3);                            //25
        p1000.setTranslateY(y - (chipSize * 2));
        p500.setTranslateX(x + (chipSize * 2) + (3 * 2));    //10
        p500.setTranslateY(y - (chipSize * 2));
        p100.setTranslateX(x + ((chipSize * 2) * 2) + (3 * 3));   //5
        p100.setTranslateY(y - (chipSize * 2));
        p50.setTranslateX(x + ((chipSize * 2) * 3) + (3 * 4));   //1
        p50.setTranslateY(y - (chipSize * 2));
        
        Text pot = new Text(Integer.toString(Poker.pot) + " Chips");
        Rectangle bck = new Rectangle(85, 12);
        bck.setFill(Color.rgb(0, 0, 0, 0.3));
        bck.setArcHeight(15);
        bck.setArcWidth(15);
        bck.setX(x + 2);
        bck.setY(y + 20);
        
        Font f = new Font("Times New Roman", 50);
        pot.setFill(Color.WHITE);
        pot.setX(x + 10);
        pot.setY(y + 30);
        
        DropShadow highlight = new DropShadow(1, Color.BLACK);
        
        for (i = totalPot; i >= 1000; i -= 1000) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip1000);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 10 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p1000.getChildren().add(chip);
            totalPot -= 1000;
        }
        k = 0;
        for (i = totalPot; i >= 500; i -= 500) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip500);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p500.getChildren().add(chip);
            totalPot -= 500;
        }
        k = 0;
        for (i = totalPot; i >= 100; i -= 100) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip100);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p100.getChildren().add(chip);
            totalPot -= 100;
        }
        k = 0;
        for (i = totalPot; i >= 50; i -= 50) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip50);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p50.getChildren().add(chip);
            totalPot -= 50;
        }
        k = 0;
        for (i = totalPot; i >= 25; i -= 25) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip25);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p25.getChildren().add(chip);
            totalPot -= 25;
        }
        k = 0;
        for (i = totalPot; i >= 10; i -= 10) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip10);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p10.getChildren().add(chip);
            totalPot -= 10;
        }
        k = 0;
        for (i = totalPot; i >= 5; i -= 5) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip5);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p5.getChildren().add(chip);
            totalPot -= 5;
        }
        k = 0;
        for (i = totalPot; i >= 1; i -= 1) {
            k++;
            chipImage = new ImagePattern(ImageBuffer.chip1);
            Circle chip = new Circle(chipSize, chipImage);
            chip.setEffect(highlight);
            chip.setCenterY(0 - (chipSize / 8 * k));
            chip.setCenterX(0 + -rand.nextInt(2) + 1);
            p1.getChildren().add(chip);
            totalPot -= 1;
        }
        potPane.getChildren().addAll(p1000, p500, p100, p50, p25, p10, p5, p1, bck, pot);
    }
    
    public static void displayDealPlayers(ArrayList<Player> players) {
        Image image = null;
        double cardScale = 0.6;
        
        for (Player player : players) {
            HBox pocketCards = new HBox();
            for (int i = 0; i < 2; i++) {
                Card card = player.getPocketHand().getPocketHand().get(i);

                //is it computer or person to hide cards or not to hide cards
                if (player instanceof AI) {
                    card.setFaceUp(false);
                    cardScale = 1;
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
    
    EventHandler chip1OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 1) {
                source.setVisible(false);
            } else {
                raiseAmount += 1;
            }
        }
    };
    EventHandler chip5OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 5) {
                source.setVisible(false);
            } else {
                raiseAmount += 5;
            }
        }
    };
    EventHandler chip10OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 10) {
                source.setVisible(false);
            } else {
                raiseAmount += 10;
            }
        }
    };
    EventHandler chip25OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 25) {
                source.setVisible(false);
            } else {
                raiseAmount += 25;
            }
        }
    };
    EventHandler chip50OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 50) {
                source.setVisible(false);
            } else {
                raiseAmount += 50;
            }
        }
    };
    EventHandler chip100OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 100) {
                source.setVisible(false);
            } else {
                raiseAmount += 100;
            }
        }
    };
    EventHandler chip500OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 500) {
                source.setVisible(false);
            } else {
                raiseAmount += 500;
            }
        }
    };
    EventHandler chip1000OnClickAction = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            if (Poker.getCurrentPlayer().getChips() < 1000) {
                source.setVisible(false);
            } else {
                raiseAmount += 1000;
            }
        }
    };
    EventHandler ChipOnMouseEntered = new EventHandler() {
        @Override
        public void handle(Event event) {
            DropShadow highlight = new DropShadow(10, Color.GOLDENROD);
            Circle source = (Circle) event.getSource();
            source.setEffect(highlight);
        }
    };
    EventHandler ChipOnMouseExited = new EventHandler() {
        @Override
        public void handle(Event event) {
            Circle source = (Circle) event.getSource();
            source.setEffect(null);
        }
    };
}
