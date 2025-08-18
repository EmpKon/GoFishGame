package GoFishGame;

import java.util.Collections;

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

    public String showHand() {
        this.hand.sort();
        return id() + " " + this.hand.toString();
    }

    public int score() {
        return this.score;
    }

    public void drawCard(Deck deck) {
        this.hand.addCard(deck);
    }

    public String stealCard(Player target, int rank) {
        return this.hand.stealCards(target.hand, rank).toString();
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
