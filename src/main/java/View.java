import java.util.Scanner;

/* TODO:
    - move switch case handling here
 */

class View {
    /* Collect prepared views here, with user input gathering and handling */

    static int mainMenuView () {
        /* Show basic functions of registered user */
        System.out.println("Please select function");
        System.out.println("Select 1 show all volumes");
        System.out.println("Select 2 search by name");
        System.out.println("Select 3 logout");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }

    static int userLogInView () {
        /* Let's show a prompt for the user */
        System.out.println("Hello and welcome:");
        System.out.println("Select 1 for new Customer");
        System.out.println("Select 2 for returning Customer");
        System.out.println("Select 3 for logging out");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }
}
