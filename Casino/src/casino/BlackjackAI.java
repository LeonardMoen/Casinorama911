package casino;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class BlackjackAI extends Player {

    private int runningCount = 0, bettingUnit = 0, realBet = 0;
    private double numDecks = 0.0, trueCount = 0.0;
    private boolean hit, split, stay, dDown, insurance;
    // private ArrayList<Card> counted = new ArrayList<>();
    private Random r = new Random();

    public BlackjackAI(Deck deck, ArrayList<Player> players, Dealer dealer) {
        super("John", deck);
        setNumDecks(deck);
        setAi();
    }

    public void set(Deck deck, ArrayList<Player> players, Dealer dealer) {
        setRunningCount(players, dealer);
        setNumDecks(deck);
        setTrueCount();
        setBettingUnit();
        setRealBet();
    }

    public void setRunningCount(ArrayList<Player> players, Dealer dealer) {
        for (int i = 0; i < players.size(); i++) {
            for (int h = 0; h < players.get(i).getPocketHands().size(); h++) {
                for (int c = 0; c < players.get(i).getPocketHands().get(h).getPlayerHand().size(); c++) {
                    if (players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() == 10 || players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() == 1) {
                        this.runningCount -= 1;
                    } else if (players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() >= 2 && players.get(i).getPocketHands().get(h).getPlayerHand().get(c).getWorth() <= 6) {
                        this.runningCount += 1;
                    }
                }
            }
        }
        for (int d = 0; d < dealer.getDealerHand().getPlayerHand().size(); d++) {
            if (dealer.getDealerHand().getPlayerHand().get(d).getWorth() == 10 || dealer.getDealerHand().getPlayerHand().get(d).getWorth() == 1) {
                this.runningCount -= 1;
            } else if (dealer.getDealerHand().getPlayerHand().get(d).getWorth() >= 2 && dealer.getDealerHand().getPlayerHand().get(d).getWorth() <= 6) {
                this.runningCount += 1;
            }
        }
    }

    @Override
    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance() {
        this.insurance = this.runningCount >= 5;
    }

    public void setAi() {
        super.setAi(true);
    }

    public void setTrueCount() {
        this.trueCount = this.runningCount / this.numDecks;
    }

    public void setNumDecks(Deck deck) {
        numDecks = 0;
        int cards = 0;
        double remainder;
        for (int i = 0; i < deck.getDeck().size(); i++) {
            cards += 1;
        }
        remainder = cards % 52;
        if (remainder >= 13 && remainder < 39) {
            numDecks += 0.5;
        } else if (remainder >= 39) {
            numDecks += 1;
        }
        numDecks += (int) (cards / 52);
    }

    public void setHit(Deck deck, int n) {
        if (super.getRealTotal(n) <= 15) {
            this.hit = true;
        } else if (super.getRealTotal(n) + deck.getDeck().get(0).getWorth() > 21) {
            this.hit = false;
            super.setStay(true);
        } else if (super.getRealTotal(n) + deck.getDeck().get(0).getWorth() <= 21) {
            this.hit = true;
        }
    }

    public void setSplit() {
        if (super.getChips() >= this.realBet) {
            int eh = r.nextInt(2);
            if (eh == 0) {
                this.split = true;
            } else if (eh == 1) {
                this.split = false;
            }
        }
    }

    public void setdDown(Deck deck, int n) {
        if (super.getChips() >= this.realBet) {
            if (super.getRealTotal(n) >= 9 && super.getRealTotal(n) <= 11) {
                if (super.getRealTotal(n) + deck.getDeck().get(0).getWorth() >= 16) {
                    dDown = true;
                } else {
                    dDown = false;
                }
            }
        }
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isSplit() {
        return split;
    }

    public boolean isdDown() {
        return dDown;
    }

    public void setBettingUnit() {
        this.bettingUnit = super.getChips() / 10;
    }

    public void setRealBet() {
        if (this.trueCount == 0) {
            setBettingUnit();
            this.realBet = this.bettingUnit;
        } else if (this.trueCount - 1 < 0) {
            this.realBet = super.getChips() / r.nextInt(((25 - 10) + 1) + 10);
        } else {
            this.realBet = (int) ((this.trueCount - 1) * this.bettingUnit);
        }
    }

    public int getRealBet() {
        return realBet;
    }

    public int getRunningCount() {
        return runningCount;
    }

    public double getTrueCount() {
        return trueCount;
    }

    public double getNumDecks() {
        return numDecks;
    }

    public int getBettingUnit() {
        return bettingUnit;
    }

}
