package casino;

import java.util.ArrayList;

public class Player {

    String name;
    int chips;
    ArrayList<PocketHand> pocketHand = new ArrayList<>();
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
        setTotal();
    }

    public void setBet(int bet) {
        this.bet = bet;
        chips = chips - bet;
    }

    public int setTotal() {
        this.total = 0;
        int optionalTotal = 0;
        for (int i = 0; i < pocketHand.get(0).getPlayerHand().size(); i++) {
            if (pocketHand.get(0).getPlayerHand().get(i).getValue() == 1) {
                optionalTotal += 11;
                total += 1;
            } else {
                optionalTotal += pocketHand.get(0).getPlayerHand().get(i).getValue();
                total += pocketHand.get(0).getPlayerHand().get(i).getValue();
            }
        }
        if (optionalTotal <= 21) {
            return optionalTotal;
        } else {
            return 0;
        }
    }

    public int getTotal() {
        return total;
    }

    public int getBet() {
        return bet;
    }

    public void ifSplit(Deck deck) {
        this.pocketHand.add(new PocketHand(deck, pocketHand.get(0).getPlayerHand().get(1).getValue(), pocketHand.get(0).getPlayerHand().get(1).getSuit()));
        this.pocketHand.remove(pocketHand.get(0).getPlayerHand().get(1));
    }

    public void setPocketHand(Deck deck) {
        this.pocketHand.clear();
        this.pocketHand.add(new PocketHand(deck));
    }

    public ArrayList<PocketHand> getPocketHand() {
        return pocketHand;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
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

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public ArrayList<PocketHand> getPocketHand() {
        return pocketHand;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
