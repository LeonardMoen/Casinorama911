/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casino;

/**
 *
 * @author laua8572
 */
public class Roulette {

    private Bet bet;
    private int[][] board = new int [13][3];

    public void playRoulette() {
        createBoard(board);
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
                System.out.print(board[i][j]+"\t");
            }
            System.out.println("");
        }
    }
    
    public static void checkWin(){
        //get odds from bet method and see if they win
    }
    public static void payout(){
        //get pay ratio (odds-1), add to player's account
    }
    

}
