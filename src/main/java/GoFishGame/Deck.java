package GoFishGame;
import GoFishGame.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck = new ArrayList<>();
    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

    public Deck() {
        for (String suit : suits) {
            for (int rank = 0; rank < 13; rank++) {
                Card card = new Card(rank + 1, suit);
                deck.add(card);
            }
        }
    };

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public Card drawCard() {
        return deck.remove(0);
    }

    public String toString() {
        return "Deck: " + deck;
    }
}