import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Divari {

    /* TODO:
        - Basic views
            -- logging in
            -- creating new user
        - Prepared statements
        - SQL creation statements
        - Schema-based admin authentication
     */
    
    private static List<Integer> searchBooks() {
        Scanner user_input = new Scanner(System.in);
        Integer selection = View.searchMenuView();
        List<Integer> idList = null;
        boolean cont = true;
        while (cont) {
            try {
                switch (selection) {
                    case 1:
                        System.out.println("Hae teosten nimistä: ");
                        String name = user_input.nextLine();
                        idList = ConnectDB.doSearchByColumn("nimi", name);
                        cont = false;
                        break;
                    case 2:
                        System.out.println("Hae kirjailijoista: ");
                        String author = user_input.nextLine();
                        idList = ConnectDB.doSearchByColumn("tekija", author);
                        cont = false;
                        break;
                    case 3:
                        System.out.println("Hae tyypillä: ");
                        String type = user_input.nextLine();
                        idList = ConnectDB.doSearchByColumn("tyyppi", type);
                        cont = false;
                        break;
                    case 4:
                        System.out.println("Hae luokalla: ");
                        String bookClass = user_input.nextLine();
                        idList = ConnectDB.doSearchByColumn("luokka", bookClass);
                        cont = false;
                        break;
                    default:
                        System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista");
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e);
            }
        }
        
        return idList;
    }

    public static void searchAllBooks() {
        Scanner user_input = new Scanner(System.in);
        Integer selection = View.searchAllMenuView();
        boolean cont = true;
        while (cont) {
            try {
                switch (selection) {
                    case 1:
                        ConnectDB.searchAll();
                        cont = false;
                        break;
                    case 2:
                        //System.out.println("Hae luokalla: ");
                        //String bookClassTot = user_input.nextLine();
                        String bookClassTot = "";
                        ConnectDB.searchAllByColumn(bookClassTot);
                        cont = false;
                        break;
                    default:
                        System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista");
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e);
            }
        }
    }

    public static void main (String args[]) {

        boolean cont = true;
        Customer customer = new Customer();
        Scanner user_input = new Scanner(System.in);

        while (cont && !customer.statusQuery()) {
            try {
                Integer selection = View.userLogInView();
                /* Logging in */
                switch (selection) {
                  case 1:
                    System.out.println("Anna perustietoja itsestäsi:");
                    System.out.println("Nimi:");
                        String name = user_input.nextLine();
                    System.out.println("Käyttäjätunnus:");
                        String uid = user_input.nextLine();
                    System.out.println("Aseta salasana:");
                        String password = user_input.nextLine();
                    System.out.println("Osoite:");
                        String address = user_input.nextLine();
                    System.out.println("Puhelinnumero (+358...):");
                        String phoneNumber = user_input.nextLine();
                    System.out.println("Email:");
                        String email = user_input.nextLine();

                    String userData[] = {name, uid, password, address, phoneNumber, email};
                    ConnectDB.addUser(userData);
                    break;
                case 2:
                    String[] authInfo = new String[2];
                    System.out.println("Syötä käyttäjätunnus: ");
                    authInfo[0] = user_input.nextLine();
                    System.out.println("Salasana: ");
                    authInfo[1] = user_input.nextLine();
                    customer.signIn(authInfo);
                    break;
                case 3:
                    customer.logOut();
                    cont = false;
                    break;
                default:
                    System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista.");
                    break;
                }
            } catch (NumberFormatException err) {
                System.out.println("Not valid input: " + err);
            }
        }

        while (cont && customer.statusQuery() && !customer.isAdmin()) {
            /* Main menu */
            try {
                Integer selection = View.mainMenuView();
                switch (selection) {
                  case 1:
                      searchAllBooks();
                      break;
                  case 2:
                      List<Integer> idList = searchBooks();
                      
                      if (idList != null) {
                          System.out.println("Lisää haluamasi nide tilaukseen syöttämällä sen järjestysnumeron tai \"0\" ilman lainausmerkkejä peruaksesi valinnan.");
                          int row = Integer.parseInt(user_input.nextLine());
                          
                          if (row > 0) {
                              ConnectDB.addOrder(customer, idList.get(row - 1));
                              System.out.println("Nide lisätty onnistuneesti tilaukseesi.");
                              System.out.println();
                          }
                      }     
                      break;
                  case 3:
                      int found = ConnectDB.getActiveOrders(customer);
                      
                      if (found > 0) {
                          Integer orderSelection = View.orderMenuView();
                          
                          switch (orderSelection) {
                              case 1:
                                ConnectDB.finishOrders(customer);
                                System.out.println("Tilaus varmistettu. Maksu suoritettu onnistuneesti.");
                                break;
                              case 2:
                                ConnectDB.clearOrders(customer);
                                System.out.println("Tilaus peruttu onnistuneesti.");
                                break;
                              case 3:
                                break;
                              default:
                                System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista.");
                                break;
                          }
                      }
                      
                      break;
                  case 4:
                      System.out.println("Kirjaudu ulos");
                      customer.logOut();
                      cont = false;
                      break;
                  default:
                      System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista.");
                      break;
                  }
            } catch (NumberFormatException err) {
                System.out.println("Not valid input: " + err);
            }
        }
        
        while (cont && customer.statusQuery() && customer.isAdmin()) {
            /* Admin menu */
            try {
                String isbn;
                Integer selection = View.adminMenuView();
                switch (selection) {
                  case 1:
                      System.out.println("Syötä ISBN-numero:");
                      isbn = user_input.nextLine();
                      if (ConnectDB.getBookByIsbn(isbn) != -1) {
                          System.out.println("Kyseisellä ISBN-numerolla löytyy jo teos.");
                          break;
                      }

                      System.out.println("Kirjan nimi:");
                        String title = user_input.nextLine();
                      System.out.println("Kirjan tekijä:");
                        String author = user_input.nextLine();
                      System.out.println("Kirjan luokka:");
                        String genre = user_input.nextLine();
                      System.out.println("Kirjan tyyppi:");
                        String type = user_input.nextLine();
                      System.out.println("Paino:");
                        String weight = user_input.nextLine();

                      String bookData[] = {isbn, author, title, genre, type, weight};
                      ConnectDB.addBook(customer, bookData);
                      break;
                  case 2:
                      System.out.println("Syötä kirjan ISBN-numero:");
                      isbn = user_input.nextLine();
                      int bookID = ConnectDB.getBookByIsbn(isbn);
                      if (bookID == -1) {
                          System.out.println("Kyseisellä ISBN-numerolla löytyy jo teos, yritä uudelleen.");
                          break;
                      }

                      System.out.println("Myyntihinta:");
                          String salePrice = user_input.nextLine();
                      System.out.println("Ostohinta:");
                          String purchasePrice = user_input.nextLine();
                      String shop_id = "0";

                      String itemData[] = {Integer.toString(bookID), salePrice, purchasePrice, shop_id};
                      ConnectDB.addItem(customer, itemData);
                      break;
                  case 3:
                      System.out.println("Kirjaudutaan ulos");
                      customer.logOut();
                      cont = false;
                      break;
                  default:
                      System.out.println("Valitettavasti valintaasi ei löydy, kokeile jotakin toista.");
                      break;
                }
            } catch ( NumberFormatException err ) {
                System.out.println("Invalid input: " + err);
            }
        }
    }
}