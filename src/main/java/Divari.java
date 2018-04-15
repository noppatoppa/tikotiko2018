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

        while (cont) {
            String selection = view.userLogInView();
            System.out.println(selection);
            selection = selection.replace("\n", "");

            switch (Integer.parseInt(selection)) {
                case 1:   System.out.println("New customer!");
                            break;
                case 2:   customer.signIn();
                            break;
                case 3:   customer.logOut();
                            cont = false;
                            break;
                default:    System.out.println("Not a valid selection, sorry.");
                            break;
            }
        }
        customer.statusQuery();

        System.out.println("Connecting to db from main:");
        connectDB.doSearch();
    }
}