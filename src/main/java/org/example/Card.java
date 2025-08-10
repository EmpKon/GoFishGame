package org.example;


public class Card {
    int rank;
    String suit;

    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Suit: " + this.suit + "\nRank: " + this.rank;
    }
}
