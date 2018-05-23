
package casino;

public class PocketHand extends Hand{
    public boolean checkSuited(){
        if(hand[0].getSuit().equals(hand[1].getSuit())){
            return true;
        }
        return false;
    }
    
    public boolean pocketPair(){
        if(hand[0].getValue()==hand[1].getValue()){
            return true;
        }
        return false;
    }
}
