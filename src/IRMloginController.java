
import java.io.IOException;
import java.util.Optional;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class IRMloginController {

    @FXML
    private Button irmloginbutton;

    @FXML
    private Button irmsignupbutton;


    public void toadminHandler(ActionEvent event) throws IOException{
        // Create confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin Confirmation");
        alert.setHeaderText("You are about to switch to the Admin Login.");
        alert.setContentText("Do you want to continue?");
            
        // Show alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Proceed with logout
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();
            } catch (Exception e) {
                System.out.println("Error loading loginpage.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void irmloginHandler(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerloginpage.fxml"));
            Parent root = loader.load();

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
    public void irmsignupHandler(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signuppage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
