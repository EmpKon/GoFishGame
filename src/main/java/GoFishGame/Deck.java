package org.example;
import src.main.java.org.example.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    ArrayList<Card> Deck = new ArrayList<>();
    int[] ranks = new int[13];
    String[] suits = new String[4];

    public void deckFill() {
        this.ranks = ranks;
        for (int i = 0; i < this.ranks.length; i++) {
            this.ranks[i] = i;
        }
    }
}