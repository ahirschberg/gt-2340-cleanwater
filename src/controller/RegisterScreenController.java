package controller;

import fxapp.AuthenticationManager;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.PermissionLevel;
import model.Token;
import model.User;

import java.security.NoSuchAlgorithmException;

/**
 * Handles the registration screen of the app.
 */
public class RegisterScreenController {
    private MainFXApplication main;
    private AuthenticationManager authenticationManager;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text errorMessage;

    @FXML
    private ComboBox<String> permissionBox;

    /**
     * Called automatically on initialization.
     * sets up combo box.
     */
    @FXML
    public void initialize() {
        permissionBox.getItems().addAll("User", "Worker", "Manager", "Admin");
    }

    /**
     * Registers the main application with this controller
     *
     * @param main the main application
     */
    public void register(MainFXApplication main, AuthenticationManager authenticationManager) {
        this.main = main;
        this.authenticationManager = authenticationManager;
    }

    /**
     * When the user attempts to register, validate their
     * input and notify the main
     * app of the registration
     */
    @FXML
    public void onRegisterSelected() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {
            errorMessage.setText("Required field left blank.");
        } else if (!password.equals(confirmPassword)) {
            errorMessage.setText("Password fields do not match.");
        } else if (permissionBox.getSelectionModel().isEmpty()) {
            errorMessage.setText("Choose an access level");
        } else {
            //Create account with appropriate permissions
            PermissionLevel pl = PermissionLevel.values()
                    [permissionBox.getSelectionModel().getSelectedIndex()];
            User newUser = new User(username,
                    authenticationManager.tokenFromCredentials(username, password), pl);

            if (main.notifyRegistration(newUser)) {
                main.setLoginScene();
            } else {
                errorMessage.setText("User already exists.");
            }
        }
    }

    /**
     * When the user selects the login button, take them back
     * to the login screen.
     */
    @FXML
    public void onLoginSelected() {
        main.setLoginScene();
    }

}
