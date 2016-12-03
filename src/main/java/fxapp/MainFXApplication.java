package fxapp;

import controller.HistReportController;
import controller.HistReportDataController;
import controller.LoginScreenController;
import controller.MainScreenController;
import controller.MapScreenController;
import controller.QualityReportController;
import controller.RegisterScreenController;
import controller.ReportDetailsScreenController;
import controller.SourceReportScreenController;
import controller.UserInfoScreenController;
import controller.UserListScreenController;
import controller.ViewReportsScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HistoricalData;
import model.Report;
import model.SourceReport;
import model.Token;
import model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * The main controller for the JavaFX application.
 */
public class MainFXApplication extends Application {
    private UserInfoScreenController userInfoScreenController;
	private UserListScreenController userListScreenController;
    private HistReportController histReportController;
    private MapScreenController mapScreenController;
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;
    private Stage activeScreen;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registerScene;
    private Scene userInfoScene;
    private Scene viewReportsScene;
	private Scene userListScene;
    private Scene sourceReportScene;
    private Scene mapScene;
    private Scene qualityReportScene;
    private ReportManager reportManager;
    private ViewReportsScreenController viewReports;
    private DatabaseManager databaseManager;
	private UserManager userManager;
    private Scene sourceReportDetailsScene;
    private Scene histReportDataScene;
    private Scene histReportScene;
    private ReportDetailsScreenController sourceReportDetails;

