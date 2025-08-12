package GoFishGame;
import GoFishGame.Card;

public class Main {
    public static void main(String[] args) {
        Card Test = new Card(13, "Hearts");
        Deck DeckTest = new Deck();
        Hand handTest = new Hand();
        System.out.println(Test.toString());
        DeckTest.fill();
        System.out.println(DeckTest.toString());
        DeckTest.shuffle();
        System.out.println(DeckTest.toString());
        handTest.addCard(DeckTest);
        System.out.println(handTest.toString());
        System.out.println(DeckTest.toString());
    }
}