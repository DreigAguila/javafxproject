import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private TextField wherefromtextfield;

    @FXML
    private TextField wheretoTextfield;

    @FXML
    private void returntohomepageHandler(ActionEvent event) {

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

}
