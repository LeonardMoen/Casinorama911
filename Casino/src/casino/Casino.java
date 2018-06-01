package casino;

import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Casino {
    static Scanner sc = new Scanner(System.in);
    static int pot=0;
    public static void main(String[] args){
        int response;
        ArrayList<Card>communityCards=new ArrayList<Card>();
        ArrayList<Card>possibleCard= new ArrayList<Card>();
        ArrayList<Player>players = new ArrayList<Player>();
        for(int i =1;i<=4;i++){
            String name = "Player "+i;
            players.add(new Player(name,i));
        }

        //Hands that have broke code
// <editor-fold defaultstate="collapsed" desc=" DESCRIPTION ">
        Card card=new Card(1,"Club");
        Card card1=new Card(7,"Club");
        Card card2=new Card(9,"Club");
        Card card3=new Card(4,"Club");
        Card card4=new Card(10,"Club");
        Card card5=new Card(12,"Club");
        Card card6=new Card(2,"Club");

// </editor-fold>

 



        do{
            System.out.println("NEW HAND");
            Deck deck = new Deck();
            deck.shuffle();
            ArrayList<Player>playersInRound=new ArrayList<Player>();
            for (Player player : players) {
                if(player.getChips()!=0){
                    playersInRound.add(player);
                }
            }
            for(int i = communityCards.size()-1;i>=0;i--){
                communityCards.remove(communityCards.get(i));
            }
            for (Player player : playersInRound) {
                player.setTotalChipsInPot(0);
                player.setPocketHand(new PocketHand());
            }
            dealPlayers(playersInRound,deck);


            int smallBlindNum = setBlinds(playersInRound); 

            for (Player player : playersInRound) {
                System.out.println(player.getName()+" "+player.getBlind().getTypeBlind()+" Chips: "+player.getChips());
                for(Card card7:player.getPocketHand().getPocketHand()){
                    System.out.println(card7);
                }
                System.out.println("");
            }
            for(int i =0;i<4;i++){
               Collections.sort(playersInRound);
               playersInRound = roundOfBetting(i,playersInRound,smallBlindNum); 
               if(playersInRound.size()==1){
                   break;
               }
               for (Player player : playersInRound) {
                    player.setChipsInCurrent(0);
               }
                if(i==0){
                    System.out.println("Round 1");
                    deck.getDeck().remove(0);
                    for(int j=0;j<3;j++){
                        communityCards.add(deck.getDeck().get(0));
                        deck.getDeck().remove(0);
                    }
                }
                else if(i==3){
                    System.out.println("Round 4");
                }
                else{
                    System.out.println("Round 2");
                    deck.getDeck().remove(0);
                    communityCards.add(deck.getDeck().get(0));
                    deck.getDeck().remove(0);
                }
                System.out.println("Community Cards: ");
                for (Card card7 : communityCards) {
                    System.out.println(card7);
                }
            }
            for (Player player : playersInRound) {
                player.setHand(determineHand(player.getPocketHand(),communityCards));
            }
            do{
                distributeWin(playersInRound);
            }while(pot>0);
        }while(true); 
//        for(Card card7: hand.getHand()){
//            System.out.println(card7);
//        }
//        System.out.println(hand.handValue());
    }
    
    public static Hand determineHand(PocketHand pocketHand,ArrayList<Card>communityCards){
        ArrayList<Card>possibleCard = new ArrayList<Card>();
        for (Card communityCard : communityCards) {
            possibleCard.add(communityCard);
        }
        for (Card card : pocketHand.getPocketHand()) {
            possibleCard.add(card);
        }
        Collections.sort(possibleCard);
        Hand highestHand = new Hand();
        int numCard = 0;
        int numHand=0;
        for(int i=0;i<possibleCard.size();i++){
            for(int j =i+1;j<possibleCard.size();j++){
                Hand tempHand= new Hand();
                for(int chosenCard=0;chosenCard<possibleCard.size();chosenCard++){
                    if(chosenCard!=i&&chosenCard!=j){
                        tempHand.getHand()[numCard]=possibleCard.get(chosenCard);
                        if(tempHand.compareTo(highestHand)<0&&numCard==4){
                            highestHand = tempHand;
                        }
                        numCard+=1;
                    }
                }
                numHand+=1;
                numCard=0;
            }
                
        }
        return highestHand;
    }
    
    
    
    public static void dealPlayers(ArrayList<Player>players,Deck deck){
        for(int i =0;i<2;i++){
             for (Player player : players) {
                 player.getPocketHand().getPocketHand().add(deck.getDeck().get(0));
                 deck.getDeck().remove(0);
             }
         }
    }
    
    public static int setBlinds(ArrayList<Player>players){
        int smallBlindNum=0;
        boolean firstTurn=true;
        for (int i =0;i<players.size();i++) {
            if(players.get(i).getBlind().getTypeBlind().equals("small")){
                if(i<players.size()-2){
                    players.get(i+2).getBlind().setBlindAmount(players.get(i+1).getBlind().getBlindAmount());
                    players.get(i+2).getBlind().setTypeBlind(players.get(i+1).getBlind().getTypeBlind());
                    players.get(i+1).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(i+1).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(i+1).getPlayerNum();
                    pot+=players.get(i+2).getBlind().getBlindAmount();
                    pot+=players.get(i+1).getBlind().getBlindAmount();
                }
                else if(i==players.size()-2){
                    players.get(0).getBlind().setBlindAmount(players.get(i+1).getBlind().getBlindAmount());
                    players.get(0).getBlind().setTypeBlind(players.get(i+1).getBlind().getTypeBlind());
                    players.get(i+1).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(i+1).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(i+1).getPlayerNum();
                    pot+=players.get(0).getBlind().getBlindAmount();
                    pot+=players.get(i+1).getBlind().getBlindAmount();
                }
                else if (i==players.size()-1){
                    players.get(1).getBlind().setBlindAmount(players.get(0).getBlind().getBlindAmount());
                    players.get(1).getBlind().setTypeBlind(players.get(0).getBlind().getTypeBlind());
                    players.get(0).getBlind().setBlindAmount(players.get(i).getBlind().getBlindAmount());
                    players.get(0).getBlind().setTypeBlind(players.get(i).getBlind().getTypeBlind());
                    players.get(i).getBlind().setBlindAmount(0);
                    players.get(i).getBlind().setTypeBlind("null");
                    smallBlindNum = players.get(0).getPlayerNum();
                    pot+=players.get(0).getBlind().getBlindAmount();
                    pot+=players.get(1).getBlind().getBlindAmount();
                    
                }
                
                firstTurn = false;
                break;
            }
        }
        if(firstTurn){
            players.get(0).getBlind().setTypeBlind("small");
            players.get(0).getBlind().setBlindAmount(10);
            players.get(1).getBlind().setTypeBlind("big");
            players.get(1).getBlind().setBlindAmount(20);
            smallBlindNum = players.get(0).getPlayerNum();
            pot+=players.get(0).getBlind().getBlindAmount();
            pot+=players.get(1).getBlind().getBlindAmount();
        }
        return  smallBlindNum;
    }
    
    
    public static ArrayList<Player> roundOfBetting(int round, ArrayList<Player>players, int smallBlindNum){
        boolean action=true;
        int requiredChips=0;
        int response;
        boolean raised = false;
        int startPlayer=0;
        if(round == 0){
            for(int i =0;i<players.size();i++){
                if(players.get(i).getBlind().getTypeBlind().equalsIgnoreCase("big")){
                    requiredChips=players.get(i).getBlind().getBlindAmount();
                    players.get(i).setChips(players.get(i).getChips()-players.get(i).getBlind().getBlindAmount());
                    players.get(i).setChipsInCurrent(requiredChips);
                    players.get(i).setTotalChipsInPot(requiredChips);
                    if(i==players.size()-1){
                        startPlayer = players.get(0).getPlayerNum();
                    }
                    else if (i==0){
                        players.get(players.size()-1).setChips(players.get(players.size()-1).getChips()-players.get(players.size()-1).getBlind().getBlindAmount());
                        players.get(players.size()-1).setChipsInCurrent(players.get(players.size()-1).getBlind().getBlindAmount());
                        players.get(players.size()-1).setTotalChipsInPot(players.get(players.size()-1).getBlind().getBlindAmount());
                        startPlayer = players.get(i+1).getPlayerNum();
                    }
                    else{
                        players.get(i-1).setChips(players.get(i-1).getChips()-players.get(i-1).getBlind().getBlindAmount());
                        players.get(i-1).setChipsInCurrent(players.get(i-1).getBlind().getBlindAmount());
                        players.get(i-1).setTotalChipsInPot(players.get(i-1).getBlind().getBlindAmount());
                        startPlayer = players.get(i+1).getPlayerNum();
                    }
                    break;
                }    
            }
        }
        else{
            boolean found=false;
            for(int i =0;i<players.size()-1;i++){
                for (Player player : players) {
                    if(player.getPlayerNum()==smallBlindNum+i){
                        startPlayer = player.getPlayerNum();
                        found=true;
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
        }
        do{
            Collections.sort(players);
            players = sortPlayers(players,startPlayer);
            for (int i =players.size()-1;i>=0;i--) {
                if(raised==true&&players.get(i).getPlayerNum()==startPlayer){
                }    
                else{
                    if(players.size()==1){
                        raised=false;
                        break;
                    }
                    if(players.get(i).getChips()!=0){
                        do{
                            action=true;
                            System.out.println(players.get(i).getName()+" is acting");
                            System.out.println("1. Raise");
                            System.out.println("2. Check");
                            System.out.println("3. Call");
                            System.out.println("4. Fold");
                            System.out.print("Enter the Value You would like to do: ");
                            response = sc.nextInt();
                            if(response==2&&requiredChips!=players.get(i).getChipsInCurrent()){
                                System.out.println("You cannot check");
                                action=false;
                            }
                            else if(players.get(i).getChipsInCurrent()==requiredChips&&response==3){
                                System.out.println("You already have enough chips in you cannot call");
                                action=false;
                            }
                        }while(response<1||response>4||action==false);
                        if(response==1){
                            int raise  = raise(players.get(i), requiredChips);
                            raised=true;
                            startPlayer=players.get(i).getPlayerNum();
                            requiredChips+=raise;
                            break;
                        }
                        else if (response ==3){
                            call(players.get(i),requiredChips);
                        }
                        else if(response==4){
                            players.remove(i);
                        }
                        raised=false;
                    }
                }
            }
        }while(raised==true);
        return players;
    }
    
    public static int raise(Player player, int requiredChips){
        int raise;
        if(player.getChipsInCurrent()<requiredChips){
            call(player,requiredChips);
        }
        do{
            System.out.println("Chips: "+ player.getChips());
            System.out.print("Enter the amount you would like to raise: ");
            raise = sc.nextInt();
            if(raise>player.getChips()){
                System.out.println("You don't have enough chips");
            }
        }while(raise>player.getChips());
        pot+=raise;
        player.setChips(player.getChips()-raise);
        player.setChipsInCurrent(player.getChipsInCurrent()+raise);
        player.setTotalChipsInPot(player.getTotalChipsInPot()+raise);
        if(raise==player.getChips()){
            System.out.println("All In");
        }
        return raise;
    }
    
    public static void call(Player player, int requiredChips){
        int callAmount;
        callAmount = requiredChips - player.getChipsInCurrent();
        if(callAmount<=player.getChips()){
            pot+=callAmount;
            if(callAmount==player.getChips()){
                System.out.println("All In");
            }
            player.setChips(player.getChips()-callAmount);
            player.setChipsInCurrent(player.getChipsInCurrent()+callAmount);
            player.setTotalChipsInPot(player.getTotalChipsInPot()+callAmount);
            
        }
        else{
            pot+=player.getChips();
            player.setChipsInCurrent(player.getChipsInCurrent()+player.getChips());
            player.setTotalChipsInPot(player.getTotalChipsInPot()+player.getChips());
            player.setChips(0);
            System.out.println("All In");
        }
    }
    
    public static ArrayList<Player> sortPlayers(ArrayList<Player>players,int startPlayer){
        ArrayList<Player>sortedPlayers = new ArrayList<Player>();
        for (int i =0;i<players.size();i++) {
            if(players.get(i).getPlayerNum()==startPlayer){
                if(i==0){
                    for(int j =1;j<=players.size();j++){
                        sortedPlayers.add(players.get(players.size()-j));

                    } 
                }
                else{
                    for(int j =1;j<=players.size();j++){
                        sortedPlayers.add(players.get(i-1));
                        i-=1;
                        if(i<=0){
                            i=players.size();
                        }     
                    } 
                }
                
                break;
            }
            
        }
        return sortedPlayers;
    }
    
    public static void distributeWin(ArrayList<Player>players){
        Hand winningHand = new Hand();
        Player winningPlayer=null;
        if(players.size()==1){
            winningPlayer = players.get(0);
            players.get(0).setChips(players.get(0).getChips()+pot);
            pot=0;
        }
        else{
            for (Player player : players) {
                if(player.getHand().compareTo(winningHand)<0){
                    winningPlayer=player;
                    winningHand=player.getHand();
                }
            }
            int remainingPot=0;
            for (Player player : players) {
                if(player.getTotalChipsInPot()>winningPlayer.getTotalChipsInPot()){
                    pot-=player.getTotalChipsInPot()-winningPlayer.getTotalChipsInPot();
                    remainingPot+=player.getTotalChipsInPot()-winningPlayer.getTotalChipsInPot();
                }
            }
            winningPlayer.setChips(winningPlayer.getChips()+pot);
            players.remove(winningPlayer);
            pot=0+remainingPot;
        }
        System.out.println(winningPlayer.getName()+" won with "+ winningPlayer.getHand().handValue()+ " Chips: "+ winningPlayer.getChips());
    }
}
