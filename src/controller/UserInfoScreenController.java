package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Token;
import model.User;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class UserInfoScreenController {
    private MainFXApplication main;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField orgField;

    @FXML
    public void initialize() {
    }
    
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }
    
    @FXML
    public void onProfileSaved() {
        
    }

    @FXML
    public void onCancel() {
        main.setLogoutScene();
    }

}
