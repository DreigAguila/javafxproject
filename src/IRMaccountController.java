import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IRMaccountController {
      @FXML
    private Button accountpagebutton;

    @FXML
    private Button activitybuttonpage;

    @FXML
    private Label displaycustomernamelabel;

    @FXML
    private Button emergencycontactsbutton;

    @FXML
    private Button helpcenterbutton;

    @FXML
    private Button homebuttonpage;

    @FXML
    private Button messagesbuttonpage;

    @FXML
    private Button pathtoeditcustomerbutton;

    @FXML
    private Button paymentmethodsbutton;

    @FXML
    private Button savedplacesbutton;

    @FXML
    private Button settingsbutton;

    public void initialize() {
        refreshCustomerData();
    }
    
    public void refreshCustomerData() {
        Customer customer = UserSession.getInstance().getCustomer();
        if (customer != null) {
            displaycustomernamelabel.setText(customer.getCustomerfullname());
        } else {
            displaycustomernamelabel.setText("Guest"); // Default text if no user is logged in
        }
    }

    @FXML
    private void homepageHandler(ActionEvent event) {

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
    private void activitypageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMactivitypage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMactivitypage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void messagespageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMmessagespage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMmessagespage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void editnlogoutHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerEditnLogoutPage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading customerEditnLogoutPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
