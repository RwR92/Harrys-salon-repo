import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class VirkØkonomi {
    private Kalender2 kalender;

    public VirkØkonomi (Kalender2 kalender) {
        this.kalender = kalender;
    }


    public void gemBooking()  {
        try {
            BufferedWriter fil = new BufferedWriter(new FileWriter("src//VirksomhedsØkonomi.txt", false));
            PrintWriter filUd = new PrintWriter(fil);

            for (Booking b: kalender.hentAlleBookinger()) {
                filUd.println(
                        "Dato: " + b.getDato() +
                        "; Kl: " +b.getTid() +
                        "; Kunde: " + b.getNavn() +
                        "; Total: " + b.getTotalPrice() + "Kr"
                );
            }
            filUd.close();
            System.out.println("Bookinger gemt i fil!");

        } catch (IOException e) {
            System.out.println("\u001B[31mFejl ved udskrivning af booking!" + e.getMessage() + "\u001B[0m");
        }
    }

    public void findBooking (LocalDate dato) {
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(("src//VirksomhedsØkonomi.txt")));
            String linje = buffer.readLine();
            boolean datoFundet=false;

            while (linje!=null) {
                if (linje.contains("Dato: " + dato.toString())) {
                    System.out.println(linje);
                    datoFundet=true;
                    break;
                }
                if (!datoFundet) {
                    System.out.println("Ingen bookinger for d. " + dato);
                    break;
                }
            }
        }

        catch (IOException e) {
            System.out.println("\u001B[31mFejl ved læsning af fil!" + e.getMessage() + "\u001B[0m");
        }

    }


    public static void main(String[] args) {
        Kalender2 kalender = new Kalender2();
       VirkØkonomi økonomi = new VirkØkonomi(kalender);

        Kunde kunde = new Kunde("Ava", "30222230");
        LocalDate dato = LocalDate.parse("2025-10-26");
        LocalTime time = LocalTime.parse("12:30");
        Booking b = new Booking(kunde, dato, time, 350);

        LocalDate dato2 = LocalDate.parse("2025-12-12");
        økonomi.findBooking(dato2);

        Kunde k2 = new Kunde("Jesper", "20202020");
        LocalDate dato3 = LocalDate.parse("2025-10-29");
        LocalTime time2 = LocalTime.parse("10:30");
        Booking b2 = new Booking(k2,dato3, time2, 450 );
        kalender.tilfoejBooking(b);
        kalender.tilfoejBooking(b2);
        økonomi.gemBooking();


    }

}// coomit og oush main, booking klasse,