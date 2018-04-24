import java.util.Scanner;

/* TODO:
    - move switch case handling here
 */

class View {
    /* Collect prepared views here, with user input gathering and handling */

    static int mainMenuView () {
        /* Show basic functions of registered user */
        System.out.println("Valitse toiminto");
        System.out.println("1) Näytä kaikki teokset");
        System.out.println("2) Hae kirjaa");
        System.out.println("3) Kirjaudu ulos");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        //System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }

    static int searchMenuView () {
        /* Show search menu */
        System.out.println("Valitse toiminto:");
        System.out.println("1) Etsi kirjan nimellä");
        System.out.println("2) Etsi kirjailijalla");
        System.out.println("3) Etsi tyypillä");
        System.out.println("4) Etsi lajilla");
        System.out.println("5) Etsi kaikki teokset luokasta");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.nextLine();

        return Integer.parseInt(selection);
    }

    static int searchAllMenuView () {
        System.out.println("1) Etsi kaikki teokset");
        System.out.println("2) Etsi kaikki teokset luokasta");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.nextLine();

        return Integer.parseInt(selection);
    }

    static int userLogInView () {
        /* Let's show a prompt for the user */
        System.out.println("Tervetuloa:");
        System.out.println("1) Uusi asiakas");
        System.out.println("2) Palaava asiakas");
        System.out.println("3) Lopeta käyttö");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();

        //System.out.println("Selection was: " + selection);
        return Integer.parseInt(selection);
    }

    static int adminMenuView () {
        /* Show admin functions */
        System.out.println("Valitse ylläpitotoiminto");
        System.out.println("1) Lisää kirja");
        System.out.println("2) Lisää niteitä");
        System.out.println("3) Kirjaudu ulos");
        Scanner user_input = new Scanner(System.in);
        String selection = user_input.next();
        
        return Integer.parseInt(selection);
    }
}
