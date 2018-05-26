
package casino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Casino {

    public static void main(String[] args){
        ArrayList<Card>possibleCard= new ArrayList<Card>();
        ArrayList<Player>players = new ArrayList<Player>();
        for(int i =0;i<8;i++){
            String name = "Player"+i;
            players.add(new Player(name));
        }
        //Hands that have broke code
// <editor-fold defaultstate="collapsed" desc=" DESCRIPTION ">
        Card card=new Card(12,"Club");
        Card card1=new Card(7,"Club");
        Card card2=new Card(12,"Spade");
        Card card3=new Card(2,"Club");
        Card card4=new Card(13,"Diamond");
        Card card5=new Card(5,"Heart");
        Card card6=new Card(5,"Heart");

//        Card card=new Card(13,"Spade");
//        Card card1=new Card(10,"Club");
//        Card card2=new Card(2,"Spade");
//        Card card3=new Card(11,"Spade");
//        Card card4=new Card(6,"Spade");
//        Card card5=new Card(2,"Club");
//        Card card6=new Card(7,"Spade");
//Q
//        Card card=new Card(13,"Heart");
//        Card card1=new Card(11,"Diamond");
//        Card card2=new Card(10,"Heart");
//        Card card3=new Card(5,"Spade");
//        Card card4=new Card(9,"Club");
//        Card card5=new Card(5,"Club");
//        Card card6=new Card(11,"Spade");
// </editor-fold>
         Deck deck = new Deck();
         deck.shuffle();
         dealPlayers(players);
//        for(Card card7: hand.getHand()){
//            System.out.println(card7);
//        }
//        System.out.println(hand.handValue());
    }
    
    public static Hand determineHand(ArrayList<Card>possibleCard,Hand hand,int num,Hand highestHand){
        if(possibleCard.size()==0||num==5){
            return highestHand;
        }
        //System.out.println(hand.handValue()+" "+highestHand.handValue());
        if(!(hand.getHand()[4].equals(new Card(0,"empty")))&&hand.handValue()>highestHand.handValue()){
            return hand;
        }
        Card card =possibleCard.get(0);
        boolean contains= false;
        for (Card card1 : hand.getHand()) {
            if(card.equals(card1)){
                contains=true;
            }
        }
        if(contains==false){
            hand.getHand()[num]=card;
//            for (Card card1 : hand.getHand()) {
//                System.out.println(card1);
//            }
            possibleCard.remove(0);
        }
        else{
            possibleCard.remove(0);
            return determineHand(possibleCard,hand,num,highestHand);
        }
        if(determineHand(possibleCard,hand,num+1,highestHand).handValue()>highestHand.handValue()){
            return determineHand(possibleCard,hand,num+1,highestHand);
        }

        return determineHand(possibleCard,hand,num,highestHand);
    }
    
    public static Hand runDetermineHand(PocketHand pocketHand, ArrayList<Card>communityCards){
//        possibleCard.add(deck.getDeck().get(0));
//        possibleCard.add(deck.getDeck().get(1));
//        possibleCard.add(deck.getDeck().get(2));
//        possibleCard.add(deck.getDeck().get(3));
//        possibleCard.add(deck.getDeck().get(4));
//        possibleCard.add(deck.getDeck().get(5));
//        possibleCard.add(deck.getDeck().get(6));
//      
//        Collections.sort(possibleCard);
//        for (Card card7 : possibleCard) {
//            System.out.println(card7);
//        }
//        System.out.println("");
//        Hand hand = determineHand(possibleCard, new Hand(),0,new Hand());
//        for (int i = 0; i < 10; i++) {
//            //System.out.println("");
//            Hand highestHand=hand.copyOf(hand);
//            possibleCard.add(deck.getDeck().get(0));
//            possibleCard.add(deck.getDeck().get(1));
//            possibleCard.add(deck.getDeck().get(2));
//            possibleCard.add(deck.getDeck().get(3));
//            possibleCard.add(deck.getDeck().get(4));
//            possibleCard.add(deck.getDeck().get(5));
//            possibleCard.add(deck.getDeck().get(6));
////            possibleCard.add(card);
////            possibleCard.add(card1);
////            possibleCard.add(card2);
////            possibleCard.add(card3);
////            possibleCard.add(card4);
////            possibleCard.add(card5);
////            possibleCard.add(card6);
//            hand = determineHand(possibleCard, new Hand(),0,highestHand);
//        }
////        possibleCard.add(card);
////        possibleCard.add(card1);
////        possibleCard.add(card2);
////        possibleCard.add(card3);
////        possibleCard.add(card4);
////        possibleCard.add(card5);
////        possibleCard.add(card6);
//        possibleCard.add(deck.getDeck().get(0));
//        possibleCard.add(deck.getDeck().get(1));
//        possibleCard.add(deck.getDeck().get(2));
//        possibleCard.add(deck.getDeck().get(3));
//        possibleCard.add(deck.getDeck().get(4));
//        possibleCard.add(deck.getDeck().get(5));
//        possibleCard.add(deck.getDeck().get(6));
//        Collections.sort(possibleCard);
//        Hand lowestFive=new Hand();
//        for(int i = 2;i<possibleCard.size();i++){
//            lowestFive.getHand()[i-2]=possibleCard.get(i);
//        }
//        //System.out.println(lowestFive.handValue());
//        if(lowestFive.handValue()>hand.handValue()){
//            hand=lowestFive;
//        }
          return new Hand();
    }
    
    public static void dealPlayers(ArrayList<Player>players){
        for(int i =0;i<2;i++){
             for (Player player : players) {
                 player.getPocketHand()
             }
         }
    }
}
