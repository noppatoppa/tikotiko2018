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
                        String name = user_input.next();
                    System.out.println("Select username:");
                        String uid = user_input.next();
                    System.out.println("Set a password:");
                        String password = user_input.next();
                    System.out.println("Address:");
                        String address = user_input.next();
                    System.out.println("Phone number (+358...):");
                        String phoneNumber = user_input.next();
                    System.out.println("Email:");
                        String email = user_input.next();

                    String userData[] = {name, uid, password, address, phoneNumber, email};
                    ConnectDB.addUser(userData); // TODO: After adding, login automatically
                    customer.signIn(new String[] {uid, password});
                    break;
                case 2:
                    String[] authInfo = new String[2];
                    System.out.println("Please give username: ");
                    authInfo[0] = user_input.next();
                    System.out.println("Please give password: ");
                    authInfo[1] = user_input.next();
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

        while (cont && customer.statusQuery()) {
            /* Main menu */
            Integer selection = View.mainMenuView();
            switch (selection) {
                case 1:
                    System.out.println("Making a search: ");
                    ConnectDB.doSearch();
                    break;
                case 2:
                    System.out.println("Give title to search: ");
                    String name = user_input.next();
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
    }
}