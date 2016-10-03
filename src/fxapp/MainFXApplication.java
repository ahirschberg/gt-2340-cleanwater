package fxapp;

import controller.LoginScreenController;
import controller.LogoutScreenController;
import controller.RegisterScreenController;
import controller.UserInfoScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Token;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main controller for the JavaFX application
 */
public class MainFXApplication extends Application  {
    private UserInfoScreenController userInfoScreenController;
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;
    private Stage activeScreen;
    private Scene logoutScene;
    private Scene loginScene;
    private Map<Token, User> registeredUsers;
    private Scene registerScene;
    private Scene userInfoScene;

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
        this.registeredUsers = new HashMap<>();
        initRootLayout(primaryStage);
    }

    /**
     * Set scene to logout controls
     */
    public void setLogoutScene() {
        setScene(logoutScene, "Cleanwater - Water Reporting System");
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
            loginLoader.setLocation(MainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            logoutLoader.setLocation(MainFXApplication.class.getResource("../view/LogoutScreen.fxml"));
            registerLoader.setLocation(MainFXApplication.class.getResource("../view/RegisterScreen.fxml"));
            userInfoLoader.setLocation(MainFXApplication.class.getResource("../view/UserInfoScreen.fxml"));
            BorderPane loginLayout = loginLoader.load();
            BorderPane logoutLayout = logoutLoader.load();
            BorderPane registerLayout = registerLoader.load();
            BorderPane userInfoLayout = userInfoLoader.load();

            // Show the scene containing the root layout.
            loginScene = new Scene(loginLayout);
            logoutScene = new Scene(logoutLayout);
            registerScene = new Scene(registerLayout);
            userInfoScene = new Scene(userInfoLayout);

            // Give the controller access to the main app.
            LoginScreenController controller = loginLoader.getController();
            LogoutScreenController logout = logoutLoader.getController();
            RegisterScreenController register = registerLoader.getController();
            userInfoScreenController = userInfoLoader.getController();
            controller.registerMainApp(this);
            logout.registerMainApp(this);
            register.registerMainApp(this);
            userInfoScreenController.registerMainApp(this);

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
        loggedInUser = registeredUsers.get(token);
        return loggedInUser != null;
    }

    /**
     * Notifies that a user has registered
     * @param registered the user to add to the database
     * @param token the user's unique identifying information
     */
    public void notifyRegistration(User registered, Token token) {
        registeredUsers.put(token, registered);
        System.out.println(registeredUsers);
    }


}
