import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IRMCustomerLoginController {
    
    @FXML
    private Button backbutton;

    @FXML
    private Button gmailbutton;

    @FXML
    private Button mobilenobutton;

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
    private void connectgmailHandler(ActionEvent event){
     try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connectusinggmailaccount.fxml"));
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
    private void connectcontactHandler(ActionEvent event){
     try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connectusingcontactnumber.fxml"));
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
    private void gotosignupHandler(ActionEvent event){
     try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signuppage.fxml"));
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

}   

    


