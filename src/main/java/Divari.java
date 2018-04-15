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
            System.out.println(selection);

            /* Logging in */
            switch (selection) {
                case 1:
                    System.out.println("New customer!"); // TODO
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