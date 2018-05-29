package casino;

import java.util.ArrayList;

public class Player {

    String name;
    int chips;
    ArrayList<PocketHand> pocketHand = new ArrayList<>();
    ArrayList<Integer> numsBetOn = new ArrayList<Integer>();
    Hand hand;
    int playerNum, total, bet, insuranceAmount;
    boolean insurance = false, stay = false, naturalBlackJack;

    public Player(String name) {
        this.name = name;
        this.chips = 500;
    }

    public void setInsuranceAmount(int insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public boolean isNaturalBlackJack() {
        return naturalBlackJack;
    }

    public void setNaturalBlackJack(boolean naturalBlackJack) {
        this.naturalBlackJack = naturalBlackJack;
    }

    public int getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount() {
        this.insuranceAmount = bet / 2;
        chips = chips - insuranceAmount;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public boolean isStay() {
        return stay;
    }

    public Player(String name, Deck deck) {
        this.name = name;
        this.chips = 500;
        this.pocketHand.add(new PocketHand(deck));
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setBet(int bet) {
        this.bet = bet;
        chips = chips - bet;
    }

    public int setTotal(int n) {
        this.total = 0;
        int optionalTotal = 0;
        for (int i = 0; i < pocketHand.get(n).getPlayerHand().size(); i++) {
            if (pocketHand.get(n).getPlayerHand().get(i).getWorth() == 1) {
                optionalTotal += 11;
                total += 1;
            } else {
                optionalTotal += pocketHand.get(n).getPlayerHand().get(i).getWorth();
                total += pocketHand.get(n).getPlayerHand().get(i).getWorth();
            }
        }
        if (optionalTotal <= 21) {
            return optionalTotal;
        } else {
            return 0;
        }
    }

    public void setObTotal(int n) {
        this.total = n;
    }

    public int getTotal(int n) {
        total = 0;
        for (int i = 0; i < pocketHand.get(n).getPlayerHand().size(); i++) {
            total += pocketHand.get(n).getPlayerHand().get(i).getWorth();
        }
        return total;
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
        this.pocketHand.get(0).hitCard(deck);
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

    public void payout(int amount) {
        this.setChips(chips + amount);
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public ArrayList<Integer> getNumsBetOn() {
        return numsBetOn;
    }

    public void setNumsBetOn(ArrayList<Integer> numsBetOn) {
        this.numsBetOn = numsBetOn;
    }
}
