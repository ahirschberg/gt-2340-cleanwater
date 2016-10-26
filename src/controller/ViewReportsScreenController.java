package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the report viewer screen.
 */
public class ViewReportsScreenController {
    private MainFXApplication main;
    /**
     * Are purity reports being viewed
     * or availability reports?
     */
    private boolean viewPurityReports;

    @FXML
    private ListView<String> reportsList;

    @FXML
    public void initialize() {

    }
    
    public void showPurityReports(boolean show) {
        viewPurityReports = show;
    }

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Updates the reports list with stringified reports.
     */
    public void setReportsList() {
        Stream<Report> reports;
        if (viewPurityReports) {
            //TODO: get purity reports
        } else {
            reports = main.getReportManager().getSourceReports();
        }
        reportsList.getItems().clear();
        reportsList.getItems().addAll(reports.map(Report::toString).collect(Collectors.toList()));
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setMainScene();
    }
}
