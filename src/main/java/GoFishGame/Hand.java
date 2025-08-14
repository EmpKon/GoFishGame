package GoFishGame;

import java.util.ArrayList;
import java.util.Hashtable;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Deck deck) {
        hand.add(deck.drawCard());
    }

    public Boolean setCheck() {
        Hashtable<Integer, Integer> ranks = new Hashtable<Integer, Integer>();
        for (Card card : hand) {
            int rank = card.getRank();
            if (ranks.contains(rank)) {
                ranks.compute(rank, (k, val) -> val + 1);
            }
            else {
                ranks.put(rank, 1);
                }
        }
        for (Integer value : ranks.values()) {
            if (value == 4)
                return true;
        }
        return false;
    };

    @Override
    public String toString() {
        return "Hand: " + hand;
    }
}
