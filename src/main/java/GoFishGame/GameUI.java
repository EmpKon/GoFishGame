package GoFishGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

public interface GameUI {
    void showMessage(String msg);
    void showMessage(int num);
    void showMessage(Collection<Integer> numbers);
    int getInt(String msg, int min, int max);
    int getIntFromList(String msg, Collection<Integer> ranks);
    void announceWinner(ArrayList<Player> winners);
}
