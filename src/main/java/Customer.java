public class Customer implements Users {
    private boolean signedIn = false;

    /* TODO:
        - proper setter and getters for status
     */

    public void signIn(String[] auth) {
        System.out.println("Username: " + auth[0] + ", password " + auth[1]);
        if (ConnectDB.getAuthFromDb(auth[0]).equals(auth[1])) {
            System.out.println("Auth successful!");
            this.signedIn = true;
        } else {
            System.out.println("Auth failed!");
            this.signedIn = false;
        }
        System.out.println("Status: " + this.signedIn);
        //return signedIn;
    }

    public void logOut() {
        this.signedIn = false;
        System.out.println("Status: " + this.signedIn);
        //return signedIn;
    }

    public boolean statusQuery() {
        System.out.println(this.signedIn);
        return this.signedIn;
    }

    public void searchBook(String searchCriteria) {
        //return searchCriteria;
    }
}
