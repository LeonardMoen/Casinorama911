
package casino;

import java.util.ArrayList;

public class PocketHand{
    private ArrayList<Card>pocketHand;

    public PocketHand() {
        pocketHand= new ArrayList<Card>();
    }

    public ArrayList<Card> getPocketHand() {
        return pocketHand;
    }

    public void setPocketHand(ArrayList<Card> pocketHand) {
        this.pocketHand = pocketHand;
    }
    
    
    public boolean checkSuited(){
        
        if(pocketHand.get(0).getSuit().equals(pocketHand.get(1).getSuit())){
            return true;
        }
        return false;
    }
    
    public boolean pocketPair(){
        if(pocketHand.get(0).getValue()==pocketHand.get(1).getValue()){
            return true;
        }
        return false;
    }
}
