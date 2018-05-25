/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author laua8572
 */
public class Roulette {

    private Bet bet;
    private int[][] board = new int[13][3];

    static InputStreamReader inStream = new InputStreamReader(System.in);
    static BufferedReader stdin = new BufferedReader(inStream);
    static Random rand = new Random();

    public Roulette() {

    }

    public void playRoulette() throws IOException {
        
        
        
        createBoard(board);
        Bet bet = new Bet();

        System.out.println("Pick a bet type");
        System.out.println("(1)Single\t(2)Split\t(3)Street\t(4)Double Street\t(5)Corner\t(6)High, Low, even, odd, black or red");
        System.out.println("(7)Dozen or snake or Column");
        int choice = Integer.parseInt(stdin.readLine());

        System.out.println("How much do you want to bet?"); //need to substract bet from player chips
        int amountBet = Integer.parseInt(stdin.readLine());

        if (choice == 1) {
            System.out.println("What num do you want to bet on?");  //place chip here (on board)
            int betNum = Integer.parseInt(stdin.readLine());
            
        }

    }
    
    public void Winner(){
        System.out.println("You Won!!!!!!!!");
    }

    public static void createBoard(int[][] board) {
        board[0][0] = 0;
        int counter = 1;
        for (int i = 1; i <= 12; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = counter;
                counter++;
            }
        }
        displayBoard(board);
    }

    public static void displayBoard(int[][] board) {
        for (int i = 0; i <= 12; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public static void checkWin() {
        //get odds from bet method and see if they win
    }

    public static void payout() {
        //get pay ratio (odds-1), add to player's account
    }

}
