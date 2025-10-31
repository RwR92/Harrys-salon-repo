import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
            int switchValg;
            System.out.println("***Harry's frisør salon***");
            System.out.println("Tast 1: for bogføring");
            System.out.println("Tast 2: for booking");
            System.out.println("Tast 3: for at oprette kvittering");
            System.out.println("Tast 4: for at afslutte");
            System.out.println("Tast 5: for vare/service");


            String valg = scn.nextLine();
            switchValg = SystemUtility.gyldigtValgTilMenuer(valg,scn);

            switch (switchValg) {
                case 1:
                    valgBogfoering();  //metodekald til Bogførings menu
                    break;
                case 2:
                    bookValg(); //metodekald til Booking menu
                    break;
                case 4:
                    System.out.println("\nprogram lukker");
                    start = false;
                    break;
                case 5:
                    vareServiceHaandtering();
                    break;
                case 3:
                    Salg salg = new Salg("order", 0.0);
                    salg.tilfoejVareTilBooking(scn, kalender, økonomi);
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
        }

            while (start) {
                int switchValg;
                System.out.println("Tast 1: udskriver specifik dato");
                System.out.println("Tast 2: udskriver alle kvitteringer");
                System.out.println("Tast 3: gå tilbage til startmenu");
                System.out.println("Tast 4: gå til Booking");


                String valg = scn.nextLine();
                switchValg = SystemUtility.gyldigtValgTilMenuer(valg,scn);


                switch (switchValg) {
                    case 1:
                        System.out.println("udskriver specifik dato");
                        Salg.findDagKvittering();
                        break;
                    case 2:
                        System.out.println("Udskriver alle kvitteringer");
                        Salg.visKvitteringerFraFil();
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
                } //switch with valgBogfoering
            } //Whileloop for valgBogfoering
        } //public void valgBogfoering
     //menu for bogføring.

    public void bookValg() {
        while (start) {
            int switchValg;
            System.out.println("Tast 1: Opret booking");
            System.out.println("Tast 2: For at slette booking");
            System.out.println("Tast 3: For at vise Bookinger for en bestemt dag(kun bagud)");
            System.out.println("Tast 4: for at se Bookinger, Kun for en bestemt dag");
            System.out.println("Tast 5: For at gå til Startmenu");
            System.out.println("Tast 6: For at gå til Bogføring");
            System.out.println("Tast 7: Vis ledige tider 4 dage fra dato");

            String valg = scn.nextLine();
            switchValg = SystemUtility.gyldigtValgTilMenuer(valg,scn);


            switch (switchValg) {
                case 1:  //Opretter booking.
                    opretBooking();
                    break;

                case 2:  //Slet booking.
                    sletBooking();
                    break;

                case 3: // se bookinger for en bestemt dag. (kun bagud)
                    seBookingBestemtDag();
                    break;

                case 4: //se bookinger på hvilken som helst dag
                    findDagBooking();
                    break;

                case 5: // Til hoved menuen.
                    menuStart();
                    break;

                case 6: // Til Bogføring menuen.
                    valgBogfoering();
                    break;

                case 7: // Printer 4 dage frem med de ledige tider.
                    visLedigeTider4DageFrem();
                    break;

                default:
                    System.out.println("ugyldigt input");
            } //switch til bookvalg
        } //while loop til bookvalg
    } // public void bookValg

    public void opretBooking() {
        System.out.print("Kundens navn: ");
        String navn = scn.nextLine();
        navn = SystemUtility.gyldigtNavn(navn,scn);

        System.out.print("Kundens mobil nummer: ");
        String nummer = scn.nextLine();
        nummer = SystemUtility.gyldigtNummer(nummer,scn);

        double totalPrice = 0;

        Kunde kunde = new Kunde(navn, nummer);

        LocalDate dato = SystemUtility.gyldigDato(scn);

        while (dato.isBefore(LocalDate.now())) {
            System.out.println(dato + " Er før " + LocalDate.now() + " Skriv en gyldig dato.");
            System.out.print("Dato? (yyyy-mm-dd): ");
            dato = LocalDate.parse(scn.nextLine());
        }
        //bruger checkDayOfWeek til at bestemme om det er lukkedag
        boolean itIsWeekend = ClosingDays.checkDayOfWeek(dato);
        if (itIsWeekend)
            return; //Hopper ud af nuværende loop.

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
        String valgteTid1 = scn.nextLine();
        while (!valgteTid1.matches("\\d") && Integer.parseInt(valgteTid1) > ledigeTider.size()) {
            System.out.print("Skriv et tal :");
            valgteTid1 = scn.nextLine();
        }
        int valgteTid2 = Integer.parseInt(valgteTid1);
        LocalTime tid = ledigeTider.get(valgteTid2 - 1);

        kalender.tilfoejBooking(new Booking(kunde, dato, tid, totalPrice));
        System.out.println("Booking tilføjet for " + kunde.getNavn());
        økonomi.gemBooking();
        System.out.println("Bookinger gemt i fil!");
        menuStart();
    } //Opret booking.

    public void sletBooking() {
        System.out.print("Kundens navn: ");
        String navn = scn.nextLine();
        navn = SystemUtility.gyldigtNavn(navn,scn);
        LocalDate dato2 = SystemUtility.gyldigDato(scn);
        kalender.fjernBooking(navn, dato2);
        økonomi.gemBooking();
    } //Slet booking.

    public void seBookingBestemtDag() {
        while (keepGoing) {
            System.out.println("Indtast adgangskode");
            String logIn = scn.nextLine();
            if (!logIn.equals("hairyharry")) {
                System.out.println("Forkert indtastet");
            } else {
                System.out.println("Du er logget ind");
                keepGoing = false;
            }
        }   //spørgAdgangskode
        LocalDate datoSøg = SystemUtility.gyldigDato(scn);
        LocalDate dateToday = LocalDate.now();
        if (datoSøg.isBefore(dateToday)) {
            økonomi.findBooking(datoSøg.toString());
        } else System.out.println("\u001B[38;2;255;193;7mHer kan du kun se bookinger fra dagen før dags dato!\u001B[0m");
    }      //Se bookinger for en bestemt dag. men kun bagud

    public static  void findDagBooking() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Dato for Booking yyyy-mm-dd");
        String soegedato = String.valueOf(SystemUtility.gyldigDato(scn));

        try (BufferedReader br = new BufferedReader(new FileReader("src//ListeBookinger.txt"))) {
            boolean fundet = false;
            String linje;
            while ((linje = br.readLine()) != null) {
                if (linje.startsWith(soegedato)) {
                    System.out.println(linje);
                    fundet = true;
                }
            }
            if (!fundet) {
                System.out.println("ingen Bookinger på: " + soegedato);
                System.out.println("");
            }
        } catch (IOException e) {
            System.out.println("fejl ved læsning" + e.getMessage());
        }

        Booking booking = null;
    } //find booking for hvilken som helst dag. og kun specifikke dag

    public void visLedigeTider4DageFrem() {
        LocalDate d = SystemUtility.gyldigDato(scn);
        kalender.visLedigeTiderFor4Dage(d);
    } // VisLedigeTider4DageFrem metode

    public void vareServiceHaandtering(){
        Salg salg2=new Salg("ordrer", 0.0);

        while (start) {
            int switchValg;
            System.out.println("Tast 1: for at tilføje vare til systemet");
            System.out.println("Tast 2: for at fjerne vare fra systemet");
            System.out.println("Tast 3: for at se varerene i systemet");
            System.out.println("Tast 4: for at tilføje services til systemet");
            System.out.println("Tast 5: for at fjerne Services fra systemet");
            System.out.println("Tast 6: for at vise services i Systemet");
            System.out.println("Tast 7: gå tilbage til startmenu");

            String valg = scn.nextLine();
            switchValg = SystemUtility.gyldigtValgTilMenuer(valg,scn);

            switch (switchValg) {
                case 1: //Tilføje vare
                    System.out.println("Varen/servicens navn:");
                    String navn = scn.nextLine();
                    navn = SystemUtility.gyldigtNavn(navn,scn);
                    System.out.println("Varen/servicens pris:");
                    String prisTekst = scn.nextLine();
                    prisTekst = SystemUtility.gyldigPrisForStrings(prisTekst,scn);
                    double pris = Double.parseDouble(String.valueOf(prisTekst));
                    salg2.tilfoejVarer(new Salg.Vare(navn, pris));
                    System.out.println("Opdateret Vare/serviceliste:");
                    salg2.visVarer();
                    break;

                case 2: //Fjerne vare
                    System.out.println("Skriv navnet på den vare/service du vil slette.");
                    String sletteNavn = scn.nextLine();
                    sletteNavn = SystemUtility.gyldigtNavn(sletteNavn,scn);
                    salg2.fjernVarer(sletteNavn);
                    System.out.println("Opdateret vare/serviceliste:");
                    salg2.visVarer();
                    break;
                case 3:
                    salg2.visVarer();
                    break;
                case 4: //Tilføj service
                    System.out.println("Varen/servicens navn:");
                    navn = scn.nextLine();
                    navn = SystemUtility.gyldigtNavn(navn,scn);
                    System.out.println("Varen/servicens pris:");
                    prisTekst = scn.nextLine();
                    prisTekst = SystemUtility.gyldigPrisForStrings(prisTekst,scn);
                    pris = Double.parseDouble(String.valueOf(prisTekst));
                    salg2.tilfoejService(new Salg.Service(navn, pris));
                    System.out.println("Opdateret Vare/serviceliste:");
                    salg2.visService();
                    break;
                case 5: //Fjerne Services
                    System.out.println("Skriv navnet på den service du vil slette.");
                    sletteNavn = scn.nextLine();
                    sletteNavn = SystemUtility.gyldigtNavn(sletteNavn,scn);
                    salg2.fjernService(sletteNavn);
                    System.out.println("Opdateret vare/serviceliste:");
                    salg2.visService();
                    break;
                case 6: //Vise services
                    salg2.visService();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Ugyldigt input");
            }
        }
    }//Vi håndtere skabelsen af nye ydelser huehuehue
}
// Menuer class

