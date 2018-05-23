
package casino;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class Hand implements Comparable<Card>{

    Card[]hand=new Card[5];
    public Hand() {
    }

    public Card[] getHand() {
        return hand;
    }

    
    
    public int handValue(){
        Arrays.sort(hand);
        for (Card card : hand) {
            System.out.println(card);
        }


        int handValue=0;
        Arrays.sort(hand);
        if (checkThreeKind(0)){
            handValue=3;
            return handValue;
        }
       
        if (checkTwoPair()){
            handValue=2;
            return handValue;
        }
        if(checkPair()){
            handValue=1;
            return handValue;
        }  
        return handValue;
    }
    
    private boolean checkPair(){
        for (int i =0;i<hand.length;i++) {
            for (int j =0;j<hand.length;j++) {
                if(hand[i].getValue()==hand[j].getValue()&&!(hand[i].equals(hand[j]))){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkTwoPair(){
        int numPairs = 0;
        
        for (int i =0;i<hand.length-1;i++) {
            if(hand[i].getValue()==hand[i+1].getValue()){
                numPairs+=1;
                i+=1;
            }
        }
        if(numPairs==2){
            return true;
        }
        return false;
    }
    
    private boolean checkThreeKind(int start){
        int numSame=1;
        if(start==hand.length-2){
            return false;
        }
        for(int i=start+1;i<hand.length;i++){
            if(hand[i].getValue()==hand[start].getValue()){
                numSame+=1;
            }
        }
        if(numSame==3){
            return true;
        }
        return checkThreeKind(start+1);
    }
    
    

    

    @Override
    public int compareTo(Card t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
