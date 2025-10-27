import java.time.LocalDate;
import java.time.LocalTime;

public class Kvittering {
    private final Kunde kunde;
    private int pris;
    private final LocalDate dag;
    private final LocalTime tid;

    Kvittering(Kunde kunde, LocalDate dag, LocalTime tid){
        this.kunde = kunde;
        pris = 300;
        this.dag = dag;
        this.tid = tid;
    }

    public int getPris(){
        return pris;
    }

    public LocalDate getDag(){
        return dag;
    }

    public String toString(){
        return kunde+" Tid: "+dag+" "+tid+" Pris: "+pris;
    }
}
