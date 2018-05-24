package casino;

import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Comparable {

    ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Heart");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Diamond");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Club");
            deck.add(card);
        }
        for (int i = 1; i <= 13; i++) {
            Card card = new Card(i, "Spade");
            deck.add(card);
        }
    }

    public Deck(int n) {
        While(n > 0)
        {
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Heart");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Diamond");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Club");
                deck.add(card);
            }
            for (int i = 1; i <= 13; i++) {
                Card card = new Card(i, "Spade");
                deck.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    @Override
    public int compareTo(Object t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
