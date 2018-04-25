import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
//            System.out.println("Connection closed.");
        }
    }

    static void searchAll() {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);

        try {
            Integer rowCount = 0;
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT *" + "FROM keskusdivari.teos");
            while (rset.next()) {
                System.out.println("Teos on nimeltään " + rset.getString("nimi"));
                ResultSetMetaData metadata = rset.getMetaData();
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    row.append(rset.getString(i)).append(", ");
                }
                System.out.println("Täydet tiedot: " + row);
                rowCount++;
            }
            System.out.println("Tuloksia: " + rowCount + " kappaletta");
            stmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
    }

    static void searchAllByColumn(String paramName) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);

        try {
            PreparedStatement pstmt;
            String sql = "SELECT t.luokka, SUM(n.hinta), AVG(n.hinta)\n" +
                    "FROM keskusdivari.nide AS n\n" +
                    "LEFT JOIN keskusdivari.teos AS t ON n.teos_id = t.teos_id\n" +
                    "WHERE n.nide_id NOT IN (\n" +
                    "select nide_id from keskusdivari.tilaus where tila IN ('varattu', 'myyty'))" +
                    "GROUP BY t.luokka\n" +
                    ";";
            pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            //pstmt.setString(1, paramName);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                do {
                    System.out.println("LUOKKA: " + rset.getString("luokka"));
                    System.out.println(String.format("KOKONAISHINTA: %.2f euroa", rset.getFloat("sum")));
                    System.out.println(String.format("KESKIHINTA: %.2f euroa", rset.getFloat("avg")));
                    System.out.println("------------------------");
                } while (rset.next());
            }
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
    }

    static List<Integer> doSearchByColumn(String columnName, String paramName) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
//        System.out.println(columnName + " " + paramName);

        List<Integer> idList = new ArrayList<Integer>();

        try {
            PreparedStatement pstmt;
            Integer rowCount = 0;
            //String sql = "SELECT * FROM keskusdivari.teos WHERE " + columnName + " ILIKE ?";
            String sql = "SELECT keskusdivari.nide.nide_id, keskusdivari.teos.nimi,";
            sql += "keskusdivari.teos.tekija, keskusdivari.nide.hinta, keskusdivari.divari.nimi AS divari ";
            sql += "FROM keskusdivari.teos ";
            sql += "LEFT JOIN keskusdivari.nide ON keskusdivari.teos.teos_id = keskusdivari.nide.teos_id ";
            sql += "LEFT JOIN keskusdivari.divari ON keskusdivari.nide.divari_id = keskusdivari.divari.divari_id ";
            sql += "WHERE keskusdivari.nide.nide_id NOT IN (\n" +
                    "\tselect nide_id from keskusdivari.tilaus where tila IN ('varattu', 'myyty') \n" +
                    ")";
            sql += "AND keskusdivari.teos." + columnName + " ILIKE ? ;";
            pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, "%" + paramName + "%");
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                System.out.println("------------------------");
                System.out.println("Hakutulos " + (rowCount + 1));
                System.out.println("NIMI: " + rset.getString("nimi"));
                System.out.println("KIRJAILIJA: " + rset.getString("tekija"));
                System.out.println(String.format("HINTA: %.2f euroa", rset.getFloat("hinta")));
                System.out.println("DIVARI: " + rset.getString("divari"));
