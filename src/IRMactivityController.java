import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IRMactivityController {
    
    @FXML
    private Button accountpagebutton;

    @FXML
    private Button activitybuttonpage;

    @FXML
    private Button homebuttonpage;

    @FXML
    private Button messagesbuttonpage;
    
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
    private void accountpageHandler(ActionEvent event) {

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

    
}
