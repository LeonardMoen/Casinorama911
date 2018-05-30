
package casino;


public class Player implements Comparable {
    private String name;
    private int chips;
    private PocketHand pocketHand;
    private Hand hand;
    private int playerNum;
    private Blind blind;
    private int chipsInCurrent;

    public Player(String name, int playerNum) {
        this.name = name;
        this.playerNum=playerNum;
        this.chips = 500;
        blind=new Blind();
        pocketHand = new PocketHand();
        this.chipsInCurrent=0;
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

    public Blind getBlind() {
        return blind;
    }

    public int getChipsInCurrent() {
        return chipsInCurrent;
    }

    public void setChipsInCurrent(int chipsInCurrent) {
        this.chipsInCurrent = chipsInCurrent;
    }
    

    public void setBlind(Blind blind) {
        this.blind = blind;
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

    @Override
    public int compareTo(Object t) {
        Player player = (Player)t;
        if(playerNum>player.getPlayerNum()){
            return 1;
        }
        else{
            return -1;
        }
    }
}
