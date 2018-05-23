package blackjackjava;

import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Comparable {

    private ArrayList<Card> deck = new ArrayList<>();

    public Deck() {
        for (int suit = 0; suit < 4; suit++) {
            for (int i = 1; i <= 13; i++) {
                deck.add(new Card(suit, i));
            }
        }
    }


    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
