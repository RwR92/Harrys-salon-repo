import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VirkØkonomi {
    private Kalender kalender;

    public VirkØkonomi(Kalender kalender) {
        this.kalender = kalender;
    }


    public void gemBooking() {
        try {
            BufferedWriter fil = new BufferedWriter(new FileWriter("src//ListeBookinger.txt", false));
            PrintWriter filUd = new PrintWriter(fil);

            for (Booking b : kalender.hentAlleBookinger()) {
                filUd.println(
                        b.getDato() +
                                ";" + b.getTid() +
                                ";" + b.getNavn() +
                                ";" + b.getTelefon() +
                                ";" + b.getTotalPrice()
                );
            }
            filUd.close();

        } catch (IOException e) {
            System.out.println("\u001B[31mFejl ved udskrivning af booking!" + e.getMessage() + "\u001B[0m");
        }
    }//gemBooking metode

    public void findBooking(String dato) {
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(("src//ListeBookinger.txt")));
            String linje;
            boolean datoFundet = false;

            while ((linje = buffer.readLine()) != null) {
                if (linje.contains(dato)) {
                    String[] bidder = linje.split(";");
                    String tid = bidder[1];
                    String navn = bidder[2];
                    String pris = bidder[4];

                    System.out.println("Bookinger for " + dato + ":");
                    System.out.println("- " + navn + " kl. " + tid + " Total: " + pris + " kr.");
                    datoFundet = true;
                }
            }
            if (!datoFundet) {
                System.out.println("Ingen bookinger for d. " + dato);
            }
        } catch (IOException e) {
            System.out.println("\u001B[31mFejl ved læsning af fil!" + e.getMessage() + "\u001B[0m");
        }

    }//findBooking metode

    public void loadFraFil() {
        try {
            FileReader fil = new FileReader("src//ListeBookinger.txt");
            BufferedReader ind = new BufferedReader(fil);

            String linje;
            while ((linje = ind.readLine()) != null) {
                String[] bidder = linje.split(";");

                LocalDate dato = LocalDate.parse(bidder[0]);
                LocalTime tid = LocalTime.parse(bidder[1]);

                String navn = bidder[2];
                String nummer = bidder[3];

                double pris = Double.parseDouble(bidder[4]);

                Kunde k = new Kunde(navn, nummer);

                kalender.tilfoejBooking(new Booking(k, dato, tid, pris));
                gemBooking();
            }

        } catch (IOException e) {
            System.out.println("\u001B[31mFejl ved udskrivning af booking!" + e.getMessage() + "\u001B[0m");
        }
    } //loadFraFil metode
}// coomit og oush main, booking klasse,