    /**
     * Start the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Called at start of application, initializes appropriate controllers.
     * @param primaryStage the FX stage of the application
     */
    public void start(Stage primaryStage) {
        try {
            this.databaseManager = new DatabaseManager();
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
        this.reportManager = new ReportManager(databaseManager);
        this.userManager = new UserManager(databaseManager);
        initRootLayout(primaryStage);
        User.registerDatabaseManager(databaseManager);
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

    /**
     * Set scene to submit quality reports
     */
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

    /**
     * Set scene to admin-only user list.
     * This function does not check if the current user is admin level privelege.
     * That responsiblity is delegated to the caller.
     */
    public void setUserListScene() {
	    userListScreenController.populateUserList(userManager.getAllUsers());
        setScene(userListScene, "Cleanwater - User List");
    }

    /**
     * Set scene to view purity reports
     */
    public void setViewPurityScene() {
        viewReports.setReportsList(reportManager.getPurityReports());
        setScene(viewReportsScene, "Cleanwater - View Purity Reports");
    }

    /**
     * Sets scene to create historical graph.
     */
    public void setHistReportDataScene() {
        setScene(histReportDataScene, "Cleanwater - Enter data for graph");
    }

    /**
     * Sets scene to view historical graph
     * @param d historical data to view graph of
     */
    public void setHistReportScene(HistoricalData d) {
        histReportController.setData(d);
        histReportController.setReportsList(reportManager.getPurityReports());
        histReportController.setGraph();
        setScene(histReportScene, "Graph of year " + d.getYear());
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
     * @param report report to be set
     */
    public void setReportDetailsScene(Report report) {
        if (report instanceof SourceReport) {
            sourceReportDetails.setReportInfo((SourceReport) report);
            setScene(sourceReportDetailsScene,
                    "Cleanwater - View Individual Source Reports");
        } else {
            System.err.println("report details screen not implemented for "
                    + report.getClass());
        }
    }

    /**
     * Changes active views
     * @param s scene to view
     * @param title window titlebar title
     */
    private void setScene(Scene s, String title) {
        activeScreen.hide();
        activeScreen.setScene(s);
        activeScreen.setTitle(title);
        activeScreen.show();
    }

    /**
     * Gets the active user
     *
     * @return logged in user
     */
    public User getActiveUser() {
        return loggedInUser;
    }

    /**
     * sets the active user, for testing
     * @param user user specified for testing
     */
    public void setActiveUser(User user) {
        loggedInUser = user;
    }

    /**
     * gets active screen, for testing
     * @return active screen
     */
    public Stage getActiveScreen() {
        return activeScreen;
    }

    /**
     * launches test environ, for testing
     */
    public void launchTestEnv() {
        launch();
    }

    /**
     * Initializes views
     * @param mainScreen primary stage of the application
     */
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
            FXMLLoader histReportLoader = new FXMLLoader();
            FXMLLoader qualityReportLoader = new FXMLLoader();
            FXMLLoader reportDetailsLoader = new FXMLLoader();
            FXMLLoader userListLoader = new FXMLLoader();
            loginLoader.setLocation(MainFXApplication
                    .class.getResource("../view/LoginScreen.fxml"));
            mainLoader.setLocation(MainFXApplication
                    .class.getResource("../view/MainScreen.fxml"));
            registerLoader.setLocation(MainFXApplication
                    .class.getResource("../view/RegisterScreen.fxml"));
            userInfoLoader.setLocation(MainFXApplication
                    .class.getResource("../view/UserInfoScreen.fxml"));
            sourceReportLoader.setLocation(MainFXApplication
                    .class.getResource("../view/SourceReportScreen.fxml"));
            viewReportsLoader.setLocation(MainFXApplication
                    .class.getResource("../view/ViewReportsScreen.fxml"));
            mapLoader.setLocation(MainFXApplication
                    .class.getResource("../view/MapScreen.fxml"));
            reportDetailsLoader.setLocation(MainFXApplication
                    .class.getResource("../view/ReportDetailsScreen.fxml"));
            qualityReportLoader.setLocation(MainFXApplication
                    .class.getResource("../view/PurityReportScreen.fxml"));
            histReportDataLoader.setLocation(MainFXApplication
                    .class.getResource("../view/HistReportDataScreen.fxml"));
            histReportLoader.setLocation(MainFXApplication
                    .class.getResource("../view/HistReportScreen.fxml"));
            userListLoader.setLocation(MainFXApplication
                    .class.getResource("../view/UserListScreen.fxml"));
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
            BorderPane histLayout = histReportLoader.load();
            BorderPane userListLayout = userListLoader.load();

            AuthenticationManager authenticationManager =
                    new AuthenticationManager("SHA-1");

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
            histReportScene = new Scene(histLayout);
            userListScene = new Scene(userListLayout);

            // Give the controller access to the main app.
            LoginScreenController controller = loginLoader.getController();
            MainScreenController logout = mainLoader.getController();
            RegisterScreenController register = registerLoader.getController();
            QualityReportController qualityReport = qualityReportLoader
                    .getController();
            HistReportDataController histData = histReportDataLoader
                    .getController();
            userInfoScreenController = userInfoLoader.getController();
            histReportController = histReportLoader.getController();
            SourceReportScreenController sourceReport =
                    sourceReportLoader.getController();
            viewReports = viewReportsLoader.getController();
            mapScreenController = mapLoader.getController();
            sourceReportDetails = reportDetailsLoader.getController();
            userListScreenController = userListLoader.getController();
            
            controller.register(this, authenticationManager);
            logout.registerMainApp(this);
            register.register(this, authenticationManager);
            qualityReport.registerMainApp(this);
            histData.registerMainApp(this);
            userInfoScreenController.registerMainApp(this);
            sourceReport.registerMainApp(this);
            viewReports.registerMainApp(this);
            histReportController.registerMainApp(this);
            mapScreenController.registerMainApp(this);
            sourceReportDetails.registerMainApp(this);
            userListScreenController.registerMainApp(this);

            setLoginScene();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException nse) {
            System.err.println("Could not find the requested hash algorithm."
                    + " Use one your machine supports.");
            nse.printStackTrace();
        }
    }

    /**
     * Notifies that a user is attempting to log in
     *
     * @param token the user's unique identifying information
     * @return true if the user's token is valid, false otherwise
     */
    public boolean notifyLogin(Token token) {
	    loggedInUser = userManager.logInUser(token);
	    return loggedInUser != null;
    }

    /**
     * Notifies that a user has registered
     *
     * @param registered the user to add to the database
     * @return boolean registered or not
     */
    public boolean notifyRegistration(User registered) {
	    return userManager.registerNewUser(registered);
    }

    /**
     * Returns the ReportManager instance
     *
     * @return the ReportManager associated with this instance
     */
    public ReportManager getReportManager() {
        return this.reportManager;
    }
}
