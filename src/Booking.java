import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private LocalDate dato;
    private LocalTime tid;
    private Kunde kunde;

    Booking(Kunde kunde, LocalDate dato, LocalTime tid){
        this.kunde = kunde;
        this.dato = dato;
        this.tid = tid;
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

    public String toString(){
        return kunde+" "+getDato()+" "+ getTid();
    }
}


