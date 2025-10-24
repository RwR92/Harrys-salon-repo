import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.*;


    public class Kvittering {
        Scanner keyboard = new Scanner(System.in);
        private String customerName;
        private String date;
        private int numberOfHaircuts;
        private double totalPrice;
        private double vat;



        public Kvittering() {  //giver værdierne customerName og numberOfHaircuts til constructer
            this.customerName = kundeNavn();
            this.date = date();
            this.totalPrice = calculatePrice();

        }

        public String kundeNavn() {
            System.out.println("Kundens navn: ");
            return keyboard.nextLine();
        }


        public String date() {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm");
            return dateTime.format(formatter);
        }

        public double calculatePrice() {
            double priceHaircut = 200;
            System.out.println("Antal klipninger: ");
            this.numberOfHaircuts = keyboard.nextInt();
            keyboard.nextLine();
            this.totalPrice = numberOfHaircuts * priceHaircut;
            this.vat = totalPrice * 0.25;
            return totalPrice;
        }

       public String toString () {
           return
                   "HARRY'S SALON \n" +
                   "--- Kvittering ---\n" +
                   "Dato: " + date + "\n" +
                   "Kunde: " + customerName + "\n" +
                   "TOTAL: " + totalPrice + " Kr\n" +
                   "Moms udgør: " + vat + " Kr";
       }

        public static void main (String [] args) {
            Kvittering k1 = new Kvittering();
            System.out.println(k1);

        }

}