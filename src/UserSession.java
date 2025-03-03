public class UserSession {
    private static UserSession instance;
    private Customer customer;
    private Transaction transaction;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Get the single instance of UserSession (Singleton Pattern)
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

    // Get the customer ID (returns null if no customer is set)
    public String getCustomerId() {
        return (customer != null) ? customer.getCustomerid() : null;
    }

    // Set the active transaction
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        System.out.println("✅ Transaction set with riderId: " + 
        (transaction != null ? transaction.getRiderId() : "null"));
    }

    // Get the active transaction
    public Transaction getTransaction() {
        return transaction;
    }

    // Get the transaction ID (returns null if no transaction is set)
    public String getTransactionId() {
        return (transaction != null) ? transaction.getTransactionId() : null ;
    }

    // Clear the session (logout)
    public void clearSession() {
        customer = null;
        transaction = null;
    }

    
}