package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.*;

/**
 * Created by hankg_000 on 10/11/2016.
 */
public class ViewReportsScreenController {
    private MainFXApplication main;

    @FXML
    private ListView<String> reportsList;

    @FXML
    public void initialize() {

    }

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    public void setReportsList() {
        reportsList.getItems().clear();
        reportsList.getItems().addAll(main.getReportManager().toString());
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setLogoutScene();
    }
}
