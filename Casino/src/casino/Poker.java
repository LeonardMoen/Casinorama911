package casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
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
import javafx.util.Duration;

public class Poker {

    static Scanner sc = new Scanner(System.in);
    static int pot = 0;
    static Pane rootPane = new Pane();
    static Pane burnPane = new Pane();
    static Pane communityCardPane = new Pane();
    static Random rand = new Random();

    public Poker() {
        playPoker();
    }

    public static void playPoker() {
        int response;
        ArrayList<Card> communityCards = new ArrayList<Card>();
        ArrayList<Card> possibleCard = new ArrayList<Card>();
        ArrayList<Player> players = new ArrayList<Player>();

        String name = "Player 1";
        players.add(new Player(name, 1));
        for (int i = 2; i < 5; i++) {
            players.add(new AI("Player " + i, i));
        }
        for (Player player : players) {
            rootPane.getChildren().add(player.getPane());
        }
        do {
            System.out.println("NEW HAND");
            Deck deck = new Deck();
            deck.shuffle();
            ArrayList<Player> playersInRound = new ArrayList<Player>();
            for (Player player : players) {
                if (player.getChips() != 0) {
                    playersInRound.add(player);
                }
            }
            for (int i = communityCards.size() - 1; i >= 0; i--) {
                communityCards.remove(communityCards.get(i));
            }
            for (Player player : playersInRound) {
                player.setTotalChipsInPot(0);
                player.setPocketHand(new PocketHand());
            }
            dealPlayers(playersInRound, deck);

            int smallBlindNum = setBlinds(playersInRound);

            for (Player player : playersInRound) {
                System.out.println(player.getName() + " " + player.getBlind().getTypeBlind() + " Chips: " + player.getChips());
                for (Card card7 : player.getPocketHand().getPocketHand()) {
                    System.out.println(card7);
                }
                System.out.println("");
            }
            for (int i = 0; i < 4; i++) {
                Collections.sort(playersInRound);
                playersInRound = roundOfBetting(i, playersInRound, smallBlindNum, communityCards);
                if (playersInRound.size() == 1) {
                    break;
                }
                for (Player player : playersInRound) {
                    player.setChipsInCurrent(0);
                }
                if (i == 0) {
                    System.out.println("Round 1");
                    deck.getDeck().remove(0);
                    for (int j = 0; j < 3; j++) {
                        communityCards.add(deck.getDeck().get(0));
                        deck.getDeck().remove(0);
                    }
                } else if (i == 3) {
                    System.out.println("Round 4");
                } else {
                    System.out.println("Round 2");
                    deck.getDeck().remove(0);
                    communityCards.add(deck.getDeck().get(0));
                    deck.getDeck().remove(0);
                }
                System.out.println("Community Cards: ");
                for (Card card7 : communityCards) {
                    System.out.println(card7);
                }
            }
            for (Player player : playersInRound) {
                player.setHand(determineHand(player.getPocketHand(), communityCards));
            }
            do {
                distributeWin(playersInRound);
            } while (pot > 0);
        } while (players.size() > 1);
        //        for(Card card7: hand.getHand()){
        //            System.out.println(card7);
        //        }
        //        System.out.println(hand.handValue());
    }

    public static Hand determineHand(PocketHand pocketHand, ArrayList<Card> communityCards) {
        ArrayList<Card> possibleCard = new ArrayList<Card>();
        for (Card communityCard : communityCards) {
            possibleCard.add(communityCard);
        }
        for (Card card : pocketHand.getPocketHand()) {
            possibleCard.add(card);
        }
        Collections.sort(possibleCard);
        Hand highestHand = new Hand();
        int numCard = 0;
        int numHand = 0;
        for (int i = 0; i < possibleCard.size(); i++) {
            for (int j = i + 1; j < possibleCard.size(); j++) {
                Hand tempHand = new Hand();
                for (int chosenCard = 0; chosenCard < possibleCard.size(); chosenCard++) {
                    if (chosenCard != i && chosenCard != j) {
                        tempHand.getHand()[numCard] = possibleCard.get(chosenCard);
                        if (tempHand.compareTo(highestHand) < 0 && numCard == 4) {
                            highestHand = tempHand;
                        }
                        numCard += 1;
                    }
                }
                numHand += 1;
                numCard = 0;
            }

        }
        return highestHand;
    }

