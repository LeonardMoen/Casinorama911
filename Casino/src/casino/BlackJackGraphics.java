package casino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackJackGraphics {

    public static Player currentPlayer;
    private static Button stay = new Button("Stay");
    private static Button dDown = new Button("Double Down");
    private static Button split = new Button("Split");
    private static Button hit = new Button("Hit");
    private static Button btnBet;
    private static Group root = new Group();
    private static int round = 1, bet, x = 0;
    private static Pane pCard = new Pane();
    private static Pane pBet = new Pane();
    private static Pane bCard = new Pane();

    public BlackJackGraphics() {
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

    public static void printCard(int handNum) throws InterruptedException, IOException {
        int x = 300, y = 500;
        pCard.getChildren().clear();
        root.getChildren().remove(pCard);
        for (int i = 0; i < currentPlayer.getPocketHands().get(handNum).getPlayerHand().size(); i++) {
            Card card = currentPlayer.getPocketHands().get(handNum).getPlayerHand().get(i);
            if (!card.isFaceUp()) {
                System.out.println("flipped");
                card.setFaceUp(true);
            }
            card.setX(x);
            card.setY(y);
            pCard.getChildren().add(displayCard(card));
            x += 100;
        }
        root.getChildren().add(pCard);
        setButtons(handNum);
    }

    public static void setButtons(int handNum) throws InterruptedException, IOException {
        if (root.getChildren().contains(hit)) {
            root.getChildren().remove(hit);
        }
        if (root.getChildren().contains(stay)) {
            root.getChildren().removeAll(stay);
        }
        if (root.getChildren().contains(split)) {
            root.getChildren().remove(split);
        }
        if (root.getChildren().contains(dDown)) {
            root.getChildren().remove(dDown);
        }
        if (!currentPlayer.isStay()) {
            if (currentPlayer.isAi()) {
                BlackjackAI ai = (BlackjackAI) currentPlayer;
                ai.setSplit();
                ai.setdDown(handNum);
                ai.setHit(BlackjackJAVA.deck, handNum);
                if (round == 1) {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setNaturalBlackJack(true);
                        currentPlayer.setStay(true);
                        currentPlayer.setChips((int) (currentPlayer.getBet() * 1.5 + currentPlayer.getChips() + currentPlayer.getBet()));
                        printCard(handNum);
                    }
                    if (ai.isSplit()) {
                        System.out.println("Split");
                        BlackjackJAVA.playerSplit(currentPlayer, 0);
                    } else if (ai.isdDown()) {
                        System.out.println("dd");
                        BlackjackJAVA.playerDD(currentPlayer, 0);
                    } else if (ai.isHit()) {
                        System.out.println("hit");
                        BlackjackJAVA.playerHit(currentPlayer, 0);
                    } else if (!ai.isHit()) {
                        System.out.println("stay");
                        currentPlayer.setStay(true);
                        setButtons(0);
                    }
                } else {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setStay(true);
                        printCard(handNum);
                    } else if (ai.isHit()) {
                        System.out.println("hit");
                        BlackjackJAVA.playerHit(currentPlayer, 0);
                    } else if (!ai.isHit()) {
                        System.out.println("stay");
                        currentPlayer.setStay(true);
                        setButtons(0);
                    }
                }
            } else {
                if (round == 1) {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setNaturalBlackJack(true);
                        currentPlayer.setStay(true);
                        currentPlayer.setChips((int) (currentPlayer.getBet() * 1.5 + currentPlayer.getChips() + currentPlayer.getBet()));
                        Thread.sleep(1000);
                        printCard(handNum);
                    } else if (currentPlayer.getTotal(handNum) >= 9 && currentPlayer.getTotal(handNum) <= 11) {
                        if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                            root.getChildren().addAll(hit, stay, split, dDown);
                        } else {
                            root.getChildren().addAll(hit, stay, dDown);
                        }
                    } else if (currentPlayer.getPocketHands().get(0).checkSplit()) {
                        root.getChildren().addAll(hit, stay, split);
                    } else {
                        root.getChildren().addAll(hit, stay);
                    }
                } else {
                    if (currentPlayer.getPocketHands().get(handNum).checkBlackJack() || currentPlayer.setTotal(handNum) == 21) {
                        currentPlayer.setStay(true);
                        Thread.sleep(1000);
                        printCard(handNum);
                    } else {
                        root.getChildren().addAll(hit, stay);
                    }
                }
            }
        } else if (currentPlayer.isSplit()) {
            if (handNum == 0) {
                currentPlayer.setStay(false);
                printCard(1);
            } else {
                if (root.getChildren().contains(hit)) {
                    root.getChildren().remove(hit);
                }
                if (root.getChildren().contains(stay)) {
                    root.getChildren().removeAll(stay);
                }
                if (root.getChildren().contains(split)) {
                    root.getChildren().remove(split);
                }
                if (root.getChildren().contains(dDown)) {
                    root.getChildren().remove(dDown);
                }
                nextPlayer();
            }
        } else {
            if (root.getChildren().contains(hit)) {
                root.getChildren().remove(hit);
            }
            if (root.getChildren().contains(stay)) {
                root.getChildren().removeAll(stay);
            }
            if (root.getChildren().contains(split)) {
                root.getChildren().remove(split);
            }
            if (root.getChildren().contains(dDown)) {
                root.getChildren().remove(dDown);
            }
            nextPlayer();
        }

        round++;
    }

    public static void checkWin() {
    }

    public static void nextPlayer() throws InterruptedException, IOException {
        round = 1;
        if (x >= BlackjackJAVA.numOfPlayers.size() - 1) {
            checkWin();
        } else {
            currentPlayer = BlackjackJAVA.numOfPlayers.get(x + 1);
            printCard(0);
            x += 1;
        }
    }

    public static void begin(String name) throws IOException, InterruptedException {
        hit.setLayoutX(10);
        hit.setLayoutY(10);
        stay.setLayoutX(10);
        stay.setLayoutY(40);
        split.setLayoutX(10);
        split.setLayoutY(70);
        dDown.setLayoutX(10);
        dDown.setLayoutY(100);
        hit.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerHit(currentPlayer, 0);

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        stay.setOnAction((ActionEvent event) -> {
            currentPlayer.setStay(true);
            try {
                setButtons(0);

            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        split.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerSplit(currentPlayer, 0);

            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        dDown.setOnAction((ActionEvent event) -> {
            try {
                BlackjackJAVA.playerDD(currentPlayer, 0);
                currentPlayer.setStay(true);
                setButtons(0);

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(BlackJackGraphics.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
        Scene scene = new Scene(root, 1920, 1080);
        Casino.primaryStage.setTitle("BLACKJACK!");
        Casino.primaryStage.setScene(scene);
        Casino.primaryStage.show();
        BlackjackJAVA.main(name);
    }

    public static void printBoard() throws InterruptedException, IOException {    // When I make the board. need to print the players cards
        bCard.getChildren().clear();
        if (root.getChildren().contains(bCard)) {
            root.getChildren().remove(bCard);
        }
        int lX = 150, y = 50;
        for (int c = 0; c < BlackjackJAVA.numOfPlayers.size(); c++) {
            for (int i = 0; i < BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().size(); i++) {
                Card card = BlackjackJAVA.numOfPlayers.get(c).getPocketHands().get(0).getPlayerHand().get(i);
                if (!card.isFaceUp()) {
                    System.out.println("flipped");
                    card.setFaceUp(true);
                }
                card.setX(lX);
                card.setY(y);
                bCard.getChildren().add(displayCard(card));
                lX += 100;
            }
            y += 80;
            lX = 150;
        }
        root.getChildren().add(bCard);
        printCard(0);
    }

    public static void setBet(int n) throws IOException {
        currentPlayer = BlackjackJAVA.numOfPlayers.get(n);
        if (currentPlayer.isAi()) {
            BlackjackAI ai = (BlackjackAI) currentPlayer;
            ai.setRealBet();
            ai.setBet(ai.getRealBet());
            System.out.println(currentPlayer.getName() + " bet " + currentPlayer.getBet());
            if (n == BlackjackJAVA.numOfPlayers.size() - 1) {
                currentPlayer = BlackjackJAVA.numOfPlayers.get(0);
                try {
                    printBoard();

                } catch (InterruptedException ex) {
                    Logger.getLogger(BlackJackGraphics.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                setBet(n + 1);
            }
        } else {
            Label tfBet = new Label("Bet: ");
            TextField tf = new TextField();
            HBox hb = new HBox();
            hb.getChildren().addAll(tfBet, tf);
            hb.setSpacing(5);
            btnBet = new Button("Bet");
            btnBet.setLayoutX(250);
            btnBet.setLayoutY(10);
            pBet.getChildren().addAll(btnBet, hb);
            root.getChildren().addAll(pBet);
            btnBet.setOnAction((ActionEvent event) -> {
                if ((tf.getText() != null && !tf.getText().isEmpty())) {
                    if (Integer.parseInt(tf.getText()) > currentPlayer.getChips()) {
                        pBet.getChildren().clear();
                        hb.getChildren().clear();
                        root.getChildren().remove(pBet);
                        System.out.println(currentPlayer.getName() + " not enough chips");
                        try {
                            setBet(n);

                        } catch (IOException ex) {
                            Logger.getLogger(BlackJackGraphics.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        bet = Integer.parseInt(tf.getText());
                        currentPlayer.setBet(bet);
                        System.out.println(currentPlayer.getName() + " bet " + currentPlayer.getBet());
                        pBet.getChildren().clear();
                        hb.getChildren().clear();
                        root.getChildren().remove(pBet);
                        if (n == BlackjackJAVA.numOfPlayers.size() - 1) {
                            currentPlayer = BlackjackJAVA.numOfPlayers.get(0);
                            try {
                                printBoard();

                            } catch (InterruptedException | IOException ex) {
                                Logger.getLogger(BlackJackGraphics.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                setBet(n + 1);

                            } catch (IOException ex) {
                                Logger.getLogger(BlackJackGraphics.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
        }
    }

}
