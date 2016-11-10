package controller;


import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Location;
import model.PurityReport;

public class QualityReportController {
    private int reportNum;
    private MainFXApplication main;
    @FXML
    private final ComboBox<String> waterTypeBox;
    @FXML
    private final TextField latitudeField;

    @FXML
    private final TextField longitudeField;

    @FXML
    private final TextField virusPPM;

    @FXML
    private final TextField contaminantPPM;

    @FXML
    private final Text errorMessage;

    /**
     * Called automatically on view initalization.
     * Sets up the combobox.
     */
    @FXML
    public void initialize() {
        waterTypeBox.getItems().addAll("Safe", "Treatable", "Unsafe");
    }

    /**
     * Gives the controller a reference to the main app
     *
     * @param main the main app
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onCancelSelected() {
        main.setMainScene();
    }

    /**
     * Submit button action.
     * Generates and saves report.
     */
    @FXML
    public void onSubmitSelected() {
        String latString = latitudeField.getText();
        String longString = longitudeField.getText();
        double latitude = 0;
        double longitude = 0;
        String waterPurity = waterTypeBox.getSelectionModel().getSelectedItem();
        String virusPPMStr = virusPPM.getText();
        String contaminantPPMStr = contaminantPPM.getText();
        double contaminantPPMNum = 0;
        double virusPPMNum = 0;
        try {
            latitude = Double.parseDouble(latString);
            longitude = Double.parseDouble(longString);
            contaminantPPMNum = Double.parseDouble(contaminantPPMStr);
            virusPPMNum = Double.parseDouble(virusPPMStr);
            if (latitude > 90.0 || latitude < -90.0) {
                throw new Exception();
            } else if (longitude > 180.0 || latitude < -180.0) {
                throw new Exception();
            }

            if (waterPurity == null) {
                errorMessage.setText("Please enter a water purity level");
            } else {
                main.getReportManager().addPurityReport(new PurityReport(
                        new Location(latitude, longitude),
                        virusPPMNum, contaminantPPMNum, waterPurity));
                reportNum++;
                main.setMainScene();
            }
        } catch (Exception e) {
            errorMessage.setText("Format invalid");
        }
    }
}

