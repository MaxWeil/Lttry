import java.util.Arrays;

/**
 * Created by maxwe on 10/12/2016.
 */
public class DrawLotto {
    String date = new String();
    int[] numbers = new int[7];

    public DrawLotto() {
    }

    public DrawLotto(int[] numbers) {
        this.numbers = numbers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "DrawLotto{" + "numbers=" + Arrays.toString(numbers) + '}';
    }
}
