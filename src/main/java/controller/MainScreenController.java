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
     *
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called when the user presses the logout button.
     * takes user to login view
     */
    @FXML
    public void onLogoutPressed() {
        main.setLoginScene();
    }

    /**
     * Called when the user presses the edit profile button
     * takes user to edit profile view
     */
    @FXML
    public void onProfileEdit() {
        main.setUserInfoScene();
    }

    /**
     * Submit source report button action
     * takes user to view for submitting source reports
     */
    @FXML
    public void onSubmitSourceReport() {
        main.setSourceReportScene();
    }

    /**
     * View source report button action
     * takes user to view for viewing source reports
     */
    @FXML
    public void onViewSourceReports() {
        main.setViewReportsScene();
    }

    /**
     * View purity report button action
     * takes user to view for submitting purity reports
     */
    @FXML
    public void onViewPurityReports() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.WORKER
                || permissionLevel == PermissionLevel.MANAGER) {
            main.setViewPurityScene();
        }
    }

    /**
     * View historical reports button action
     * Takes user to historical reports view.
     */
    @FXML
    public void onViewHistoricalReports() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.MANAGER) {
            main.setHistReportDataScene();
        }
    }

    /**
     * View map button action.
     * takes user to the map
     */
    @FXML
    public void onViewMap() {
        main.setMapScene();
    }

    /**
     * View list of users.
     * Only admins can access this.
     */
    @FXML
    public void onViewUserList() {
	    User currentUser = main.getActiveUser();
	    PermissionLevel permissionLevel = currentUser.getPermissionLevel();
	    if (permissionLevel == PermissionLevel.ADMIN) {
	        main.setUserListScene();
        }
    }

    /**
     * Submit quality report button action.
     * takes user to view for submitting quality report
     */
    @FXML
    public void onSubmitQualityReport() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.WORKER
                || permissionLevel == PermissionLevel.MANAGER) {
            main.setQualityReportScene();
        }
    }
}
