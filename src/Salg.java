
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Salg {
    String navn;
    double pris;
    ArrayList<Vare> valgtVarer;
    ArrayList<Service> valgtServices;
    static ArrayList<Vare> muligVarer = new ArrayList<>();
    static ArrayList<Service> muligServices = new ArrayList<>();
    Scanner scn = new Scanner(System.in);


    public static class Service{
        String navn;
        double pris;

        public Service(String navn, double pris){
            this.navn=navn;
            this.pris=pris;
        }
    }
    public static class Vare{
        String navn;
        double pris;

        public Vare(String navn, double pris){
            this.navn=navn;
            this.pris=pris;

        }
    }

    public Salg(String navn, double pris){
        this.navn=navn;
        this.pris=pris;
//        this.muligVarer=new ArrayList<>();
//        this.muligServices=new ArrayList<>();



        this.valgtVarer = new ArrayList<Vare>();
        this.valgtServices = new ArrayList<Service>();


    }

    public void tilfoejVareTilBooking(Scanner scn, Kalender kalender, VirkØkonomi økonomi){ //opret kvittering
        System.out.println("Kundens navn:");
        String kundeNavn=scn.nextLine();
        kundeNavn = SystemUtility.gyldigtNavn(kundeNavn,scn);

        LocalDate bookingDate = SystemUtility.gyldigDato(scn);

        Booking booking= null;
        for(Booking b : kalender.hentAlleBookinger()) {
            if(b.getNavn().equalsIgnoreCase(kundeNavn)&& b.getDato().equals(bookingDate)){
                booking = b;
                break;
            }

        }
        if(booking==null){
            System.out.println("Booking ikke fundet!");
            return;
        }

        Salg salg = new Salg("salg",0.0);
        boolean tilfoejvare=true;

        while(tilfoejvare==true){ //tilføj vare til Arraylist
            System.out.println("Tilgængelige varer og services: ");
            visVarer();
            visService();
            System.out.println("0. Færdig");

            int valg= scn.nextInt();
            scn.nextLine();

            if(valg==0){
                tilfoejvare=false;
            } else if (valg <= muligVarer.size()){
                Vare valgtVare = muligVarer.get(valg - 1);
                salg.tilfoejVarer(valgtVare);
                System.out.println("Valgt vare tilføjet");
            } else if (valg - muligVarer.size() <= muligServices.size()) {
                Service valgtService = muligServices.get(valg - muligVarer.size() - 1);
                salg.tilfoejService(valgtService);
            }
            else {
                System.out.println("ugyldigt valg");
            }

        }
        booking.setTotalPrice(booking.getTotalPrice() + salg.pris);
        System.out.println("Ny Totalt beløb: "+ booking.getTotalPrice()+"Kr,-");
        bogfoerSalg(booking,salg);
        økonomi.gemBooking();
    } //også brugt til service kobler en existerende booking med vare/service

    public void fjernService(String navn){
        Salg.loadServicesFraFil();
        Salg.loadVareFraFil();
        boolean fundet=false;
        for(int i=0; i<muligServices.size(); i++){
            Service s = muligServices.get(i);
            if(s.navn.equalsIgnoreCase(navn)){
                muligServices.remove(i);
                fundet=true;
                System.out.println("Service: "+ s.navn +" er slettet");
                break;
            }
        }
    } // fjern services fra arraylist som txt-fil bliver læst fra
    public void fjernVarer(String navn){
        Salg.loadServicesFraFil();
        Salg.loadVareFraFil();
        boolean fundet=false;
        for(int i=0; i< muligVarer.size(); i++){
            Vare v = muligVarer.get(i);
            if(v.navn.equalsIgnoreCase(navn)){
                muligVarer.remove(i);
                fundet = true;
                System.out.println("vare: "+v.navn+" er slettet.");
                break;
            }
        }
        if(!fundet){
            System.out.println("Ingen varer/service med navnet: "+ navn + " blev fundet");
        }
    } //fjern vare fra arraylist som txt-fil bliver læst fra

    public void tilfoejService(Service service){
        valgtServices.add(service);
        this.pris += service.pris;
        gemAlleServicesTilFil();
    } //tilføj service til arraylist som txt-fil bliver læst fra
    public void tilfoejVarer(Vare vare){
        valgtVarer.add(vare);
        this.pris += vare.pris;
        gemAlleVarerTilFil();
    } //tilføj vare til arraylist som txt-fil bliver læst fra

    public void visVarer(){
        Salg.loadServicesFraFil();
        Salg.loadVareFraFil();
        for (int i = 0; i < muligVarer.size(); i++) {
            Vare v = muligVarer.get(i);
            System.out.println((i+1) +" "+ v.navn+" - "+ v.pris+"Kr,-");
        }
    } //viser varer der er i arraylist, men læser først fil ind i arraylist
    public void visService(){
        Salg.loadServicesFraFil();
        Salg.loadVareFraFil();
        for (int i = 0; i < muligServices.size(); i++){
            Service s = muligServices.get(i);
            System.out.println((muligVarer.size()+i+1)+" "+s.navn+" - "+ s.pris+"Kr,-");
        }
    }//viser service der er i arraylist, men læser først fil ind i arraylist

    private void bogfoerSalg(Booking booking, Salg salg){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src//Bogføring.txt",true))){
            StringBuilder linje = new StringBuilder();
            linje.append(booking.getDato()).append(";")
                    .append(booking.getTid()).append(";")
                    .append(booking.getNavn()).append(";")
                    .append(booking.getTelefon()).append(";");

            for(Vare v:salg.valgtVarer){
                linje.append(v.navn).append("-").append(v.pris).append(",");
            }
            for (Service s:salg.valgtServices){
                linje.append(s.navn).append("-").append(s.pris).append(",");
            }
            linje.append(";TOTAL=").append(salg.pris);
            bw.write(linje.toString());
            bw.newLine();

            System.out.println("salget er bogført");

        }catch(IOException e){
            System.out.println("Fejl ved bogføring" + e.getMessage());

        }
    } //skriver booking objektet med tilføjede vare/service ind i en txt-fil
    public static void visKvitteringerFraFil() {
        try (BufferedReader br = new BufferedReader(new FileReader("src//Bogføring.txt"))) {
            String linje;
            while ((linje = br.readLine()) != null) {
                System.out.println(linje);
            }
        } catch (IOException e) {
            System.out.println("Fejl ved læsning af kvitteringsfil: " + e.getMessage());
        }
    } //viser alle objekter med tilhørende variabler fra bogføring
    public static  void findDagKvittering(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Dato for Booking yyyy-mm-dd");
        String soegedato = String.valueOf(SystemUtility.gyldigDato(scn));

        try(BufferedReader br = new BufferedReader(new FileReader("src//Bogføring.txt"))){
            boolean fundet=false;
            String linje;
            while ((linje = br.readLine()) !=null){
                if(linje.startsWith(soegedato)){
                    System.out.println(linje);
                    fundet=true;
                }
            }
            if(!fundet){
                System.out.println("ingen kvitteringer på: "+ soegedato);
                System.out.println("");
            }
        }catch (IOException e){
            System.out.println("fejl ved læsning" + e.getMessage());
        }

        Booking booking = null;


    } //viser alle objekter for en specifik dag med tilhørende variabler fra bogføring

    public void gemAlleVarerTilFil() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src//Varer.txt",true))) {
            for (Vare v : valgtVarer) {
                bw.write(" " + v.navn + ";" + v.pris);
                bw.newLine();
            }
            System.out.println("nye Varer er gemt");
        } catch (IOException e) {
            System.out.println("Fejl ved gemning: " + e.getMessage());
        }
    }//gemmer nye vare bruger tilføjer til txt-fil
    public void gemAlleServicesTilFil() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src//Services.txt",true))) {

            for (Service s : valgtServices) {
                bw.write(" " + s.navn + ";" + s.pris);
                bw.newLine();
            }
            System.out.println("nye Services er gemt");
        } catch (IOException e) {
            System.out.println("Fejl ved gemning: " + e.getMessage());
        }
    }//gemmer nye vare bruger tilføjer til txt-fil

    public static void loadServicesFraFil() {
        muligServices.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src//Services.txt"))) {
            String linje;
            while ((linje = br.readLine()) != null) {
                String[] dele = linje.split(";");
                if (dele.length == 2) {
                    String navn = dele[0].trim();
                    double pris = Double.parseDouble(dele[1].trim());
                    muligServices.add(new Service(navn, pris));
                }
            }

        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af services: " + e.getMessage());
        }
    } //læser services fra txt-fil til arraylist
    public static void loadVareFraFil() {
        muligVarer.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src//Varer.txt"))) {
            String linje;
            while ((linje = br.readLine()) != null) {
                String[] dele = linje.split(";");
                if (dele.length == 2) {
                    String navn = dele[0].trim();
                    double pris = Double.parseDouble(dele[1].trim());
                    muligVarer.add(new Vare(navn, pris));
                }
            }

        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af Varer: " + e.getMessage());
        }
    } //læser varer fra txt-fil til arraylist

} // Salg class
