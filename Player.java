
package blackjackjava;

public class Player {
    private String name;
    private double value;
    private Hand pocketHand;

    public Player(String name, double value, Hand pocketHand) {
        this.name = name;
        this.value = value;
        this.pocketHand = pocketHand;
    }

    public Player(String name, Deck deck) {
        this.name = name;
        this.value = 500;
        this.pocketHand = new Hand(deck);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Hand getPocketHand() {
        return pocketHand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setPocketHand(Hand pocketHand) {
        this.pocketHand = pocketHand;
    }
    
    
}
