import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menuer m=new Menuer();
        m.menuStart();
    }
    public static class Menuer{


public void menuStart (){
    System.out.println("***Harry's frisør salon***");
    System.out.println("Tast 1: for bogføring");
    System.out.println("Tast 2: for booking");
    Scanner scn= new Scanner(System.in);
    int bogforingvalg = 0;
    int bookingvalg = 0;

    boolean start= true;
    while (start){

        int valg = scn.nextInt();

        switch (valg){
            case 1:
                System.out.println("Tast 1: for at se specifik dato");
                System.out.println("Tast 2: for at se alle datoer");
               bogforingvalg = scn.nextInt();
                break;
            case 2:
                System.out.println("Tast 1: opret booking");
                System.out.println("Tast 2: for at slette booking");
                bookingvalg = scn.nextInt();
                break;
            default:
                System.out.println("ugyldigt valg");

        }

        if (bogforingvalg!= 0){
            switch (bogforingvalg){
                case 1:
                    System.out.println("udskriver specifik dato");
                    break;
                case 2:
                    System.out.println("udskriver alle kvitteringer");
                    break;
                default:
                    System.out.println("ugyldigt valg");
            }
        }
        if (bookingvalg!=0){
            switch (bookingvalg){
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                default:
                    System.out.println("ugyldigt valg");

            }
        }
    }

}
public void test(){


        }

    }
}
