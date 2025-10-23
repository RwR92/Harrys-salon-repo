import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Kalender {
    ArrayList<Booking> bookinger = new ArrayList<>();

    public void tilføjBooking(Booking booking){
        bookinger.add(booking);
    }
}

class testing1{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Kalender kalender = new Kalender();

        System.out.println("Kundens navn");
        String navn = scan.nextLine();
        System.out.println("Kundens nummer");
        String nummer = scan.nextLine();
        Kunde kunde = new Kunde(navn, nummer);

        System.out.println("Dato? (yyyy-mm-dd)");
        LocalDate dato = LocalDate.parse(scan.nextLine());
        System.out.println("Tid (hh:mm)");
        LocalTime tid = LocalTime.parse(scan.nextLine());

        kalender.tilføjBooking(new Booking(kunde, dato, tid));
        System.out.println(kalender.bookinger);
    }
}
