package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;
    private Stage activeScreen;
    private Scene mainScene;
    private Scene loginScene;
    private Scene registerScene;
    private Scene userInfoScene;
    private Scene viewReportsScene;
    private Scene sourceReportScene;
    private ReportManager reportManager;
    private  ViewReportsScreenController viewReports;
    private DatabaseManager databaseManager;

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
        this.reportManager = new ReportManager();
        initRootLayout(primaryStage);
        try {
            this.databaseManager = new DatabaseManager();
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        }
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
     * Set scene to source report controls
     */
    public void setSourceReportScene() {
        setScene(sourceReportScene, "Cleanwater - Submit Source Report");
    }

    /**
     * Set scene to report view
     */
    public void setViewReportsScene() {
        viewReports.setReportsList();
        setScene(viewReportsScene, "Cleanwater - View Source Reports");
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
            FXMLLoader logoutLoader = new FXMLLoader();
            FXMLLoader registerLoader = new FXMLLoader();
            FXMLLoader userInfoLoader = new FXMLLoader();
            FXMLLoader sourceReportLoader = new FXMLLoader();
            FXMLLoader viewReportsLoader = new FXMLLoader();
            loginLoader.setLocation(MainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            logoutLoader.setLocation(MainFXApplication.class.getResource("../view/LogoutScreen.fxml"));
            registerLoader.setLocation(MainFXApplication.class.getResource("../view/RegisterScreen.fxml"));
            userInfoLoader.setLocation(MainFXApplication.class.getResource("../view/UserInfoScreen.fxml"));
            sourceReportLoader.setLocation(MainFXApplication.class.getResource("../view/SourceReportScreen.fxml"));
            viewReportsLoader.setLocation(MainFXApplication.class.getResource("../view/ViewReportsScreen.fxml"));
            BorderPane loginLayout = loginLoader.load();
            BorderPane logoutLayout = logoutLoader.load();
            BorderPane registerLayout = registerLoader.load();
            BorderPane userInfoLayout = userInfoLoader.load();
            BorderPane sourceReportLayout = sourceReportLoader.load();
            BorderPane viewReportsLayout = viewReportsLoader.load();

            // Show the scene containing the root layout.
            loginScene = new Scene(loginLayout);
            mainScene = new Scene(logoutLayout);
            registerScene = new Scene(registerLayout);
            userInfoScene = new Scene(userInfoLayout);
            sourceReportScene = new Scene(sourceReportLayout);
            viewReportsScene = new Scene(viewReportsLayout);

            // Give the controller access to the main app.
            LoginScreenController controller = loginLoader.getController();
            MainScreenController logout = logoutLoader.getController();
            RegisterScreenController register = registerLoader.getController();
            userInfoScreenController = userInfoLoader.getController();
            SourceReportScreenController sourceReport = sourceReportLoader.getController();
           viewReports = viewReportsLoader.getController();
            controller.registerMainApp(this);
            logout.registerMainApp(this);
            register.registerMainApp(this);
            userInfoScreenController.registerMainApp(this);
            sourceReport.registerMainApp(this);
            viewReports.registerMainApp(this);


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
            loggedInUser = databaseManager.getUser(token);
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
        return databaseManager.storeUser(registered);
    }

    /**
     * Returns the ReportManager instance
     * @return the ReportManager associated with this instance
     */
    public ReportManager getReportManager() {
        return this.reportManager;
    }
}
