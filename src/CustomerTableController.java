import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerTableController implements Initializable{

    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    @FXML
    private Button logoutButton;

    @FXML
    private Button ridertablebutton;

    @FXML
    private Button admintablebutton;

    @FXML
    private Button customercreatebutton;

    @FXML
    private Button customerupdatebutton;

    @FXML
    private Button customerdeletebutton;
    
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> customerIDcolumn;

    @FXML
    private TableColumn<Customer, String> customerfullnamecolumn;

    @FXML
    private TableColumn<Customer, String> passwordcolumn;

    @FXML
    private TableColumn<Customer, String> citycolumn;

    @FXML
    private TableColumn<Customer, String> zipcolumn;  

    @FXML
    private TableColumn<Customer, String> streetcolumn;

    @FXML
    private TableColumn<Customer, String> contactnumbercolumn;
    
    @FXML
    private TableColumn<Customer, String> emailcolumn;

    @FXML
    private TextField customercitytextfield;

    @FXML
    private TextField customercontactnotextfield;

    @FXML
    private TextField customeremailtextfield;

    @FXML
    private TextField customerfullnametextfield;

    @FXML
    private TextField customerpasstextfield;

    @FXML
    private TextField customerstreettextfield;

    @FXML
    private TextField customerziptextfield;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label usernamedisplay;

    //regex for email and contact number
    private final Pattern gmailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    private final Pattern phoneNumberPattern = Pattern.compile("^09\\d{9}$");


    public void displayName(String username)
    {
        usernamedisplay.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        displayCustomer();
    }

    private void initializeCol(){
        customerIDcolumn.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        customerfullnamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerfullname"));
        passwordcolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        citycolumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        zipcolumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        streetcolumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        contactnumbercolumn.setCellValueFactory(new PropertyValueFactory<>("contactnumber"));
        emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }   

    private void displayCustomer(){
        
        customerList.clear();
        
        //return a set of users
        ResultSet customerResult = DatabaseHandler.getCustomer();

        try {
            //loop through set of users from database
            while (customerResult.next()){
                String customerid = customerResult.getString("Customer_id");
                String customerfullname = customerResult.getString("CustomerFullName");
                String password = customerResult.getString("Password");
                String city = customerResult.getString("City");
                String zip = customerResult.getString("Zip");
                String street = customerResult.getString("Street");
                String contactnumber = customerResult.getString("ContactNum");
                String email = customerResult.getString("Email");

                //create new user interface
                Customer newCustomer = new Customer(customerid, customerfullname, password, city, zip, street, contactnumber, email);
                //add to array list
                customerList.add(newCustomer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerTable.setItems(customerList);
    }

    @FXML
    private void createCustomer(ActionEvent event){
        String createCustomername = customerfullnametextfield.getText();
        String createCustomerpass = customerpasstextfield.getText();
        String createCustomerCity = customercitytextfield.getText();
        String createCustomerzip = customerziptextfield.getText();
        String createCustomerstreet = customerstreettextfield.getText();
        String createCustomernumber = customercontactnotextfield.getText();
        String createCustomeremail = customeremailtextfield.getText();

        createCustomername = createCustomername.trim();
        createCustomerpass = createCustomerpass.trim();
        createCustomerCity = createCustomerCity.trim();
        createCustomerzip = createCustomerzip.trim();
        createCustomerstreet = createCustomerstreet.trim();
        createCustomernumber = createCustomernumber.trim();
        createCustomeremail = createCustomeremail.trim();

        if (createCustomername.isEmpty() || createCustomerpass.isEmpty() || createCustomerCity.isEmpty() || createCustomerzip.isEmpty() || createCustomerstreet.isEmpty()
            || createCustomernumber.isEmpty() || createCustomeremail.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields must be filled");
            return;
        }if (createCustomername.length() == 0){
            showAlert(AlertType.ERROR, "You must input your name.");
            return;
        }if (createCustomerpass.length() == 0){
            showAlert(AlertType.ERROR, "You must input your password.");
            return;
        }if (createCustomerCity.length() == 0){
            showAlert(AlertType.ERROR, "You must input your city .");
            return;
        }if (createCustomerzip.length() == 0){
            showAlert(AlertType.ERROR, "You must input your zip.");
            return;
        }if (createCustomerstreet.length() == 0){
            showAlert(AlertType.ERROR, "You must input your street.");
            return;
        }if (createCustomernumber.length() == 0){
            showAlert(AlertType.ERROR, "You must input your phone number.");
            return;
        }if (createCustomeremail.length() == 0){
            showAlert(AlertType.ERROR, "You must input your email");
            return;
        } if (!gmailPattern.matcher(createCustomeremail).matches()) {
            showAlert(AlertType.ERROR, "Email must be a Gmail address");
            return;
        } if (!phoneNumberPattern.matcher(createCustomernumber).matches()) {
            showAlert(AlertType.ERROR, "Phone number must start with 09 and be exactly 11 digits long");
            return;
        }

    Customer customer = new Customer("", createCustomername, createCustomerpass, createCustomerCity, createCustomerzip, createCustomerstreet,
                                        createCustomernumber, createCustomeremail);

    if(DatabaseHandler.addCustomer(customer)){
        System.out.println("Customer Name: " + createCustomername);
        System.out.println("Customer Pass: " + createCustomerpass);
        System.out.println("Customer City: " + createCustomerCity);
        System.out.println("Customer Zip: " + createCustomerzip);
        System.out.println("Customer Street: " + createCustomerstreet);
        System.out.println("Customer Contact Number: " + createCustomernumber);
        System.out.println("Customer Email: " + createCustomeremail);
        showAlert(AlertType.INFORMATION, "Successfully created");

        // Refresh the table view
        displayCustomer();
    } else {
        showAlert(AlertType.ERROR, "Cannot create.");
    }
}

    @FXML
    private void updateCustomer(ActionEvent event){
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(AlertType.ERROR, "Please select a user to update.");
            return;
        }
            String oldCustomerFullName = selectedCustomer.getCustomerFullName();
            String newCustomerfullname = customerfullnametextfield.getText().trim();
            String newCustomerpassword = customerpasstextfield.getText().trim();
            String newCustomercity = customercitytextfield.getText().trim();
            String newCustomerzip = customerziptextfield.getText().trim();
            String newCustomerstreet = customerstreettextfield.getText().trim();
            String newCustomernumber = customercontactnotextfield.getText().trim();
            String newCustomeremail = customeremailtextfield.getText().trim();

        if (newCustomerfullname.isEmpty() || newCustomerpassword.isEmpty() || newCustomercity.isEmpty() || newCustomerzip.isEmpty() 
            || newCustomerstreet.isEmpty() || newCustomernumber.isEmpty() || newCustomeremail.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields must be filled");
            return;
        }if (newCustomerfullname.length() == 0){
            showAlert(AlertType.ERROR, "You must input your name.");
            return;
        }if (newCustomerpassword.length() == 0){
            showAlert(AlertType.ERROR, "You must input your password.");
            return;
        }if (newCustomercity.length() == 0){
            showAlert(AlertType.ERROR, "You must input your city .");
            return;
        }if (newCustomerzip.length() == 0){
            showAlert(AlertType.ERROR, "You must input your zip.");
            return;
        }if (newCustomerstreet.length() == 0){
            showAlert(AlertType.ERROR, "You must input your street.");
            return;
        }if (newCustomernumber.length() == 0){
            showAlert(AlertType.ERROR, "You must input your phone number.");
            return;
        }if (newCustomeremail.length() == 0){
            showAlert(AlertType.ERROR, "You must input your email");
            return;
        } if (!gmailPattern.matcher(newCustomeremail).matches()) {
            showAlert(AlertType.ERROR, "Email must be a Gmail address");
            return;
        } if (!phoneNumberPattern.matcher(newCustomernumber).matches()) {
            showAlert(AlertType.ERROR, "Phone number must start with 09 and be exactly 11 digits long");
            return;
        }
        
        Customer updatedCustomer = new Customer(selectedCustomer.getCustomerId(), newCustomerfullname, newCustomerpassword, newCustomercity, 
        newCustomerzip, newCustomerstreet, newCustomernumber, newCustomeremail);
        if(DatabaseHandler.updateCustomer(oldCustomerFullName, updatedCustomer)){
            showAlert(AlertType.INFORMATION, "Successfully updated");
            displayCustomer();
        }else{
            showAlert(AlertType.ERROR, "Unsuccessfully updated");
            return;
        }
    }


    
    @FXML
    private void deleteCustomer(ActionEvent event){

        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(AlertType.ERROR, "Please select a customer to delete.");
            return;
        }

        if(DatabaseHandler.deleteCustomer(selectedCustomer.getCustomerFullName())){
            showAlert(AlertType.INFORMATION, "Successfully deleted");
            displayCustomer();
        } else {
            showAlert(AlertType.ERROR, "Unsuccessfully deleted");
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
    private void admintableHandler(ActionEvent event){
        try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();
            
            HomeController homeController = loader.getController();
            // Pass username from textfield to displayName() method
            homeController.displayName(usernamedisplay.getText());
            
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
