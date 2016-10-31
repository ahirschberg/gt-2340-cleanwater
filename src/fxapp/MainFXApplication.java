package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Report;
import model.SourceReport;
import model.Token;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main controller for the JavaFX application.
 */
public class MainFXApplication extends Application  {
    private UserInfoScreenController userInfoScreenController;
    private MapScreenController mapScreenController;
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;
    private Stage activeScreen;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registerScene;
    private Scene userInfoScene;
    private Scene viewReportsScene;
    private Scene sourceReportScene;
    private Scene mapScene;
    private Scene qualityReportScene;
    private ReportManager reportManager;
    private ViewReportsScreenController viewReports;
    private DatabaseManager databaseManager;
    private Scene sourceReportDetailsScene;
    private Scene histReportDataScene;
    private ReportDetailsScreenController sourceReportDetails;

    /**
     * Gets the active user
     * @return logged in user
     */
    public User getActiveUser() {
        return loggedInUser;
    }

    /**
     * Start the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        try {
            this.databaseManager = new DatabaseManager();
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        this.reportManager = new ReportManager(databaseManager);
        initRootLayout(primaryStage);
    }

    /**
     * Set scene to main controls
     */
    public void setMainScene() {
        setScene(mainScene, "Cleanwater - Water Reporting System");
    }

    /**
     * Set scene to login controls
     */
    public void setLoginScene() {
        setScene(loginScene, "Cleanwater - Login");
    }

    /**
     * Set scene to registration controls
     */
    public void setRegisterScene() {
        setScene(registerScene, "Cleanwater - Register New User");
    }

    /**
     * Set scene to profile controls
     */
    public void setUserInfoScene() {
        userInfoScreenController.initFields();
        setScene(userInfoScene, "Cleanwater - Edit Profile");
    }
    public void setQualityReportScene() {
        setScene(qualityReportScene, "Cleanwater - Submit quality report");
    }
    /**
     * Set scene to source report controls
     */
    public void setSourceReportScene() {
        setScene(sourceReportScene, "Cleanwater - Submit Source Report");
    }

    /**
     * Set scene to report view
     */
    public void setViewReportsScene() {
        viewReports.setReportsList(reportManager.getSourceReports());
        setScene(viewReportsScene, "Cleanwater - View Source Reports");
    }
    
    public void setViewPurityScene() {
        viewReports.setReportsList(reportManager.getPurityReports());
        setScene(viewReportsScene, "Cleanwater - View Purity Reports");
    }
    public void setHistReportDataScene() {
        setScene(histReportDataScene, "Cleanwater - Enter data for graph");
    }
    
    /**
     * Set scene to water availability map
     */
    public void setMapScene() {
        mapScreenController.refreshMarkers();
        setScene(mapScene, "Cleanwater - Water Map");
    }

    /**
     * Set scene to individual report details view
     */
    public void setReportDetailsScene(Report report) {
        if (report instanceof SourceReport) {
            sourceReportDetails.setReportInfo((SourceReport) report);
            setScene(sourceReportDetailsScene, "Cleanwater - View Individual Source Reports");
        } else {
            System.err.println("report details screen not implemented for " + report.getClass());
        }
    }

    private void setScene(Scene s, String title) {
        activeScreen.hide();
        activeScreen.setScene(s);
        activeScreen.setTitle(title);
        activeScreen.show();
    }

