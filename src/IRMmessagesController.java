import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IRMmessagesController {
     @FXML
    private Button accountpagebutton;

    @FXML
    private Button activitybuttonpage;

    @FXML
    private ToggleButton chatsButton;

    @FXML
    private Pane chatsPane;

    @FXML
    private Button homebuttonpage;

    @FXML
    private Button messagesbuttonpage;

    @FXML
    private ToggleButton notificationButtons;

    @FXML
    private Pane notificationsPane;

    @FXML
    private ToggleGroup toggleGroup1;
    
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

