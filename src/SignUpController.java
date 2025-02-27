
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController{

    @FXML
    private TextField citytextfield;

    @FXML
    private TextField conumbertextfield;

    @FXML
    private TextField emailaccounttextfield;

    @FXML
    private TextField firstnametextfield;

    @FXML
    private TextField lastnametextfield;

    @FXML
    private TextField passwordtextfield;

    @FXML
    private TextField streettextfield;

    @FXML
    private TextField zipcodetextfield;

    @FXML
    private Button returnloginpage;

    @FXML
    private Button createaccountButton;

    private final Pattern gmailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    private final Pattern phoneNumberPattern = Pattern.compile("^09\\d{9}$");


    @FXML
    private void returntocustomerloginHandler(ActionEvent event){
     try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("iridemotologinpage.fxml"));
            Parent root = loader.load();
            
            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
              
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @FXML
    private void createaccountHandler(ActionEvent event){
        String firstName = firstnametextfield.getText().trim();
        String lastName = lastnametextfield.getText().trim();
        String customerFullName = firstName + " " + lastName;
        String password = passwordtextfield.getText().trim();
        String city = citytextfield.getText().trim();
        String zip = zipcodetextfield.getText().trim();
        String street = streettextfield.getText().trim();
        String contactNumber = conumbertextfield.getText().trim();
        String email = emailaccounttextfield.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || city.isEmpty() || zip.isEmpty() || street.isEmpty()
            || contactNumber.isEmpty() || email.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields must be filled");
            return;
        }
        if (!gmailPattern.matcher(email).matches()) {
            showAlert(AlertType.ERROR, "Email must be a Gmail address");
            return;
        }  if (!phoneNumberPattern.matcher(contactNumber).matches()) {
            showAlert(AlertType.ERROR, "Phone number must start with 09 and be exactly 11 digits long");
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to create this account?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            Customer customer = new Customer("", customerFullName, password, city, zip, street, contactNumber, email);

            if (DatabaseHandler.addCustomer(customer)) {
                showAlert(AlertType.INFORMATION, "Your account has been successfully created.");
                clearFields();
                returntocustomerloginHandler(event);
            } else {
                showAlert(AlertType.ERROR, "Cannot create customer.");
            }
        }
    }
    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void clearFields() {
        firstnametextfield.clear();
        lastnametextfield.clear();
        passwordtextfield.clear();
        citytextfield.clear();
        zipcodetextfield.clear();
        streettextfield.clear();
        conumbertextfield.clear();
        emailaccounttextfield.clear();
    }
}   
