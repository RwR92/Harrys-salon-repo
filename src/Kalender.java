import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Kalender {
    private final ArrayList<Booking> bookinger = new ArrayList<>();
    private final ArrayList<Booking> arkiv = new ArrayList<>();
    final ArrayList<Kvittering> faktura = new ArrayList<>();

    // METODER TIL BOOKING
    public void tilfoejBooking(Booking booking){ //ansvar = sørge for booking overholder regler og så tilføje det
        DayOfWeek dag = booking.getDato().getDayOfWeek();
        if (dag.equals(DayOfWeek.SATURDAY) || (dag.equals(DayOfWeek.SUNDAY))){
            System.out.println("Harry's Salon har ikke åben i weekenden. Der er lukket "+dag+".");
            return;
        }

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
        System.out.println("Booking oprettet for "+booking.getNavn()+". Tid: "+booking.getDato()+" "+booking.getTid());
        bookinger.add(booking);
        sortList();
    }

    public void fjernBooking(String telefon){ // ansvar = give admin mulighed for at fjerne booking
        boolean fjernetBooking = bookinger.removeIf(b -> b.getTelefon().equals(telefon));
        if (fjernetBooking){
            System.out.println("Booking er fjernet for ["+telefon+"].\n");
        } else {
            System.out.println("Ingen booking med telefon nummeret ["+telefon+"] blev fundet.\n");
        }
    }

    public void bookingFaerdiggjort(){ // ansvar = efter dag rykkes booking i arkiv og faktura laves
        bookinger.removeIf(b -> {
            if (b.getDato().isBefore(dagsDato)) {
                arkiv.add(b);
                faktura.add(new Kvittering(b.getKunde(), b.getDato(), b.getTid()));
                return true;
            }
            return false;
        });
    }

    public void hentArkiv(){ // ansvar = udskrive arkiv
        System.out.println();
        System.out.println("Arkiv hos Harry's salon:");
        for(Booking b : arkiv){
            System.out.println(b);
        }
        System.out.println();
    }

    public void hentAlleBookinger(){ // ansvar = udskrive alle bookings
        System.out.println();
        System.out.println("Bookinger hos Harry's salon:");
        for(Booking b : bookinger){
            System.out.println(b);
        }
        System.out.println();
    }

    public void visBookingerForDag(LocalDate dato){ // ansvar = udskrive bookings for specifik dag
        System.out.println("Bookinger for "+ dato + ":");
        for(Booking b : bookinger){
            if(b.getDato().equals(dato)){
                System.out.println(b);
            }
        }
        System.out.println();
    }

    public void sortList(){ // ansvar = sortere bookings så de er efter tid
        bookinger.sort((b1, b2) -> {
            int comparator = b1.getDato().compareTo(b2.getDato());
            if (comparator != 0) return comparator;
            return b1.getTid().compareTo(b2.getTid());
        });
    }

    // METODER TIL BOGFØRING
    public int samletRegningForDag(LocalDate dag){ // ansvar = faktura for en dag
        int samletRegning = 0;
        for(Kvittering k : faktura){
            if (k.getDag().equals(dag)){
                samletRegning += k.getPris();
            }
        }
        return samletRegning;
    }


    // SIMULATION
    LocalDate dagsDato = LocalDate.of(2025, 10, 27);

    public void simulereDag(){ // ansvar = simulation
        dagsDato = dagsDato.plusDays(1);
        System.out.println("Ny simuleret dag = "+dagsDato);
        bookingFaerdiggjort();
    }
} // kalender
