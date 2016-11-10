package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Report;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles the report viewer screen.
 */
public class ViewReportsScreenController {
    private MainFXApplication main;

    @FXML
    private final ListView<String> reportsList;

    /**
     * Called automatically on view initialization
     */
    @FXML
    public void initialize() {

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
     * Updates the reports list with stringified reports.
     * @param reports to store.
     */
    public void setReportsList(Stream<? extends Report> reports) {
        reportsList.getItems().clear();
        reportsList.getItems().addAll(reports.map(Report::toString)
                .collect(Collectors.toList()));
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setMainScene();
    }
}
