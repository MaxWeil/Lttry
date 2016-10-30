import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by maxwe on 10/12/2016.
 */
public class Draws {
    DrawLotto lotto = new DrawLotto();
    DrawJoker joker = new DrawJoker();
    DrawEuromillions euromillions = new DrawEuromillions();

    public Draws() {
    }

    //constructor for extracting data from a document
    public Draws(Document document) {
        int[] numbers = new int[20];
        Elements elements;
        elements = document.select("span");     //select the tag span

        String helpDateLJ = new String();
        String helpDateEM = new String();
        int helpEM[] = new int[2];

        int j = 0, k = 0;
        for (int i = 0; i < 82; i++) {

            //look for matching lotto & joker elements with specific class value
            if ((elements.get(i).attr("class").equals("TextContentLOErgebnis")) && (j < 13)) {
                numbers[j] = Integer.parseInt(elements.get(i).text());
                j++;
            }

            //look for matching euromillions elements with specific class value
            if(elements.get(i).attr("class").equals("TextContentEMErgebnis")) {
                numbers[j] = Integer.parseInt(elements.get(i).text());
                j++;
            }

            //getting date text of the lotto & joker draw
            if (elements.get(i).attr("class").equals("TextContentHeadlineLO")) {
                helpDateLJ = elements.get(i).text();
            }

            //getting date text of the euromillions draw
            if(elements.get(i).attr("class").equals("TextContentHeadlineEM")){
                helpDateEM = elements.get(i).text();
            }

            //look for matching euromillion images and get the "alt" value
            elements = document.select("img");          //looking for "img" tag
            if(k <= 1 && isNumeric(elements.get(i).attr("alt"))) {
                helpEM[k] = Integer.parseInt(elements.get(i).attr("alt"));
                k++;
            }


            elements = document.select("span");     //looking for "span" tag
        }

        //set the last two euromillions numbers
        for(int i = 0; i < helpEM.length; i++){
            numbers[i + 18] = helpEM[i];
        }

        //isolate the dates
        helpDateLJ = helpDateLJ.split(" ")[5];
        helpDateEM = helpDateEM.split(" ")[2];

        //set date for lotto & joker
        lotto.setDate(helpDateLJ);
        joker.setDate(helpDateLJ);
        euromillions.setDate(helpDateEM);

        //save the numbers to the matching array (lotto/joker/euromillions)
        for (int i = 0; i < 7; i++) {
            lotto.numbers[i] = numbers[i];
        }

        for (int i = 7; i < 13; i++) {
            joker.numbers[i - 7] = numbers[i];
        }

        for (int i = 13; i < 20; i++){
            euromillions.numbers[i - 13] = numbers[i];
        }
    }

    //constructor for just saving thr numbers to the matching array
    /*
        numbers array:
            0 - 6:  Lotto
            6 - 13: Joker
     */
    public Draws(int[] numbers) {
        for (int i = 0; i < 7; i++) {
            lotto.numbers[i] = numbers[i];
        }

        for (int i = 7; i < 13; i++) {
            joker.numbers[i - 7] = numbers[i];
        }
    }

    public Draws getNumbers(String[] url) {
        //document for saving the website
        Document document = null;
        try {
            System.out.println("connecting to: " + url[0]);
            document = Jsoup.connect(url[0]).get();              //connecting & getting website.html
        } catch (IOException ioe) {                                         //catching IOExceptions
            ioe.printStackTrace();
        }

        System.out.println("success!");

        return new Draws(document);                                  //extract files from website to the Draws array
    }

    public String toString() {
        return "\nDraw from " +
                lotto.getDate() +
                "\n" +
                lotto.toString() +
                "\n" +
                joker.toString() +
                "\n\nDraw from " +
                euromillions.getDate() +
                "\n" +
                euromillions.toString();
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
