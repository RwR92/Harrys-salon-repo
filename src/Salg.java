import java.util.ArrayList;

public class Salg {
    String navn;
    double pris;
    ArrayList<Vare> varer;

    public Salg(String navn, double pris){
        this.navn=navn;
        this.pris=pris;
        this.varer=new ArrayList<>();
        varer.add(new Vare("Shampoo", 150.0));
        varer.add(new Vare("Voks", 100.0));
        varer.add(new Vare("kam", 50.0));



        }
    public void tilfoejVarer(Vare vare){
        varer.add(vare);
        this.pris += vare.pris;
    }
    public void visVarer(){
        for (Vare v: varer){
            System.out.println(v.navn+" - "+ v.pris+"Kr,-");
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
}
