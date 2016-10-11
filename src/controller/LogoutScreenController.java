package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;

public class LogoutScreenController {
    private MainFXApplication main;

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called when the user presses the logout button.
     */
    @FXML
    public void onLogoutPressed() {
        main.setLoginScene();
    }

    /**
     * Called when the user presses the edit profile button
     */
    @FXML
    public void onProfileEdit() {
        main.setUserInfoScene();
    }

    @FXML
    public void onSubmitSourceReport() {
        main.setSourceReportScene();
    }

    @FXML
    public void onViewSourceReports() {
        main.setViewReportsScene();
    }
}
