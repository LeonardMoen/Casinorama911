
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
        if(checkRoyalFlush()){
            handValue=9;
            return handValue;
        }
            
        if(checkStraight()&&checkFlush()){
            handValue=8;
            return handValue;
        }
        if(checkFourKind()){
            handValue=7;
            return handValue;
        }
        if(checkFullHouse()){
            handValue=6;
            return handValue;
        }
        if(checkFlush()){
            handValue=5;
            return handValue;
        }
        else if(checkStraight()){
            handValue=4;
            return handValue;
        }
        else if (checkThreeKind(0)){
            handValue=3;
            return handValue;
        }
       
        else if (checkTwoPair()){
            handValue=2;
            return handValue;
        }
        else if(checkPair()){
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
    
    private boolean checkStraight(){
        if(hand[1].getValue()==hand[0].getValue()-1&&hand[2].getValue()==hand[0].getValue()-2&&hand[3].getValue()==hand[0].getValue()-3&&hand[4].getValue()==hand[0].getValue()-4){
            return true;
        }
        else if(hand[0].getValue()==1){
            if(hand[1].getValue()==13&&hand[2].getValue()==12&&hand[3].getValue()==11&&hand[4].getValue()==10){
                return true;
            }
            else if(hand[1].getValue()==5&&hand[2].getValue()==4&&hand[3].getValue()==3&&hand[4].getValue()==2){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkFlush(){
        String suit = hand[0].getSuit();
        for(Card card : hand){
            if(!(card.getSuit().equals(suit))){
                return false;
            }
        }
        return true;
    }
    
    private boolean checkFullHouse(){
        boolean triple=false,pair=false;
        int valueOfTrip=0;
        for(int i =0;i<hand.length-2;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()){
                triple=true;
                valueOfTrip=hand[i].getValue();
            }
        }
        for(int i =0;i<hand.length-1;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()!=valueOfTrip){
                pair=true;
            }
        }
        
        if(triple&&pair){
            return true;
        }
        return false;
    }
    
    private boolean checkFourKind(){
        for(int i =0;i<hand.length-3;i++){
            if(hand[i].getValue()==hand[i+1].getValue()&&hand[i].getValue()==hand[i+2].getValue()&&hand[i].getValue()==hand[i+3].getValue()){
                return true;
            }
        }
        return false;
    }
    
    private boolean checkRoyalFlush(){
        boolean straight = false;
        boolean flush = true;
        
        if(hand[0].getValue()==1&&hand[1].getValue()==13&&hand[2].getValue()==12&&hand[3].getValue()==11&&hand[4].getValue()==10){
             straight = true;
        }
        
        String suit = hand[0].getSuit();
        for(Card card : hand){
            if(!(card.getSuit().equals(suit))){
                flush = false;
            }
        }
        if(flush&&straight){
            return true;
        }
        return false;
        
    }

    

    @Override
    public int compareTo(Card t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
