package GoFishGame;

public class Player {
    int score;
    Hand hand;

    public Player(int score, Hand hand) {
        this.score = score;
        this.hand = hand;
    }

    public String pShowHand() {
        return this.hand.toString();
    }

    public int pScore() {
        return this.score;
    }

    public void pDrawCard(Deck deck) {
        this.hand.addCard(deck);
    }

    public Boolean pCheckSet() {
        Boolean set = this.hand.checkSet();
        if (set) {
            this.score += 1;
        }
        return set;
    }
}
