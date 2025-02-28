import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WhereToController {
    
    @FXML
    private Button returntobookingbutton;

    @FXML
    private TextField wheretoTextfield;

    @FXML
    private Button wheretolocationButton;

    @FXML
    private void returntobookingHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("locationPage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading locationPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
