public class UserSession {
    private static UserSession instance;
    private Customer customer;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Get the single instance of UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the logged-in customer
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Get the currently logged-in customer
    public Customer getCustomer() {
        return customer;
    }

    // Clear the session (logout)
    public void clearSession() {
        customer = null;
    }
}
