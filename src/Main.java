import com.sun.security.jgss.GSSUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kalender k=new Kalender();
        Menu m = new Menu(k);

        m.startMenu();

    }
}

class Menu{
    Kalender kalender;
    int forkertPassword = 0;

    Menu(Kalender kalender){
        this.kalender = kalender;
    }

    public void startMenu(){
        boolean start = true;
        boolean erLoggetPaa = false;

        while (start){
            String startMenu;

            System.out.println("***Harry's frisør salon***");
            System.out.println("[1] Bogføring");
            System.out.println("[2] Administration");
            System.out.println("[3] Luk system");
            while(true){
                startMenu = Tools.scan.nextLine();
                if (startMenu.equals("1") || startMenu.equals("2") || startMenu.equals("3")){
                    break;
                } else {
                    System.out.println("Vælg [1], [2] eller [3].");
                }
            }

            switch (startMenu){
                case "1":
                    if(erLoggetPaa){
                        System.out.println("Åbner bogføring.");
                        bogfoering();
                    } else {
                        System.out.println("Skriv password for at se bogføring");
                        String password = Tools.scan.nextLine();
                        if (password.equals("hairyharry")){
                            System.out.println("Korrekt password. Åbner bogføring.");
                            erLoggetPaa = true;
                            bogfoering();
                        } else {
                            System.out.println("Forkert password. Går tilbage til startmenu.");
                            forkertPassword++;
                            if (forkertPassword == 3){
                                System.out.println("Du har brugt for mange forsøg. Lukker systemet.");
                                System.exit(0);
                            }
                        }
                    }
                    break; // end of case "1"
                case "2":
                    administration();
                    break;
                case "3":
                    start = false;
                    break;
            }
        }
    }

    public void bogfoering(){
        boolean loop = true;
        while(loop){
            System.out.println("[1] Vælg en specifik dato");
            System.out.println("[2] Udskriv alle kvitteringer");
            System.out.println("[3] Gå tilbage til startmenu");
            System.out.println("[4] Gå til booking");

            String valg;
            while (true) {
                valg = Tools.scan.nextLine();
                if (valg.equals("1") || valg.equals("2") || valg.equals("3") || valg.equals("4") || valg.equals("5")) {
                    break;
                } else {
                    System.out.println("Vælg [1], [2], [3] eller [4].");
                }
            }

            switch (valg) {
                case "1":
                    System.out.println("Hvilken dato vil du se regninger for?");
                    LocalDate fakturaForBestemtDag = LocalDate.parse(Tools.scan.nextLine());
                    kalender.hentFakturaForDag(fakturaForBestemtDag);
                    break;
                case "2":
                    System.out.println("Udskriver alle regninger");
                    kalender.hentAlleFaktura();
                    break;
                case "3":
                    loop = false;
                    break;
                case "4":
                    administration();
                    break;
            }
        }

    }

    public void administration(){
        boolean loop = true;
        while(loop) {
            System.out.println("[1] Opret ny booking");
            System.out.println("[2] Aflys en booking");
            System.out.println("[3] Vis bookings");
            System.out.println("[4] Gå tilbage");
            System.out.println("[5] Gå en dag frem (simulation)");

            String valg;
            while (true) {
                valg = Tools.scan.nextLine();
                if (valg.equals("1") || valg.equals("2") || valg.equals("3") || valg.equals("4") || valg.equals("5")) {
                    break;
                } else {
                    System.out.println("Vælg [1], [2], [3] eller [4]. [5] for simulation.");
                }
            }

            switch (valg) {
                case "1":
                    System.out.println("Hvad er kundens navn");
                    String navn = Tools.scan.nextLine();
                    System.out.println("Hvad er kundens telefonnummer");
                    String nummer = Tools.scan.nextLine();
                    Kunde kunde = new Kunde(navn, nummer);

                    System.out.println("Hvilken dato? (yyyy-mm-dd)");
                    LocalDate dato = LocalDate.parse(Tools.scan.nextLine());
                    System.out.println("Hvilken tid? (hh:mm)");
                    LocalTime tid = LocalTime.parse(Tools.scan.nextLine());

                    kalender.tilfoejBooking(new Booking(kunde, dato, tid));
                    break; // end of case "1"

                case "2":
                    System.out.println("Hvad er kundens telefonnummer?");
                    String telefon = Tools.scan.nextLine();
                    kalender.fjernBooking(telefon);
                    break;

                case "3":
                    System.out.println("[1] Vis alle aktuelle bookings");
                    System.out.println("[2] Vis en specifik dag");
                    System.out.println("[3] Vis arkiv");

                    String entenEller;
                    while (true) {
                        entenEller = Tools.scan.nextLine();
                        if (entenEller.equals("1") || entenEller.equals("2") || entenEller.equals("3")) {
                            break;
                        } else {
                            System.out.println("Vælg [1], [2] eller [3]");
                        }
                    }
                    switch (entenEller) {
                        case "1":
                            kalender.hentAlleBookinger();
                            break;
                        case "2":
                            System.out.println("Hvilken dato vil du hente bookings for? (yyyy-mm-dd)");
                            LocalDate datoUdtraek = LocalDate.parse(Tools.scan.nextLine());
                            System.out.println();
                            kalender.visBookingerForDag(datoUdtraek);
                            break;
                        case "3":
                            kalender.hentArkiv();
                            break;
                    }
                    break; // end of case "3"

                case "4":
                    loop = false;
                    break;
                case "5":
                    kalender.simulereDag();
                    break;
            }
        }
    }
}

class Tools{
    public static Scanner scan = new Scanner(System.in);
}