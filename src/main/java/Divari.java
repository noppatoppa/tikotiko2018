import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.util.Scanner;

public class Divari {

    /* TODO:
        - Basic views
            -- logging in
            -- creating new user
        - Prepared statements
        - SQL creation statements
        - Schema-based admin authentication
     */

    public static void main (String args[]) {

        boolean cont = true;
        Customer customer = new Customer();
        Scanner user_input = new Scanner(System.in);

        while (cont && !customer.statusQuery()) {
            Integer selection = View.userLogInView();

            /* Logging in */
            switch (selection) {
                case 1:
                    System.out.println("New customer!"); // TODO

                    System.out.println("Please give some basic information:");
                    System.out.println("First your name:");
                        String name = user_input.nextLine();
                    System.out.println("Select username:");
                        String uid = user_input.nextLine();
                    System.out.println("Set a password:");
                        String password = user_input.nextLine();
                    System.out.println("Address:");
                        String address = user_input.nextLine();
                    System.out.println("Phone number (+358...):");
                        String phoneNumber = user_input.nextLine();
                    System.out.println("Email:");
                        String email = user_input.nextLine();

                    String userData[] = {name, uid, password, address, phoneNumber, email};
                    ConnectDB.addUser(userData);
                    break;
                case 2:
                    String[] authInfo = new String[2];
                    System.out.println("Please give username: ");
                    authInfo[0] = user_input.nextLine();
                    System.out.println("Please give password: ");
                    authInfo[1] = user_input.nextLine();
                    customer.signIn(authInfo);
                    break;
                case 3:
                    customer.logOut();
                    cont = false;
                    break;
                default:
                    System.out.println("Not a valid selection, sorry.");
                    break;
            }
        }

        while (cont && customer.statusQuery() && !customer.isAdmin()) {
            /* Main menu */
            Integer selection = View.mainMenuView();
            switch (selection) {
                case 1:
                    System.out.println("Making a search: ");
                    ConnectDB.doSearch();
                    break;
                case 2:
                    System.out.println("Give title to search: ");
                    String name = user_input.nextLine();
                    ConnectDB.doSearchByName(name);
                    break;
                case 3:
                    System.out.println("Logging out");
                    customer.logOut();
                    cont = false;
                    break;
                default:
                    System.out.println("Not a valid selection, sorry.");
                    break;
            }
        }
        
        while (cont && customer.statusQuery() && customer.isAdmin()) {
            /* Admin menu */
            try {
                String isbn;
                Integer selection = View.adminMenuView();
                switch (selection) {
                    case 1:
                        System.out.println("Please enter the book's ISBN number:");
                        isbn = user_input.nextLine();
                        if (ConnectDB.bookExists(isbn) != -1) {
                            System.out.println("A book record with the ISBN number you entered already exists.");
                            break;
                        }

                        System.out.println("Book title:");
                            String title = user_input.nextLine();
                        System.out.println("Book author:");
                            String author = user_input.nextLine();
                        System.out.println("Year published:");
                            String year = user_input.nextLine();
                        System.out.println("Book genre:");
                            String genre = user_input.nextLine();
                        System.out.println("Book type:");
                            String type = user_input.nextLine();

                        String bookData[] = {isbn, author, title, year, genre, type};
                        ConnectDB.addBook(bookData);
                        break;
                    case 2:
                        System.out.println("Please enter the book's ISBN number:");
                        isbn = user_input.nextLine();
                        int bookID = ConnectDB.bookExists(isbn);
                        if (bookID == -1) {
                            System.out.println("A book record with the ISBN number you entered does not exist, please try again.");
                            break;
                        }

                        System.out.println("Sale price:");
                            String salePrice = user_input.nextLine();
                        System.out.println("Purchase price:");
                            String purchasePrice = user_input.nextLine();
                        System.out.println("Weight:");
                            String weight = user_input.nextLine();

                        String itemData[] = {Integer.toString(bookID), salePrice, purchasePrice, weight};
                        ConnectDB.addItem(itemData);
                        break;
                    case 3:
                        System.out.println("Logging out");
                        customer.logOut();
                        cont = false;
                        break;
                    default:
                        System.out.println("Not a valid selection, sorry.");
                        break;
                }
            } catch ( NumberFormatException err ) {
                System.out.println("Invalid input: " + err);
            }
        }
    }
}