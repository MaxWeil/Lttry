/**
 * Created by maxwe on 10/10/2016.
 */

public class lttryMain {

    public static void main(String[] args) {
        //Link to lotto numbers from Austria
        String[] numbersAustria = {"https://www.win2day.at/gaming/RundenPlakat_wai.jsp"};

        Draws draws = new Draws();

        draws = draws.getNumbers(numbersAustria);
        System.out.println(draws.toString());

        System.out.println("\n--------------------------\nGenerator test\n");
        Generate generate = new Generate();
        generate.generateDraw("all");
        System.out.println(generate.toString());
    }
}