//                ResultSetMetaData metadata = rset.getMetaData();
//                StringBuilder row = new StringBuilder();
//                for (int i = 1; i <= metadata.getColumnCount(); i++) {
//                    row.append(rset.getString(i)).append(", ");
//                }
                idList.add(rset.getInt("nide_id"));
                rowCount++;
            }
            System.out.println("------------------------");
            System.out.println("Tuloksia: " + rowCount + " kappaletta");

            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
        
        return idList;
    }

    // returns an array where 0 = password, 1 = user id, 2 = admin on successful auth
    static String[] getAuthFromDb(String username) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        String[] authData = new String[3];

        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("SELECT salasana, asiakas_id, admin FROM keskusdivari.asiakas WHERE ktunnus = ?");
            pstmt.clearParameters();
            pstmt.setString(1, username);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                authData[0] = rset.getString("salasana");
                authData[1] = Integer.toString(rset.getInt("asiakas_id"));
                authData[2] = Boolean.toString(rset.getBoolean("admin"));
            } else {
                System.out.println("Ei löytynyt mitään!");
                authData[0] = null;
                authData[1] = null;
                authData[2] = null;
            }
            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
        return authData;
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
            pstmt = con.prepareStatement("INSERT INTO keskusdivari.asiakas (ktunnus, salasana, nimi, osoite, puhnro, email) VALUES (?, ?, ?, ?, ?, ?)");
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

    // returns the id of the book if found, otherwise -1
    static int getBookByIsbn(String isbn) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("SELECT keskusdivari.teos.teos_id FROM teos WHERE isbn = ?");
            pstmt.clearParameters();
            pstmt.setString(1, isbn);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getInt("teos_id");
            } else {
                return -1;
            }
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
        
        return -1;
    }
    
    static void addBook(String[] args) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("INSERT INTO keskusdivari.teos (isbn, tekija, nimi, luokka, tyyppi, paino) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, args[0]);
            pstmt.setString(2, args[1]);
            pstmt.setString(3, args[2]);
            pstmt.setString(4, args[3]);
            pstmt.setString(5, args[4]);
            pstmt.setInt(6, Integer.parseInt(args[5]));
            pstmt.executeUpdate();
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
    
    static void addItem(String[] args) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("INSERT INTO keskusdivari.nide (teos_id, hinta, sisaanosto_hinta, divari_id) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, Integer.parseInt(args[0]));
            pstmt.setFloat(2, Float.parseFloat(args[1]));
            pstmt.setFloat(3, Float.parseFloat(args[2]));
            pstmt.setInt(4, Integer.parseInt(args[3]));
            pstmt.executeUpdate();
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
    
    static void addOrder(Customer customer, int itemId) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            PreparedStatement pstmt;
            pstmt = con.prepareStatement("INSERT INTO keskusdivari.tilaus (pvm, tila, asiakas_id, nide_id) VALUES (?, ?, ?, ?)");
            
            LocalDate curTime = LocalDate.now();
            pstmt.setDate(1, new Date(curTime.getYear() - 1900, curTime.getMonthValue() - 1, curTime.getDayOfMonth()));
            pstmt.setString(2, "varattu");
            pstmt.setInt(3, customer.userId());
            pstmt.setInt(4, itemId);
            pstmt.executeUpdate();
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
    
    static int getActiveOrders(Customer customer) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);

        int rowCount = 0;
        try {
            PreparedStatement pstmt;
            float sum = 0;
            int totalWeight = 0;
            String sql = "SELECT keskusdivari.teos.nimi, keskusdivari.teos.tekija,";
            sql += "keskusdivari.nide.hinta, keskusdivari.divari.nimi AS divari, ";
            sql += "SUM(keskusdivari.nide.hinta) AS summa, SUM(keskusdivari.teos.paino) AS paino ";
            sql += "FROM keskusdivari.tilaus ";
            sql += "LEFT JOIN keskusdivari.nide ON keskusdivari.tilaus.nide_id = keskusdivari.nide.nide_id ";
            sql += "LEFT JOIN keskusdivari.teos ON keskusdivari.nide.teos_id = keskusdivari.teos.teos_id ";
            sql += "LEFT JOIN keskusdivari.divari ON keskusdivari.nide.divari_id = keskusdivari.divari.divari_id ";
            sql += "WHERE keskusdivari.tilaus.asiakas_id = ? ";
            sql += "AND keskusdivari.tilaus.tila = 'varattu' ";
            sql += "GROUP BY keskusdivari.teos.nimi, keskusdivari.teos.tekija, keskusdivari.nide.hinta, divari";
            pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setInt(1, customer.userId());
            ResultSet rset = pstmt.executeQuery();
            System.out.println("Varatut niteesi:");
            while (rset.next()) {
                sum = rset.getFloat("summa");
                totalWeight = rset.getInt("paino");
                System.out.println("------------------------");
                System.out.println("NIMI: " + rset.getString("nimi"));
                System.out.println("KIRJAILIJA: " + rset.getString("tekija"));
                System.out.println(String.format("HINTA: %.2f euroa", rset.getFloat("hinta")));
                System.out.println("DIVARI: " + rset.getString("divari"));
//                ResultSetMetaData metadata = rset.getMetaData();
//                StringBuilder row = new StringBuilder();
//                for (int i = 1; i <= metadata.getColumnCount(); i++) {
//                    row.append(rset.getString(i)).append(", ");
//                }
                rowCount++;
            }
            
            if (rowCount == 0) {
                System.out.println("Sinulla ei ole tällä hetkellä yhtään varausta.");
                return rowCount;
            }
            
            System.out.println("------------------------");
            System.out.println(String.format("Niteiden hinta: %.2f euroa", sum));
            
            float postageRate = 0;
            
            if (totalWeight >= 50 && totalWeight < 100) {
                postageRate = 1.4f;
            }
            else if (totalWeight >= 100 && totalWeight < 250) {
                postageRate = 2.1f;
            }
            else if (totalWeight >= 250 && totalWeight < 500) {
                postageRate = 2.8f;
            }
            else if (totalWeight >= 500 && totalWeight < 1000) {
                postageRate = 5.6f;
            }
            else if (totalWeight >= 1000 && totalWeight < 2000) {
                postageRate = 8.4f;
            }
            else if (totalWeight < 2000) {
                postageRate = 14.0f;
            }
            System.out.println(String.format("Postikulut: %.2f euroa", postageRate));
            System.out.println(String.format("Tilauksen yhteishinta: %.2f euroa", sum + postageRate));
            System.out.println();
            pstmt.close();  // sulkee automaattisesti myös tulosjoukon rset
        } catch (SQLException err) {
            System.out.println("Shit went down, yo " + err.getMessage());
        }
        finally {
            closeConnection(con);
        }
        
        return rowCount;
    }
    
    static void finishOrders(Customer customer) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            String sql = "UPDATE keskusdivari.tilaus SET tila = ? ";
            sql += "WHERE asiakas_id = ?";
            
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "myyty");
            pstmt.setInt(2, customer.userId());
            pstmt.executeUpdate();
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
    
    static void clearOrders(Customer customer) {
        String[] params = {PROTOKOLLA, PALVELIN, PORTTI, TIETOKANTA, KAYTTAJA, SALASANA};
        Connection con = connect(params);
        
        try {
            String sql = "DELETE FROM keskusdivari.tilaus ";
            sql += "WHERE keskusdivari.tilaus.asiakas_id = ? ";
            sql += "AND keskusdivari.tilaus.tila = 'varattu'";
            
            PreparedStatement pstmt;
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customer.userId());
            pstmt.executeUpdate();
        } catch ( Exception err ) {
            System.out.println("Shit went down, yo " + err.getMessage());
        } finally {
            closeConnection(con);
        }
    }
}
