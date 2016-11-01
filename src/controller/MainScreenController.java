package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import model.PermissionLevel;
import model.User;

/**
 * Handles the main screen of the app.
 */
public class MainScreenController {
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
    
    @FXML
    public void onViewPurityReports() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.WORKER || permissionLevel == PermissionLevel.MANAGER) {
            main.setViewPurityScene();
        }
    }
    @FXML
    public void onViewHistoricalReports() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.MANAGER) {
            main.setHistReportDataScene();
        }
    }
    @FXML
    public void onViewMap() {
        main.setMapScene();
    }

    @FXML
    public void onSubmitQualityReport() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.WORKER || permissionLevel == PermissionLevel.MANAGER) {
            main.setQualityReportScene();
        }
    }
}
