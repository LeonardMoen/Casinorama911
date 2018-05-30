package casino;

import java.io.*;

public class Casino {
    
    public static InputStreamReader inStream = new InputStreamReader(System.in);
    public static BufferedReader stdin = new BufferedReader(inStream);
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Card card = new Card(4, "Spade");
        Card card1 = new Card(3, "Heart");
        Card card2 = new Card(1, "Diamond");
        Card card3 = new Card(2, "Spade");
        Card card4 = new Card(5, "Heart");
        Hand hand = new Hand();
        hand.getHand()[0] = card;
        hand.getHand()[1] = card1;
        hand.getHand()[2] = card2;
        hand.getHand()[3] = card3;
        hand.getHand()[4] = card4;
        System.out.println(hand.handValue());
        BlackjackJAVA.main(args);
    }
    
}
