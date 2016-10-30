import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by maxwe on 10/30/2016.
 */
public class Draw {
    String date = new String();
    int[] numbers;
    int[] luckyNumbers;

    public Draw() {
        date = "00.00.0000";
        numbers = new int[]{0};
        luckyNumbers = new int[]{0};
    }

    public Draw(String date, int[] numbers, int[] luckyNumbers) {
        this.date = date;
        this.numbers = numbers;
        this.luckyNumbers = luckyNumbers;
    }

    public void fill(Draw[] draws){

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

    public int[] getLuckyNumbers() {
        return luckyNumbers;
    }

    public void setLuckyNumbers(int[] luckyNumbers) {
        this.luckyNumbers = luckyNumbers;
    }

    public String toString() {
        return "Draw from " + date + ": " + Arrays.toString(numbers) + " | " + Arrays.toString(luckyNumbers) + "\n";
    }
}

class DownloadDraw {
    Draw[] draws = new Draw[3];

    public DownloadDraw(){
        for(int i = 0; i < draws.length; i++){
            draws[i] = new Draw();
        }
    }

    public Draw[] getDraws(){
        return downloadInfo();
    }

    String url = "https://www.win2day.at/gaming/RundenPlakat_wai.jsp";
    Document document;

    public Draw[] downloadInfo() {

        try {
            System.out.println("connecting to: " + url + "\n");
            document = Jsoup.connect(url).get();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("success!" + "\n");

        parseInfo();

        return draws;
    }

    public void parseInfo() {
        Elements elements;
        String[] helpDates;
        int[] helpEM;
        int[] helpNumbers;
        int[] helpSetNumbers;

        elements = document.select("span");
        helpDates = new String[2];
        helpEM = new int[2];
        helpNumbers = new int[20];

        int j = 0, k = 0;
        for (int i = 0; i < 82; i++) {

            //look for matching lotto & joker elements with specific html class attribute
            if ((elements.get(i).attr("class").equals("TextContentLOErgebnis")) && (j < 13)) {
                helpNumbers[j] = Integer.parseInt(elements.get(i).text());
                j++;
            }

            //look for matching euromillions elements with specific html class attribute
            if (elements.get(i).attr("class").equals("TextContentEMErgebnis")) {
                helpNumbers[j] = Integer.parseInt(elements.get(i).text());
                j++;
            }

            //looking for date of lotto & joker draw
            if (elements.get(i).attr("class").equals("TextContentHeadlineLO")) {
                helpDates[0] = elements.get(i).text();
            }

            //looking for date of euromillions draw
            if (elements.get(i).attr("class").equals("TextContentHeadlineEM")) {
                helpDates[1] = elements.get(i).text();
            }


            //setting searchtag to img for euromillions lucky numbers
            elements = document.select("img");

            //looking for euromillions lucky numbers images and getting the value
            if((k < 2) && isNumeric(elements.get(i).attr("alt"))){
                helpEM[k] = Integer.parseInt(elements.get(i).attr("alt"));
                k++;
            }

            //resetting the search tag to span
            elements = document.select("span");
        }

        //clean the dates
        helpDates[0] = helpDates[0].split(" ")[5];
        helpDates[1] = helpDates[1].split(" ")[2];

        //setting the dates
        draws[0].setDate(helpDates[0]);     //Lotto date
        draws[1].setDate(helpDates[0]);     //Joker date
        draws[2].setDate(helpDates[1]);     //Euromillions date

        //setting the lotto numbers
        helpSetNumbers = new int[6];
        for(int i = 0; i < 6; i++){
            helpSetNumbers[i] = helpNumbers[i];
        }
        draws[0].setNumbers(helpSetNumbers);
        helpSetNumbers = new int[1];
        helpSetNumbers[0] = helpNumbers[6];
        draws[0].setLuckyNumbers(helpSetNumbers);

        //setting the joker numbers
        helpSetNumbers = new int[6];
        for(int i = 7; i < 13; i++){
            helpSetNumbers[i - 7] = helpNumbers[i];
        }
        draws[1].setNumbers(helpSetNumbers);

        //setting the euromillions numbers
        helpSetNumbers = new int[5];
        for(int i = 13; i < 18; i++){
            helpSetNumbers[i - 13] = helpNumbers[i];
        }
        draws[2].setNumbers(helpSetNumbers);
        helpSetNumbers = new int[2];
        draws[2].setLuckyNumbers(helpEM);
    }

    public boolean isNumeric(String s){
        try{
            double d = Double.parseDouble(s);
        }catch(NumberFormatException nfo){
            return false;
        }

        return true;
    }

}

class CreateDraw {
    Draw[] draws = new Draw[3];

    public CreateDraw(){
        for(int i = 0; i < 3; i++){
            draws[i] = new Draw();
        }
    }

    public Draw[] getDraw(){
        return randomize();
    }

    public Draw[] randomize(){
        draws[0] = generateNumbers(45, 6, 1, "s");      //lotto
        draws[1] = generateNumbers(10, 6, 0, "du");     //joker
        draws[2] = generateNumbers(50, 5, 0, "s");      //euromillions numbers
        draws[2].setLuckyNumbers(generateNumbers(12, 2, 0, "s").getNumbers());      //euromillions lucky numbers

        return draws;
    }

    public Draw generateNumbers(int pool, int draws, int luckNum, String sortMode /*s,u,du*/) {
        Random random;
        int[] numbers = new int[draws + luckNum];
        Draw draw = new Draw();
        Boolean check;

        random = new Random();

        switch (sortMode) {
            case "s":
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

            case "u":
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

            case "du":
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

        //set numbers to the Draw variable
        int[] help = new int[draws];
        for(int i = 0; i < draws; i++){
            help[i] = numbers[i];
        }
        draw.setNumbers(help);

        //set lucky numbers to the Draw variable
        help = new int[luckNum];
        for(int i = draws; i < draws + luckNum; i++){
            help[i - draws] = numbers[i];
        }
        draw.setLuckyNumbers(help);

        return draw;
    }
}