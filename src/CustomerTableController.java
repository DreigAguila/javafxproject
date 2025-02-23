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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerTableController {
    
    @FXML
    private TableColumn<?, ?> accountcreatedcolumn;

    @FXML
    private Button admintablebutton;

    @FXML
    private TextField customercitytextfield;

    @FXML
    private TextField customercontactnotextfield;

    @FXML
    private Button customercreatebutton;

    @FXML
    private Button customerdeletebutton;

    @FXML
    private TextField customeremailtextfield;

    @FXML
    private TextField customerfullnametextfield;

    @FXML
    private TextField customerpasstextfield;

    @FXML
    private TextField customerstreettextfield;

    @FXML
    private Button customerupdatebutton;

    @FXML
    private TextField customerziptextfield;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private TableColumn<?, ?> passwordcolumn;

    @FXML
    private Button ridertablebutton;

    @FXML
    private TableColumn<?, ?> useridcolumn;

    @FXML
    private TableColumn<?, ?> usernamecolumn;

    @FXML
    private Label usernamedisplay;

    @FXML
    private TableView<?> userstable;

    @FXML
    private Button logoutAdmin;   


    @FXML
    private void admintableHandler(ActionEvent event){
        try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
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
    private void logoutAdmin(ActionEvent event) {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout Confirmation");
    alert.setHeaderText("You are about to log out.");
    alert.setContentText("Do you want to continue?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            // Close the current window
            logoutButton.getScene().getWindow().hide();

            // Load the login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

}
