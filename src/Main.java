import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kalender k=new Kalender();
        Menu m = new Menu(k);
//        Menuer m=new Menuer(k);
//
//        m.menuStart();
        System.out.println("***Harry's frisør salon***");
        while (true){
            m.startMenu();
        }

    }
}

class Menu{
    Kalender kalender;

    Menu(Kalender kalender){
        this.kalender = kalender;
    }

    public void startMenu(){
        String startMenu;

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
                bogfoering();
                break;
            case "2":
                administration();
                break;
            case "3":
                System.exit(0);
        }
    }

    public void bogfoering(){
        while(true){

        }
    }

    public void administration(){
        boolean loop = true;
        while(loop) {
            System.out.println("[1] Opret ny booking");
            System.out.println("[2] Aflys en booking");
            System.out.println("[3] Vis bookings");
            System.out.println("[4] Gå tilbage");

            String valg;
            while (true) {
                valg = Tools.scan.nextLine();
                if (valg.equals("1") || valg.equals("2") || valg.equals("3") || valg.equals("4")) {
                    break;
                } else {
                    System.out.println("Vælg [1], [2], [3] eller [4].");
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
                    break;
                case "2":
                    System.out.println("Hvad er kundens telefonnummer?");
                    String telefon = Tools.scan.nextLine();
                    kalender.fjernBooking(telefon);
                    break;
                case "3":
                    System.out.println("[1] Vis alle aktuelle bookings");
                    System.out.println("[2] Vis en specifik dag");

                    String entenEller;
                    while (true) {
                        entenEller = Tools.scan.nextLine();
                        if (entenEller.equals("1") || entenEller.equals("2")) {
                            break;
                        } else {
                            System.out.println("Vælg [1] eller [2]");
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
                    }
                    break;
                case "4":
                    loop = false;
                    break;
            }
        }
    }
}

class Tools{
    static Scanner scan = new Scanner(System.in);
}