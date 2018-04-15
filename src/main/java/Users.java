public interface Users {
    void signIn(String[] auth);
    void logOut();
    boolean statusQuery();
    void searchBook(String searchCriteria);
}
