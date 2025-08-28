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
    public boolean checkForSet(Player player) {
        String name = player.id();
        int set = player.checkSet();

        if (set > 0) {
            System.out.println(name + " has completed a set of 4 rank " + set + " cards!");
            player.clearSet();
            System.out.println(name + " now has a total score of " + player.score() + "!");
            return true;
        }
        return false;
    }

    public Player selectOpponent(ArrayList<Player> opps) {
        System.out.println("Who do you want to take a card from?");
        int counter = 0;

        for (Player p : opps) {
            counter++;
            System.out.println(counter + ". " + p.id());
        }

        int target = intInpValidation(1, opps.size(), inp);
        return opps.get(target - 1);
    }

    public int intInpValidation(int min, int max, Scanner inp) {

        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }

        while (true) {
            if (inp.hasNextInt()) {
                int val = inp.nextInt();
                if (val >= min && val <= max) {
                    return val;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            }
        }
    }

    public boolean turnSteal(Player player, ArrayList<Player> opps, Scanner inp, Deck deck) { //TODO Fat messy method, split this up
        String name = player.id();
        player.showHand();
        Player opp = selectOpponent(opps);

        while(true) {
            System.out.println(name + "'s turn");
            System.out.println("What rank would you like to steal?");
            SortedSet<Integer> ranks = player.ranksInHand();

            for (int rank : ranks) {
                System.out.println(rank);
            }

            if (inp.hasNextInt()) {
                int pick = inp.nextInt();

                if (ranks.contains(pick)) {
                    ArrayList<Card> stolenCards = player.stealCard(opp, pick);

                    if (stolenCards.isEmpty()) {

                        System.out.println("Go Fish!");
                        System.out.println("You drew " + player.drawCard(deck) + "!");
                        return checkForSet(player); //Player gets to attempt a steal again through completing a set through drawing
                    }
                    else {
                        System.out.println("You stole: " + stolenCards);
                        System.out.println("You can try to steal again!");
                        checkForSet(player);
                        return true;
                    }
                }

                else {
                    System.out.println("You can't try to steal a rank that you don't have");
                }

            } else {
                System.out.println("Please input a number");
                inp.next();
            }
        }
    }


    public void turn(Player player) throws InterruptedException {
        String name = player.id();
        ArrayList<Player> opps = new ArrayList<Player>();
        int counter = 0;
        System.out.println(name + "'s turn!");

        checkForSet(player); //Make this an option for an action during a player's turn

        System.out.println("What would you like to do?");
        //TODO method for options here
        //TODO cases, check hand, steal, complete set(?), view scores, view completed ranks, view cards left

        for (Player p : players) {
            if (p != player) {
                opps.add(p);
            }
        }

        boolean stole = false;
        do {
            stole = turnSteal(player, opps, inp, deck);
        } while (stole);
    }

    public void play() throws InterruptedException {
        int curr = 0;
        while (!end) {
            turn(this.players.get(curr));
            curr = (curr + 1) % pCount;
        }

    }

    public static void main(String[] args) {
        ;
    }

}
