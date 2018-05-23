package blackjackjava;

public class Hand {

    private Card[] playerHand = new Card[2];

    public Hand(Deck deck) {
        for (int x = 0; x < playerHand.length; x++) {
            playerHand[x] = deck.getDeck().get(x);
            deck.getDeck().remove(x);
        }
    }

    public Card[] getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Card[] playerHand) {
        this.playerHand = playerHand;
    }

}
