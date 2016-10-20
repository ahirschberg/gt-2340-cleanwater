package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Report;

import java.util.Optional;

public class ReportDetailsScreenController {
    private MainFXApplication main;

    @FXML
    private ListView<String> reportInfo;

    @FXML
    private Label reportTitle;

    @FXML
    public void initialize() {

    }

    /**
     * Sets the report info. Unfinished
     */
    public void setReportInfo(Report report) {

        reportTitle.setText("Water report for " + report.getLocation());
        reportInfo.getItems().clear();
        reportInfo.getItems().addAll("Location: " + report.getLocation(), "Condition: " + report.getWaterCondition(),
                "Water Type: " + report.getWaterType());
    }

    /**
     * Registers the main application with this controller
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

    @FXML
    public void onMapSelected() { main.setMapScene(); }
}
