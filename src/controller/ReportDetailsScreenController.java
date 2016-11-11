package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.SourceReport;

public class ReportDetailsScreenController {
    private MainFXApplication main;

    @FXML
    private ListView<String> reportInfo;

    @FXML
    private Label reportTitle;

    /**
     * Called automatically on view initialization
     */
    @FXML
    public void initialize() {

    }

    /**
     * Sets the report info. Unfinished
     * @param report The report from which the info comes.
     */
    public void setReportInfo(SourceReport report) {

        reportTitle.setText("Water report for " + report.getLocation());
        reportInfo.getItems().clear();
        reportInfo.getItems().addAll("Location: " + report.getLocation(),
                "Condition: " + report.getWaterCondition(),
                "Water Type: " + report.getWaterType()
                        + "Date: " + report.getCreationDatetime().toString());
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
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setMainScene();
    }

    /**
     * Map button action
     * takes the user to the map.
     */
    @FXML
    public void onMapSelected() {
        main.setMapScene();
    }
}
