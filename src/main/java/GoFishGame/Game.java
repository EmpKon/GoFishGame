package GoFishGame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedSet;

import static java.lang.Thread.sleep;

public class Game {
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Player> activePlayers = new ArrayList<Player>();
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

    void addPlayer(Player p) {
        players.add(p);
        activePlayers.add(p);
    }

    void dropPlayer(Player p) {
        ui.showMessage(p.id() + " has dropped out of the game! \nThey ran out of cards and the deck is completely empty!");
        activePlayers.remove(p);
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
            addPlayer(new Player());
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

    public ArrayList<Card> attemptSteal(Player player, ArrayList<Player> opps) {
        String name = player.id();
        player.showHand();
        Player opp = selectOpponent(opps);
        SortedSet<Integer> ranks = player.ranksInHand();

        ui.showMessage(player.id() + "'s turn");

        int pick = ui.getIntFromList("What rank would you like to steal?\n" + ranks, ranks);
        return player.stealCard(opp, pick);
    }

    public boolean turnSteal(Player player, ArrayList<Player> opps, Scanner inp, Deck deck) { //TODO Fat messy method, split this up
        ArrayList<Card> stolenCards = attemptSteal(player, opps);

        if (stolenCards.isEmpty()) {
            if (!deck.isEmpty()) {
                ui.showMessage("Go Fish!");
                ui.showMessage("You drew " + player.drawCard(deck) + "!");
            }
            else {
                ui.showMessage("The deck is empty!");
            }
            return checkForSet(player); //Player gets to attempt a steal again through completing a set through drawing
        } else {
            ui.showMessage("You stole: " + stolenCards);
            ui.showMessage("You can try to steal again!");

            checkForSet(player);
            return true;
        }
    }



    public void turn(Player player) {
        String name = player.id();
        ArrayList<Player> opps = new ArrayList<Player>();
        int counter = 0;
        ui.showMessage(name + "'s turn!");

        checkForSet(player); //Make this an option for an action during a player's turn

        ui.showMessage("What would you like to do?");
        //TODO method for options here
        //TODO cases, check hand, steal, complete set(?), view scores, view completed ranks, view cards left

        for (Player p : activePlayers) {
            if (p != player) {
                opps.add(p);
            }
        }

        if (player.handIsEmpty() && deck.isEmpty()) {
            dropPlayer(player);
        }

        boolean stole = false;
        do {
            stole = turnSteal(player, opps, inp, deck);
        } while (stole);
    }

    public void play() {
        int curr = 0;
        while (!end) {
            turn(this.activePlayers.get(curr));
            curr = (curr + 1) % pCount;
        }

    }

    public static void main(String[] args) {
        ;
    }

}
