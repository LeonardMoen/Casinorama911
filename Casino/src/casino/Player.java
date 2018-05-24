package casino;

import java.util.ArrayList;

public class Player {

    String name;
    int chips;
    ArrayList<PocketHand> pocketHand;
    Hand hand;
    int playerNum, total, bet;

    public Player(String name) {
        this.name = name;
        this.chips = 500;
    }

    public Player(String name, Deck deck) {
        this.name = name;
        this.chips = 500;
        this.pocketHand.add(new PocketHand(deck));
        this.total = pocketHand.get(0).getPlayerHand().get(0).getValue() + pocketHand.get(0).getPlayerHand().get(1).getValue();
    }

    public void setBet(int bet) {
        this.bet = bet;
        chips = chips - bet;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public int getBet() {
        return bet;
    }

    public void ifSplit(Deck deck) {
        this.pocketHand.add(new PocketHand(deck,pocketHand.get(0).getPlayerHand().get(1).getValue(),pocketHand.get(0).getPlayerHand().get(1).getSuit()));
    }

    public void setPocketHand(Deck deck) {
        this.pocketHand.clear();
        this.pocketHand.add(new PocketHand(deck));
        this.total = pocketHand.get(0).getPlayerHand().get(0).getValue() + pocketHand.get(0).getPlayerHand().get(1).getValue();
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
