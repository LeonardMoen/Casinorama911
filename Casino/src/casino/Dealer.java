package casino;

public class Dealer {

    private PocketHand dealerHand;
    private int total;

    public Dealer(Deck deck) {
        this.dealerHand = new PocketHand(deck);
    }

    public PocketHand getDealerHand() {
        return dealerHand;
    }

    public void setTotal() {
        this.total = 0;
        for (int i = 0; i < dealerHand.getPlayerHand().size(); i++) {
            this.total = dealerHand.getPlayerHand().get(i).getWorth();
        }
    }

    public void setDealerHand(PocketHand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public boolean checkInsured() {
        return this.dealerHand.getPlayerHand().get(0).getValue() == 1;
    }

    public boolean checkSeventeen() {
        return total >= 17;
    }
}
