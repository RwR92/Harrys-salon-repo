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
            System.out.println("***Harry's frisør salon***");
            System.out.println("Tast 1: for bogføring");
            System.out.println("Tast 2: for booking");
            System.out.println("Tast 3: for at afslutte");
            System.out.println("Tast 4: for vare/service");
            System.out.println("Tast 5: for at oprette kvittering");

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
                case 4:
                    vareServiceHaandtering();
                    break;
                case 5:
                    Salg salg=new Salg("order", 0.0);
                    salg.tilfoejVareTilBooking(scn,kalender,økonomi);
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
                }//switch with valgBogfoering
            }
        }
        //Whileloop for valgBogfoering
    } //public void valgBogfoering

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

    public void opretBooking() {
        System.out.print("Kundens navn: ");
        String navn = scn.nextLine();
        navn = gyldigtNavn(navn);

        System.out.print("Kundens mobil nummer: ");
        String nummer = scn.nextLine();
        nummer = gyldigtNummer(nummer);

        System.out.print("Total pris: ");
        double totalPrice = scn.nextDouble();
        scn.nextLine();

        Kunde kunde = new Kunde(navn, nummer);

        LocalDate dato = gyldigDato();

        while (dato.isBefore(LocalDate.now())) {
            System.out.println(dato + " Er før " + LocalDate.now() + " Skriv en gyldig dato.");
            System.out.print("Dato? (yyyy-mm-dd): ");
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
        navn = gyldigtNavn(navn);
        LocalDate dato2 = gyldigDato();
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
        LocalDate datoSøg = gyldigDato();
        LocalDate dateToday = LocalDate.now();
        if (datoSøg.isBefore(dateToday)) {
            økonomi.findBooking(datoSøg.toString());
        } else System.out.println("\u001B[38;2;255;193;7mHer kan du kun se bookinger fra dagen før dags dato!\u001B[0m");
    }      //Se bookinger for en bestemt dag.

    public void visLedigeTider4DageFrem() {
        System.out.print("Dato? (yyyy-mm-dd): ");
        LocalDate d = gyldigDato();
        kalender.visLedigeTiderFor4Dage(d);
    } // VisLedigeTider4DageFrem metode

    public String gyldigtNummer(String nummer) {
        while (!nummer.matches("\\d{8}")) { //"\\d{8}" betyder at nummeret skal indeholde cifre fra 0-9 og være 8 lang.
            System.out.println("Nummeret skal være 8 cifre lang.");
            System.out.print("Kundens mobil nummer: ");
            nummer = scn.nextLine();
        }
        return nummer;
    } // GyldigtNummer metode

    public String gyldigtNavn(String navn) {
        while (!navn.matches("[a-zA-ZæøåÆØÅ\\- ]+")) { //Tjek for om navnet indeholder danske bogstaver eller bindestreg og mellemrum og + for at sike mindst et tegn.
            System.out.print("Ugyldigt navn, brug bogstaver: ");
            navn = scn.nextLine();
        }
        return navn;
    } // gyldigtNavn metode

    public LocalDate gyldigDato() {
        System.out.print("Dato? (yyyy-mm-dd): ");

        while (true) {
            try {
                LocalDate dato = LocalDate.parse(scn.nextLine());
                return dato;
            } catch (DateTimeParseException e) {
                System.out.print("Ugyldig dato, prøv igen (yyyy-mm-dd): ");

            }

        }
    } //gyldigDato metode

    public void vareServiceHaandtering(){
        Salg salg2=new Salg("ordrer", 0.0);

        while(start){
            System.out.println("Tast 1: for at tilføje vare til systemet");
            System.out.println("Tast 2: for at fjerne vare fra systemet");
            System.out.println("Tast 3: for at se varerene i systemet");
            System.out.println("Tast 4: for at tilføje services til systemet");
            System.out.println("Tast 5: for at fjerne Services fra systemet");
            System.out.println("Tast 6: for at vise services i Systemet");
            System.out.println("Tast 7: gå tilbage til startmenu");

            int valg = scn.nextInt();
            scn.nextLine();

            switch(valg){
                case 1:
                    System.out.println("Varen/servicens navn:");
                    String navn= scn.nextLine();
                    System.out.println("Varen/servicens pris:");
                    String prisTekst= scn.nextLine();
                    double pris = Double.parseDouble(String.valueOf(prisTekst));
                    salg2.tilfoejVarer(new Salg.Vare(navn,pris));
                    System.out.println("Opdateret Vare/serviceliste:");
                    salg2.visVarer();
                    break;

                case 2:
                    System.out.println("Skriv navnet på den vare/service du vil slette.");
                    String sletteNavn= scn.nextLine();
                    salg2.fjernVarer(sletteNavn);
                    System.out.println("Opdateret vare/serviceliste:");
                    salg2.visVarer();
                    break;
                case 3:
                    salg2.visVarer();
                    break;
                case 4:
                    System.out.println("Varen/servicens navn:");
                    navn = scn.nextLine();
                    System.out.println("Varen/servicens pris:");
                    prisTekst = scn.nextLine();
                    pris = Double.parseDouble(String.valueOf(prisTekst));
                    salg2.tilfoejService(new Salg.Service(navn,pris));
                    System.out.println("Opdateret Vare/serviceliste:");
                    salg2.visService();
                    break;
                case 5:
                    System.out.println("Skriv navnet på den service du vil slette.");
                    sletteNavn = scn.nextLine();
                    salg2.fjernService(sletteNavn);
                    System.out.println("Opdateret vare/serviceliste:");
                    salg2.visService();
                    break;
                case 6:
                    salg2.visService();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Ugyldigt input");
            }
        }
    } //Vi håndtere skabelsen af nye ydelser huehuhe

} // Menuer class
