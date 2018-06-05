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

    public static void main(String[] args) throws IOException, InterruptedException {
        initializeGame();
        dealer = new Dealer(deck);
        addAI();
        int answer;
        do {
            System.out.println("");
            placeBets();
            if (numOfPlayers.isEmpty()) {
                break;
            }
            System.out.println("");
            printBoard();
            resetCharacteristics();
            for (int i = 0; i < numOfPlayers.size(); i++) {
                System.out.print("\n" + numOfPlayers.get(i).getName().toUpperCase() + " would you like to:\n1) Play again\n2) Cash out\nEnter choice: ");
                if (numOfPlayers.get(i).isAi()) {
                    BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
                    Thread.sleep(1000);
                    if (ai.isLeave()) {
                        System.out.print("2");
                        answer = 2;
                    } else {
                        System.out.print("1");
                        answer = 1;
                    }
                } else {
                    answer = Integer.parseInt(stdin.readLine());
                }
                if (answer == 2) {
                    numOfPlayers.remove(i);
                    i = i - 1;
                }

            }
            if (deck.getDeck().isEmpty()) {
                deck = new Deck();
            }
        } while (!numOfPlayers.isEmpty());
        System.out.println("\nThank god youre gone!");
    }

    public static void addAI() throws IOException {
        numOfPlayers.add(new BlackjackAI("John",deck));
        numOfPlayers.add(new BlackjackAI("Bob",deck));
        numOfPlayers.add(new BlackjackAI("Carina",deck));
    }

    public static void placeBets() throws IOException, InterruptedException {
        boolean repeat;
        int response;
        BlackjackAI ai;
        for (int i = 0; i < numOfPlayers.size(); i++) {
            if (numOfPlayers.get(i).isAi()) {
                ai = (BlackjackAI) (numOfPlayers.get(i));
                System.out.println(ai.getName().toUpperCase() + "\t\tChips: $" + ai.getChips());
                ai.setRealBet();
                ai.setBet(ai.getRealBet());
                System.out.print("How much would you like to bet: $");
                Thread.sleep(1200);
                System.out.print(ai.getBet());
                System.out.println("");
            } else {
                response = 0;
                if (numOfPlayers.get(i).getChips() == 0) {
                    System.out.println(numOfPlayers.get(i).getName().toUpperCase() + ", you have no more CHIPS!\n");
                    System.out.print("Would you like to:\n1) Buy more chips\n2) Leave table\nEnter your choice: ");
                    response = Integer.parseInt(stdin.readLine());
                    switch (response) {
                        case 1:
                            System.out.print("How many chips would you like to buy? ");
                            int buy = Integer.parseInt(stdin.readLine());
                            numOfPlayers.get(i).setChips(buy);
                            break;
                        case 2:
                            numOfPlayers.remove(i);
                            i = i - 1;
                            break;
                        default:
                            System.out.println("That was not one of the options!");
                            break;
                    }
                }
                System.out.println("");
                if (response != 2) {
                    System.out.println(numOfPlayers.get(i).getName().toUpperCase() + "\t\tChips: $" + numOfPlayers.get(i).getChips());
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
        }
    }

    public static void resetCharacteristics() {
        for (int i = 0; i < numOfPlayers.size(); i++) {
            numOfPlayers.get(i).setBet(0);
            numOfPlayers.get(i).setStay(false);
            numOfPlayers.get(i).setNaturalBlackJack(false);
            numOfPlayers.get(i).setInsurance(false);
            numOfPlayers.get(i).setInsuranceAmount(0);
            if (numOfPlayers.get(i).isAi()) {
                BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
                ai.set(deck, numOfPlayers, dealer);
            }
            for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                numOfPlayers.get(i).getPocketHands().remove(s);
            }
            numOfPlayers.get(i).getPocketHands().add(new PocketHand(deck));
            dealer = new Dealer(deck);
        }
    }

    public static void printBoard() throws IOException, InterruptedException {
        BlackjackAI ai;
        System.out.println("\nDEALER ~ HAND");
        System.out.println(dealer.getDealerHand().getPlayerHand().get(0) + "\t*********");
        if (dealer.checkInsured()) {
            System.out.println("Would you like insurance?");
        }
        for (int i = 0; i < numOfPlayers.size(); i++) {
            if (dealer.getDealerHand().getPlayerHand().get(0).getValue() == 1) {
                System.out.print(numOfPlayers.get(i).getName().toUpperCase() + " ~ Would you like insurance?");
                if (numOfPlayers.get(i).isAi()) {
                    ai = (BlackjackAI) (numOfPlayers.get(i));
                    if (ai.isInsurance()) {
                        System.out.print(" yes");
                        getInsurance(i);
                    } else {
                        System.out.print(" no");
                    }
                } else if (stdin.readLine().equalsIgnoreCase("yes")) {
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
            if (numOfPlayers.get(i).setTotal(0) != 0) {
                if (numOfPlayers.get(i).setTotal(0) != numOfPlayers.get(i).getTotal(0)) {
                    System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0) + " or " + numOfPlayers.get(i).setTotal(0));
                } else {
                    System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0));
                }
            } else {
                System.out.println(numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(0) + "\t\t" + numOfPlayers.get(i).getPocketHands().get(0).getPlayerHand().get(1) + "\t\tTotal: " + numOfPlayers.get(i).getTotal(0));
            }
            playRound(i, 0);
        }
        playDealer();
        System.out.println("");
        checkWin();
    }

    public static void checkWin() throws IOException {
        checkInsuranceWin();
        if (dealer.isBust()) {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (numOfPlayers.get(i).getTotal(s) <= 21 && !numOfPlayers.get(i).isNaturalBlackJack()) {
                        numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet() * 2);
                        System.out.println("Player " + numOfPlayers.get(i).getName() + " won due to dealer BUST!");
                    }
                }
            }
        } else {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                for (int s = 0; s < numOfPlayers.get(i).getPocketHands().size(); s++) {
                    if (numOfPlayers.get(i).setTotal(s) > numOfPlayers.get(i).getTotal(s) && numOfPlayers.get(i).setTotal(s) <= 21) {
                        numOfPlayers.get(i).setObTotal(numOfPlayers.get(i).setTotal(s));
                    } else {
                        numOfPlayers.get(i).setObTotal(numOfPlayers.get(i).getTotal(s));
                    }
                    if (!numOfPlayers.get(i).isNaturalBlackJack() && numOfPlayers.get(i).getTotal() <= 21) {
                        if (dealer.getTotal() > numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " lost!");
                            numOfPlayers.get(i).setBet(0);
                        } else if (dealer.getTotal() == numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " stands!");
                            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet());
                        } else if (dealer.getTotal() < numOfPlayers.get(i).getTotal()) {
                            System.out.println("Player " + numOfPlayers.get(i).getName() + " won!");
                            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet() * 2);
                        }
                    }
                }
            }
        }
    }

    public static void checkInsuranceWin() {
        if (dealer.getDealerHand().getPlayerHand().get(0).getWorth() == 1 && dealer.getDealerHand().getPlayerHand().get(1).getWorth() == 10) {
            for (int i = 0; i < numOfPlayers.size(); i++) {
                if (numOfPlayers.get(i).isInsurance()) {
                    numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getInsuranceAmount() * 3);
                    System.out.println("Player " + numOfPlayers.get(i).getName() + " insurance paid off! Chips: " + numOfPlayers.get(i).getChips());
                }
            }
        }
    }

    public static void getInsurance(int i) throws IOException {
        numOfPlayers.get(i).setInsurance(true);
        if (numOfPlayers.get(i).getChips() >= numOfPlayers.get(i).getBet() / 2) {
            numOfPlayers.get(i).setInsuranceAmount();
        } else {
            System.out.println("You do not have enough chips");
        }
    }

    public static void playDealer() throws InterruptedException {
        System.out.println("\n~ DEALER ~ ");
        printDealer();
        while (!dealer.checkSeventeen()) {
            System.out.println("");
            dealer.getDealerHand().hitCard(deck);
            printDealer();
            Thread.sleep(1000);
        }
        if (dealer.getTotal() > 21) {
            System.out.println("\nDealer BUST!");
            dealer.setBust(true);
        }
    }

    public static void printDealer() {
        for (int i = 0; i < dealer.getDealerHand().getPlayerHand().size(); i++) {
            System.out.print(dealer.getDealerHand().getPlayerHand().get(i) + "\t\t");
        }
        System.out.print("Total: " + dealer.getTotal());
    }

    public static void printCards(int i, int handNum) throws IOException {
        for (int c = 0; c < numOfPlayers.get(i).getPocketHands().get(handNum).getPlayerHand().size(); c++) {
            System.out.print(numOfPlayers.get(i).getPocketHands().get(handNum).getPlayerHand().get(c) + "\t\t");
        }
        if (numOfPlayers.get(i).setTotal(handNum) != 0) {
            if (numOfPlayers.get(i).setTotal(handNum) != numOfPlayers.get(i).getTotal(handNum)) {
                System.out.print("Total: " + numOfPlayers.get(i).setTotal(handNum) + " or " + numOfPlayers.get(i).getTotal(handNum));
            } else {
                System.out.print("Total: " + numOfPlayers.get(i).getTotal(handNum));
            }
        } else {
            System.out.print("Total: " + numOfPlayers.get(i).getTotal(handNum));
        }
        if (numOfPlayers.get(i).getTotal(handNum) > 21) {
            System.out.println("\tBUST!\n");
            numOfPlayers.get(i).setStay(true);
            numOfPlayers.get(i).setBet(0);
        }
        System.out.println("");
    }

    public static void playerHit(int i, int handNum) throws IOException {
        numOfPlayers.get(i).getPocketHands().get(handNum).hitCard(deck);
        printCards(i, handNum);
    }

    public static void playerSplit(int i, int handNum) throws IOException, InterruptedException {
        if (numOfPlayers.get(i).getChips() >= numOfPlayers.get(i).getBet()) {
            numOfPlayers.get(i).ifSplit(deck);
            numOfPlayers.get(i).getPocketHands().get(1).setSplitBet(numOfPlayers.get(i).getBet());
            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() - numOfPlayers.get(i).getPocketHands().get(1).getSplitBet());
            for (int d = handNum; d < numOfPlayers.get(i).getPocketHands().size(); d++) {
                System.out.println("\nDeck " + (d + 1) + ":\t");
                printCards(i, d);
                playRound(i, d);
            }
        } else {
            System.out.println("You do not have enough chips to split!");
        }
    }

    public static void playerDD(int i, int handNum) throws IOException {
        if (numOfPlayers.get(i).getChips() >= numOfPlayers.get(i).getBet()) {
            numOfPlayers.get(i).setChips(numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet());
            numOfPlayers.get(i).setBet(numOfPlayers.get(i).getBet() * 2);
            numOfPlayers.get(i).getPocketHands().get(handNum).hitCard(deck);
            printCards(i, handNum);
            numOfPlayers.get(i).setStay(true);
        } else {
            System.out.println("You do not have enough chips to double down!");
        }
        System.out.println("");
    }

    public static void playRound(int i, int handNum) throws IOException, InterruptedException {
        int response;
        round = 1;
        if (handNum > 0) {
            numOfPlayers.get(i).setStay(false);
        }
        while (!numOfPlayers.get(i).isStay()) {
            if (numOfPlayers.get(i).isAi()) {
                BlackjackAI ai = (BlackjackAI) (numOfPlayers.get(i));
                ai.setdDown(deck, handNum);
                ai.setSplit();
                ai.setHit(deck, handNum);
                if (round == 1) {
                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
                        System.out.println("NATURAL BLACKJACK!");
                        numOfPlayers.get(i).setNaturalBlackJack(true);
                        numOfPlayers.get(i).setStay(true);
                        numOfPlayers.get(i).setChips((int) (numOfPlayers.get(i).getBet() * 1.5 + numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet()));
                        break;
                    } else if (numOfPlayers.get(i).getPocketHands().get(handNum).checkSplit() && ai.isdDown()) {
                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down\n4) Split");
                        if (ai.isSplit()) {
                            Thread.sleep(2000);
                            System.out.println("4");
                            playerSplit(i, handNum);
                        } else if (ai.isdDown()) {
                            Thread.sleep(2000);
                            System.out.println("3");
                            playerDD(i, handNum);
                        }
                    } else if (numOfPlayers.get(i).getPocketHands().get(handNum).checkSplit()) {
                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split");
                        if (ai.isSplit()) {
                            Thread.sleep(2000);
                            System.out.println("3");
                            playerSplit(i, handNum);
                        }
                    } else if (ai.isdDown()) {
                        System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down");
                        Thread.sleep(2000);
                        System.out.println("3");
                        playerDD(i, handNum);
                    } else if (ai.isHit()) {
                        System.out.println("Would you like to\n1) Hit\n2) Stay");
                        Thread.sleep(2000);
                        System.out.println("1");
                        playerHit(i, handNum);
                    } else if (!ai.isHit()) {
                        Thread.sleep(2000);
                        System.out.println("2");
                    }
                } else {
                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
                        System.out.println("BLACKJACK!");
                        numOfPlayers.get(i).setStay(true);
                        break;
                    } else if (ai.isHit()) {
                        System.out.println("Would you like to\n1) Hit\n2) Stay");
                        Thread.sleep(2000);
                        System.out.println("1");
                        playerHit(i, handNum);
                    } else if (!ai.isHit()) {
                        Thread.sleep(2000);
                        System.out.println("2");
                    }
                }
            } else {
                if (round == 1) {
                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
                        System.out.println("NATURAL BLACKJACK!");
                        numOfPlayers.get(i).setNaturalBlackJack(true);
                        numOfPlayers.get(i).setStay(true);
                        numOfPlayers.get(i).setChips((int) (numOfPlayers.get(i).getBet() * 1.5 + numOfPlayers.get(i).getChips() + numOfPlayers.get(i).getBet()));
                        break;
                    } else if (numOfPlayers.get(i).getTotal(handNum) >= 9 && numOfPlayers.get(i).getTotal(handNum) <= 11) {
                        if (numOfPlayers.get(i).getPocketHands().get(0).checkSplit()) {
                            System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split\n4) Double Down");
                            response = Integer.parseInt(stdin.readLine());
                            switch (response) {
                                case 3:
                                    playerSplit(i, handNum);
                                    break;
                                case 4:
                                    playerDD(i, handNum);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Double down");
                            response = Integer.parseInt(stdin.readLine());
                            if (response == 3) {
                                playerDD(i, handNum);
                            }
                        }
                    } else {
                        if (numOfPlayers.get(i).getPocketHands().get(0).checkSplit()) {
                            System.out.println("Would you like to\n1) Hit\n2) Stay\n3) Split");
                            response = Integer.parseInt(stdin.readLine());
                            if (response == 3) {
                                playerSplit(i, handNum);
                            }
                        } else {
                            System.out.println("Would you like to\n1) Hit\n2) Stay");
                            response = Integer.parseInt(stdin.readLine());
                        }
                    }
                } else {
                    if (numOfPlayers.get(i).getPocketHands().get(handNum).checkBlackJack() || numOfPlayers.get(i).setTotal(handNum) == 21) {
                        System.out.println("BLACKJACK!");
                        numOfPlayers.get(i).setStay(true);
                        break;
                    } else {
                        System.out.println("Would you like to\n1) Hit\n2) Stay");
                        response = Integer.parseInt(stdin.readLine());
                    }
                }
                if (response == 1) {
                    playerHit(i, handNum);
                } else if (response == 2) {
                    numOfPlayers.get(i).setStay(true);
                    break;
                }
            }
            round++;
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
