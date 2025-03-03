import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WhereToController {

    @FXML
    private Button returntobookingButton;

    @FXML
    private ImageView whereToMapImageView;

    @FXML
    private TextField wheretoTextfield;

    @FXML
    private Button wheretolocationButton;

    @FXML
    public void initialize() {
        // Trigger updateImage() whenever text is typed
        wheretoTextfield.setOnKeyReleased(event -> updateImage());
    }

    public void updateImage() {
        String location = wheretoTextfield.getText().trim().toLowerCase();
        String imagePath;

        // Determine image path based on location
        switch (location) {
            case "makati":
            case "makati city":
                imagePath = "file:/C:/iridemotoLocation/Makati_in_Metro_Manila.png";
                break;
            case "manila":
            case "manila city":
                imagePath = "file:/C:/iridemotoLocation/Manila_in_Metro_Manila.png";
                break;
            case "paranaque":
            case "parañaque":
            case "paranaque city":
            case "parañaque city":
                imagePath = "file:/C:/iridemotoLocation/Paranaque_in_Metro_Manila.png";
                break;
            case "laspinas":
            case "las piñas":
            case "las pinas":
            case "las pinas city":
                imagePath = "file:/C:/iridemotoLocation/Las_Pinas_in_Metro_Manila.png";
                break;
            case "caloocan":
            case "caloocan city":
                imagePath = "file:/C:/iridemotoLocation/Caloocan_in_Metro_Manila.png";
                break;
            case "malabon":
            case "malabon city":
                imagePath = "file:/C:/iridemotoLocation/Malabon_in_Metro_Manila.png";
                break;
            case "mandaluyong":
            case "mandaluyong city":
                imagePath = "file:/C:/iridemotoLocation/Mandaluyong_in_Metro_Manila.png";
                break;
            case "marikina":
            case "marikina city":
                imagePath = "file:/C:/iridemotoLocation/Marikina_in_Metro_Manila.png";
                break;
            case "muntinlupa":
            case "muntinlupa city":
                imagePath = "file:/C:/iridemotoLocation/Muntinlupa_in_Metro_Manila.png";
                break;
            case "navotas":
            case "navotas city":
                imagePath = "file:/C:/iridemotoLocation/Navotas_in_Metro_Manila.png";
                break;
            case "pasay":
            case "pasay city":
                imagePath = "file:/C:/iridemotoLocation/Pasay_in_Metro_Manila.png";
                break;
            case "pasig":
            case "pasig city":
                imagePath = "file:/C:/iridemotoLocation/Pasig_in_Metro_Manila.png";
                break;
            case "pateros":
            case "pateros city":
                imagePath = "file:/C:/iridemotoLocation/Pateros_in_Metro_Manila.png";
                break;
            case "quezon city":
            case "quezon":
            case "qc":
                imagePath = "file:/C:/iridemotoLocation/Quezon_City_in_Metro_Manila.png";
                break;
            case "san juan":
            case "san juan city":
                imagePath = "file:/C:/iridemotoLocation/San_Juan_in_Metro_Manila.png";
                break;
            case "taguig":
            case "taguig city":
                imagePath = "file:/C:/iridemotoLocation/Taguig_in_Metro_Manila.png";
                break;
            case "valenzuela":
            case "valenzuela city":
                imagePath = "file:/C:/iridemotoLocation/Valenzuela_in_Metro_Manila.png";
                break;
            default:
                imagePath = "file:/C:/iridemotoLocation/Metro_Manila_location_map.png"; // Default image
                break;
        }

        // Debug: Print selected location and image path
        System.out.println("User entered location: " + location);
        System.out.println("Attempting to load image from: " + imagePath);

        try {
            // Load image and set it to ImageView
            Image image = new Image(imagePath);

            // Debug: Check if the image is successfully loaded
            if (image.isError()) {
                System.out.println("Error: Failed to load image from " + imagePath);
            } else {
                System.out.println("Image loaded successfully from: " + imagePath);
            }

            whereToMapImageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Exception occurred while loading image: " + e.getMessage());
        }
    }

    @FXML
    private void dropOffProceedHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("locationPage.fxml"));
            Parent root = loader.load();

            LocationController locationController = loader.getController();

            // Get the entered drop-off location
            String selectedDropOff = wheretoTextfield.getText().trim();
            locationController.setDropOffLocation(selectedDropOff);

            // Load the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returntobookingHandler(ActionEvent event) {
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("locationPage.fxml"));
            Parent root = loader.load();

            // Load stage and scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Error loading locationPage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
}