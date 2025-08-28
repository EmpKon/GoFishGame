package GoFishGame;

import java.util.SortedSet;

public interface GameUI {
    void showMessage(String msg);
    int getInt(String msg, int min, int max);
    int getIntFromList(String msg, SortedSet<Integer> ranks);
    void announceWinner();
}
