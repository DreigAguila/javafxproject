import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;





public class UBookingFinalController {

    @FXML
    private Button applyButton;

    @FXML
    private Button endRideButton;

    @FXML
    private TextField enterTimeTextfield;

    @FXML
    private TextField entervoucherTextfield;

    @FXML
    private Label faredisplay;

    @FXML
    private Button payButton;

    @FXML
    private Button returntobookingButton;

    @FXML
    private Label riderNameInfo;

    @FXML
    private Label riderLocationInfo;

    @FXML
    private Label riderPhoneInfo;

    @FXML
    private Label riderPlateInfo;

    @FXML
    private Label riderVehicleInfo;

    @FXML
    private ComboBox<String> selectPaymentCBox;

    @FXML
    private Button startRideButton;

    @FXML
    private Label estimatedTimeLabel;

    @FXML
    private Label rideridInfo;

    @FXML
    private Label pickupLocationLabel;

    @FXML
    private Label dropOffLocationLabel;

    @FXML
    private TableView<Rider> randomRiderInfo;

    @FXML
    private TableColumn<Rider, String> riderIdColumn;

    @FXML
    private TableColumn<Rider, String> riderLocation;

    @FXML
    private TableColumn<Rider, String> riderNameColumn;

    @FXML
    private TableColumn<Rider, String> riderPhoneColumn;

    @FXML
    private TableColumn<Rider, String> riderPlateColumn;

    @FXML
    private TableColumn<Rider, String> riderVehicleColumn;
    
    private Stage stage;
    private Scene scene;
    private Parent root;


    private ObservableList<Rider> riderList = FXCollections.observableArrayList(); 
    private static final String url = "jdbc:mysql://localhost:3306/moveit"; // Replace with your actual database URL
    private static final String user = "root"; // Replace with your actual database username
    private static final String password = "password"; // Replace with your actual database password
    private boolean isPaid = false; // Flag to track if payment has been made
    private double fare = 0.0; // Store the fare to avoid recalculating

    // List to store rider data

    @FXML
    public void initialize() {
        // Set the options for payment type (this seems unrelated to the rider data)
        selectPaymentCBox.setItems(FXCollections.observableArrayList("Gcash", "Card", "Cash"));

        // Bind columns to appropriate properties of Rider class
        riderIdColumn.setCellValueFactory(new PropertyValueFactory<>("riderid"));
        riderNameColumn.setCellValueFactory(new PropertyValueFactory<>("riderfullname"));
        riderPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("ridercontactnumber"));
        riderPlateColumn.setCellValueFactory(new PropertyValueFactory<>("platenumber"));
        riderLocation.setCellValueFactory(new PropertyValueFactory<>("city"));
        riderVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

        // Load random rider data
        loadRandomRiderData();

