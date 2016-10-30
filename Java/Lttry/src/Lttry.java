/**
 * Created by maxwe on 10/30/2016.
 */
public class Lttry {
    public static void main(String[] args){
        Draw[] draws = new DownloadDraw().getDraws();
        //draws: Lotto, Joker, Euromillions

        for(int i = 0; i < 3; i++) {
            System.out.println(draws[i].toString());
        }

        draws = new CreateDraw().getDraw();

        System.out.println("random test: \n");
        for(int i = 0; i < 3; i++) {
            System.out.println(draws[i].toString());
        }
    }
}
