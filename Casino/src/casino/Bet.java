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
public class Bet {
    
    
    //make methods of all types of bets, return a num of squares bet on (payout is (37/odds)-1 to keep house advantage)
   
    public void Bet(){
        
    }
    public int single(){
        return 1;
    }
    public int splitH(){
        return 2;       //check if it is horizontal in roulette class
    }
    public int splitV(){
        return 4;       //check if it is vertical in roulette class
    }
    public int street(){
        return 3;
    }
    public int doubleStreet(){
        return 6;
    }
    public int corner(){    //check if it is a corner num in roulette class
        return 4;
    }
    public int lowHighEvenOddRedBlack(){
        return 18;
    }
    public int dozenOrSnakeOrColumn(){
        return 12;
    }

    

    
    
    
    
    
    
}
