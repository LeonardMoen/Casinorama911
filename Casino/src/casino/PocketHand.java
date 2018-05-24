package casino;

import java.util.ArrayList;

public class PocketHand {

    private ArrayList<Card> playerHand = new ArrayList<>();

    public PocketHand(Deck deck) {
        for (int x = 0; x < 2; x++) {
            playerHand.add(deck.getDeck().get(x));
            deck.getDeck().remove(x);
        }
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void hitCard(Deck deck) {
        playerHand.add(deck.getDeck().get(0));
        deck.getDeck().remove(0);
    }

    public boolean checkBust() {
        int check = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            check += playerHand.get(i).getValue();
        }
        return check > 21;
    }

    public boolean checkSplit() {
        return this.playerHand.get(0).getValue() == this.playerHand.get(1).getValue();
    }

    public boolean checkBlackJack() {
        int check = 0;
        for (int i = 0; i < playerHand.size(); i++) {
            check += playerHand.get(i).getValue();
        }
        return check == 21;
    }
//
//    public boolean checkSuited() {
//        if (hand[0].getSuit().equals(hand[1].getSuit())) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean pocketPair() {
//        if (hand[0].getValue() == hand[1].getValue()) {
//            return true;
//        }
//        return false;
//    }

}
