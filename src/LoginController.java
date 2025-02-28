

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class LoginController {

    @FXML
    Label usernamelabel;

    @FXML
    Label passwordlabel;

    @FXML
    TextField usernametextfield;

    @FXML
    TextField passwordtextfield;

    @FXML
    Button loginbutton;

    @FXML
    Button returntouserbutton;

    public void loginbuttonhandler(ActionEvent event) throws IOException {
        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText().trim();

        // Check if fields are empty and show alerts
        if (uname.isEmpty() && pword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Error");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
            return;
        } else if (uname.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Error");
            alert.setHeaderText("Username is missing!");
            alert.setContentText("Please enter your username.");
            alert.showAndWait();
            return;
        } else if (pword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Error");
            alert.setHeaderText("Password is missing!");
            alert.setContentText("Please enter your password.");
            alert.showAndWait();
            return;
        }

        System.out.println("Welcome to my app, " + uname);
        System.out.println("Show username: " + uname);
        System.out.println("Show password: " + pword);

        if (DatabaseHandler.validateLogin(uname, pword)) {
            System.out.println("Login successful");

            // Load homepage.fxml when login button is clicked
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            // Pass username from textfield to displayName() method
            homeController.displayName(uname);
            

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } else {
            System.out.println("Login unsuccessful");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid username or password!");
            alert.setContentText("Please try again.");
            alert.showAndWait();
    }
}

    public void returntouserpagehandler(ActionEvent event) {
        // Create confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Returning to App");
        alert.setContentText("Do you want to continue?");

        // Show alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
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
            
    
        }catch (IOException e) {
            System.out.println("Error loading iridemotologinpage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
}


