package casino;

import java.io.*;
import java.util.ArrayList;

public class BlackjackJAVA {

    public static InputStreamReader inStream = new InputStreamReader(System.in);
    public static BufferedReader stdin = new BufferedReader(inStream);
    public static ArrayList<Player> numOfPlayers = new ArrayList<>();
    public static Deck deck;
    public static Dealer dealer;

    public static void main(String[] args) throws IOException {
        initializeGame();
        System.out.println("");
        dealer = new Dealer(deck);
        placeBets();
        System.out.println("");
        printBoard();
    }

    public static void placeBets() throws IOException {
        for (int i = 0; i < numOfPlayers.size(); i++) {
            System.out.println(numOfPlayers.get(i).getName() + "\t\tChips: $" + numOfPlayers.get(i).getChips());
            System.out.print("How much would you like to bet: $");
            numOfPlayers.get(i).setBet(Integer.parseInt(stdin.readLine()));
        }
    }

    public static void printBoard() throws IOException {
        for (int i = 0; i < numOfPlayers.size(); i++) {
            System.out.println(numOfPlayers.get(i).getName() + "\t\tBet: $" + numOfPlayers.get(i).getBet());
            System.out.println("\n~HAND~\n");
            System.out.println(numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(0) + "\t" + numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal());
            if (numOfPlayers.get(i).getPocketHand().get(i).checkSplit()) {
                System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split");
            }
        }
    }

    public static void initializeGame() throws IOException {
        System.out.println("Welcome to Blackjack!");
        System.out.print("\nEnter number of players: ");
        int number = Integer.parseInt(stdin.readLine());
        System.out.print("\nHow many decks would you like to play with? ");
        int numOfDecks = Integer.parseInt(stdin.readLine());
        System.out.println("\nPlayer Names");
        deck = new Deck(numOfDecks);
        deck.shuffle();
        for (int i = 1; i <= number; i++) {
            System.out.print("Player name #" + i + ": ");
            String name = stdin.readLine();
            numOfPlayers.add(new Player(name, deck));
        }
    }

}
