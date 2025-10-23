public class Kunde {
    private String navn;
    private String telefon;

    public Kunde(String navn, String telefon){
        this.navn = navn;
        this.telefon = telefon;
    }
    public String getNavn(){
        return navn;
    }
    public String getTelefon(){
        return telefon;
    }

    public String toString(){
        return navn + " ("+telefon+")";
    }
}

class testing0{
    public static void main(String[] args) {
        String tlf = "20130021";
        Kunde a = new Kunde("Nico", tlf);
        System.out.println(a);
    }
}
