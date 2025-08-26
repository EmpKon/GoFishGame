package GoFishGame;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedSet;

import static java.lang.Thread.sleep;

public class Game {
    ArrayList<Player> players = new ArrayList<Player>();
    Scanner inp = new Scanner(System.in);
    int pCount;
    int cardCount;
    private Deck deck;
    Boolean end = false;

    public void setup(Deck deck) {
        this.deck = deck;

        do {
            System.out.println("Enter the number of players! (2-5)");
            while (!inp.hasNext("[2-5]")) {
                System.out.println("Please enter a valid number!");
                inp.next();
            }
            pCount = inp.nextInt();
            if (pCount > 3) {
                cardCount = 7;
            }
            else {
                cardCount = 5;
            }
        } while (pCount < 1);
        System.out.println("Starting game with " + pCount + " players and a " + cardCount + " card hand!");
        for (int p = 0; p < pCount; p++) {
            players.add(new Player());
        }

        this.deck.shuffle();

        System.out.println("Dealing cards...");
        for (Player p : players) {
            for (int dealt = 0; dealt < cardCount; dealt++) {
                p.drawCard(this.deck);
            }
        }
        //temporary check
        for (Player p : players) {
            System.out.println(p.showHand());
        }
    }
    public void checkForSet(Player player) {
        String name = player.id();
        int set = player.checkSet();
        if (set > 0) {
            System.out.println(name + " has completed a set of 4 rank " + set + " cards!");
            player.clearSet();
            System.out.println(name + " now has a total score of " + player.score() + "!");
        }

    }

    public Boolean turnSteal(Player player, ArrayList<Player> opps, Scanner inp, Deck deck) { //TODO Fat messy method, split this up
        String name = player.id();
        player.showHand();
        System.out.println("Who do you want to take a card from?");
        int counter = 0;
        for (Player p : opps) {
            counter++;
            System.out.println(counter + ". " + p.id());
        }

        if (inp.hasNextInt()) { //TODO inp validation method
            int target = inp.nextInt();
            if (target > 0 && target < opps.size() + 1) {
                Player opp = opps.get(target-1);
                System.out.println(name + "'s turn");
                System.out.println("What rank would you like to steal?");
                SortedSet<Integer> ranks = player.hand.ranksInHand();
        for (int rank : ranks) {
            System.out.println(rank);
        }
        if (inp.hasNextInt()) {
            int pick = inp.nextInt();
            if (ranks.contains(pick)) {
                ArrayList<Card> stolenCards = player.stealCard(opp, pick);
                if (stolenCards.isEmpty()) {
                    System.out.println("Go Fish!");
                    player.drawCard(deck);
                    return false;
                }
                else {
                    System.out.println("You stole: " + stolenCards);
                    System.out.println("You can try to steal again!"); //Currently cant switch targets after successful steal
                    return true;
                }
            }
        }
            }
        }
            else {
        System.out.println("\nPlease input a number!\n");
        inp.next();

        }
    return false;
    }


    public void turn(Player player) throws InterruptedException {
        String name = player.id();
        ArrayList<Player> opps = new ArrayList<Player>();
        int counter = 0;
        System.out.println(name + "'s turn!");

        checkForSet(player); //Make this an option for an action during a player's turn

        System.out.println("What would you like to do?");
        //TODO Options here
        for (Player p : players) {
            if (p != player) {
                opps.add(p);
            }
        }
        turnSteal(player, opps, inp, deck);
    }

    public void play() throws InterruptedException {
        int curr = 0;
        do {
            if (curr < pCount) {
                System.out.println(curr);
                }
            else {
                curr = 0;
            }

            turn(this.players.get(curr));
            curr++;
        } while (!end);

    }

    public static void main(String[] args) {
        ;
    }

}
