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

        while (cont && !customer.statusQuery()) {
            Integer selection = View.userLogInView();
            System.out.println(selection);

            /* Logging in */
            switch (selection) {
                case 1:
                    System.out.println("New customer!"); // TODO
                    break;
                case 2:
                    customer.signIn();
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
                    Scanner user_input = new Scanner(System.in);
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

        System.out.println("Connecting to db from main:");

    }
}