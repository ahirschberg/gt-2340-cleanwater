package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Token;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

/**
 * Handles the login screen.
 */
public class LoginScreenController {
    private MainFXApplication main;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text message;

    /**
     * Gives the controller a reference to the main app
     * @param main the main app
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Called when the user selects the login button
     * Notifies the main app of the login attempt.
     */
    @FXML
    public void onLoginSelected() {
        try {
            if (main.notifyLogin(Token.fromCredentials(usernameField.getText(), passwordField.getText()))) {
                main.setMainScene();
            } else {
                message.setText("Username or password incorrect.");
            }
        } catch (NoSuchAlgorithmException e) {
            MainFXApplication.LOGGER.log(Level.SEVERE, "No algorithm detected for token.");
            e.printStackTrace();
            message.setText("ERROR: Cannot hash password. See console for details.");
        }
    }

    /**
     * Called when the user presses the register button
     * Switches to the new user registration UI
     */
    @FXML
    public void onRegisterPressed() {
        main.setRegisterScene();
    }


}
