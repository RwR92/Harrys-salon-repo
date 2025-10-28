import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Kalender k=new Kalender();
        VirkØkonomi økonomi = new VirkØkonomi(k);
        økonomi.loadFraFil();
        Menuer m=new Menuer(k, økonomi);
        m.menuStart();
    }
} //Main klasse



