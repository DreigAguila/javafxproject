import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

import javax.imageio.IIOException;


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
    private Label nameLabel;

    @FXML
    private TextField usernametextfield;

    @FXML
    private TextField passwordtextfield;


    public void displayName(String username)
    {
        usernamedisplay.setText("User");
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

        if (createUsername.length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username provided");
            alert.showAndWait();
    
          
        }

        if (createPassword.length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no password provided");
            alert.showAndWait();
           
      
        }

        User user = new User("", createUsername, createPassword, "");
        if (DatabaseHandler.addUser(user)){
            System.out.println("Username: " + createUsername);
            System.out.println("Password: " + createPassword);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account Created");
            alert.showAndWait();
            displayUsers();
          

        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot Create Account");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void deleteUser(ActionEvent event){

        User user = userstable.getSelectionModel().getSelectedItem();
        String username = user.getUsername();

        if(DatabaseHandler.deleteUser(user)){
            System.out.println("User: " + username + " has deleted");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("User deleted");
            alert.showAndWait();
            displayUsers();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot Delete User");
            alert.showAndWait();

        }
        displayUsers();
    }

    @FXML
    private void updateUser(ActionEvent event){

        String createUsername = usernametextfield.getText();
        String createPassword = passwordtextfield.getText();

        createUsername = createUsername.trim();
        createPassword = createPassword.trim();

        if (createUsername.length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no username provided");
            alert.showAndWait();
    
          
        }

        if (createPassword.length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no password provided");
            alert.showAndWait();
           
      
        }

        User user = new User("", createUsername, createPassword, "");
        if (DatabaseHandler.updateUser(user)){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("User Created");
            alert.showAndWait();
            displayUsers();
          
        } else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot Update User");
            alert.showAndWait();
        }
    }
        
}
    

    
