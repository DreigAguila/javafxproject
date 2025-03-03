import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;
    private static Connection connection = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }


    public static Connection getDBConnection() {
        connection = null;
    
        String dburl = "jdbc:mysql://localhost:3306/moveit";
        String userName = "root";
        String passWord = "password";
    
        try {
            connection = DriverManager.getConnection(dburl, userName, passWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return connection;
    }

    public ResultSet execQuery(String query) {

        ResultSet result;

        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }

        return result;
    }

    public static boolean validateLogin(String username, String password) {
        getInstance();
        String query = "SELECT * FROM users WHERE UserName = ? AND Password = ?";
    
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setString(1, username);
            stmt.setString(2, password);
    
            ResultSet result = stmt.executeQuery();
    
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public static ResultSet getUsers(){
        getInstance();
        ResultSet result = null;

        try{
            String query = "SELECT * FROM users";
            result = handler.execQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public static boolean addUser(User user) {
        getInstance();
        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO `users` (Username, Password) VALUES (?,?)");
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static boolean updateUser(String oldUsername, User user) {
        getInstance();
        try {
            String updateStatement ="UPDATE `users` SET Username = ?, Password = ? WHERE Username = ?";
            pstatement = getDBConnection().prepareStatement(updateStatement);
            pstatement.setString(1, user.getUsername());
            pstatement.setString(2, user.getPassword());
            pstatement.setString(3, oldUsername);

            int res = pstatement.executeUpdate();
            
            if (res > 0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static boolean deleteUser(User user) {
        getInstance();
        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM `users` WHERE Username=?");
            pstatement.setString(1, user.getUsername());

            int res = pstatement.executeUpdate();
            if (res > 0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    public static boolean changePassword(String username, String password){
        getInstance();
        try {
        String query = "UPDATE users SET Password = '" + password + "' WHERE Username = '" + username + "'";
        
        System.out.println(query);

        pstatement= getDBConnection().prepareStatement(query);
        int result = pstatement.executeUpdate();
    
        if (result > 0) {
            return true;
        }
        
    } catch (Exception e) {

        }
        return false;
    }

     // Generates a new Customer_id
     private static String generateCustomerId() {
        getInstance();
        String customerId = null;
        try {
            String query = "SELECT IFNULL(MAX(CAST(SUBSTRING(Customer_id, 3, 3) AS UNSIGNED)), 0) + 1 AS next_id FROM CustomerTable";
            pstatement = getDBConnection().prepareStatement(query);
            ResultSet rs = pstatement.executeQuery();
            if (rs.next()) {
                int nextId = rs.getInt("next_id");
                customerId = String.format("C-%03d", nextId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }
    
    
    public static ResultSet getCustomer() {
        getInstance();
        ResultSet result = null;
        try {
            String query = "SELECT C.Customer_id, C.CustomerFullName, C.Password, L.City, L.Zip, L.Street, CT.ContactNum, CT.Email " +
                            "FROM CustomerTable C " +
                            "INNER JOIN LocationTable L ON C.Customer_id = L.Customer_id " +
                            "INNER JOIN ContactTable CT ON C.Customer_id = CT.Customer_id";
            pstatement = getDBConnection().prepareStatement(query);
            result = pstatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean addCustomer(Customer customer){
        getInstance();
        try {
            String customerId = generateCustomerId();
            if (customerId == null) {
                return false;
            }
            
            String customerQuery = "INSERT INTO CustomerTable (Customer_id, CustomerFullName, Password) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(customerQuery);
            pstatement.setString(1, customerId);
            pstatement.setString(2, customer.getCustomerfullname());
            pstatement.setString(3, customer.getPassword());
            pstatement.executeUpdate();

            // Insert into LocationTable
            String locationQuery = "INSERT INTO LocationTable (Customer_id, City, Zip, Street) VALUES (?, ?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, customerId);
            pstatement.setString(2, customer.getCity());
            pstatement.setString(3, customer.getZip());
            pstatement.setString(4, customer.getStreet());
            pstatement.executeUpdate();

            // Insert into ContactTable
            String contactQuery = "INSERT INTO ContactTable (Customer_id, ContactNum, Email) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(contactQuery);
            pstatement.setString(1, customerId);
            pstatement.setString(2, customer.getContactnumber());
            pstatement.setString(3, customer.getEmail());
            pstatement.executeUpdate();

            
            return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }

    public static boolean updateCustomer(String oldCustomerFullName, Customer customer) {
        getInstance();
        try {
            // Update CustomerTable
            String customerQuery = "UPDATE CustomerTable SET CustomerFullName = ?, Password = ? WHERE CustomerFullName = ?";
            pstatement = getDBConnection().prepareStatement(customerQuery);
            pstatement.setString(1, customer.getCustomerfullname());
            pstatement.setString(2, customer.getPassword());
            pstatement.setString(3, oldCustomerFullName);
            pstatement.executeUpdate();

            // Update LocationTable
            String locationQuery = "UPDATE LocationTable SET City = ?, Zip = ?, Street = ? WHERE Customer_id = (SELECT Customer_id FROM CustomerTable WHERE CustomerFullName = ?)";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, customer.getCity());
            pstatement.setString(2, customer.getZip());
            pstatement.setString(3, customer.getStreet());
            pstatement.setString(4, oldCustomerFullName);
            pstatement.executeUpdate();

            // Update ContactTable
            String contactQuery = "UPDATE ContactTable SET ContactNum = ?, Email = ? WHERE Customer_id = (SELECT Customer_id FROM CustomerTable WHERE CustomerFullName = ?)";
            pstatement = getDBConnection().prepareStatement(contactQuery);
            pstatement.setString(1, customer.getContactnumber());
            pstatement.setString(2, customer.getEmail());
            pstatement.setString(3, oldCustomerFullName);
            pstatement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deleteCustomer(String customerFullName) {
        getInstance();
        try {
            // Delete from ContactTable
            String contactQuery = "DELETE FROM ContactTable WHERE Customer_id = (SELECT Customer_id FROM CustomerTable WHERE CustomerFullName = ?)";
            pstatement = getDBConnection().prepareStatement(contactQuery);
            pstatement.setString(1, customerFullName);
            pstatement.executeUpdate();

            // Delete from LocationTable
            String locationQuery = "DELETE FROM LocationTable WHERE Customer_id = (SELECT Customer_id FROM CustomerTable WHERE CustomerFullName = ?)";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, customerFullName);
            pstatement.executeUpdate();

            // Delete from CustomerTable
            String customerQuery = "DELETE FROM CustomerTable WHERE CustomerFullName = ?";
            pstatement = getDBConnection().prepareStatement(customerQuery);
            pstatement.setString(1, customerFullName);
            int res = pstatement.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    

    //validation for using contact number
    public static Customer validateCustomerContact(String contactNumber, String password) {
        getInstance();
        connection = DatabaseHandler.getDBConnection();
    
        String query = "SELECT C.Customer_id, C.CustomerFullName, C.Password, " +
                        "L.City, L.Zip, L.Street, " +
                        "CT.ContactNum, CT.Email " +
                        "FROM CustomerTable C " +
                        "INNER JOIN LocationTable L ON C.Customer_id = L.Customer_id " +
                        "INNER JOIN ContactTable CT ON C.Customer_id = CT.Customer_id " +
                        "WHERE CT.ContactNum = ? AND C.Password = ?";
    
        try {
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, contactNumber);
            pstatement.setString(2, password);
            ResultSet resultSet = pstatement.executeQuery();
    
            if (resultSet.next()) {
                return new Customer(
                    resultSet.getString("Customer_id"),
                    resultSet.getString("CustomerFullName"),
                    resultSet.getString("Password"),
                    resultSet.getString("City"),
                    resultSet.getString("Zip"),
                    resultSet.getString("Street"),
                    resultSet.getString("ContactNum"),
                    resultSet.getString("Email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no customer is found
    }

    //validation for using gmailaccount
    public static Customer validateCustomerGmail(String email, String customerpassword){
        getInstance();
        connection = DatabaseHandler.getDBConnection();

        
        String query = "SELECT C.Customer_id, C.CustomerFullName, C.Password, " +
                   "L.City, L.Zip, L.Street, " +
                   "CT.ContactNum, CT.Email " +
                   "FROM CustomerTable C " +
                   "INNER JOIN LocationTable L ON C.Customer_id = L.Customer_id " +
                   "INNER JOIN ContactTable CT ON C.Customer_id = CT.Customer_id " +
                   "WHERE CT.Email = ? AND C.Password = ?";


                   try {
                    pstatement = connection.prepareStatement(query);
                    pstatement.setString(1, email);
                    pstatement.setString(2, customerpassword);
                    ResultSet resultSet = pstatement.executeQuery();
            
                    if (resultSet.next()) {
                        return new Customer(
                            resultSet.getString("Customer_id"),
                            resultSet.getString("CustomerFullName"),
                            resultSet.getString("Password"),
                            resultSet.getString("City"),
                            resultSet.getString("Zip"),
                            resultSet.getString("Street"),
                            resultSet.getString("ContactNum"),
                            resultSet.getString("Email")
                        
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null; // Return null if login fails
            }


     // Generates a new Rider_id
     private static String generateRiderId() {
        getInstance();
        String riderId = null;
        try {
            String query = "SELECT IFNULL(MAX(CAST(SUBSTRING(Rider_id, 3, 3) AS UNSIGNED)), 0) + 1 AS next_id FROM RiderTable";
            pstatement = getDBConnection().prepareStatement(query);
            ResultSet rs = pstatement.executeQuery();
            if (rs.next()) {
                int nextId = rs.getInt("next_id");
                riderId = String.format("R-%03d", nextId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riderId;
    }

    public static ResultSet getRider() {
        getInstance();
        ResultSet result = null;
        try {
            String query = "SELECT R.Rider_id, R.RiderFullname, R.RiderContactNo, RL.Zip, RL.City, RL.Street, V.PlateNumber, V.Vehicle, RR.Rating, RR.ShipOnTime " +
               "FROM RiderTable R " +
               "LEFT JOIN RiderLocationTable RL ON R.Rider_id = RL.Rider_id " +
               "LEFT JOIN VehicleTable V ON R.Rider_id = V.Rider_id " +
               "LEFT JOIN RiderRatingTable RR ON R.Rider_id = RR.Rider_id " +
               "ORDER BY R.Rider_id ASC";

                pstatement = getDBConnection().prepareStatement(query);
                result = pstatement.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
        }
        return result;
    }

    public static String getRiderId(String riderFullname) {
        String query = "SELECT Rider_id FROM RiderTable WHERE RiderFullname = ?";
        String riderId = null;

        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, riderFullname);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                riderId = rs.getString("Rider_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return riderId;
    }

    public static boolean addRider(Rider rider){
        getInstance();
        try {
            String riderId = generateRiderId();
            if (riderId == null) {
                return false;
            }
            
              // Insert into RiderTable
            String riderQuery = "INSERT INTO RiderTable (Rider_id, RiderFullname, RiderContactNo) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(riderQuery);
            pstatement.setString(1, riderId);
            pstatement.setString(2, rider.getRiderfullname());
            pstatement.setString(3, rider.getRidercontactnumber());
            pstatement.executeUpdate();
            pstatement.close();

            /// Insert into RiderLocationTable
            String locationQuery = "INSERT INTO RiderLocationTable (Rider_id, Zip, City, Street) VALUES (?, ?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, riderId);
            pstatement.setString(2, rider.getZip());
            pstatement.setString(3, rider.getCity());
            pstatement.setString(4, rider.getStreet());
            pstatement.executeUpdate();
            pstatement.close();

            // Insert into VehicleTable
            String vehicleQuery = "INSERT INTO VehicleTable (PlateNumber, Vehicle, Rider_id) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(vehicleQuery);
            pstatement.setString(1, rider.getPlatenumber()); // Ensure Rider object has plate number
            pstatement.setString(2, rider.getVehicle());
            pstatement.setString(3, riderId);
            pstatement.executeUpdate();
            pstatement.close();
            
            // Calculate shipontime based on rating
             String shipontime = calculateShipOnTime(rider.getRating());
             // Insert into RiderRatingTable
            String ratingQuery = "INSERT INTO RiderRatingTable (Rider_id, Rating, ShipOnTime) VALUES (?, ?, ?)";
            pstatement = getDBConnection().prepareStatement(ratingQuery);
            pstatement.setString(1, riderId);
            pstatement.setString(2, rider.getRating()); // Ensure Rider object has a rating
            pstatement.setString(3, shipontime);
            pstatement.executeUpdate();
            pstatement.close();
            
            return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }
    
    public static boolean updateRider(String oldRiderName, Rider rider) {
        getInstance();
        try {
            String riderId = rider.getRiderid();
            if (riderId == null) {
                return false;
            }      
            // Update RiderTable
            String riderQuery = "UPDATE RiderTable SET RiderFullname = ?, RiderContactNo = ? WHERE Rider_id = ?";
            pstatement = getDBConnection().prepareStatement(riderQuery);
            pstatement.setString(1, rider.getRiderfullname());
            pstatement.setString(2, rider.getRidercontactnumber());
            pstatement.setString(3, riderId);
            pstatement.executeUpdate();
            pstatement.close();

            // Update RiderLocationTable
            String locationQuery = "UPDATE RiderLocationTable SET Zip = ?, City = ?, Street = ? WHERE Rider_id = ?";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, rider.getZip());
            pstatement.setString(2, rider.getCity());
            pstatement.setString(3, rider.getStreet());
            pstatement.setString(4, riderId);
            pstatement.executeUpdate();
            pstatement.close();

            // Update VehicleTable
            String vehicleQuery = "UPDATE VehicleTable SET PlateNumber = ?, Vehicle = ? WHERE Rider_id = ?";
            pstatement = getDBConnection().prepareStatement(vehicleQuery);
            pstatement.setString(1, rider.getPlatenumber());
            pstatement.setString(2, rider.getVehicle());
            pstatement.setString(3, riderId);
            pstatement.executeUpdate();
            pstatement.close();

            // Calculate shipontime based on rating
            String shipontime = calculateShipOnTime(rider.getRating());

            // Update RiderRatingTable
            String ratingQuery = "UPDATE RiderRatingTable SET Rating = ?, ShipOnTime = ? WHERE Rider_id = ?";
            pstatement = getDBConnection().prepareStatement(ratingQuery);
            pstatement.setInt(1, rider.getRating()); // Use setInt for rating
            pstatement.setString(2, shipontime);
            pstatement.setString(3, riderId);
            pstatement.executeUpdate();
            pstatement.close();


            return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }

    public static boolean deleteRider(String riderFullName) {
        getInstance();
        try {
            // Delete from RiderRatingTable
            String ratingQuery = "DELETE FROM RiderRatingTable WHERE Rider_id = (SELECT Rider_id FROM RiderTable WHERE RiderFullName = ?)";
            pstatement = getDBConnection().prepareStatement(ratingQuery);
            pstatement.setString(1, riderFullName);
            pstatement.executeUpdate();
    
            // Delete from VehicleTable
            String vehicleQuery = "DELETE FROM VehicleTable WHERE Rider_id = (SELECT Rider_id FROM RiderTable WHERE RiderFullName = ?)";
            pstatement = getDBConnection().prepareStatement(vehicleQuery);
            pstatement.setString(1, riderFullName);
            pstatement.executeUpdate();
    
            // Delete from RiderLocationTable
            String locationQuery = "DELETE FROM RiderLocationTable WHERE Rider_id = (SELECT Rider_id FROM RiderTable WHERE RiderFullName = ?)";
            pstatement = getDBConnection().prepareStatement(locationQuery);
            pstatement.setString(1, riderFullName);
            pstatement.executeUpdate();
    
            // Delete from RiderTable
            String riderQuery = "DELETE FROM RiderTable WHERE RiderFullName = ?";
            pstatement = getDBConnection().prepareStatement(riderQuery);
            pstatement.setString(1, riderFullName);
            int res = pstatement.executeUpdate();
    
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
   private static String calculateShipOnTime(int rating) {
    String shipontime = "";
    switch (rating) {
        case 5:
            shipontime = "99%";
            break;
        case 4:
            shipontime = "80%";
            break;
        case 3:
            shipontime = "60%";
            break;
        case 2:
            shipontime = "40%";
            break;
        case 1:
            shipontime = "20%";
            break;
        default:
            shipontime = "0%";
            break;
    }
    return shipontime;
}

    // Retrieves the fare from the fare_matrix table
    public static double getFare(String origin, String destination) throws SQLException {
        String query = "SELECT fare FROM fare_matrix WHERE origin = ? AND destination = ?";
        try (Connection connection = getDBConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, origin);
            statement.setString(2, destination);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("fare");
                } else {
                    return -1; // Indicate that no fare was found
                }
            }
        }
    }


    private static String normalizeCityName(String cityName) {
    cityName = cityName.trim().toLowerCase(); // Trim spaces & convert to lowercase

    // Map common abbreviations and alternative names
    Map<String, String> cityAliases = new HashMap<>();
    cityAliases.put("qc", "Quezon City");
    cityAliases.put("quezon", "Quezon City");
    cityAliases.put("manila", "Manila");
    cityAliases.put("makati", "Makati");

    // Return the full name if an alias exists; otherwise, return the original input
    return cityAliases.getOrDefault(cityName, capitalize(cityName));
}

    // Capitalizes first letter of each word (for database consistency)
    private static String capitalize(String cityName) {
        String[] words = cityName.split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append(" ");
        }
        return capitalized.toString().trim();
}

    public static double getFareNormalized(String origin, String destination) throws SQLException {
    String normalizedOrigin = normalizeCityName(origin);
    String normalizedDestination = normalizeCityName(destination);

    System.out.println("🔍 Searching fare for: " + normalizedOrigin + " → " + normalizedDestination);

    String query = "SELECT fare FROM fare_matrix WHERE origin = ? AND destination = ?";
    
    try (Connection connection = getDBConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, normalizedOrigin);
        statement.setString(2, normalizedDestination);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                System.out.println("✅ Found fare: " + resultSet.getDouble("fare"));
                return resultSet.getDouble("fare");
            } else {
                System.out.println("❌ No fare found.");
                return -1;
            }
        }
     }
    }

            // Generates the next Transaction ID
        public static String getNextTransactionId(Connection conn) throws SQLException {
            String query = "SELECT Transaction_id FROM TransactionTable ORDER BY Transaction_id DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String lastTransactionId = rs.getString("Transaction_id");
                    int nextId = Integer.parseInt(lastTransactionId.substring(1)) + 1;
                    return String.format("T%03d", nextId);
                } else {
                    return "T001"; // Default to T001 if no transactions exist
                }
            }
        }

        // Generates the next Booking ID
        public static String getNextBookingId(Connection conn) throws SQLException {
            String query = "SELECT Booking_id FROM TransactionTable ORDER BY Booking_id DESC LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String lastBookingId = rs.getString("Booking_id");
                    int nextId = Integer.parseInt(lastBookingId.substring(1)) + 1;
                    return String.format("B%03d", nextId);
                } else {
                    return "B001"; // Default to B001 if no bookings exist
                }
            }
}

    public static void storeBooking(String customerId, LocalTime pickupTime, LocalDateTime arrivalTime, double amountPaid) {
    String transactionSql = "INSERT INTO TransactionTable (Transaction_id, Booking_id, Customer_id, Rider_id, pickup_time, arrival_time, amount_paid, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getDBConnection();
         PreparedStatement transactionStmt = conn.prepareStatement(transactionSql)) {

        // Generate the next Transaction_id and Booking_id
        String nextTransactionId = getNextTransactionId(conn);
        String nextBookingId = getNextBookingId(conn);

        // Retrieve Rider ID from UserSession
        Transaction transaction = UserSession.getInstance().getTransaction();
        String riderId = (transaction != null && transaction.getRiderId() != null) 
                ? transaction.getRiderId() 
                : "No rider assigned";  // Default value if no rider is assigned

        LocalDate pickupDate = LocalDate.now(); // Get the current pickup date

        // Set parameters for the SQL statement
        transactionStmt.setString(1, nextTransactionId); // Transaction_id
        transactionStmt.setString(2, nextBookingId); // Booking_id
        transactionStmt.setString(3, customerId);
        transactionStmt.setString(4, riderId); // Rider_id (handles NULL)
        transactionStmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(pickupDate, pickupTime))); // pickup_time
        transactionStmt.setTimestamp(6, Timestamp.valueOf(arrivalTime)); // arrival_time
        transactionStmt.setDouble(7, amountPaid); // amount_paid
        transactionStmt.setString(8, "Paid"); // payment_status

        // Execute the statement
        int transactionRowsInserted = transactionStmt.executeUpdate();

        if (transactionRowsInserted > 0) {
            System.out.println("✅ Transaction stored successfully for customer: " + customerId);
        } else {
            System.out.println("❌ Failed to store transaction.");
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Log error properly
    }
}



    public static ObservableList<Transaction> getTransactions() {
    ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    
    String query = "SELECT * FROM transactiontable";

    try (Connection conn = getDBConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String transactionId = rs.getString("Transaction_id");
            String bookingId = rs.getString("Booking_id");
            String customerId = rs.getString("Customer_id");
            String riderId = rs.getString("Rider_id");
            LocalDateTime transactionDate = rs.getTimestamp("transaction_date").toLocalDateTime();
            LocalDateTime pickupTime = rs.getTimestamp("pickup_time").toLocalDateTime();
            LocalDateTime arrivalTime = rs.getTimestamp("arrival_time").toLocalDateTime();
            double amountPaid = rs.getDouble("amount_paid");
            String paymentStatus = rs.getString("payment_status");

            transactionList.add(new Transaction(transactionId, bookingId, customerId, riderId, transactionDate, pickupTime, arrivalTime, amountPaid, paymentStatus));
        }   
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return transactionList;
}

    public static void updateRiderRating(String riderId, int newRating) {

        if (riderId == null || riderId.trim().isEmpty()) {
            System.out.println("❌ Rider ID is NULL. Cannot update rating.");
            return; // Prevent execution
        }
        
        String fetchRatingsQuery = "SELECT Rating FROM RiderRatingTable WHERE Rider_id = ?";
        String updateRatingQuery = "UPDATE RiderRatingTable SET Rating = ?, ShipOnTime = ? WHERE Rider_id = ?";

        try (Connection conn = getDBConnection();
             PreparedStatement fetchStmt = conn.prepareStatement(fetchRatingsQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateRatingQuery)) {

            fetchStmt.setString(1, riderId);
            ResultSet rs = fetchStmt.executeQuery();

            int totalRatings = 0;
            int ratingCount = 0;

            while (rs.next()) {
                totalRatings += rs.getInt("Rating");
                ratingCount++;
            }

            // Include the new rating in the calculation
            totalRatings += newRating;
            ratingCount++;

            // Calculate the mean rating
            double meanRating = (double) totalRatings / ratingCount;

            // Determine the shipontime based on the mean rating
            String shipOnTime;
            if (meanRating >= 4.5) {
                shipOnTime = "99%";
            } else if (meanRating >= 4.0) {
                shipOnTime = "80%";
            } else if (meanRating >= 3.0) {
                shipOnTime = "60%";
            } else if (meanRating >= 2.0) {
                shipOnTime = "40%";
            } else {
                shipOnTime = "20%";
            }

            // Update the rider's rating and shipontime
            updateStmt.setDouble(1, meanRating);
            updateStmt.setString(2, shipOnTime);
            updateStmt.setString(3, riderId);
            updateStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}