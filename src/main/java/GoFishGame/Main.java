package GoFishGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        boolean end = false;
        Scanner inp = new Scanner(System.in);
        Deck deck = new Deck();
        Hand hand = new Hand();
        Game game = new Game();

        game.setup(deck);
        game.play();
    }
}