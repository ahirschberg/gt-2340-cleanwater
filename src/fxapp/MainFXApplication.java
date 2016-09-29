package fxapp;

import controller.LoginScreenController;
import controller.LogoutScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Token;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jster on 9/20/2016.
 */
public class MainFXApplication extends Application  {
    private BorderPane loginLayout;
    private BorderPane logoutLayout;
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;
    private Stage activeScreen;
    private Scene logoutScene;
    private Scene loginScene;
    private HashMap<Token, User> registeredUsers;

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    public void start(Stage primaryStage) {
        initRootLayout(primaryStage);
    }
    
    public void setLogoutScene() {
        setScene(logoutScene);
        activeScreen.setTitle("Cleanwater");
    }
    
    public void setLoginScene() {
        setScene(loginScene);
        activeScreen.setTitle("Login");
    }
    
    private void setScene(Scene s) {
        activeScreen.hide();
        activeScreen.setScene(s);
        activeScreen.show();
    }

    private void initRootLayout(Stage mainScreen) {
        activeScreen = mainScreen;
        try {
            // Load root layout from fxml file.
            FXMLLoader loginLoader = new FXMLLoader();
            FXMLLoader logoutLoader = new FXMLLoader();
            loginLoader.setLocation(MainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            logoutLoader.setLocation(MainFXApplication.class.getResource("../view/LogoutScreen.fxml"));
            loginLayout = loginLoader.load();
            logoutLayout = logoutLoader.load();

            // Give the controller access to the main app.
            LoginScreenController controller = loginLoader.getController();
            LogoutScreenController logout = logoutLoader.getController();
            controller.registerMainApp(this);
            logout.registerMainApp(this);

            // Show the scene containing the root layout.
            loginScene = new Scene(loginLayout);
            logoutScene = new Scene(logoutLayout);
            
            setLoginScene();
        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen!!");
            e.printStackTrace();
        }
    }

    public void notifyLoginAttempt(Token token) {
        if (token != null) {
            loggedInUser = registeredUsers.get(token);
        }
    }
}
