package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Location;
import model.SourceReport;

/**
 * Handles the source report screen of the app.
 */
public class SourceReportScreenController {
    private MainFXApplication main;
    private int reportNum = 1;

    @FXML
    private final TextField latitudeField;

    @FXML
    private final TextField longitudeField;

    @FXML
    private final ComboBox<String> waterTypeBox;

    @FXML
    private final ComboBox<String> waterConditionBox;

    @FXML
    private final Text errorMessage;

    /**
     * called automatically on view initalization
     * initializes combobox
     */
    @FXML
    public void initialize() {
        waterTypeBox.getItems().addAll("Bottled", "Well", "Stream",
                "Lake", "Spring", "Other");
        waterConditionBox.getItems().addAll("Waste", "Treatable-Clear",
                "Treatable-Muddy", "Potable");
    }

    /**
     * Registers the main application with this controller
     *
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called when submit is pressed
     * submits source report.
     */
    @FXML
    public void onSubmitSelected() {
        String latString = latitudeField.getText();
        String longString = longitudeField.getText();
        double latitude;
        double longitude;
        String waterType = waterTypeBox.getSelectionModel().getSelectedItem();
        String waterCondition = waterConditionBox
                .getSelectionModel().getSelectedItem();

        try {
            latitude = Double.parseDouble(latString);
            longitude = Double.parseDouble(longString);
            if (latitude > 90.0 || latitude < -90.0) {
                throw new Exception();
            } else if (longitude > 180.0 || latitude < -180.0) {
                throw new Exception();
            }

            if (waterType == null) {
                errorMessage.setText("Please enter a water type");
            } else if (waterCondition == null) {
                errorMessage.setText("Please enter a water condition");
            } else {
                main.getReportManager().addSourceReport(new
                        SourceReport(new Location(latitude, longitude),
                        waterType, waterCondition));
                reportNum++;
                main.setMainScene();
            }
        } catch (Exception e) {
            errorMessage.setText("Latitude or Longitude was invalid");
        }
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setMainScene();
    }
}
