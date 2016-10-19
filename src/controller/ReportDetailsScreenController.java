package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Report;

import java.util.Optional;

/**
 * Created by hankg_000 on 10/18/2016.
 */
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
    public void setReportInfo() {

        Optional<Report> reportMaybe = main.getReportManager().getReports().findFirst();
        if (!reportMaybe.isPresent()) {
            reportTitle.setText("Report not found");
            return;
        }
        Report report = reportMaybe.get();
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
    public void onMapSelected() { main.setMainScene();} //TODO change scene set to map page
}
