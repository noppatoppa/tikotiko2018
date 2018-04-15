public class Customer implements userCustomer {
    private boolean signedIn = false;

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

    public void statusQuery() {
        System.out.println(signedIn);
    }

    public void searchBook(String searchCriteria) {
        //return searchCriteria;
    }
}
