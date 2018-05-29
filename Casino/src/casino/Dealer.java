package casino;

public class Dealer {

    private PocketHand dealerHand;
    private int total;
    private boolean bust = false;

    public Dealer(Deck deck) {
        this.dealerHand = new PocketHand(deck);
    }

    public PocketHand getDealerHand() {
        return dealerHand;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public void setTotal() {
        this.total = 0;
        for (int i = 0; i < dealerHand.getPlayerHand().size(); i++) {
            this.total += dealerHand.getPlayerHand().get(i).getWorth();
        }
    }

    public int getTotal() {
        setTotal();
        return total;
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
