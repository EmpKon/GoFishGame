package GoFishGame;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Deck deck) {
        hand.add(deck.drawCard());
    }

    public void clearSet(int rank) {
        hand.removeIf(card -> card.rank == rank);
    };

    public Boolean checkSet() {
        Hashtable<Integer, Integer> ranks = new Hashtable<Integer, Integer>();
        for (Card card : hand) {
            int rank = card.getRank();
            if (ranks.containsKey(rank)) {
                ranks.compute(rank, (k, val) -> val == null ? 1 : val + 1);
            }
            else {
                ranks.put(rank, 1);
                }
        }
        for (int r = 0; r < ranks.size(); r++) {
            List<Integer> keys = new ArrayList<>(ranks.keySet());
            int key = keys.get(r);
            int value = ranks.get(key);
            if (value == 4) {
                clearSet(key);
                return true;
        }}
        return false;
    }

    @Override
    public String toString() {
        return "Hand: " + hand;
    }
}
