public class Kunde {
    private final String navn;
    private final String telefon;

    public Kunde(String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public String getNavn() {
        return navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public String toString() {
        return navn + " (" + telefon + ")";
    }
}

