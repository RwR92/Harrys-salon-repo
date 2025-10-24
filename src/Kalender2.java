import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Kalender2 {
    private ArrayList<Booking> bookinger = new ArrayList<>();


    public void tilfoejBooking(Booking b){
        for(Booking eksisterende : bookinger){
            if(eksisterende.getDato().equals(b.getDato()) && eksisterende.getTid().equals(b.getTid())){
                System.out.println("Tidspunktet er optaget.");
                return;
            }
        }
        bookinger.add(b);
        System.out.println("Booking tilføjet for " + b.toString());
    }
    public  void fjernBooking(String navn, LocalDate dato, LocalTime tidspunkt){
        for(int i = 0; i < bookinger.size(); i++){
            Booking b = bookinger.get(i);
            if(b.getNavn().equals(navn) && b.getDato().equals(dato) && b.getTid().equals(tidspunkt)){
                bookinger.remove(i);
                System.out.println("Booking er fjernet for "+ navn);
                return;
            }
        }
    }
    public void visBookingerForDag(LocalDate dato){
        System.out.println("Bookinger for "+ dato + ":");
        for(Booking b : bookinger){
            if(b.getDato().equals(dato)){
                System.out.println("- "+b.getNavn() +" kl. "+b.getTid()+" ("+b.getDato());
            }
        }
    }
    public boolean erTidOptaget(LocalDate dato, LocalTime tidspunkt){
        for(Booking b : bookinger){
            if(b.getDato().equals(dato) && b.getTid().equals(tidspunkt)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Booking>hentAlleBookinger(){
        return bookinger;
    }
}//Kalender class
