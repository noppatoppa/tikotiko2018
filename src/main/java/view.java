import java.util.Scanner;

class view {
    static String userLogInView () {

        /* Collect prepared views here, with user input gathering and handling */
        /* Let's show a prompt for the user */
        System.out.println("Hello and welcome:");
        System.out.println("Select 1 for new Customer");
        System.out.println("Select 2 for returning Customer");
        System.out.println("Select 3 for logging out");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        System.out.println("Selection was: " + selection);
        return selection;
    }
}
