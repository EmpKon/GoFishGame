package GoFishGame;

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

    public String pId() {
        return "Player_" + this.id;
    }

    public String pShowHand() {
        return pId() + " " + this.hand.toString();
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