        // Add listener to enterTimeTextfield to calculate ETA when time is entered
        enterTimeTextfield.setOnAction(this::calculateETA);
    }

    private void loadRandomRiderData() {
        // Query to fetch random rider data from the database
        String query = "SELECT R.Rider_id, R.RiderFullname, R.RiderContactNo, RL.Zip, RL.City, RL.Street, " +
                "V.PlateNumber, V.Vehicle, COALESCE(RR.Rating, 0) AS Rating, COALESCE(RR.ShipOnTime, 'N/A') AS ShipOnTime " +
                "FROM RiderTable R " +
                "LEFT JOIN RiderLocationTable RL ON R.Rider_id = RL.Rider_id " +
                "LEFT JOIN VehicleTable V ON R.Rider_id = V.Rider_id " +
                "LEFT JOIN RiderRatingTable RR ON R.Rider_id = RR.Rider_id " +
                "ORDER BY RAND() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                // Get the rider data from the result set
                String riderId = rs.getString("Rider_id");
                String riderFullname = rs.getString("RiderFullname");
                String riderContactNo = rs.getString("RiderContactNo");
                String zip = rs.getString("Zip");
                String city = rs.getString("City");
                String street = rs.getString("Street");
                String plateNumber = rs.getString("PlateNumber");
                String vehicle = rs.getString("Vehicle");
                int rating = rs.getInt("Rating");
                String shipOnTime = rs.getString("ShipOnTime");

                // Create a Rider object with the retrieved data
                Rider rider = new Rider(riderId, riderFullname, riderContactNo, zip, city, street, plateNumber, vehicle, rating, shipOnTime);

                // Add the rider to the observable list
                riderList.clear();
                riderList.add(rider);

                // Set the items for the TableView to display
                randomRiderInfo.setItems(riderList);

                // Pre-select the rider in the table (optional)
                randomRiderInfo.getSelectionModel().select(rider);

            } else {
                // Show alert if no rider data was found
                showAlert(Alert.AlertType.INFORMATION, "No Rider Found", "No rider found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading rider data.");
        }
    }

    @FXML
    void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("locationPage.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setPickupLocation(String pickupLocation) {
        pickupLocationLabel.setText(pickupLocation);
    }

    public void setDropOffLocation(String dropOffLocation) {
        dropOffLocationLabel.setText(dropOffLocation);
    }

    public void setFare(double fare) {
        this.fare = fare;
        faredisplay.setText(String.format("₱%.2f", fare));
    }

    @FXML
    void calculateETA(ActionEvent event) {
        String origin = pickupLocationLabel.getText(); // Use actual origin
        String destination = dropOffLocationLabel.getText(); // Use actual destination

        String query = "SELECT distance_km FROM fare_matrix WHERE origin = ? AND destination = ?";

        try (Connection conn = DatabaseHandler.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, origin);
            stmt.setString(2, destination);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double distanceKm = rs.getDouble("distance_km");
                double etaMinutes = distanceKm * 1.5;

                // Parse the time input
                String timeInput = enterTimeTextfield.getText().trim();
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
                try {
                    LocalTime time = LocalTime.parse(timeInput, timeFormatter);
                    LocalTime etaTime = time.plusMinutes((long) etaMinutes);
                    estimatedTimeLabel.setText(String.format("ETA: %s (%.2f minutes)", etaTime.format(timeFormatter), etaMinutes));
                } catch (DateTimeParseException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Please use the format 'h:mm a' (e.g. 1:30 PM/AM ).");
                }
            } else {
                estimatedTimeLabel.setText("No route found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while calculating the ETA.");
        }
    }

    @FXML
    void handleStartRideButton(ActionEvent event) {
        // Set the current time as the start time
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        enterTimeTextfield.setText(currentTime.format(timeFormatter));

        calculateETA(event);
    }

    @FXML
    void handleEndRideButton(ActionEvent event) throws IOException {
        // Debugging: Check if method is called
        System.out.println("handleEndRideButton called");

        // Check if payment has been made
        System.out.println("isPaid value: " + isPaid);
        if (!isPaid) {
            showAlert(Alert.AlertType.ERROR, "Payment Required", "Please complete the payment before ending the ride.");
            return;
        }

        // Get current customer ID from UserSession
        String currentCustomerId = UserSession.getInstance().getCustomerId();
        System.out.println("Customer ID: " + currentCustomerId);
        if (currentCustomerId == null || currentCustomerId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No logged-in customer found.");
            return;
        }

        // Parse pickup time
        String pickupTimeInput = enterTimeTextfield.getText().trim();
        System.out.println("Pickup Time Input: " + pickupTimeInput);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        try {
            LocalTime pickupTime = LocalTime.parse(pickupTimeInput, timeFormatter);
            LocalTime endTime = LocalTime.now();
            LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), endTime);

            // Parse the fare amount
            System.out.println("Fare display: " + faredisplay.getText());
            double amountPaid = Double.parseDouble(faredisplay.getText().replace("₱", "").trim());

            // Store booking in the database
            System.out.println("Storing booking...");
            DatabaseHandler.storeBooking(currentCustomerId, pickupTime, endDateTime, amountPaid);
            System.out.println("Booking stored successfully.");

            showAlert(Alert.AlertType.INFORMATION, "Ride Ended", "The ride has ended. Thank you for using our service!");

            // Open ratingyourDriver.fxml in a new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ratingyourDriver.fxml"));
            Parent ratingRoot = loader.load();
            Stage ratingStage = new Stage();
            ratingStage.setScene(new Scene(ratingRoot));
            ratingStage.setTitle("Rate Your Driver");
            ratingStage.show();

            // Set an event handler to transition to the homepage when the rating window is closed
            ratingStage.setOnHidden(e -> {
                try {
                    FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("IRMhomepage.fxml"));
                    Parent homeRoot = homeLoader.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(homeRoot);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid time format. Please use 'h:mm a' (e.g., 1:30 PM/AM).");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid fare amount. Please enter a valid price.");
        }
    }

    @FXML
    void handlePayButton(ActionEvent event) {
        String paymentMethod = selectPaymentCBox.getValue(); // Get selected payment method
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a payment method first.");
            return;
        }

        // Ensure that the fare is valid before proceeding
        String fareText = faredisplay.getText().replace("₱", "").trim();
        if (fareText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Fare amount is missing.");
            return;
        }

        try {
            double fareAmount = Double.parseDouble(fareText);
            if (fareAmount <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid fare amount. Please check the applied discount.");
                return;
            }

            // Proceed with payment processing
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful",
                    "Thank you for successfully paying ₱" + String.format("%.2f", fareAmount) + " using " + paymentMethod + ".");
            isPaid = true; // Set the flag to true when payment is made

            System.out.println("✔ Payment Successful!");
            System.out.println("✔ Paid Amount: ₱" + fareAmount);
            System.out.println("✔ Payment Method: " + paymentMethod);

        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Failed to parse fare from faredisplay.");
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid fare format.");
        }
    }

    @FXML
    void handleApplyVoucher(ActionEvent event) {
        String promoCode = entervoucherTextfield.getText().trim();
        if (promoCode.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a promo code.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/moveit";
        String user = "root";
        String password = "password";

        String fetchDiscountQuery = "SELECT Percentage FROM promotable WHERE Promo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(fetchDiscountQuery)) {

            stmt.setString(1, promoCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int discountPercent = rs.getInt("Percentage");
                System.out.println("✔ Promo Code Found: " + promoCode);
                System.out.println("✔ Discount Percentage: " + discountPercent + "%");

                String fareText = faredisplay.getText().replace("₱", "").trim();
                System.out.println("✔ Fare Before Discount: " + fareText);

                if (fareText.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Fare is missing. Please check your booking details.");
                    return;
                }

                double originalFare = Double.parseDouble(fareText);
                double discountAmount = originalFare * (discountPercent / 100.0);
                double finalFare = originalFare - discountAmount;

                System.out.println("✔ Discount Amount: ₱" + discountAmount);
                System.out.println("✔ Final Fare After Discount: ₱" + finalFare);

                // Update UI using Platform.runLater()
                Platform.runLater(() -> faredisplay.setText(String.format("₱%.2f", finalFare)));

                showAlert(Alert.AlertType.INFORMATION, "Voucher Applied",
                        "Promo Code: " + promoCode + "\nDiscount: " + discountPercent + "%\nNew Fare: ₱" + finalFare);
            } else {
                System.out.println("❌ Invalid Promo Code Entered: " + promoCode);
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid promo code.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while applying the voucher.");
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Failed to parse fare from faredisplay.");
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid fare format.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}