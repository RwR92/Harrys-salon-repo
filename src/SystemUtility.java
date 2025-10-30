import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/*
Dette er en klasse til metoder som kan lave checks på bruger inputs og stoppe evt. fejl før de sker.

 */
public class SystemUtility {

    public static int gyldigtValgTilMenuer(String valgIn, java.util.Scanner scn) {
        while (!valgIn.matches("\\d+")) {
            System.out.print("Ugyldigt valg. Brug et tal :");
            valgIn = scn.nextLine();
        }
        return Integer.parseInt(valgIn.trim());
    } //gyldigtValgTilMenuer metode

    public static String gyldigtNummer(String nummer, java.util.Scanner scn) {
        while (!nummer.matches("\\d{8}")) { //"\\d{8}" betyder at nummeret skal indeholde cifre fra 0-9 og være 8 lang.
            System.out.println("Nummeret skal være 8 cifre lang.");
            System.out.print("Kundens mobil nummer: ");
            nummer = scn.nextLine();
        }
        return nummer;
    } // GyldigtNummer metode

    public static String gyldigtNavn(String navn, java.util.Scanner scn) {
        while (!navn.matches("[a-zA-ZæøåÆØÅ\\- ]+")) { //Tjek for om navnet indeholder danske bogstaver eller bindestreg og mellemrum og + for at sike mindst et tegn.
            System.out.print("Ugyldigt navn, brug bogstaver: ");
            navn = scn.nextLine();
        }
        return navn;
    } // gyldigtNavn metode

    public static LocalDate gyldigDato(java.util.Scanner scn) {
        System.out.print("Dato? (yyyy-mm-dd): ");

        while (true) {
            try {
                LocalDate dato = LocalDate.parse(scn.nextLine());
                return dato;
            } catch (DateTimeParseException e) {
                System.out.print("Ugyldig dato, prøv igen (yyyy-mm-dd): ");

            }

        }
    } //gyldigDato metode

    public static String gyldigPrisForStrings(String pris, java.util.Scanner scn){
        while(!pris.matches("\\d+")){
            System.out.println("Prisen kan kun indeholde tal.");
            System.out.print("Varen/servicens pris :");
            pris = scn.nextLine();
        }
        return pris;
    } //gyldigPrisForStrings metode

    public static Double gyldigPrisForDouble(String prisIn, java.util.Scanner scn){
        while(!prisIn.matches("\\d+(\\.\\d+)?")){
            System.out.println("Prisen kan kun indeholde tal.");
            System.out.print("Varen/servicens pris :");
            prisIn = scn.nextLine();
        }
        return Double.parseDouble(prisIn);
    }//gyldigPrisForDouble
}
