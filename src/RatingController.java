import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RatingController {
    @FXML
    private Button rateButton;

    @FXML
    private ComboBox<Integer> ratingComboBox;

    @FXML
    private Button xButton;

    private String riderId;

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    @FXML
    public void initialize() {
        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
    }

    @FXML
    void handleSubmitRating(ActionEvent event) {
        Integer rating = ratingComboBox.getValue();
        if (rating == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a rating.");
            return;
        }

        // Update the rider's rating in the database
        DatabaseHandler.updateRiderRating(riderId, rating);

        showAlert(Alert.AlertType.INFORMATION, "Thank You", "Thank you for rating your driver!");

        // Close the rating window
        Stage stage = (Stage) rateButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleXButton(ActionEvent event) {
        try {
            // Load the homepage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) xButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the homepage.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}