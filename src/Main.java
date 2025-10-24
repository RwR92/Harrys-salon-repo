import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kalender2 k=new Kalender2();
        Menuer m=new Menuer(k);
        m.menuStart();
    }
}

class Menuer{
    boolean start=true;
    Scanner scn= new Scanner(System.in);
    Kalender2 kalender;


    Menuer(Kalender2 kalender){
        this.kalender = kalender;
    }

    public void menuStart (){
        while (start){
            System.out.println("***Harry's frisør salon***");
            System.out.println("Tast 1: for bogføring");
            System.out.println("Tast 2: for booking");
            System.out.println("Tast 3: for at afslutte");

            int valg = scn.nextInt();
            scn.nextLine();

            switch (valg) {
                case 1:
                    valgBogfoering();
                    break;
                case 2:
                    bookValg();
                    break;
                case 3:
                    System.out.println("\nprogram lukker");
                    start=false;
                    break;
                default:
                    System.out.println("Ugyldigt input");
            }

        }
    }
    public void valgBogfoering(){
        while(start){
        System.out.println("Tast 1: udskriver specifik dato");
        System.out.println("Tast 2: udskriver alle kvitteringer");
        System.out.println("Tast 3: gå tilbage til startmenu");
        System.out.println("Tast 4: gå til Booking");

        int valg= scn.nextInt();
        scn.nextLine();


        switch (valg) {
            case 1:
                System.out.println("udskriver specifik dato");
                break;
            case 2:
                System.out.println("udskriver alle kvitteringer");
                break;
            case 3:
                System.out.println("gå tilbage til startmenu");
                menuStart();
            case 4:
                bookValg();
                break;
            case 5:

            default:
                System.out.println("ugyldigt input");
        }
        }
        }

    public void bookValg(){
        while (start) {
            System.out.println("Tast 1: opret booking");
            System.out.println("Tast 2: for at slette booking");
            System.out.println("Tast 3: for at gå til Startmenu");
            System.out.println("Tast 4: for at gå til Bogføring");

            int valg = scn.nextInt();
            scn.nextLine();


            switch (valg) {
                case 1:
                    System.out.println("Kundens navn");
                    String navn = scn.nextLine();
                    System.out.println("Kundens nummer");
                    String nummer = scn.nextLine();
                    Kunde kunde = new Kunde(navn, nummer);

                    System.out.println("Dato? (yyyy-mm-dd)");
                    LocalDate dato = LocalDate.parse(scn.nextLine());

                    ArrayList<LocalTime> ledigeTider = kalender.findLedigeTider(dato);
                    if(ledigeTider.isEmpty()){
                        System.out.println("Der er ingen ledige tider den dag.");
                        return;
                    }

                    System.out.println("Ledige tider: ");
                    for(int i = 0; i < ledigeTider.size(); i ++){
                        System.out.println((i+1) + ") " + ledigeTider.get(i));
                    }

                    System.out.print("Vælg et nummer for ønsket tid: ");
                    int valgteTid = scn.nextInt();
                    scn.nextLine();
                    LocalTime tid = ledigeTider.get(valgteTid - 1);

                    //System.out.println("Tid (hh:mm)");
                    //LocalTime tid = LocalTime.parse(scn.nextLine());

                    kalender.tilfoejBooking(new Booking(kunde, dato, tid));
                    menuStart();
                    break;
                case 2:
                    System.out.println("Not here");
                    break;
                case 3:
                    menuStart();
                    break;
                case 4:
                    valgBogfoering();
                    break;
                default:
                    System.out.println("ugyldigt input");
            }
        }
        }
    }

