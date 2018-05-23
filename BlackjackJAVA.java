package blackjackjava;

public class BlackjackJAVA {

    public static void main(String[] args) {
        Deck a = new Deck();
        a.shuffle();
        System.out.println(a.getDeck());
    }

}
