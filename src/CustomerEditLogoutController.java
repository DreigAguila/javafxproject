import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerEditLogoutController implements Initializable {
    @FXML
    private Button customerlogoutbutton;

    @FXML
    private Button editCustomerButton;
    
    @FXML
    private Button saveCustomerButton;

    @FXML
    private TextField editcitytextfield;

    @FXML
    private TextField editemailtextfield;

    @FXML
    private TextField editnametextfield;

    @FXML
    private TextField editpasswordtextfield;

    @FXML
    private TextField editphonetextfield;

    @FXML
    private TextField editstreettextfield;

    @FXML
    private TextField editziptextfield;

    @FXML
    private Button returntoaccountbutton;

    private Customer currentCustomer;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
    loadCustomerInformation();  // Load customer from session
    setFieldsEditable(false);   // Initially, fields should not be editable
}

    private void loadCustomerInformation() {
        // Get the logged-in customer from UserSession
        currentCustomer = UserSession.getInstance().getCustomer(); 

        if (currentCustomer == null) {
            System.out.println("No customer found in session.");
            return;
        }

        // Display data in the text fields
        editnametextfield.setText(currentCustomer.getCustomerfullname());
        editemailtextfield.setText(currentCustomer.getEmail());
        editpasswordtextfield.setText(currentCustomer.getPassword());
        editphonetextfield.setText(currentCustomer.getContactnumber());
        editstreettextfield.setText(currentCustomer.getStreet());
        editcitytextfield.setText(currentCustomer.getCity());
        editziptextfield.setText(currentCustomer.getZip());
}

    // Set fields editable or non-editable
    private void setFieldsEditable(boolean isEditable) {
        editnametextfield.setEditable(isEditable);
        editemailtextfield.setEditable(isEditable);
        editpasswordtextfield.setEditable(isEditable);
        editphonetextfield.setEditable(isEditable);
        editstreettextfield.setEditable(isEditable);
        editcitytextfield.setEditable(isEditable);
        editziptextfield.setEditable(isEditable);

        saveCustomerButton.setDisable(!isEditable); // Enable save button only when editing
    }

    // Handle edit button click - enable text fields for editing
    @FXML
    private void handleEditCustomer(ActionEvent event) {
        setFieldsEditable(true);
    }

    // Handle save button click - update database with new information
    @FXML
    private void handleSaveCustomer(ActionEvent event) {

    // Retrieve customer from UserSession
    currentCustomer = UserSession.getInstance().getCustomer(); 
  
    // Retrieve input values (trimmed)
    String newFullName = editnametextfield.getText().trim();
    String newPassword = editpasswordtextfield.getText().trim();
    String newCity = editcitytextfield.getText().trim();
    String newZip = editziptextfield.getText().trim();
    String newStreet = editstreettextfield.getText().trim();
    String newPhoneNumber = editphonetextfield.getText().trim();
    String newEmail = editemailtextfield.getText().trim();

    // Check if any field is modified
    boolean isModified = !newFullName.equals(currentCustomer.getCustomerfullname()) ||
                         !newPassword.equals(currentCustomer.getPassword()) ||
                         !newCity.equals(currentCustomer.getCity()) ||
                         !newZip.equals(currentCustomer.getZip()) ||
                         !newStreet.equals(currentCustomer.getStreet()) ||
                         !newPhoneNumber.equals(currentCustomer.getContactnumber()) ||
                         !newEmail.equals(currentCustomer.getEmail());

    if (!isModified) {
        showAlert("No Changes Detected", "You must update at least one field before saving!");
        return;
    }

    // Show confirmation alert
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, 
        "Are you sure you want to update this account?", 
        ButtonType.YES, ButtonType.NO
    );
    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.YES) {
        // Regex validation
        Pattern gmailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
        Pattern phoneNumberPattern = Pattern.compile("^09\\d{9}$");

        if (!gmailPattern.matcher(newEmail).matches()) {
            showAlert("Invalid Email", "Please enter a valid Gmail address (e.g., example@gmail.com).");
            return;
        }

        if (!phoneNumberPattern.matcher(newPhoneNumber).matches()) {
            showAlert("Invalid Phone Number", "Please enter a valid phone number (e.g., 09123456789).");
            return;
        }

        // Create an updated Customer object
        Customer updatedCustomer = new Customer(
            currentCustomer.getCustomerid(),
            newFullName,
            newPassword,
            newCity,
            newZip,
            newStreet,
            newPhoneNumber,
            newEmail
        );

        // Update database
        boolean success = DatabaseHandler.updateCustomer(currentCustomer.getCustomerfullname(), updatedCustomer);
        if (success) {
            System.out.println("Customer details updated successfully!");
            showAlert("Success", "Customer details have been updated successfully.");
            setFieldsEditable(false); // Disable editing after saving

            // Update UserSession with new details
            UserSession.getInstance().setCustomer(updatedCustomer);
        } else {
            showAlert("Update Failed", "Failed to update customer details. Please try again.");
        }
    } else {
        System.out.println("Update canceled by user.");
    }
}
    @FXML
    private void returnaccountpageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMaccountpage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMaccountpage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }
    @FXML
    private void logoutCustomer(ActionEvent event) {
    // Reset stored location data
    LocationController.resetLocationData();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText("You are about to log out.");
    alert.setContentText("Do you want to continue?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            // Close the current window
            customerlogoutbutton.getScene().getWindow().hide();

          
            UserSession.getInstance().clearSession();

            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("iridemotologinpage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
