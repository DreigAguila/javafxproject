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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private TableColumn<Rider, String> ratingColumn;

    @FXML
    private TableColumn<Rider, String> shipontimeColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        displayRider();
    }

    private void initializeCol(){
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

    private void displayRider(){
        
        riderList.clear();
        
        //return a set of users
        ResultSet riderResult = DatabaseHandler.getRider();

        try {
            //loop through set of users from database
            while (riderResult.next()){
                String riderid = riderResult.getString("Rider_id");
                String riderfullname = riderResult.getString("RiderFullName");
                String ridercontactnumber = riderResult.getString("RiderContactNo");
                String zip = riderResult.getString("Zip");
                String city = riderResult.getString("City");
                String street = riderResult.getString("Street");
                String platenumber = riderResult.getString("PlateNumber");
                String vehicle = riderResult.getString("Vehicle");
                String rating = riderResult.getString("Rating");
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


}
