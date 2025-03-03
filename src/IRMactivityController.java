import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class IRMactivityController implements Initializable{
    
    ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    
    @FXML
    private Button accountpagebutton;

    @FXML
    private Button activitybuttonpage;

    @FXML
    private Button homebuttonpage;

    @FXML
    private Button messagesbuttonpage;

    @FXML
    private TableView<Transaction> transactionHistory;

    @FXML
    private TableColumn<Transaction, String> customeridColumn;

    @FXML
    private TableColumn<Transaction, String> rideridColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> transactiondateColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> pickuptimeColumn;
    
    @FXML
    private TableColumn<Transaction, LocalDateTime> arrivaltimeColumn;

    @FXML
    private TableColumn<Transaction, Double> amountpaidColumn;

    @FXML
    private TableColumn<Transaction, String> paymentstatusColumn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        loadTransactionHistory();
    }

    private void initializeCol() {
        customeridColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        rideridColumn.setCellValueFactory(new PropertyValueFactory<>("riderId"));
        transactiondateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        pickuptimeColumn.setCellValueFactory(new PropertyValueFactory<>("pickupTime"));
        arrivaltimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        amountpaidColumn.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        paymentstatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
    }

    private void loadTransactionHistory() {
        String customerId = UserSession.getInstance().getCustomerId();
        ObservableList<Transaction> transactions = DatabaseHandler.getTransactionsForCustomer(customerId);
        transactionHistory.setItems(transactions);
    }


    @FXML
    private void homepageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMhomepage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMhomepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void messagespageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMmessagespage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMmessagespage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void accountpageHandler(ActionEvent event) {

        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IRMaccountpage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            System.out.println("Error loading IRMaccountpage.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    
}
