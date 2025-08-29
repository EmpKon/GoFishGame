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
    private final GameUI ui;
    private Deck deck;
    Boolean end = false;

    public Game() {
        this.ui = new ConsoleUI();
    }

    public Game(GameUI ui) {
        this.ui = ui;
    }

    private int startingHandSize(int players) {
        return players > 3 ? 5 : 7;
    }

    private void dealCards() {
        for (Player p : players) {
            for (int dealt = 0; dealt < cardCount; dealt++) {
                p.drawCard(this.deck);
            }
        }
    }

    public void setup(Deck deck) {
        this.deck = deck;
        this.deck.shuffle();

        pCount = ui.getInt("Enter the number of players! (2-5)", 2, 5);
        cardCount = startingHandSize(pCount);

        ui.showMessage("Starting game with " + pCount + " players and a " + cardCount + " card hand!");

        for (int p = 0; p < pCount; p++) {
            players.add(new Player());
        }
        dealCards();

        //temporary check
        for (Player p : players) {
            ui.showMessage(p.showHand());
        }
    }

    public boolean checkForSet(Player player) {
        String name = player.id();
        int set = player.checkSet();

        if (set > 0) {
            ui.showMessage(name + " has completed a set of 4 rank " + set + " cards!");
            player.clearSet();
            ui.showMessage(name + " now has a total score of " + player.score() + "!");
            return true;
        }
        return false;
    }

    public Player selectOpponent(ArrayList<Player> opps) {
        ui.showMessage("Who do you want to take a card from?");
        int counter = 0;

        for (Player p : opps) {
            counter++;
            ui.showMessage(counter + ". " + p.id());
        }
        int target = ui.getInt("", 1, opps.size());
        return opps.get(target - 1);
    }

    public boolean turnSteal(Player player, ArrayList<Player> opps, Scanner inp, Deck deck) { //TODO Fat messy method, split this up
        String name = player.id();
        player.showHand();
        Player opp = selectOpponent(opps);

        while(true) {
            ui.showMessage(name + "'s turn\nWhat rank would you like to steal?");
            SortedSet<Integer> ranks = player.ranksInHand();

            ui.showMessage(ranks);

            if (inp.hasNextInt()) {
                int pick = inp.nextInt();

                if (ranks.contains(pick)) {
                    ArrayList<Card> stolenCards = player.stealCard(opp, pick);

                    if (stolenCards.isEmpty()) {

                        ui.showMessage("Go Fish!");
                        ui.showMessage("You drew " + player.drawCard(deck) + "!");
                        return checkForSet(player); //Player gets to attempt a steal again through completing a set through drawing
                    } else {
                        ui.showMessage("You stole: " + stolenCards);
                        ui.showMessage("You can try to steal again!");
                        checkForSet(player);
                        return true;
                    }
                } else {
                    ui.showMessage("You can't try to steal a rank that you don't have");
                }

            } else {
                ui.showMessage("Please input a number");
                inp.next();
            }
        }
    }


    public void turn(Player player) throws InterruptedException {
        String name = player.id();
        ArrayList<Player> opps = new ArrayList<Player>();
        int counter = 0;
        ui.showMessage(name + "'s turn!");

        checkForSet(player); //Make this an option for an action during a player's turn

        ui.showMessage("What would you like to do?");
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
