import java.util.Scanner;

/* TODO:
    - move switch case handling here
 */

class View {
    /* Collect prepared views here, with user input gathering and handling */

    static int mainMenuView () {
        /* Show basic functions of registered user */
        System.out.println("Please select function");
        System.out.println("1) show all volumes");
        System.out.println("2) search by attribute");
        System.out.println("3) logout");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        //System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }

    static int searchMenuView () {
        /* Show search menu */
        System.out.println("Select search:");
        System.out.println("1) search by name");
        System.out.println("2) search by author");
        System.out.println("3) search by type");
        System.out.println("4) search by class");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.nextLine();

        return Integer.parseInt(selection);
    }

    static int userLogInView () {
        /* Let's show a prompt for the user */
        System.out.println("Hello and welcome:");
        System.out.println("1) for new Customer");
        System.out.println("2) for returning Customer");
        System.out.println("3) for logging out");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        //System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }

    static int adminMenuView () {
        /* Show admin functions */
        System.out.println("Select admin action");
        System.out.println("1) add new book");
        System.out.println("2) add new item(s)");
        System.out.println("3) logout");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();
        
        return Integer.parseInt(selection);
    }
}
