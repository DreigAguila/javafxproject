import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class HomeController implements Initializable{

    ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    Label usernamedisplay;

    @FXML
    private TableView<User> userstable;

    @FXML
    private TableColumn<User, String> useridcolumn;

    @FXML
    private TableColumn<User, String> usernamecolumn;

    @FXML
    private TableColumn<User, String> passwordcolumn;

    @FXML
    private TableColumn<User, String> accountcreatedcolumn;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button customertablebutton;

    @FXML
    private Button ridertablebutton;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField usernametextfield;

    @FXML
    private TextField passwordtextfield;

    @FXML
    private Label usernamelabel;

    public void displayName(String username)
    {
        usernamedisplay.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        displayUsers();
    }

    private void initializeCol(){
        useridcolumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        usernamecolumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedcolumn.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
    }

    private void displayUsers(){

        userList.clear();

        //return a set of users
        ResultSet result = DatabaseHandler.getUsers();

        try {
            //loop through set of users from database
            while (result.next()){
                String userid = result.getString("User_id");
                String username = result.getString("Username");
                String password = result.getString("Password");
                String accountcreated = result.getString("AccountCreated");

                //create new user interface
                User newuser = new User(userid, username, password, accountcreated);
                //add to array list
                userList.add(newuser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userstable.setItems(userList);
    }
   
    @FXML
    private void createUser(ActionEvent event){
        String createUsername = usernametextfield.getText();
        String createPassword = passwordtextfield.getText();

        createUsername = createUsername.trim();
        createPassword = createPassword.trim();

        if (createUsername.isEmpty() || createPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields must be filled");
            return;
        }
        if (createUsername.length() == 0){
            showAlert(AlertType.ERROR, "You must input a username.");
            return;
        }

        if (createPassword.length() == 0){
            showAlert(AlertType.ERROR, "You must input a password.");
            return;
        }

        User user = new User("", createUsername, createPassword, "");
        if (DatabaseHandler.addUser(user)){
            System.out.println("Username: " + createUsername);
            System.out.println("Password: " + createPassword);
            showAlert(AlertType.INFORMATION, "Successfully created");
            displayUsers();
        }
        else{
            showAlert(AlertType.ERROR, "Cannot created.");
            return;
        }
    }
    
    @FXML
    private void deleteUser(ActionEvent event){

        User user = userstable.getSelectionModel().getSelectedItem();
        String username = user.getUsername();

        
        if(DatabaseHandler.deleteUser(user)){
            showAlert(AlertType.INFORMATION, "Successfully deleted");
            displayUsers();
        }else{
            showAlert(AlertType.ERROR, "Unsuccessfully deleted");
            return;

        }
        displayUsers();
    }

    @FXML
    private void updateUser(ActionEvent event) {
    
        User selectedUser = userstable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(AlertType.ERROR, "Please select a user to update.");
            return;
        }

        String oldUsername = selectedUser.getUsername();
        String newUsername = usernametextfield.getText().trim();
        String newPassword = passwordtextfield.getText().trim();

       
        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields must be filled");
            return;
        }
        if (newUsername.length() == 0) {
            showAlert(AlertType.ERROR, "You must input a username.");
            return;
        }

        if (newPassword.length() == 0) {
            showAlert(AlertType.ERROR, "You must input a password.");
            return;
        
        }

        User updatedUser = new User(selectedUser.getUserid(), newUsername, newPassword, "");
        if(DatabaseHandler.updateUser(oldUsername, updatedUser)){
            showAlert(AlertType.INFORMATION, "Successfully updated");
            displayUsers();
            
        }else{
            showAlert(AlertType.ERROR, "Unsuccessfully updated");
            return;
        }
    
} 
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    private void customertableHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customertablepage.fxml"));
            Parent root = loader.load();


            CustomerTableController customerTableController = loader.getController();
            customerTableController.displayName(usernamedisplay.getText());

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading customertablepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void ridertableHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ridertablepage.fxml"));
            Parent root = loader.load();


            RiderTableController riderTableController = loader.getController();
            riderTableController.displayName(usernamedisplay.getText());

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading customertablepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    
    @FXML
    private void transactiontableHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionTable.fxml"));
            Parent root = loader.load();

            
            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading transactionTable.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }
}


    


    

    
