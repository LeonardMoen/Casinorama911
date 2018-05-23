package blackjackjava;

public class Card {

    private int suit, number;

    public Card(int suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        switch (number) {
            case 1:
                return 11;
            case 12:
                return 11;
            case 13:
                return 11;
            default:
                return number;
        }
    }

    @Override
    public String toString() {
        switch (suit) {
            case 0:
                return number + " of Hearts";
            case 1:
                return number + " of Clubs";
            case 2:
                return number + " of Diamonds";
            case 3:
                return number + " of Spades";
        }
        return "";
    }
}
