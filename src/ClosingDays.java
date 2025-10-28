import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClosingDays {


    public static boolean checkDayOfWeek(LocalDate dato) {
        DayOfWeek dayOfWeek = dato.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            System.out.println("\u001B[38;2;255;193;7mDu prøver at booke på en weekend, vi har lukket d. " + dato + "!\u001B[0m");
            return true;
        }
        return false;
    } //checkDayOfWeek

    public static boolean checkHolyDays(LocalDate dato) {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("src//HolydayList.txt"));
            ArrayList<String> list = new ArrayList<>();
            String linje = bReader.readLine();

            while (linje != null) {
                String[] bidder = linje.split(";");
                String holyday = bidder[0];
                String date = bidder[1];
                list.add(date);
                linje = bReader.readLine();              //læs næste linje
            }
            bReader.close();
            if (list.contains(dato.toString())) {
                System.out.println("\u001B[38;2;255;193;7mDu prøver at booke på en helligdag, vi har lukket d. " + dato + "!\u001B[0m");
                return true;
            }

        } catch (IOException e) {
            System.out.println("\u001B[31mFejl ved indlæsning af fil!" + e.getMessage() + "\u001B[0m");
        }
        return false;
    } //checkHolyDays



}