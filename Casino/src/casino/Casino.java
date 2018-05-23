
package casino;

public class Casino {

    public static void main(String[] args) {
        Card card=new Card(1,"Spade");
        Card card1=new Card(1,"Heart");
        Card card2=new Card(4,"Diamond");
        Card card3=new Card(12,"Spade");
        Card card4=new Card(12,"Heart");
        Hand hand = new Hand();
        hand.getHand()[0]=card;
        hand.getHand()[1]=card1;
        hand.getHand()[2]=card2;
        hand.getHand()[3]=card3;
        hand.getHand()[4]=card4;
        System.out.println(hand.handValue());
    }
    
}
