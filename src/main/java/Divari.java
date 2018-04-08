import java.sql.*;

public class Divari {

    private static final String PROTOKOLLA = "jdbc:postgresql:";
    private static final String PALVELIN = "localhost";
    private static final int PORTTI = 5432;
    private static final String TIETOKANTA = "divari";  // tähän oma käyttäjätunnus
    private static final String KAYTTAJA = "postgres";  // tähän oma käyttäjätunnus
    private static final String SALASANA = "salakala";  // tähän tietokannan salasana

    public static void main (String args[]) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(PROTOKOLLA + "//" + PALVELIN + ":" + PORTTI + "/" + TIETOKANTA, KAYTTAJA, SALASANA);

            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT *" +
                    "FROM divari");
            if (rset.next()) {
                System.out.println("Teoksia löytyi: " + rset.getInt(1));
            } else {
                System.out.println("Ei löytynyt mitään!");
            }
            stmt.close();  // sulkee automaattisesti myös tulosjoukon rset

        } catch (SQLException poikkeus) {
            System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());
        }

        if (con != null) try {     // jos yhteyden luominen ei onnistunut, con == null
            con.close();
        } catch(SQLException poikkeus) {
            System.out.println("Yhteyden sulkeminen tietokantaan ei onnistunut. Lopetetaan ohjelman suoritus.");
            return;
        }
    }
}