    public static void dealPlayers(ArrayList<Player> players, Deck deck) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getPocketHand().getPocketHand().add(deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
        }
    }

    public static int setBlinds(ArrayList<Player> players) {
        int smallBlindNum = 0;
        boolean firstTurn = true;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getBlind().getTypeBlind().equals("small")) {
                if (i < players.size() - 2) {
                    players.get(i + 2).getBlind().setBlindAmount(players.get(i + 1).getBlind().getBlindAmount());
                    players.get(i + 2).getBlind().setTypeBlind(players.get(i + 1).getBlind().getTypeBlind());
                    players.get(i + 1).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(i + 1).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(i + 1).getPlayerNum();
                    pot += players.get(i + 2).getBlind().getBlindAmount();
                    pot += players.get(i + 1).getBlind().getBlindAmount();
                } else if (i == players.size() - 2) {
                    players.get(0).getBlind().setBlindAmount(players.get(i + 1).getBlind().getBlindAmount());
                    players.get(0).getBlind().setTypeBlind(players.get(i + 1).getBlind().getTypeBlind());
                    players.get(i + 1).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(i + 1).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(i + 1).getPlayerNum();
                    pot += players.get(0).getBlind().getBlindAmount();
                    pot += players.get(i + 1).getBlind().getBlindAmount();
                } else if (i == players.size() - 1) {
                    players.get(1).getBlind().setBlindAmount(players.get(0).getBlind().getBlindAmount());
                    players.get(1).getBlind().setTypeBlind(players.get(0).getBlind().getTypeBlind());
                    players.get(0).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(0).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(0).getPlayerNum();
                    pot += players.get(0).getBlind().getBlindAmount();
                    pot += players.get(1).getBlind().getBlindAmount();

                }

                firstTurn = false;
                break;
            }
        }
        if (firstTurn) {
            players.get(0).getBlind().setTypeBlind("small");
            players.get(0).getBlind().setBlindAmount(10);
            players.get(1).getBlind().setTypeBlind("big");
            players.get(1).getBlind().setBlindAmount(20);
            smallBlindNum = players.get(0).getPlayerNum();
            pot += players.get(0).getBlind().getBlindAmount();
            pot += players.get(1).getBlind().getBlindAmount();
        }
        return smallBlindNum;
    }

    public static ArrayList<Player> roundOfBetting(int round, ArrayList<Player> players, int smallBlindNum, ArrayList<Card> communityCards) {
        boolean action = true;
        int requiredChips = 0;
        int response;
        boolean raised = false;
        int startPlayer = 0;
        int bigBlind = 0;
        if (round == 0) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")) {
                    requiredChips = players.get(i).getBlind().getBlindAmount();
                    players.get(i).setChips(players.get(i).getChips() - players.get(i).getBlind().getBlindAmount());
                    players.get(i).setChipsInCurrent(requiredChips);
                    players.get(i).setTotalChipsInPot(requiredChips);
                    bigBlind = players.get(i).getBlind().getBlindAmount();
                    if (i == players.size() - 1) {
                        startPlayer = players.get(0).getPlayerNum();
                    } else if (i == 0) {
                        players.get(players.size() - 1).setChips(players.get(players.size() - 1).getChips() - players.get(players.size() - 1).getBlind().getBlindAmount());
                        players.get(players.size() - 1).setChipsInCurrent(players.get(players.size() - 1).getBlind().getBlindAmount());
                        players.get(players.size() - 1).setTotalChipsInPot(players.get(players.size() - 1).getBlind().getBlindAmount());
                        startPlayer = players.get(i + 1).getPlayerNum();
                    } else {
                        players.get(i - 1).setChips(players.get(i - 1).getChips() - players.get(i - 1).getBlind().getBlindAmount());
                        players.get(i - 1).setChipsInCurrent(players.get(i - 1).getBlind().getBlindAmount());
                        players.get(i - 1).setTotalChipsInPot(players.get(i - 1).getBlind().getBlindAmount());
                        startPlayer = players.get(i + 1).getPlayerNum();
                    }
                    break;
                }
            }
        } else {
            boolean found = false;
            for (int i = 0; i < players.size() - 1; i++) {
                for (Player player : players) {
                    if (player.getPlayerNum() == smallBlindNum + i) {
                        startPlayer = player.getPlayerNum();
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
        do {
            Collections.sort(players);
            players = sortPlayers(players, startPlayer);
            for (int i = players.size() - 1; i >= 0; i--) {
                if (raised == true && players.get(i).getPlayerNum() == startPlayer) {
                } else {
                    if (players.size() == 1) {
                        raised = false;
                        break;
                    }
                    if (players.get(i).getChips() != 0 && !(players.get(i) instanceof AI)) {
                        do {
                            action = true;
                            System.out.println(players.get(i).getName() + " is acting");
                            System.out.println("1. Raise");
                            System.out.println("2. Check");
                            System.out.println("3. Call");
                            System.out.println("4. Fold");
                            System.out.print("Enter the Value You would like to do: ");
                            response = sc.nextInt();
                            if (response == 2 && requiredChips != players.get(i).getChipsInCurrent()) {
                                System.out.println("You cannot check");
                                action = false;
                            } else if (players.get(i).getChipsInCurrent() == requiredChips && response == 3) {
                                System.out.println("You already have enough chips in you cannot call");
                                action = false;
                            }
                        } while (response < 1 || response > 4 || action == false);
                        if (response == 1) {
                            int raise = raise(players.get(i), requiredChips);
                            raised = true;
                            startPlayer = players.get(i).getPlayerNum();
                            requiredChips += raise;
                            break;
                        } else if (response == 3) {
                            call(players.get(i), requiredChips);
                        } else if (response == 4) {
                            players.remove(i);
                        }
                        raised = false;
                    } else {
                        AI ai = (AI) (players.get(i));
                        if (round != 0) {
                            response = ai.rateOfReturn(communityCards, players, pot, requiredChips, bigBlind);
                            if (response > 4) {
                                raise(players.get(i), requiredChips, response);
                                raised = true;
                                startPlayer = players.get(i).getPlayerNum();
                                requiredChips += response;
                                break;
                            } else if (response == 3) {
                                call(players.get(i), requiredChips);
                            } else if (response == 4) {
                                System.out.println(players.get(i).getName() + " folded");
                                players.remove(i);
                            }
                            raised = false;
                        } else {
                            response = ai.preFlopBetting(bigBlind, requiredChips);
                            if (response == 0 && ai.getChipsInCurrent() != requiredChips) {
                                call(players.get(i), requiredChips);
                            } else if (response == -1) {
                                System.out.println(players.get(i).getName() + " folded");
                                players.remove(i);
                            } else if (response == 0) {
                                System.out.println(players.get(i).getName() + " checked");
                            } else {
                                int raise = raise(players.get(i), requiredChips, response);
                                raised = true;
                                startPlayer = players.get(i).getPlayerNum();
                                requiredChips += raise;
                                break;
                            }
                        }
                        raised = false;
                    }
                }
            }
        } while (raised == true);
        return players;
    }

    public static int raise(Player player, int requiredChips) {
        int raise;
        if (player.getChipsInCurrent() < requiredChips) {
            call(player, requiredChips);
        }
        do {
            System.out.println("Chips: " + player.getChips());
            System.out.print("Enter the amount you would like to raise: ");
            raise = sc.nextInt();
            if (raise > player.getChips()) {
                System.out.println("You don't have enough chips");
            }
        } while (raise > player.getChips());
        pot += raise;
        player.setChips(player.getChips() - raise);
        player.setChipsInCurrent(player.getChipsInCurrent() + raise);
        player.setTotalChipsInPot(player.getTotalChipsInPot() + raise);
        if (raise == player.getChips()) {
            System.out.println("All In");
        }
        return raise;
    }

    public static int raise(Player player, int requiredChips, int raise) {
        if (player.getChipsInCurrent() < requiredChips) {
            call(player, requiredChips);
        }
        pot += raise;
        player.setChips(player.getChips() - raise);
        player.setChipsInCurrent(player.getChipsInCurrent() + raise);
        player.setTotalChipsInPot(player.getTotalChipsInPot() + raise);
        if (raise == player.getChips()) {
            System.out.println("All In");
        }
        System.out.println(player.getName() + " raised " + raise);
        return raise;
    }

    public static void call(Player player, int requiredChips) {
        int callAmount;
        callAmount = requiredChips - player.getChipsInCurrent();
        if (callAmount <= player.getChips()) {
            pot += callAmount;
            if (callAmount == player.getChips()) {
                System.out.println("All In");
            }
            player.setChips(player.getChips() - callAmount);
            player.setChipsInCurrent(player.getChipsInCurrent() + callAmount);
            player.setTotalChipsInPot(player.getTotalChipsInPot() + callAmount);

        } else {
            pot += player.getChips();
            player.setChipsInCurrent(player.getChipsInCurrent() + player.getChips());
            player.setTotalChipsInPot(player.getTotalChipsInPot() + player.getChips());
            player.setChips(0);
            System.out.println("All In");
        }
        System.out.println(player.getName() + " called");
    }

    public static ArrayList<Player> sortPlayers(ArrayList<Player> players, int startPlayer) {
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerNum() == startPlayer) {
                if (i == 0) {
                    for (int j = 1; j <= players.size(); j++) {
                        sortedPlayers.add(players.get(players.size() - j));

                    }
                } else {
                    for (int j = 1; j <= players.size(); j++) {
                        sortedPlayers.add(players.get(i - 1));
                        i -= 1;
                        if (i <= 0) {
                            i = players.size();
                        }
                    }
                }

                break;
            }

        }
        return sortedPlayers;
    }

    public static void distributeWin(ArrayList<Player> players) {
        Hand winningHand = new Hand();
        Player winningPlayer = null;
        if (players.size() == 1) {
            winningPlayer = players.get(0);
            players.get(0).setChips(players.get(0).getChips() + pot);
            pot = 0;
        } else {
            for (Player player : players) {
                if (player.getHand().compareTo(winningHand) < 0) {
                    winningPlayer = player;
                    winningHand = player.getHand();
                }
            }
            int remainingPot = 0;
            for (Player player : players) {
                if (player.getTotalChipsInPot() > winningPlayer.getTotalChipsInPot()) {
                    pot -= player.getTotalChipsInPot() - winningPlayer.getTotalChipsInPot();
                    remainingPot += player.getTotalChipsInPot() - winningPlayer.getTotalChipsInPot();
                }
            }
            winningPlayer.setChips(winningPlayer.getChips() + pot);
            players.remove(winningPlayer);
            pot = 0 + remainingPot;
        }
        System.out.println(winningPlayer.getName() + " won with " + winningPlayer.getHand().handValue() + " Chips: " + winningPlayer.getChips());
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
            //deckY2 = deckY1 - (i * 0.25);
            deck.getdPane().getChildren().add(displayCard(card));
        }
    }

    public static void displayBurn(Deck deck) {
        Card card = deck.getDeck().get(0);
        card.setFaceUp(false);
        burnPane.getChildren().add(displayCard(card));
    }

    public static void displayFlop(ArrayList<Card> communityCards) {
        for (int i = 0; i < 3; i++) {
            Card card = communityCards.get(i);
            card.setFaceUp(true);
            communityCardPane.getChildren().add(displayCard(card));
        }
    }

    public static void displayTurn(ArrayList<Card> communityCards) {
        Card card = communityCards.get(3);
        card.setFaceUp(true);
        communityCardPane.getChildren().add(displayCard(card));
    }

    public static void displayRiver(ArrayList<Card> communityCards) {
        Card card = communityCards.get(4);
        card.setFaceUp(true);
        communityCardPane.getChildren().add(displayCard(card));
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

//    public static Pane usePlayerPane(Player player) {
//        int playerNum = player.getPlayerNum();
//        switch (playerNum) {
//            case 1:
//                return pane1;
//            case 2:
//                return pane2;
//            case 3:
//                return pane3;
//            case 4:
//                return pane4;
//            case 5:
//                return pane5;
//            case 6:
//                return pane6;
//            case 7:
//                return pane7;
//            case 8:
//                return pane8;
//            default:
//                return rootPane;
//        }
//    }
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

}
