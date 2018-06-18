package casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import java.util.concurrent.TimeUnit;

public class Poker {

    static Scanner sc = new Scanner(System.in);
    static int pot = 0;
    static Pane communityCardPane;
    static Random rand = new Random();
    static int requiredChips, bigBlind, round, smallBlindNum;
    static ArrayList<Player> players;
    static ArrayList<Player> allPlayers;
    static ArrayList<Card> communityCards;
    static Deck deck;
    static Player currentPlayer;
    static boolean allPlayerCheck;
    
    public Poker() {
        createPlayers();
        players = new ArrayList<Player>();
        communityCards = new ArrayList<Card>();
        Casino.getPokerGraphics().createButtons();
    }

    public static ArrayList<Player> createPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        allPlayers = new ArrayList<Player>();
        allPlayers.add(Casino.getMainPlayer());
        for (int i = 2; i < 5; i++) {
            allPlayers.add(new AI("Player " + i, i));
        }
        return allPlayers;
    }

    public static void newHand() {
        System.out.println("NEW HAND");
        pot = 0;
        players.clear();
        for (Player player : allPlayers) {
            if (player.getChips() != 0) {
                players.add(player);
            }
            else{
                allPlayers.remove(player);
            }
        }
        for (int i = communityCards.size() - 1; i >= 0; i--) {
            communityCards.remove(communityCards.get(i));
        }
        PokerGraphics.removeCommunityCard();
        for (Player player : players) {
            player.setTotalChipsInPot(0);
            player.setChipsInCurrent(0);
            player.setPocketHand(new PocketHand());
        }
        communityCardPane = new Pane();
        PokerGraphics.burnPile = new Pane();
        round = 0;
    }

    public static void playPoker() {
        newHand();
        smallBlindNum = setBlinds();
        findRequiredChips();
        deck = new Deck();
        PokerGraphics.displayDeck(deck);
        deck.shuffle();
        dealPlayers(deck);
        PokerGraphics.displayDealPlayers(players);
        Collections.sort(players);
        int startPlayer = Poker.findStartingPlayer();
        sortPlayers(startPlayer);
        currentPlayer = players.get(players.size() - 1);
        roundOfBetting();
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

    public static void dealPlayers(Deck deck) {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getPocketHand().getPocketHand().add(deck.getDeck().get(0));
                deck.getDeck().remove(0);
            }
        }
    }

    public static int setBlinds() {
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

        for (Player player : allPlayers) {
            player.getPane().getChildren().clear();
            PokerGraphics.addPlayerInfo(player);
        }
        return smallBlindNum;
    }

    public static int findStartingPlayer() {
        int startPlayer = 0;
        if (round == 0) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")) {
                    players.get(i).setChips(players.get(i).getChips() - players.get(i).getBlind().getBlindAmount());
                    players.get(i).setChipsInCurrent(players.get(i).getBlind().getBlindAmount());
                    players.get(i).setTotalChipsInPot(players.get(i).getBlind().getBlindAmount());
                    if (i == players.size() - 1) {
                        players.get(i - 1).setChips(players.get(i - 1).getChips() - players.get(i - 1).getBlind().getBlindAmount());
                        players.get(i - 1).setChipsInCurrent(players.get(i - 1).getBlind().getBlindAmount());
                        players.get(i - 1).setTotalChipsInPot(players.get(i - 1).getBlind().getBlindAmount());
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
            System.out.println(smallBlindNum);
            boolean found = false;
            for (int i = 0; i < allPlayers.size() - 1; i++) {
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
        return startPlayer;
    }

    public static int getRequiredChips() {
        return requiredChips;
    }

    public static void setRequiredChips(int requiredChips) {
        Poker.requiredChips = requiredChips;
    }

    public static void findRequiredChips() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")) {
                bigBlind = players.get(i).getBlind().getBlindAmount();
            }
        }
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Poker.players = players;
    }

    public static void playTurn() {
        for (Player player : allPlayers) {
            player.getPane().getChildren().clear();
            PokerGraphics.addPlayerInfo(player);
        }
        PokerGraphics.displayDealPlayers(players);
        currentPlayer.setNumTurn(currentPlayer.getNumTurn()+1);
        if (players.size() == 1) {
            distributeWin();
            return;
        }
        int playerIndex = 0;
        System.out.println(currentPlayer.getName() + " " + pot);
        if (currentPlayer instanceof AI) {
            System.out.println("AI turn " + round);
            AI ai = (AI) (currentPlayer);
            int response;
            if (round != 0) {
                response = ai.rateOfReturn(communityCards, players, pot, requiredChips, bigBlind);
                if (response > 4) {
                    raise(ai, requiredChips, response);
                    requiredChips += response;
                } else if (response == 3) {
                    call(ai, requiredChips);
                } else if (response == 4 && ai.getChipsInCurrent() < requiredChips) {
                    playerIndex = players.indexOf(currentPlayer);
                    System.out.println(ai.getName() + " folded");
                    PokerGraphics.displayFold(ai);
                    players.remove(ai);
                } else {
                    System.out.println(ai.getName() + " checked");
                }
                if (response != 4) {
                    playerIndex = players.indexOf(currentPlayer);
                }
            } else {
                response = ai.preFlopBetting(bigBlind, requiredChips);
                if (response == 0 && ai.getChipsInCurrent() != requiredChips) {
                    call(ai, requiredChips);
                } else if (response == -1) {
                    if(!(ai.getChipsInCurrent()==requiredChips)){
                        playerIndex = players.indexOf(currentPlayer);
                        System.out.println(ai.getName() + " folded");
                        players.remove(ai);
                        PokerGraphics.displayFold(ai);
                    }
                    else{
                        System.out.println(ai.getName() + " checked");
                    }
                } else if (response == 0) {
                    System.out.println(ai.getName() + " checked");
                } else {
                    int raise = raise(ai, requiredChips, response);
                    requiredChips += raise;
                }
                if (response != -1) {
                    playerIndex = players.indexOf(currentPlayer);
                }
            }
            determiningNextAction(playerIndex);
        }
    }

    public static void determiningNextAction(int playerIndex) {
        allPlayerCheck = false;
        PokerGraphics.displayPot();
        if (playerIndex > 0) {
            currentPlayer = players.get(playerIndex - 1);
        } else {
            currentPlayer = players.get(players.size() - 1);
        }
        if(currentPlayer.getChipsInCurrent() == requiredChips&&requiredChips==0&&currentPlayer.getNumTurn()==0){
            playTurn();
        }
        if (currentPlayer.getChipsInCurrent() != requiredChips||(round==0&&currentPlayer.getBlind().getTypeBlind().equalsIgnoreCase("big"))) {
            playTurn();
        } else {
            if (players.size() == 1) {
                distributeWin();
            }
            if (communityCards.size() == 0) {
                flop();
                round += 1;
                Collections.sort(players);
                int startPlayer = findStartingPlayer();
                sortPlayers(startPlayer);
                setCurrentPlayer(players.get(players.size() - 1));
                roundOfBetting();
            } else if (Poker.getCommunityCards().size() == 3) {
                turnAndRiver();
                round += 1;
                PokerGraphics.displayTurn(communityCards);
                Collections.sort(players);
                int startPlayer = findStartingPlayer();
                sortPlayers(startPlayer);
                setCurrentPlayer(players.get(players.size() - 1));
                allPlayerCheck = true;
                roundOfBetting();
            } else if (Poker.getCommunityCards().size() == 4&&!(allPlayerCheck)) {
                turnAndRiver();
                round += 1;
                PokerGraphics.displayRiver(communityCards);
                Collections.sort(players);
                int startPlayer = findStartingPlayer();
                sortPlayers(startPlayer);
                setCurrentPlayer(players.get(players.size() - 1));
                allPlayerCheck = true;
                roundOfBetting();
            } else if (Poker.getCommunityCards().size() == 5&&!(allPlayerCheck)) {
                round+=1;
                PokerGraphics.displayAllCards(players);
            }
        }
    }

    public static void roundOfBetting() {
        System.out.println("new round");
        PokerGraphics.displayPot();
        requiredChips = 0;
        if (round == 0) {
            requiredChips = bigBlind;
        } else {
            for (Player player : players) {
                player.setNumTurn(0);
                player.setChipsInCurrent(0);
            }
        }
        playTurn();
    }

    public static void flop() {
        PokerGraphics.displayBurn();
        deck.getDeck().remove(0);
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.getDeck().get(0));
            deck.getDeck().remove(0);
        }
        PokerGraphics.displayFlop(communityCards);
    }

    public static void turnAndRiver() {
        PokerGraphics.displayBurn();
        deck.getDeck().remove(0);
        communityCards.add(deck.getDeck().get(0));
        deck.getDeck().remove(0);
    }

    public static int raise(Player player, int requiredChips, int raise) {
        if (player.getChipsInCurrent() < requiredChips) {
            call(player, requiredChips);
        }
        if(player.getChips()>=raise){
            pot += raise;
            player.setChips(player.getChips() - raise);
            player.setChipsInCurrent(player.getChipsInCurrent() + raise);
            player.setTotalChipsInPot(player.getTotalChipsInPot() + raise);
        }
        else{
            pot += player.getChips();
            player.setChips(0);
            player.setChipsInCurrent(player.getChipsInCurrent() + player.getChips());
            player.setTotalChipsInPot(player.getTotalChipsInPot() + player.getChips());
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

    public static void sortPlayers(int startPlayer) {
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
        players = sortedPlayers;
    }

    public static void distributeWin() {
        Hand winningHand = new Hand();
        Player winningPlayer = null;
        if (players.size() == 1) {
            winningPlayer = players.get(0);
            players.get(0).setChips(players.get(0).getChips() + pot);
            pot = 0;
        } else {
            for (Player player : players) {
                player.setHand(determineHand(player.getPocketHand(), communityCards));
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
        System.out.println(winningPlayer.getName() + " Chips: " + winningPlayer.getChips());
        playPoker();
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public static ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public static int getBigBlind() {
        return bigBlind;
    }

    public static int getRound() {
        return round;
    }

    public static void setAllPlayers(ArrayList<Player> allPlayers) {
        Poker.allPlayers = allPlayers;
    }

    public static void setCommunityCards(ArrayList<Card> communityCards) {
        Poker.communityCards = communityCards;
    }

    public static void setBigBlind(int bigBlind) {
        Poker.bigBlind = bigBlind;
    }

    public static void setRound(int round) {
        Poker.round = round;
    }

    public static Deck getDeck() {
        return deck;
    }

    public static void setDeck(Deck deck) {
        Poker.deck = deck;
    }

    public static int getSmallBlindNum() {
        return smallBlindNum;
    }

    public static void setSmallBlindNum(int smallBlindNum) {
        Poker.smallBlindNum = smallBlindNum;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        Poker.currentPlayer = currentPlayer;
    }

}
