package controller;

import fxapp.AuthenticationManager;
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
    private AuthenticationManager authenticationManager;

    @FXML
    private final PasswordField passwordField;

    @FXML
    private final TextField usernameField;

    @FXML
    private final Text message;

    /**
     * Gives the controller a reference to the main app
     *
     * @param main the main app
     */
    public void register(MainFXApplication main, AuthenticationManager authenticationManager) {
        this.main = main;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Called when the user selects the login button
     * Notifies the main app of the login attempt.
     */
    @FXML
    public void onLoginSelected() {
        if (main.notifyLogin(authenticationManager.tokenFromCredentials(usernameField
                .getText(), passwordField.getText()))) {
            main.setMainScene();
        } else {
            message.setText("Username or password incorrect.");
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
