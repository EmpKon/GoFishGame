package GoFishGame;

import java.util.ArrayList;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Deck deck) {
        hand.add(deck.drawCard());
    }

    @Override
    public String toString() {
        return "Hand: " + hand;
    }
}
