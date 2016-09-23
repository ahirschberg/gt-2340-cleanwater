package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class LoginScreenController {
    private MainFXApplication main;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text message;

    @FXML
    public void initialize() {
    }
    
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    @FXML
    public void onLoginSelected() {
        byte[] token = null;
        if (usernameField.getText().equals("user") && passwordField.getText().equals("pass")) {
             token = generateToken(usernameField.getText(), passwordField.getText());
             main.setLogoutScene();
        } else {
            message.setText("Username or password incorrect.");
        }
        main.notifyLoginAttempt(token);
    }

    // Simulate a secure login system
    private byte[] generateToken(String user, String password) {
        byte[] token = null;
        try {
            token = java.security.MessageDigest.getInstance("SHA-1").digest((user + password).getBytes());
        } catch (NoSuchAlgorithmException e) {
            MainFXApplication.LOGGER.log(Level.SEVERE, "No algorithm detected for token.");
            e.printStackTrace();
        }
        return token;
    }
}
