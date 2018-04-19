import java.sql.*;

class ConnectDB {

    private static final String PROTOKOLLA = "jdbc:postgresql:";
    private static final String PALVELIN = "localhost";
    private static final String PORTTI = "5432";
    private static final String TIETOKANTA = "divari";  // tähän oma käyttäjätunnus
    private static final String KAYTTAJA = "postgres";  // tähän oma käyttäjätunnus
    private static final String SALASANA = "salakala";  // tähän tietokannan salasana


    private static Connection connect(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(args[0] + "//" + args[1] + ":" + args[2] + "/" + args[3], args[4], args[5]);
        } catch (SQLException poikkeus) {
            System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());
        }
        return con;
    }

    private static void closeConnection(Connection connection) {
        if (connection != null) try {
            connection.close();
        } catch(SQLException poikkeus) {
            System.out.println("Yhteyden sulkeminen tietokantaan ei onnistunut. Lopetetaan ohjelman suoritus.");
        }
        finally {
            System.out.println("Connection closed.");
        }
    }

    static void doSearch() {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);

        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT *" + "FROM teos");
            if (rset.next()) {
                System.out.println("Teoksia löytyi: " + rset.getInt(1));
                System.out.println("Ensimmäinen teos on nimeltään " + rset.getString("nimi"));
                ResultSetMetaData metadata = rset.getMetaData();
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    row.append(rset.getString(i)).append(", ");
                }
                System.out.println("Ja sitten: " + row);
            } else {
                System.out.println("Ei löytynyt mitään!");
            }
            stmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
    }

    static void doSearchByName(String paramName) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);

        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("SELECT * FROM teos WHERE nimi ILIKE ?");
            pstmt.clearParameters();
            pstmt.setString(1, "%" + paramName + "%");
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                System.out.println("Tämä tulee prepared statementista hakusanalla: " + paramName);
                System.out.println("Teoksia löytyi: " + rset.getInt(1));
                System.out.println("Ensimmäinen teos on nimeltään " + rset.getString("nimi"));
                ResultSetMetaData metadata = rset.getMetaData();
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    row.append(rset.getString(i)).append(", ");
                }
                System.out.println("Ja sitten: " + row);
            } else {
                System.out.println("Ei löytynyt mitään!");
            }
            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
    }

    static String getAuthFromDb(String username) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        String password = "";

        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("SELECT salasana FROM asiakas WHERE ktunnus = ?");
            pstmt.clearParameters();
            pstmt.setString(1, username);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                password = rset.getString("salasana");
            } else {
                System.out.println("Ei löytynyt mitään!");
                password = null;
            }
            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
        return password;
    }

    static void addUser(String[] args) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        String name, uid, password, address, phone, email = "";
        name = args[0]; uid = args[1]; password = args[2]; address = args[3]; phone = args[4]; email = args[5];
        Connection con = connect(params);


        /* TODO:
            - formulate prepared statements
         */

        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("INSERT INTO asiakas (ktunnus, salasana, nimi, osoite, pnumero, email) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.clearParameters();
            pstmt.setString(1, uid);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, address);
            pstmt.setString(5, phone);
            pstmt.setString(6, email);

            int resultsNum = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + resultsNum);
            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset

        } catch ( SQLException err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
}
