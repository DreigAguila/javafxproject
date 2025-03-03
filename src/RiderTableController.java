import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class RiderTableController implements Initializable{

    
    ObservableList<Rider> riderList = FXCollections.observableArrayList();
    
    @FXML
    private Button admintablebutton;

    @FXML
    private Button customertablebutton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button ridercreatebutton;

    @FXML
    private Button riderupdatebutton;

    @FXML
    private Button riderdeletebutton;

    @FXML
    private TextField riderfullnametextfield;

    @FXML
    private TextField contactnotextfield;

    @FXML
    private TextField ziptextfield;

    @FXML
    private TextField citytextfield;

    @FXML
    private TextField streettextfield;

    @FXML
    private TextField platenotextfield;

    @FXML
    private TextField vehicletextfield;

    @FXML
    private TextField ratingtextfield;

    @FXML
    private TableView<Rider> riderTable;

    @FXML
    private TableColumn<Rider, String> rideridColumn;

    @FXML
    private TableColumn<Rider, String> riderfullnameColumn;

    @FXML
    private TableColumn<Rider, String> ridercontactnoColumn;

    @FXML
    private TableColumn<Rider, String> zipColumn;

    @FXML
    private TableColumn<Rider, String> cityColumn;

    @FXML
    private TableColumn<Rider, String> streetColumn;

    @FXML
    private TableColumn<Rider, String> platenoColumn;

    @FXML
    private TableColumn<Rider, String> vehicleColumn;

    @FXML
    private TableColumn<Rider, Integer> ratingColumn;

    @FXML
    private TableColumn<Rider, String> shipontimeColumn;

    private final Pattern phoneNumberPattern = Pattern.compile("^09\\d{9}$");

    @FXML
    private Label riderusernamedisplay;

    public void displayName(String username) {
        riderusernamedisplay.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayRider();
    }

    private void initializeCol() {
        rideridColumn.setCellValueFactory(new PropertyValueFactory<>("riderid"));
        riderfullnameColumn.setCellValueFactory(new PropertyValueFactory<>("riderfullname"));
        ridercontactnoColumn.setCellValueFactory(new PropertyValueFactory<>("ridercontactnumber"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        platenoColumn.setCellValueFactory(new PropertyValueFactory<>("platenumber"));
        vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        shipontimeColumn.setCellValueFactory(new PropertyValueFactory<>("shipontime"));
    }

    private void displayRider() {
        riderList.clear();

        // Return a set of users
        ResultSet riderResult = DatabaseHandler.getRider();

        try {
            // Loop through set of users from database
            while (riderResult.next()) {
                String riderid = riderResult.getString("Rider_id");
                String riderfullname = riderResult.getString("RiderFullName");
                String ridercontactnumber = riderResult.getString("RiderContactNo");
                String zip = riderResult.getString("Zip");
                String city = riderResult.getString("City");
                String street = riderResult.getString("Street");
                String platenumber = riderResult.getString("PlateNumber");
                String vehicle = riderResult.getString("Vehicle");
                int rating = riderResult.getInt("Rating");
                String shipontime = riderResult.getString("ShipOnTime");

                Rider newRider = new Rider(riderid, riderfullname, ridercontactnumber, zip, city, street, platenumber, vehicle, rating, shipontime);
                riderList.add(newRider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        riderTable.setItems(riderList);
    }

    @FXML
    private void createRider(ActionEvent event) {
        String createRidername = riderfullnametextfield.getText();
        String createRiderContact = contactnotextfield.getText();
        String createZip = ziptextfield.getText();
        String createCity = citytextfield.getText();
        String createStreet = streettextfield.getText();
        String createPlatenumber = platenotextfield.getText();
        String createVehicle = vehicletextfield.getText();
        String createRating = ratingtextfield.getText();

        createRidername = createRidername.trim();
        createRiderContact = createRiderContact.trim();
        createZip = createZip.trim();
        createCity = createCity.trim();
        createStreet = createStreet.trim();
        createPlatenumber = createPlatenumber.trim();
        createVehicle = createVehicle.trim();
        createRating = createRating.trim();

        if (createRidername.isEmpty() || createRiderContact.isEmpty() || createZip.isEmpty() || createCity.isEmpty() || createStreet.isEmpty() || createPlatenumber.isEmpty() || createVehicle.isEmpty() || createRating.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return;
        }
        if (createRidername.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a rider name.");
            return;
        }
        if (createRiderContact.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a contact number.");
            return;
        }
        if (createZip.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a zip code.");
            return;
        }
        if (createCity.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a city.");
            return;
        }
        if (createStreet.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a street.");
            return;
        }
        if (createPlatenumber.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a plate number.");
            return;
        }
        if (createVehicle.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a vehicle.");
            return;
        }
        if (createRating.length() == 0) {
            showAlert(Alert.AlertType.ERROR, "Please input a rating.");
            return;
        }
        if (!phoneNumberPattern.matcher(createRiderContact).matches()) {
            showAlert(AlertType.ERROR, "Phone number must start with 09 and be exactly 11 digits long");
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(createRating);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Rating must be an integer.");
            return;
        }

        Rider rider = new Rider("", createRidername, createRiderContact, createZip, createCity, createStreet, createPlatenumber, createVehicle, rating, "0");
        if (DatabaseHandler.addRider(rider)) {
            System.out.println("Customer Name: " + createRidername);
            System.out.println("Customer Pass: " + createRiderContact);
            System.out.println("Customer City: " + createZip);
            System.out.println("Customer Zip: " + createCity);
            System.out.println("Customer Street: " + createStreet);
            System.out.println("Customer Contact Number: " + createPlatenumber);
            System.out.println("Customer Email: " + createVehicle);
            System.out.println("Customer Email: " + createRating);
            showAlert(Alert.AlertType.INFORMATION, "Rider has been successfully added.");
            displayRider();
        } else {
            showAlert(Alert.AlertType.ERROR, "Cannot add rider.");
        }
    }

    @FXML
    private void updateRider(ActionEvent event) {
        Rider selectedRider = riderTable.getSelectionModel().getSelectedItem();
        if (selectedRider == null) {
            showAlert(AlertType.ERROR, "Please select a user to update.");
            return;
        }
        String oldRiderName = selectedRider.getRiderfullname().trim();
        String newRiderName = riderfullnametextfield.getText().trim();
        String newRiderContact = contactnotextfield.getText().trim();
        String newZip = ziptextfield.getText().trim();
        String newCity = citytextfield.getText().trim();
        String newStreet = streettextfield.getText().trim();
        String newPlatenumber = platenotextfield.getText().trim();
        String newVehicle = vehicletextfield.getText().trim();
        String newRating = ratingtextfield.getText().trim();

        if (newRiderName.isEmpty() || newRiderContact.isEmpty() || newZip.isEmpty() || newCity.isEmpty() || newStreet.isEmpty() || newPlatenumber.isEmpty() || newVehicle.isEmpty() || newRating.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }
        if (newRiderName.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a rider name.");
            return;
        }
        if (newRiderContact.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a contact number.");
            return;
        }
        if (newZip.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a zip code.");
            return;
        }
        if (newCity.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a city.");
            return;
        }
        if (newStreet.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a street.");
            return;
        }
        if (newPlatenumber.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a plate number.");
            return;
        }
        if (newVehicle.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a vehicle.");
            return;
        }
        if (newRating.length() == 0) {
            showAlert(AlertType.ERROR, "Please input a rating.");
            return;
        }
        if (!phoneNumberPattern.matcher(newRiderContact).matches()) {
            showAlert(AlertType.ERROR, "Phone number must start with 09 and be exactly 11 digits long");
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(newRating);
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Rating must be an integer.");
            return;
        }

        Rider updatedRider = new Rider(selectedRider.getRiderid(), newRiderName, newRiderContact, newZip, newCity, newStreet, newPlatenumber, newVehicle, rating, "0");
        if (DatabaseHandler.updateRider(oldRiderName, updatedRider)) {
            showAlert(AlertType.INFORMATION, "Successfully updated");
            displayRider();
        } else {
            showAlert(AlertType.ERROR, "Unsuccessfully updated");
            return;
        }
    }

    
    @FXML
    private void deleteRider(ActionEvent event){
        Rider rider = riderTable.getSelectionModel().getSelectedItem();
        String riderName = rider.getRiderfullname();
        if(DatabaseHandler.deleteRider(riderName)){
            showAlert(AlertType.INFORMATION, "Successfully deleted");
            displayRider();
        }else{
            showAlert(AlertType.ERROR, "Unsuccessfully deleted");
            return;
        }
        displayRider();

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
    private void admintableHandler(ActionEvent event){
        try {
           // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();
            
            HomeController homeController = loader.getController();
            // Pass username from textfield to displayName() method
            homeController.displayName(riderusernamedisplay.getText()); 

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
    private void customertableHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customertablepage.fxml"));
            Parent root = loader.load();

            CustomerTableController customerTableController = loader.getController();
            customerTableController.displayName(riderusernamedisplay.getText());

            
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