    private void initRootLayout(Stage mainScreen) {
        activeScreen = mainScreen;
        try {
            // Load root layout from fxml file.
            FXMLLoader loginLoader = new FXMLLoader();
            FXMLLoader mainLoader = new FXMLLoader();
            FXMLLoader registerLoader = new FXMLLoader();
            FXMLLoader userInfoLoader = new FXMLLoader();
            FXMLLoader sourceReportLoader = new FXMLLoader();
            FXMLLoader viewReportsLoader = new FXMLLoader();
            FXMLLoader mapLoader = new FXMLLoader();
            FXMLLoader histReportDataLoader = new FXMLLoader();
            FXMLLoader qualityReportLoader = new FXMLLoader();
            FXMLLoader reportDetailsLoader = new FXMLLoader();
            loginLoader.setLocation(MainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            mainLoader.setLocation(MainFXApplication.class.getResource("../view/MainScreen.fxml"));
            registerLoader.setLocation(MainFXApplication.class.getResource("../view/RegisterScreen.fxml"));
            userInfoLoader.setLocation(MainFXApplication.class.getResource("../view/UserInfoScreen.fxml"));
            sourceReportLoader.setLocation(MainFXApplication.class.getResource("../view/SourceReportScreen.fxml"));
            viewReportsLoader.setLocation(MainFXApplication.class.getResource("../view/ViewReportsScreen.fxml"));
            mapLoader.setLocation(MainFXApplication.class.getResource("../view/MapScreen.fxml"));
            reportDetailsLoader.setLocation(MainFXApplication.class.getResource("../view/ReportDetailsScreen.fxml"));
            qualityReportLoader.setLocation(MainFXApplication.class.getResource("../view/PurityReportScreen.fxml"));
            histReportDataLoader.setLocation(MainFXApplication.class.getResource("../view/HistReportDataScreen.fxml"));
            BorderPane loginLayout = loginLoader.load();
            BorderPane mainLayout = mainLoader.load();
            BorderPane registerLayout = registerLoader.load();
            BorderPane userInfoLayout = userInfoLoader.load();
            BorderPane sourceReportLayout = sourceReportLoader.load();
            BorderPane viewReportsLayout = viewReportsLoader.load();
            BorderPane mapLayout = mapLoader.load();
            BorderPane qualReportLayout = qualityReportLoader.load();
            BorderPane reportDetailsLayout = reportDetailsLoader.load();
            BorderPane histDataLayout = histReportDataLoader.load();

            // Show the scene containing the root layout.
            loginScene = new Scene(loginLayout);
            mainScene = new Scene(mainLayout);
            registerScene = new Scene(registerLayout);
            userInfoScene = new Scene(userInfoLayout);
            sourceReportScene = new Scene(sourceReportLayout);
            viewReportsScene = new Scene(viewReportsLayout);
            mapScene = new Scene(mapLayout);
            sourceReportDetailsScene = new Scene(reportDetailsLayout);
            qualityReportScene = new Scene(qualReportLayout);
            histReportDataScene = new Scene(histDataLayout);

            // Give the controller access to the main app.
            LoginScreenController controller = loginLoader.getController();
            MainScreenController logout = mainLoader.getController();
            RegisterScreenController register = registerLoader.getController();
            QualityReportController qualityReport = qualityReportLoader.getController();
            HistReportDataController histData = histReportDataLoader.getController();
            userInfoScreenController = userInfoLoader.getController();
            SourceReportScreenController sourceReport = sourceReportLoader.getController();
            viewReports = viewReportsLoader.getController();
            mapScreenController = mapLoader.getController();
            sourceReportDetails = reportDetailsLoader.getController();
            controller.registerMainApp(this);
            logout.registerMainApp(this);
            register.registerMainApp(this);
            qualityReport.registerMainApp(this);
            histData.registerMainApp(this);
            userInfoScreenController.registerMainApp(this);
            sourceReport.registerMainApp(this);
            viewReports.registerMainApp(this);
            mapScreenController.registerMainApp(this);
            sourceReportDetails.registerMainApp(this);


            setLoginScene();
        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen!!");
            e.printStackTrace();
        }
    }

    /**
     * Notifies that a user is attempting to log in
     * @param token the user's unique identifying information
     * @return true if the user's token is valid, false otherwise
     */
    public boolean notifyLogin(Token token) {
        loggedInUser = null;
        try {
            loggedInUser = databaseManager.<User>getPersistence(User.class).retrieveOne("token", token.toString());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return loggedInUser != null;
    }

    /**
     * Notifies that a user has registered
     * @param registered the user to add to the database
     */
    public boolean notifyRegistration(User registered) {
        try {
            databaseManager.<User>getPersistence(User.class).store(registered);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the ReportManager instance
     * @return the ReportManager associated with this instance
     */
    public ReportManager getReportManager() {
        return this.reportManager;
    }
}
