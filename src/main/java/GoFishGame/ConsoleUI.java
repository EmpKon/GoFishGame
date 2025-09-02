package GoFishGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.SortedSet;

public class ConsoleUI implements GameUI {
    private final Scanner inp = new Scanner(System.in);
    
    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void showMessage(int num) {
        System.out.println(String.valueOf(num));
    }

    @Override
    public void showMessage(Collection<Integer> numbers) {
        System.out.println(numbers);
    };

    @Override
    public int getInt(String msg, int min, int max) {
        System.out.println(msg);
        if (min > max) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }

        while (true) {
            if (inp.hasNextInt()) {
                int val = inp.nextInt();
                if (val >= min && val <= max) {
                    return val;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            } else {
                System.out.println("Please enter a number");
                inp.next();
            }
        }
    }

    @Override
    public int getIntFromList(String msg, Collection<Integer> ranks) {
        while (true) {
            System.out.println(msg);
            if (inp.hasNextInt()) {
                int pick = inp.nextInt();

                if (ranks.contains(pick)) {
                    return pick;
                } else {
                    System.out.println("You can't try to steal a rank that you don't have...");
                }

            } else {
                System.out.println("Please input a number.");
                inp.next();
            }
        }
    }

    @Override
    public void announceWinner() {

    }

}
