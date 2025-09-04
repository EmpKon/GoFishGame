package GoFishGame;

import java.lang.reflect.Array;
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
        pCount = pCount-1;
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
        //ui.getInt("")

        pCount = ui.getInt("Enter the number of players! (2-5)", 2, 5);
        cardCount = startingHandSize(pCount);

        ui.showMessage("Starting game with " + pCount + " players and a " + cardCount + " card hand!");

        for (int p = 0; p < pCount; p++) {
            addPlayer(new Player());
        }
        dealCards();


//        for (Player p : players) {
//            ui.showMessage(p.showHand());
//        }
    }

    public String viewScores(ArrayList<Player> players) throws InterruptedException {
        StringBuilder output = new StringBuilder();
        for (Player player : players) {
            output.append(player.id())
                    .append("'s score: ")
                    .append(player.getScore())
                    .append("\n");
        }
        return output.toString();
    }

    public boolean checkForSet(Player player) {
        String name = player.id();
        int set = player.checkSet();

        if (set > 0) {
            ui.showMessage(name + " has completed a set of 4 rank " + set + " cards!");
            player.clearSet();
            ui.showMessage(name + " now has a total score of " + player.getScore() + "!");
            return true;
        }
        return false;
    }

    public Player selectOpponent(ArrayList<Player> opps) throws InterruptedException {
        ui.showMessage("Who do you want to take a card from?");
        int counter = 0;
        Thread.sleep(500);

        for (Player p : opps) {
            counter++;
            ui.showMessage(counter + ". " + p.id());
        }
        int target = ui.getInt("", 1, opps.size());
        return opps.get(target - 1);
    }

    public ArrayList<Card> attemptSteal(Player player, ArrayList<Player> opps) throws InterruptedException {
        int pick;
        String name = player.id();
        player.showHand();
        Player opp = selectOpponent(opps);
        SortedSet<Integer> ranks = player.ranksInHand();

        ui.showMessage(player.id() + "'s turn");

        if (player.handIsEmpty()) {
            ui.showMessage("Hand is empty! \nDrawing from the deck!");
            Card drawnCard = player.drawCard(deck);
            ui.showMessage(player.id() + " Drew " + drawnCard);
            pick = drawnCard.getRank();
        }
        else {
            pick = ui.getIntFromList("What rank would you like to steal?\n" + ranks, ranks);
        }

        return player.stealCard(opp, pick);
    }

    public boolean turnSteal(Player player, ArrayList<Player> opps, Scanner inp, Deck deck) throws InterruptedException {
        ArrayList<Card> stolenCards = attemptSteal(player, opps);

        if (stolenCards.isEmpty()) {
            if (!deck.isEmpty()) {
                ui.showMessage("Go Fish!");
                Thread.sleep(300);
                ui.showMessage("You drew " + player.drawCard(deck) + "!");
            }
            else {
                ui.showMessage("The deck is empty!");
            }
            Thread.sleep(700);
            return checkForSet(player); //Player gets to attempt a steal again through completing a set through drawing
        } else {
            ui.showMessage("You stole: " + stolenCards);
            Thread.sleep(300);
            ui.showMessage("You can try to steal again!");
            Thread.sleep(700);
            checkForSet(player);
            return true;
        }
    }



    public void turn(Player player) throws InterruptedException {
        String name = player.id();
        ArrayList<Player> opps = new ArrayList<Player>();
        int counter = 0;
        ui.showMessage(name + "'s turn!\n");
        Thread.sleep(300);

        checkForSet(player); //Make this an option for an action during a player's turn
        if (player.handIsEmpty() && deck.isEmpty()) {
            dropPlayer(player);
        }

        for (Player p : activePlayers) {
            if (p != player) {
                opps.add(p);
            }
        }

        while (true) {
            ui.showMessage("What would you like to do?"); //TODO make the method for viewing ranks
            Thread.sleep(200);
            ui.showMessage("1 = Steal from another player \n2 = Check your hand \n3 = View scores \n4 = View completed ranks");
//            int choice = ui.getInt("11 = clear the deck and resume play \n12 = clear current players hand \n13 = increase current players score \n14 = normal play", 1, 14);
            int choice = ui.getInt("", 1,4);
            if (choice == 1) {
                boolean stole = false;
                do {
                    stole = turnSteal(player, opps, inp, deck);
                } while (stole);
            }
            else if (choice == 2) {
                ui.showMessage(player.showHand());
                Thread.sleep(1500);
            }
            else if (choice == 3) {
                ui.showMessage(viewScores(players));
                Thread.sleep(400);
            }
            else if (choice == 4) {
                //TODO viewranks(players)
                ;
            }
//            else if (choice == 11) {
//                deck.empty();
//            } else if (choice == 12) {
//                player.clearHand();
//            } else if (choice == 13) {
//                player.addScore();
//            }
            else {
                break;
            }
        }
    }

    public ArrayList<Player> getWinner(ArrayList<Player> players) {
        int maxScore = 0;
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : players) {
            int pScore = player.getScore();
            if (pScore >= maxScore) {
                if (pScore == maxScore) {
                    winners.add(player);
                }
                else {
                    winners.clear();
                    winners.add(player);
                }
                maxScore = pScore;
            }
        }
        return winners;
    }

    public void play() throws InterruptedException {
        int curr = 0;
        while (pCount > 1) {
            turn(this.activePlayers.get(curr));
            curr = (curr + 1) % pCount;
        }
        ArrayList<Player> winners = getWinner(players);
        ui.announceWinner(winners);

    }

    public static void main(String[] args) {
        ;
    }

}
