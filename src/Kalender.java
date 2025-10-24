import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Kalender {
    ArrayList<Booking> bookinger = new ArrayList<>();

    public void tilfoejBooking(Booking booking){
        if (booking.getTid().isBefore(LocalTime.of(10, 0)) || booking.getTid().isAfter(LocalTime.of(18, 0))){
            System.out.println("Booking skal være inde for tidsrummet 10:00 til 18:00.\n");
            return;
        }

        for(Booking eksisterende : bookinger){
            if(eksisterende.getDato().equals(booking.getDato()) && eksisterende.getTid().equals(booking.getTid())){
                System.out.println("Tidspunktet er optaget.\n");
                return;
            }
        }
        bookinger.add(booking);
        sortList();
    }

    public void fjernBooking(String telefon){
        boolean fjernetBooking = bookinger.removeIf(b -> b.getTelefon().equals(telefon));
        if (fjernetBooking){
            System.out.println("Booking er fjernet for ["+telefon+"].\n");
        } else {
            System.out.println("Ingen booking med telefon nummeret ["+telefon+"] blev fundet.\n");
        }
    }

    public void hentAlleBookinger(){
        System.out.println();
        System.out.println("Bookinger hos Harry's salon:");
        for(Booking b : bookinger){
            System.out.println(b);
        }
        System.out.println();
    }

    public void visBookingerForDag(LocalDate dato){
        System.out.println("Bookinger for "+ dato + ":");
        for(Booking b : bookinger){
            if(b.getDato().equals(dato)){
                System.out.println(b);
            }
        }
        System.out.println();
    }

    public void sortList(){
        bookinger.sort((b1, b2) -> {
            int comparator = b1.getDato().compareTo(b2.getDato());
            if (comparator != 0) return comparator;
            return b1.getTid().compareTo(b2.getTid());
        });
    }
} // kalender
