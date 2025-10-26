import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private LocalDate dato;
    private LocalTime tid;
    private Kunde kunde;
    private double totalPrice;

    Booking(Kunde kunde, LocalDate dato, LocalTime tid){
        this.kunde = kunde;
        this.dato = dato;
        this.tid = tid;
        this.totalPrice=0.0;
    }

    public LocalTime getTid(){
        return tid;
    }

    public LocalDate getDato(){
        return dato;
    }
    public String getNavn(){
        return kunde.getNavn();
    }

    public void setTotalPrice (double totalPrice) {
        this.totalPrice=totalPrice;
    }

    public double getTotalPrice () {
        return totalPrice;
    }


    public String toString(){
        return kunde+" "+getDato()+" "+ getTid();
    }
}


