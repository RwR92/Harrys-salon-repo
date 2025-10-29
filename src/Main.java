
public class Main {
    public static void main(String[] args) {
        Kalender k=new Kalender();
        VirkØkonomi økonomi = new VirkØkonomi(k);
        økonomi.loadFraFil();
        Menuer m=new Menuer(k, økonomi);
        m.menuStart();
    }
} //Main klasse