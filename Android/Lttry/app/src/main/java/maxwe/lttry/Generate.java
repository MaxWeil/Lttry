package maxwe.lttry;

import java.util.Arrays;
import java.util.Random;

import maxwe.lttry.Draws;

/**
 * Created by maxwe on 10/12/2016.
 */
public class Generate {
    String mode = new String();
    Draws draws = new Draws();

    public Generate() {
    }

    public Generate(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Draws getDraws() {
        return draws;
    }

    public void setDraws(Draws draws) {
        this.draws = draws;
    }


    @Override
    public String toString() {
        return "Gernerate:\n" +
                "Lotto: " +
                Arrays.toString(draws.lotto.numbers) +
                "\nJoker: " +
                Arrays.toString(draws.joker.numbers) +
                "\nEuromillions: " +
                Arrays.toString(draws.euromillions.numbers);
    }

    public void generateDraw(String mode) {
        int[] help;

        switch (mode) {
            case "all":
                draws.lotto.numbers = generateNumbers(45, 6, 1, "sorted");
                draws.joker.numbers = generateNumbers(10, 6, 0, "double unsorted");

                help = generateNumbers(50, 5, 0, "sorted");
                for (int i = 0; i < 5; i++) {
                    draws.euromillions.numbers[i] = help[i];
                }
                help = generateNumbers(12, 2, 0, "sorted");
                for (int i = 0; i < 2; i++) {
                    draws.euromillions.numbers[i + 5] = help[i];
                }
                break;

            case "lotto":
                draws.lotto.numbers = generateNumbers(45, 6, 1, "sorted");
                break;

            case "joker":
                draws.joker.numbers = generateNumbers(10, 6, 0, "double unsorted");
                break;

            case "euromillions":
                help = generateNumbers(50, 5, 0, "sorted");
                for (int i = 0; i < 5; i++) {
                    draws.euromillions.numbers[i] = help[i];
                }
                help = generateNumbers(12, 2, 0, "sorted");
                for (int i = 0; i < 2; i++) {
                    draws.euromillions.numbers[i + 5] = help[i];
                }
                break;

        }
    }

    public int[] generateNumbers(int pool, int draws, int luckNum, String sortMode) {
        Random random = new Random();
        int[] numbers = new int[draws + luckNum];
        Boolean check;

        switch (sortMode) {
            case "sorted":
                for (int i = 0; i < numbers.length; i++) {
                    int temp;

                    do {
                        check = true;

                        temp = random.nextInt(pool) + 1;
                        if (temp < 1)
                            temp *= (-1);
                        else {
                            for (int j = 0; j < i; j++) {
                                if (numbers[j] == temp)
                                    check = false;
                            }
                        }
                    } while (!check);
                    numbers[i] = temp;
                }

                check = false;
                while (!check) {
                    check = true;

                    for (int i = 0; i < draws - 1; i++) {
                        if (numbers[i] > numbers[i + 1]) {
                            int help = numbers[i];
                            numbers[i] = numbers[i + 1];
                            numbers[i + 1] = help;

                            check = false;
                        }
                    }
                }

                if (luckNum > 1) {
                    check = false;
                    while (!check) {
                        for (int i = draws - 1; i < (draws + luckNum) - 1; i++) {
                            check = true;
                            if (numbers[i] > numbers[i + 1]) {
                                int help = numbers[i];
                                numbers[i] = numbers[i + 1];
                                numbers[i + 1] = help;

                                check = false;
                            }
                        }
                    }
                }

                break;

            case "unsorted":
                for (int i = 0; i < draws; i++) {
                    int temp;

                    do {
                        check = true;

                        temp = random.nextInt(pool) + 1;
                        if (temp < 1)
                            temp *= (-1);
                        for (int j = 0; j < i; j++) {
                            if (numbers[j] == temp)
                                check = false;
                        }
                    } while (!check);
                    numbers[i] = temp;
                }
                break;

            case "double unsorted":
                for (int i = 0; i < draws; i++) {
                    int temp;

                    do{
                        check = true;

                        temp = random.nextInt(pool) + 1;
                        if (temp < 1)
                            temp *= (-1);
                    }while(!check);

                    numbers[i] = temp;
                }
                break;
        }

        return numbers;
    }
}
