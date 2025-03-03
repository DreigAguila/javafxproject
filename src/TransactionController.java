import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TransactionController implements Initializable{


    ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    @FXML
    private Button admintablebutton;

    @FXML
    private Button customertablebutton;
    

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Button ridertablebutton;

    @FXML
    private TableView<Transaction> transactiontable;

    @FXML
    private TableColumn<Transaction, String> transactionidColumn;

    @FXML
    private TableColumn<?, ?> bookingidColumn;


    @FXML
    private TableColumn<Transaction, String> customeridColumn;

    @FXML
    private TableColumn<Transaction, String> rideridColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> transactiondateColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> pickuptimeColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> arrivalColumn;

    @FXML
    private TableColumn<Transaction, Double> amountpaidColumn;

    @FXML
    private TableColumn<Transaction, String> paymentstatusColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayTransactions();
    }

    private void initializeCol() {

        transactionidColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        bookingidColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        rideridColumn.setCellValueFactory(new PropertyValueFactory<>("riderId"));
        transactiondateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        pickuptimeColumn.setCellValueFactory(new PropertyValueFactory<>("pickupTime"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        amountpaidColumn.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        paymentstatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
    }

    private void displayTransactions() {
        transactionList.clear();
        transactionList = DatabaseHandler.getTransactions();
        transactiontable.setItems(transactionList);
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

    @FXML
    private void ridertableHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ridertablepage.fxml"));
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
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
