package controller;


import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.HistoricalData;

public class HistReportDataController {
    private MainFXApplication main;

    /**
     * Registers the main application with this controller
     *
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called automatically when view is initialized.
     * Sets up the combobox for the view.
     */
    @FXML
    public void initialize() {
        contTypeBox.getItems().addAll("Virus PPM", "Contaminant PPM");
    }

    @FXML
    private TextField latitudeFieldMin;

    @FXML
    private TextField latitudeFieldMax;

    @FXML
    private TextField longitudeFieldMin;

    @FXML
    private TextField longitudeFieldMax;

    @FXML
    private TextField yearField;

    @FXML
    private ComboBox<String> contTypeBox;

    @FXML
    private Text errorMessage;

    /**
     * Submits report if all fields are filled out and valid
     */
    @FXML
    public void onSubmitSelected() {
        try {
            boolean validInput = true;
            String latStringMin = latitudeFieldMin.getText();
            String longStringMin = longitudeFieldMin.getText();
            String latStringMax = latitudeFieldMax.getText();
            String longStringMax = longitudeFieldMax.getText();
            String yearString = yearField.getText();
            double latitudeMin = Double.parseDouble(latStringMin);
            double longitudeMin = Double.parseDouble(longStringMin);
            double latitudeMax = Double.parseDouble(latStringMax);
            double longitudeMax = Double.parseDouble(longStringMax);
            int year = Integer.parseInt(yearString);
            String contaminantType = contTypeBox
                    .getSelectionModel().getSelectedItem();
            if (latitudeMin > latitudeMax || longitudeMin > longitudeMax) {
                errorMessage.setText("coordinates are in wrong order");
                validInput = false;
            }
            if (contaminantType == null) {
                errorMessage.setText("select contaminant type");
                validInput = false;
            }
            if (latitudeMin > 90.0 || latitudeMin < -90.0) {
                errorMessage.setText("coordinates outside range");
                validInput = false;
            } else if (longitudeMin > 180.0 || latitudeMin < -180.0) {
                errorMessage.setText("coordinates outside range");
                validInput = false;
            }
            if (year > 2500 || year < 1800) {
                errorMessage.setText("Year outside of year range");
                validInput = false;
            }
            if (latitudeMax > 90.0 || latitudeMax < -90.0) {
                errorMessage.setText("coordinates outside range");
                validInput = false;
            } else if (longitudeMax > 180.0 || latitudeMax < -180.0) {
                errorMessage.setText("coordinates outside range");
                validInput = false;
            }
            if (validInput) {
                errorMessage.setText("");
                HistoricalData hData = new HistoricalData(latitudeMin,
                        latitudeMax, longitudeMin,
                        longitudeMax, year, contaminantType);
                main.setHistReportScene(hData);
            }
        } catch (Exception e) {
            errorMessage.setText("Input Invalid."
                   + "  Enter Valid year and coordinates");
            e.printStackTrace();
        }

    }

    /**
     * Sets the screen to the home scene
     */
    @FXML
    public void onCancelSelected() {
        main.setMainScene();
    }
}
