import java.sql.SQLException;

public class Customer implements Users {
    private int userId = 0;
    private boolean signedIn = false;
    private boolean isUserAdmin = false;

    /* TODO:
        - proper setter and getters for status
     */

    public void signIn(String[] auth) {
        System.out.println("Username: " + auth[0] + ", password " + auth[1]);
        String password = "";
        try {
            String[] data = ConnectDB.getAuthFromDb(auth[0]);
            password = data[0];
            userId = Integer.parseInt(data[1]);
        } catch (Exception err) {
            System.out.println("Problem occurred getting auth: " + err);
        }
        if (password != null && password.equals(auth[1])) {
            System.out.println("Auth successful!");
            this.signedIn = true;
        } else {
            System.out.println("Auth failed!");
            this.signedIn = false;
        }
        System.out.println("Status: " + this.signedIn);
    }

    public void logOut() {
        if (this.signedIn) this.signedIn = false;
        System.out.println("Status: " + this.signedIn);
    }

    public boolean statusQuery() {
        return this.signedIn;
    }

    public void searchBook(String searchCriteria) {
        //return searchCriteria;
    }

    public boolean isAdmin() {
        return this.isUserAdmin;
    }
    
    public int userId() {
        return this.userId;
    }
}
