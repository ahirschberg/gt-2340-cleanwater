package controller;


import fxapp.MainFXApplication;
import javafx.fxml.FXML;

public class QualityReportController {
    private MainFXApplication main;
    public void initialize() {
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

    @FXML
    public void onSubmitSelected() {}
}

