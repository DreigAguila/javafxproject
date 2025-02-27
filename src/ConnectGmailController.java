
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

public class ConnectGmailController {
       @FXML
    private TextField logingmailacctextfield;

    @FXML
    private Button logingmailbutton;

    @FXML
    private TextField passwordgmailtextfield;

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
    public void connectusinggmailHandler(ActionEvent event) throws IOException {
    String emailAccount = logingmailacctextfield.getText().trim();
    String password = passwordgmailtextfield.getText().trim();

    if (emailAccount.isEmpty() || password.isEmpty()) {
        showAlert(AlertType.ERROR, "Email and password must be filled");
        return;
    }

    // Fetch the customer object
    Customer loggedInCustomer = DatabaseHandler.validateCustomerGmail(emailAccount, password);

    if (loggedInCustomer != null) {
                showAlert(AlertType.INFORMATION, "Login successful");
                // Store the logged-in customer in session
                UserSession.getInstance().clearSession();
                UserSession.getInstance().setCustomer(loggedInCustomer);

                // Load the next scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMhomepage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            } else {
                showAlert(AlertType.ERROR, "Invalid email or password");
        }
    }
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }


}