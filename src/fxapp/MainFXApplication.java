package fxapp;

import controller.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jster on 9/20/2016.
 */
public class MainFXApplication extends Application  {
    private BorderPane rootLayout;
    public static final Logger LOGGER = Logger.getLogger("MainFXApplication");
    private User loggedInUser;

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    public void start(Stage primaryStage) {
        initRootLayout(primaryStage);
    }

    private void initRootLayout(Stage mainScreen) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            LoginScreenController controller = loader.getController();
            controller.registerMainApp(this);

            // Set the Main App title
            mainScreen.setTitle("Login");

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();


        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for LoginScreen!!");
            e.printStackTrace();
        }
    }

    public void notifyLoginAttempt(byte[] token) {
        if (token != null) {
            loggedInUser = new User(token);
        }
    }
}
