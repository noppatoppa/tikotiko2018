public class Customer implements Users {
    private boolean signedIn = false;

    /* TODO:
        - proper setter and getters for status
     */

    public void signIn() {
        signedIn = true;
        System.out.println("Status: " + signedIn);
        //return signedIn;
    }

    public void logOut() {
        signedIn = false;
        System.out.println("Status: " + signedIn);
        //return signedIn;
    }

    public boolean statusQuery() {
        System.out.println(signedIn);
        return signedIn;
    }

    public void searchBook(String searchCriteria) {
        //return searchCriteria;
    }
}
