import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kalender2 k=new Kalender2();
        Menuer m=new Menuer(k);
        m.menuStart();
    }
}

class Menuer {  //UI Klasse
    boolean start = true;
    Scanner scn = new Scanner(System.in);
    Kalender2 kalender;


    Menuer(Kalender2 kalender) {
        this.kalender = kalender;
    }

    public void menuStart() {
        while (start) {
            System.out.println("***Harry's frisør salon***");
            System.out.println("Tast 1: for bogføring");
            System.out.println("Tast 2: for booking");
            System.out.println("Tast 3: for at afslutte");

            int valg = scn.nextInt();
            scn.nextLine();

            switch (valg) {
                case 1:
                    valgBogfoering();  //metodekald til Bogførings menu
                    break;
                case 2:
                    bookValg(); //metodekald til Booking menu
                    break;
                case 3:
                    System.out.println("\nprogram lukker");
                    start = false;
                    break;
                default:
                    System.out.println("Ugyldigt input");
            }//Switch til menuStart
        }//Whileloop til menuStart
    }//public void menuStart

    public void valgBogfoering() {
        while (start) {
            System.out.println("Tast 1: udskriver specifik dato");
            System.out.println("Tast 2: udskriver alle kvitteringer");
            System.out.println("Tast 3: gå tilbage til startmenu");
            System.out.println("Tast 4: gå til Booking");


            int valg = scn.nextInt();
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
                    menuStart();  //metodekald til startmenu
                case 4:
                    bookValg();
                    break;
                case 5:

                default:
                    System.out.println("ugyldigt input");
            }//switch with valgBogfoering
        }//Whileloop for valgBogfoering
    } //public void valgBogfoering

    public void bookValg() {
        while (start) {
            System.out.println("Tast 1: opret booking");
            System.out.println("Tast 2: for at slette booking");
            System.out.println("Tast 3: for at vise Bookinger for en bestemt dag");
            System.out.println("Tast 4: for at gå til Startmenu");
            System.out.println("Tast 5: for at gå til Bogføring");

            int valg = scn.nextInt();
            scn.nextLine();


            switch (valg) {
                case 1:{
                    System.out.println("Kundens navn");
                    String navn = scn.nextLine();
                    System.out.println("Kundens nummer");
                    String nummer = scn.nextLine();
                    Kunde kunde = new Kunde(navn, nummer);

                    System.out.println("Dato? (yyyy-mm-dd)");
                    LocalDate dato = LocalDate.parse(scn.nextLine());
                    System.out.println("Tid (hh:mm)");
                    LocalTime tid = LocalTime.parse(scn.nextLine());

                    kalender.tilfoejBooking(new Booking(kunde, dato, tid));
                    menuStart();
                    break;}
                case 2:{
                    System.out.println("Kundens navn");
                    String navn=scn.nextLine();
                    System.out.println("kundens dato");
                    LocalDate dato2=LocalDate.parse(scn.nextLine());
                    kalender.fjernBooking(navn,dato2);
                    break;}
                case 3:
                    System.out.println("vælg dato du vil se for");
                    LocalDate dato3=LocalDate.parse(scn.nextLine());
                    kalender.visBookingerForDag(dato3);
                    System.out.println();
                case 4:
                    menuStart();
                    break;
                case 5:
                    valgBogfoering();
                    break;
                default:
                    System.out.println("ugyldigt input");
            } //switch til bookvalg
        } //while loop til bookvalg
    } // public void bookValg
} //class menuer



