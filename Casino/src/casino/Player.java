
package casino;


public class Player {
    String name;
    int chips;
    PocketHand pocketHand;
    Hand hand;
    int playerNum;

    public Player(String name) {
        this.name = name;
        this.chips = 500;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public PocketHand getPocketHand() {
        return pocketHand;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void setPocketHand(PocketHand pocketHand) {
        this.pocketHand = pocketHand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
