package GoFishGame;
import GoFishGame.Card;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean end = false;
        Scanner inp = new Scanner(System.in);
        Deck deck = new Deck();
        Hand hand = new Hand();
        deck.fill();
        deck.shuffle();


        while (!end) {
            System.out.println("1. Draw a card\n2. Show hand\n3. Print deck\n8. Reshuffle\n9. Refill deck\n0. End");
            switch(inp.nextInt()) {
                case 1:
                    hand.addCard(deck);
                    System.out.println(hand.toString());
                    break;

                case 2:
                    System.out.println("Hand: " + hand.toString());
                    break;

                case 3:
                    System.out.println("Deck: " + deck.toString());
                    break;

                case 8:
                    deck.shuffle();
                    System.out.println(deck.toString());
                    break;

                case 9:
                    deck.fill();
                    System.out.println(deck.toString());
                    break;

                case 0:
                    end = true;
                    break;

                default:
                    System.out.println("Invalid input");
            }

        };

    }
}