package GoFishGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;

public class Player {
    private static int serial = 1;
    private final int id;
    int score;
    Hand hand;

    public Player(int score, Hand hand) {
        this.score = score;
        this.hand = hand;
        this.id = serial++;
    }

    public Player() {
        this(0, new Hand());
    }

    public String id() {
        return "Player_" + this.id;
    }

    public boolean handIsEmpty() {
        return this.hand.isEmpty();
    }

    public String showHand() {
        this.hand.sort();
        return id() + " " + this.hand.toString();
    }


    public SortedSet<Integer> ranksInHand() {
        return hand.ranksInHand();
    }

    public int score() {
        return this.score;
    }

    public Card drawCard(Deck deck) {
        return this.hand.addCard(deck);
    }

    public ArrayList<Card> stealCard(Player target, int rank) {
        return this.hand.stealCards(target.hand, rank);
    }

    public int checkSet() {
        return this.hand.checkSet();
    }

    public void clearSet() {
        int set = this.hand.checkSet();
        if (set != 0) {
            this.hand.clearSet(set);
            this.score += 1;
        }
    }
}
