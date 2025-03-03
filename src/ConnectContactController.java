import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectContactController {
    
    @FXML
    private Button loginmobilebutton;

    @FXML
    private TextField loginmobilenotextfield;

    @FXML
    private TextField passwordmobiletextfield;

    @FXML
    private Button returnloginpage;

    @FXML
    public void returntocustomerloginHandler(ActionEvent event){
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerloginpage.fxml"));
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
    public void connectusingmobileHandler(ActionEvent event) throws IOException {
        String mobileNumber = loginmobilenotextfield.getText().trim();
        String password = passwordmobiletextfield.getText().trim();

        if (mobileNumber.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Mobile number and password must be filled");
            return;
        }
       
        Customer loggedInCustomer = DatabaseHandler.validateCustomerContact(mobileNumber, password);

        if (loggedInCustomer != null) {
            showAlert(AlertType.INFORMATION, "Login successful");
            // Load the next scene
            UserSession.getInstance().clearSession();
            UserSession.getInstance().setCustomer(loggedInCustomer);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMhomepage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } else {
            showAlert(AlertType.ERROR, "Invalid mobile number or password");
        }
    }


      private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
