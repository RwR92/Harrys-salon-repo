import java.time.DayOfWeek;
import java.time.LocalDate;

public class ClosingDays {





    public static boolean checkDayOfWeek (LocalDate dato) {
        DayOfWeek dayOfWeek = dato.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("\u001B[31mDu prøver at booke på en weekend, vi har lukket d. " + dato +"!\u001B[0m");
            return true;
        }
        return false;
    }
}