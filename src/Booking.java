import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private final LocalDate dato;
    private final LocalTime tid;
    private final Kunde kunde;

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

    public String getTelefon(){
        return kunde.getTelefon();
    }

    public String getNavn(){
        return kunde.getNavn();
    }

    public Kunde getKunde(){
        return kunde;
    }

    public String toString(){
        return kunde+" "+getDato()+" "+ getTid();
    }
}


