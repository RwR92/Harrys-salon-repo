import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FilHaandtering {
    Kalender kalender;

    FilHaandtering(Kalender kalender){
        this.kalender = kalender;
    }

    public void hentBooking(){
        try{
            FileReader fil = new FileReader("src//Bookings.txt");
            BufferedReader ind = new BufferedReader(fil);

            String linje = ind.readLine();
            while(linje != null){
                String[] delt = linje.split(";");
                LocalDate dag = LocalDate.parse(delt[0]);
                LocalTime tid = LocalTime.parse(delt[1]);
                String navn = delt[2];
                String tlf = delt[3];
                Kunde kunde = new Kunde(navn, tlf);
                kalender.tilfoejBookingFraFil(new Booking(kunde, dag, tid));
                linje = ind.readLine();
            }
            ind.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void gemBooking(Booking booking){
        try {
            FileWriter fil = new FileWriter("src//Bookings.txt", true);
            BufferedWriter buffer = new BufferedWriter(fil);
            PrintWriter ud = new PrintWriter(buffer);


            ud.println(System.lineSeparator() + booking.getDato() + ";" + booking.getTid() + ";" +
                    booking.getNavn() + ";" + booking.getTelefon());

            ud.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sletBooking(String tlfNummer){
        try {
            FileReader fil = new FileReader("src//Bookings.txt");
            BufferedReader ind = new BufferedReader(fil);

            ArrayList<String> beholdteBookings = new ArrayList<>();

            String linje = ind.readLine();
            while(linje != null){
                String[] delt = linje.split(";");
                String tlf = delt[3];

                if(!tlf.equals(tlfNummer)){
                    beholdteBookings.add(linje);
                }
                linje = ind.readLine();
            }
            ind.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("src//Bookings.txt", false));
            for (String l : beholdteBookings){
                writer.write(l);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
