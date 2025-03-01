import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
        Connection connection = null;

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
        String query = "SELECT * FROM users WHERE UserName = '" + username + "' AND Password = '" + password + "'";

        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            
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
            pstatement.setString(1, rider.getRating());
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
    
    private static String calculateShipOnTime(String rating) {
        int ratingValue = Integer.parseInt(rating);
        String shipontime = "";
        switch (ratingValue) {
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
}