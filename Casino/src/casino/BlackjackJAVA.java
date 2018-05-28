package casino;

import java.io.*;
import java.util.ArrayList;

public class BlackjackJAVA {

    public static InputStreamReader inStream = new InputStreamReader(System.in);
    public static BufferedReader stdin = new BufferedReader(inStream);
    public static ArrayList<Player> numOfPlayers = new ArrayList<>();
    public static Deck deck;
    public static Dealer dealer;
    public static int round = 1;
    public static boolean stay;

    public static void main(String[] args) throws IOException, InterruptedException {
        initializeGame();
        System.out.println("");
        dealer = new Dealer(deck);
        placeBets();
        System.out.println("");
        printBoard();
    }

    public static void placeBets() throws IOException {
        boolean repeat;
        for (int i = 0; i < numOfPlayers.size(); i++) {
            System.out.println(numOfPlayers.get(i).getName() + "\t\tChips: $" + numOfPlayers.get(i).getChips());
            do {
                System.out.print("How much would you like to bet: $");
                int bet = Integer.parseInt(stdin.readLine());
                if (bet > numOfPlayers.get(i).getChips()) {
                    System.out.println("\nYou only have $" + numOfPlayers.get(i).getChips() + "\n");
                    repeat = true;
                } else {
                    numOfPlayers.get(i).setBet(bet);
                    repeat = false;
                }
            } while (repeat);
        }
    }

    public static void printBoard() throws IOException, InterruptedException {
        System.out.println("\nDEALER ~ HAND");
        System.out.println(dealer.getDealerHand().getPlayerHand().get(0) + "\t*********");
        for (int i = 0; i < numOfPlayers.size(); i++) {
            if (dealer.getDealerHand().getPlayerHand().get(0).getValue() == 1) {
                System.out.print(numOfPlayers.get(i).getName().toUpperCase() + " ~ Would you like insurance?");
                if (stdin.readLine().equalsIgnoreCase("yes")) {
                    getInsurance(i);
                }
            }
        }
        if (1 != dealer.getDealerHand().getPlayerHand().get(0).getValue()) {
            Thread.sleep(2000);
        }
        System.out.println("");
        for (int i = 0; i < numOfPlayers.size(); i++) {
            System.out.println("\t\t" + numOfPlayers.get(i).getName().toUpperCase() + "\t\tBet: $" + numOfPlayers.get(i).getBet());
            System.out.println("~HAND~");
            if (numOfPlayers.get(i).setTotal(0) != numOfPlayers.get(i).getTotal()) {
                System.out.println(numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(0) + "\t" + numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal() + " or " + numOfPlayers.get(i).setTotal(0));
            } else {
                System.out.println(numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(0) + "\t" + numOfPlayers.get(i).getPocketHand().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal());
            }
            playRound(i);
        }
        playDealer();
    }

    public static void getInsurance(int i) throws IOException {
        numOfPlayers.get(i).setInsurance(true);
    }

    public static void playDealer() {
    }

    public static void printCards(int i) throws IOException {
    }

    public static void playerHit(int i) throws IOException {
    }

    public static void playerSplit(int i) throws IOException {
    }

    public static void playerDD(int i) throws IOException {
    }

    public static void playRound(int i) throws IOException {
        int response = 0;
        if (!numOfPlayers.get(i).isWin()) {
            if (round == 1) {
                if (numOfPlayers.get(i).getPocketHand().get(0).checkBlackJack()) {
                    System.out.println("BLACKJACK!");
                    numOfPlayers.get(i).setWin(true);
                    numOfPlayers.get(i).setChips((int) (numOfPlayers.get(i).getBet() * 1.5 + numOfPlayers.get(i).getChips()));
                } else if (numOfPlayers.get(i).getPocketHand().get(0).checkSplit()) {
                    System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split\n4) Double Down");
                    response = Integer.parseInt(stdin.readLine());
                    switch (response) {
                        case 3:
                            playerSplit(i);
                            break;
                        case 4:
                            playerDD(i);
                            break;
                        default:
                            break;
                    }
                } else {
                    System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down");
                    response = Integer.parseInt(stdin.readLine());
                    if (response == 3) {
                        playerDD(i);
                    }
                }
            } else {
                if (numOfPlayers.get(i).getPocketHand().get(0).checkBlackJack()) {
                    System.out.println("BLACKJACK!");
                    numOfPlayers.get(i).setWin(true);
                } else if (numOfPlayers.get(i).getPocketHand().get(0).checkSplit()) {
                    System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split");
                    response = Integer.parseInt(stdin.readLine());
                    if (response == 3) {
                        playerSplit(i);
                    }
                } else {
                    System.out.println("Would you like to\n1) Hit\n2) Stay");
                    response = Integer.parseInt(stdin.readLine());
                }
            }
            if (response == 1) {
                playerHit(i);
            } else if (response == 2) {
                stay = true;
            }
        }
        round++;
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
