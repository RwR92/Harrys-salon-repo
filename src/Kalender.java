import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Kalender {
    private ArrayList<Booking> bookinger = new ArrayList<>();


    public void tilfoejBooking(Booking b){
        if(!tjekTidsrum(b)){
            System.out.println("Booking skal være inde for tidsrummet 10:00 til 18:00.\n");
            return;
        }
            for(Booking eksisterende : bookinger){
                if(eksisterende.getDato().equals(b.getDato()) && eksisterende.getTid().equals(b.getTid())){
                    System.out.println("Tidspunktet er optaget.");
                    return;
                }
            }
        bookinger.add(b);

    }//tilføjBooking

    public void fjernBooking(String navn, LocalDate dato){
        for(int i = 0; i < bookinger.size(); i++){
            Booking b = bookinger.get(i);
            if(b.getNavn().equals(navn) && b.getDato().equals(dato)){
                bookinger.remove(i);
                System.out.println("Booking er fjernet for "+ navn);
                return;
            }
        }
    }// fjernBooking

    public void visLedigeTiderFor4Dage(LocalDate start){
        LocalDate d = næsteÅbneDag(start);
        int åbneDage = 0;

        while(åbneDage < 4){
            ArrayList<LocalTime>ledige = findLedigeTider(d);
            System.out.println(d + " -> "+ledige);
            d = næsteÅbneDag(d.plusDays(1));
            åbneDage ++;
        }
    }// visLedigeTiderFor4Dage metode

    private boolean erLukket(LocalDate d) { //løber dage igennem fra næsteÅbneDag og tjekker om de er åbne eller ej.
        return ClosingDays.checkDayOfWeek(d) || ClosingDays.checkHolyDays(d);
    }// erLukket metode

    private LocalDate næsteÅbneDag(LocalDate d) { //tilføjer dage som retunere true i erLukket metoden til visLedigeTiderFor4Dage metoden
        LocalDate x = d;
        while (erLukket(x)) x = x.plusDays(1);
        return x;
    }// NæsteÅbneDag metode
    
    public boolean erTidOptaget(LocalDate dato, LocalTime tidspunkt){
        for(Booking b : bookinger){
            if(b.getDato().equals(dato) && b.getTid().equals(tidspunkt)){
                return true;
            }
        }
        return false;
    }//erTidOptaget metode

    public ArrayList<Booking>hentAlleBookinger(){
        return bookinger;
    }

    public ArrayList<LocalTime> genererMuligeTider(){
        ArrayList<LocalTime> tider = new ArrayList<>();

        for (int time = 10; time < 18; time ++){
            tider.add(LocalTime.of(time,0));
            tider.add(LocalTime.of(time, 30));
        }
        return tider;
    }

    public ArrayList<LocalTime> findLedigeTider(LocalDate dato){
        ArrayList<LocalTime> muligeTider = genererMuligeTider();
    for (Booking b : bookinger){
        if(b.getDato().equals(dato)){
            muligeTider.remove(b.getTid());
        }
}
        return muligeTider;
    }

    public boolean tjekTidsrum(Booking booking){
        if (booking.getTid().isBefore(LocalTime.of(10, 0)) || booking.getTid().isAfter(LocalTime.of(18, 0))){
            return false;
        }
        return true;
    }

}//Kalender class
