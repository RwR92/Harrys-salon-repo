import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Menuer { //UI Klasse
    boolean start = true;
    Scanner scn = new Scanner(System.in);
    Kalender kalender;
    VirkØkonomi økonomi;
    boolean keepGoing = true;


    Menuer(Kalender kalender, VirkØkonomi økonomi) {
        this.kalender = kalender;
        this.økonomi = økonomi;
    } // Menuer constructor.

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
        while (keepGoing) {
            System.out.println("Indtast adgangskode");
            String logIn = scn.nextLine();
            if (!logIn.equals("hairyharry")) {
                System.out.println("Forkert indtastet");
            } else {
                System.out.println("Du er logget ind");
                keepGoing = false;
            }
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
                        System.out.println("Udskriver alle kvitteringer");
                        break;
                    case 3:
                        System.out.println("Gå tilbage til startmenu");
                        menuStart();  //metodekald til startmenu
                    case 4:
                        bookValg();
                        break;
                    case 5:

                    default:
                        System.out.println("Ugyldigt input");
                }//switch with valgBogfoering
            }
        }
        //Whileloop for valgBogfoering
    } //public void valgBogfoering
    public void opretBooking() {

        System.out.print("Kundens navn :");
        String navn = scn.nextLine();
        navn = gyldigtNavn(navn);
        System.out.print("Kundens mobil nummer :");
        String nummer = scn.nextLine();
        nummer = gyldigtNummer(nummer);
        System.out.print("Total pris: ");
        double totalPrice = scn.nextDouble();
        scn.nextLine();
        Kunde kunde = new Kunde(navn, nummer);

        System.out.print("Dato? (yyyy-mm-dd) :");
        LocalDate dato = (LocalDate.parse(scn.nextLine()));
        while(dato.isBefore(LocalDate.now())){
            System.out.println(dato+ " Er før "+ LocalDate.now()+ " Skriv en gyldig dato.");
            System.out.print("Dato? (yyyy-mm-dd) :");
            dato = LocalDate.parse(scn.nextLine());
        }
        //bruger checkDayOfWeek til at bestemme om det er lukkedag
        boolean itIsWeekend = ClosingDays.checkDayOfWeek(dato);
        if (itIsWeekend)
            return;

        //tjekker om det er helligdag
        boolean itIsHolyday = ClosingDays.checkHolyDays(dato);
        if (itIsHolyday)
            return;


        ArrayList<LocalTime> ledigeTider = kalender.findLedigeTider(dato);
        if (ledigeTider.isEmpty()) {
            System.out.println("Der er ingen ledige tider den dag.");
            return;
        }

        System.out.println("Ledige tider: ");
        for (int i = 0; i < ledigeTider.size(); i++) {
            System.out.println((i + 1) + ") " + ledigeTider.get(i));
        }

        System.out.print("Vælg et nummer for ønsket tid: ");
        int valgteTid = scn.nextInt();
        scn.nextLine();
        LocalTime tid = ledigeTider.get(valgteTid - 1);

        kalender.tilfoejBooking(new Booking(kunde, dato, tid, totalPrice));
        System.out.println("Booking tilføjet for " + kunde.getNavn());
        økonomi.gemBooking();
        System.out.println("Bookinger gemt i fil!");
        menuStart();
    } //Opret booking.
    public void sletBooking(){
        System.out.print("Kundens navn :");
        String navn = scn.nextLine();
        navn = gyldigtNavn(navn);
        System.out.print("kundens dato (yyyy-mm-dd) :");
        LocalDate dato2 = LocalDate.parse(scn.nextLine());
        kalender.fjernBooking(navn, dato2);
        økonomi.gemBooking();
    } //Slet booking.
    public void seBookingBestemtDag(){
        System.out.println("vælg dato du vil se for");
        String dato3 = scn.nextLine();
        økonomi.findBooking(dato3);
    } //Se bookinger for en bestemt dag.
    public void visLedigeTider4DageFrem(){
        System.out.println("Dato? (yyyy-mm-dd)");
        LocalDate d = LocalDate.parse(scn.nextLine());
        kalender.visLedigeTiderFor4Dage(d);
    }
    public void bookValg() {
        while (start) {
            System.out.println("Tast 1: Opret booking");
            System.out.println("Tast 2: For at slette booking");
            System.out.println("Tast 3: For at vise Bookinger for en bestemt dag");
            System.out.println("Tast 4: For at gå til Startmenu");
            System.out.println("Tast 5: For at gå til Bogføring");
            System.out.println("Tast 6: Vis ledige tider 4 dage fra dato");

            int valg = scn.nextInt();
            scn.nextLine();


            switch (valg) {
                case 1:  //Opretter booking.
                    opretBooking();
                    break;

                case 2:  //Slet booking.
                    sletBooking();
                    break;

                case 3: // se bookinger for en bestemt dag.
                    seBookingBestemtDag();
                    break;

                case 4: // Til hovede menuen.
                    menuStart();
                    break;

                case 5: // Til Bogføring menuen.
                    valgBogfoering();
                    break;

                case 6: // Printer 4 dage frem med de ledige tider.
                    visLedigeTider4DageFrem();
                    break;

                default:
                    System.out.println("ugyldigt input");
            } //switch til bookvalg
        } //while loop til bookvalg
    } // public void bookValg
    public String gyldigtNummer(String nummer){
        while(!nummer.matches("\\d{8}")){ //"\\d{8}" betyder at nummeret skal indeholde cifre fra 0-9 og være 8 lang.
            System.out.println("Nummeret skal være 8 cifre lang.");
            System.out.print("Kundens mobil nummer :");
            nummer = scn.nextLine();
        }
        return nummer;
    }
    public String gyldigtNavn(String navn){
        while(!navn.matches("[a-zA-ZæøåÆØÅ\\- ]+")){ //Tjek for om navnet indeholder danske bogstaver eller bindestreg og mellemrum og + for at sike mindst et tegn.
            System.out.print("Ugyldigt navn, brug bogstaver :");
            navn = scn.nextLine();
        }
        return navn;
    }

} // Menuer class
