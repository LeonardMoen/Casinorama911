/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author laua8572
 */
public class Roulette {

    static InputStreamReader inStream = new InputStreamReader(System.in);
    static BufferedReader stdin = new BufferedReader(inStream);
    static Random rand = new Random();

    static ArrayList<Integer> blackNums = new ArrayList<Integer>();

    public Roulette() {

    }
    
    

    public void playRoulette(ArrayList<Player> players) throws IOException {

        fillBlackNums();

        ArrayList<Bet> bets = new ArrayList<Bet>();

        for (int i = 0; i < players.size(); i++) {

            int choice = -1;
            while (true) {  //allows  you to bet more than once
                System.out.println("Pick a bet type");
                System.out.println("(0)Exit\t(1)Single\t(2)Split\t(3)Street\t(4)Corner");
                System.out.println("(5)High\t(6)Low\t(7)even\t(8)odd\t(9)black");
                System.out.println("(10)red\t(11)1st 12\t(12)2nd 12\t(13)3rd 12\t(14)Column 1\t(15)Column 2\t(16)Column 3");
                choice = Integer.parseInt(stdin.readLine());
                if (choice == 0) {
                    break;
                }
                
                choices(i, players, choice, bets);
                
            }
        }

    }
    public void choices(int i, ArrayList<Player> players, int choice, ArrayList<Bet> bets) throws IOException{
        if (choice == 1) {
                    System.out.println("What num do you want to bet on?");  //place chip here (on board)
                    int betNum = Integer.parseInt(stdin.readLine());

                    if (betNum == 0) {
                        System.out.println("You cannot bet on 0");
                    } else {
                        ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                        numsBetOn.add(betNum);

                        System.out.println("How much do you want to bet?");
                        int amountBet = Integer.parseInt(stdin.readLine());

                        players.get(i).setBet(amountBet);
                        bets.add(new Bet(amountBet, 1, i, numsBetOn));
                    }
                }
                if (choice == 2) {
                    System.out.println("Pick you first number");
                    int betNum1 = Integer.parseInt(stdin.readLine());
                    int betNum2 = num2Split(betNum1);

                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = betNum1; j <= betNum2; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 2, i, numsBetOn));

                }
                if (choice == 3) {
                    System.out.println("Pick the first number of the row you want");
                    int betNum1 = Integer.parseInt(stdin.readLine());
                    int betNum2 = betNum1 + 2;

                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = betNum1; j <= betNum2; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 3, i, numsBetOn));

                }
                if (choice == 4) {
                    System.out.println("Pick the first number of the corner you want");
                    int betNum1 = Integer.parseInt(stdin.readLine());

                    if (betNum1 != 10 || betNum1 != 11 || betNum1 != 22 || betNum1 != 23) {
                        System.out.println("that's not the 1st number of a corner");
                    } else {
                        int betNum2 = betNum1 + 4;

                        ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                        for (int j = betNum1; j <= betNum2; j++) {
                            numsBetOn.add(j);
                        }
                        numsBetOn.remove(2);//remove 3rd num cuz its not part of the corner numbers

                        System.out.println("How much do you want to bet?");
                        int amountBet = Integer.parseInt(stdin.readLine());

                        players.get(i).setBet(amountBet);
                        bets.add(new Bet(amountBet, 4, i, numsBetOn));
                    }
                }
                if (choice == 5) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 1; j <= 18; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 5, i, numsBetOn));
                }
                if (choice == 6) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 19; j <= 36; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 6, i, numsBetOn));
                }
                if (choice == 7) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 2; j <= 36; j += 2) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 7, i, numsBetOn));
                }
                if (choice == 8) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 1; j <= 36; j += 2) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 8, i, numsBetOn));
                }
                if (choice == 9) {

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 9, i, blackNums));
                }
                if (choice == 10) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();

                    for (int j = 1; j <= 36; j++) {
                        if (!(blackNums.contains(j))) {//if black nums doesnt contain j, add it to numsBetOn
                            numsBetOn.add(j);
                        }
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 10, i, numsBetOn));
                }

                if (choice == 11) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 1; j <= 12; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 11, i, numsBetOn));
                }
                if (choice == 12) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 13; j <= 24; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 12, i, numsBetOn));
                }
                if (choice == 13) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 25; j <= 36; j++) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 13, i, numsBetOn));
                }
                if (choice == 14) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 1; j <= 36; j += 3) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 14, i, numsBetOn));
                }
                if (choice == 15) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 2; j <= 36; j += 3) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 15, i, numsBetOn));
                }
                if (choice == 16) {
                    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
                    for (int j = 3; j <= 36; j += 3) {
                        numsBetOn.add(j);
                    }

                    System.out.println("How much do you want to bet?");
                    int amountBet = Integer.parseInt(stdin.readLine());

                    players.get(i).setBet(amountBet);
                    bets.add(new Bet(amountBet, 16, i, numsBetOn));
                }
    }

    public void fillBlackNums() {

        for (int j = 2; j <= 10; j += 2) {
            blackNums.add(j);
        }
        for (int j = 20; j <= 28; j += 2) {
            blackNums.add(j);

        }
        for (int j = 11; j <= 18; j += 2) {
            blackNums.add(j);
        }
        for (int j = 29; j <= 36; j += 2) {
            blackNums.add(j);
        }
    }

    public int num2Split(int betNum1) throws IOException {
        int betNum2 = -1;

        while (true) {
            System.out.println("Pick your second number");
            if (betNum2 != betNum1 + 1 || betNum2 != betNum1 - 1 || betNum2 != betNum1 + 3 || betNum2 != betNum1 - 3) {
                betNum2 = Integer.parseInt(stdin.readLine());
                return betNum2;
            } else {
                System.out.println("You didn't enter a valid 2nd number. Try again");
            }

        }
    }

    public void Winner() {
        System.out.println("You Won!!!!!!!!");
    }

    public static void checkWin() {
        //get odds from bet method and see if they win
    }

    public static void payout() {
        //get pay ratio (odds-1), add to player's account
    }





}
