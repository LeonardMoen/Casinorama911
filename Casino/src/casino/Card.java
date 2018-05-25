package casino;

public class Card implements Comparable {

    int value, worth;
    String suit;

    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth() {
        switch (value) {
            case 11:
                worth = 10;
                break;
            case 12:
                worth = 10;
                break;
            case 13:
                worth = 10;
                break;
            default:
                worth = value;
                break;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        String display = "";
        if (value >= 2 && value <= 10) {
            display += value;
        } else if (value == 1) {
            display += "Ace";
        } else if (value == 11) {
            display += "Jack";
        } else if (value == 12) {
            display += "Queen";
        } else if (value == 13) {
            display += "King";
        }
        display += " of " + suit + "s";
        return display;
    }

    @Override
    public boolean equals(Object o) {
        Card card1 = (Card) o;
        if (value == card1.getValue() && suit.equalsIgnoreCase(card1.getSuit())) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object t) {
        Card card = (Card) t;

        if (value == card.getValue()) {
            return 0;
        } else if (value == 1) {
            return -1;
        } else if (value > card.getValue() && card.getValue() != 1) {
            return -1;
        } else {
            return 1;
        }

    }
}
