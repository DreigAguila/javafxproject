import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationController {
    
    @FXML
    private Button confirmlocationButton;

    @FXML
    private Button returntohomepagebutton;

    @FXML
    private TextField totalpayTextfield;

    @FXML
    private Button whereFromButton;

    @FXML
    private Button whereToButton;

    @FXML
    private TextField pickupatTextField;

    @FXML
    private TextField wheretoTextfield;

    @FXML
    private Label pickuplabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label fareLabel;

    

    // Store data persistently (static variables)
    private static String lastPickupLocation = "Select a pickup location.";
    private static String lastDropOffLocation = "Select a drop-off location.";
    private static double lastFare = 0.0;

    // Method to set Pickup Location
    public void setPickupLocation(String location) {
        lastPickupLocation = location;
        updateUI();
    }

    // Method to set Drop-Off Location
    public void setDropOffLocation(String location) {
        lastDropOffLocation = location;
        updateFare(); // Retrieve fare from database
        updateUI();
    }

    private void updateFare() {
        System.out.println("Fetching fare for: " + lastPickupLocation + " → " + lastDropOffLocation);
        
        try {
            lastFare = DatabaseHandler.getFareNormalized(lastPickupLocation, lastDropOffLocation);
            if (lastFare == 0) {
                fareLabel.setText("Fare: Not available");
                System.out.println("❌ No matching fare found in database.");
            } else {
                fareLabel.setText("" + lastFare);
                System.out.println("✅ Fare found: " + lastFare);
            }
        } catch (SQLException e) {
            fareLabel.setText("Error retrieving fare");
            e.printStackTrace();
        }
    }
    // Method to update UI labels
    private void updateUI() {
        pickuplabel.setText(lastPickupLocation);
        destinationLabel.setText(lastDropOffLocation);
        fareLabel.setText("Fare: ₱" + lastFare);
    }

    @FXML
    public void initialize() {
        // Restore previous values when returning
        updateUI();

        totalpayTextfield.setEditable(false);
        pickupatTextField.setEditable(false);
        wheretoTextfield.setEditable(false);
    }
     // Reset values when logging out or returning to homepage
     public static void resetLocationData() {
        lastPickupLocation = "Select a pickup location.";
        lastDropOffLocation = "Select a drop-off location.";
        lastFare = 0.0;
    }
    @FXML
    private void returntohomepageHandler(ActionEvent event) {
        LocationController.resetLocationData();
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMhomepage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMhomepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void pickupHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pickupPage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading pickupPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void wheretoHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("wheretoPage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading wheretoPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
    @FXML
    private void confirmLocationHandler(ActionEvent event) {
        if (lastPickupLocation.equals("Select a pickup location.") || lastDropOffLocation.equals("Select a drop-off location.")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select both pickup and drop-off locations.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ubookingfinalPage.fxml"));
            Parent root = loader.load();

            UBookingFinalController uBookingFinalController = loader.getController();
            uBookingFinalController.setPickupLocation(lastPickupLocation);
            uBookingFinalController.setDropOffLocation(lastDropOffLocation);
            uBookingFinalController.setFare(lastFare);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Error loading ubookingfinalPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
}

    private void showAlert(Alert.AlertType alertType, String title, String message) {   
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
