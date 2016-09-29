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

public class RegisterScreenController {
    private MainFXApplication main;

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {
    }
    
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }
    
    @FXML
    public void onRegisterSelected() {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	String confirmPassword = confirmPasswordField.getText();
    }

    @FXML
    public void onLoginSelected() {
    	main.setLoginScene();
    }

}
