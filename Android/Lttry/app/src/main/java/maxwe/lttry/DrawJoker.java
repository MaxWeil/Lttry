package maxwe.lttry;

import java.util.Arrays;

/**
 * Created by maxwe on 10/12/2016.
 */
public class DrawJoker {
    String date = new String();
    int[] numbers = new int[6];

    public DrawJoker() {
    }

    public DrawJoker(int[] numbers) {
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DrawJoker{" + "numbers=" + Arrays.toString(numbers) + '}';
    }
}
