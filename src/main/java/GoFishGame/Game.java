package GoFishGame;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    ArrayList<Player> players = new ArrayList<Player>();
    Scanner inp = new Scanner(System.in);
    int pCount;
    int cardCount; //Initial hand dealt

    public void setup(Deck deck) {

        do {
            System.out.println("Enter the number of players! (2-5)");
            while (!inp.hasNext("[2-5]")) {
                System.out.println("Please enter a valid number!");
                inp.next();
            }
            pCount = inp.nextInt();
            if (pCount > 3) {
                cardCount = 7;
            }
            else {
                cardCount = 5;
            }
        } while (pCount < 1);
        System.out.println("Starting game with " + pCount + " players and a " + cardCount + " card hand!");
        for (int p = 0; p < pCount; p++) {
            players.add(new Player());
        }

        deck.shuffle();

        System.out.println("Dealing cards...");
        for (Player p : players) {
            for (int dealt = 0; dealt < cardCount; dealt++) {
                p.pDrawCard(deck);
            }
        }
        //temporary check
        for (Player p : players) {
            System.out.println(p.pShowHand());
        }
    }


    public static void main(String[] args) {
        ;
    }

}
