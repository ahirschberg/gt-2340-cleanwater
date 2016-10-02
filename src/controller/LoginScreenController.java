package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Token;

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
        if (main.notifyLogin(Token.fromCredentials(usernameField.getText(), passwordField.getText()))) {
            main.setLogoutScene();
        } else {
            main.setLogoutScene(); //TODO: track registered users
            message.setText("Username or password incorrect.");
        }
    }

    @FXML
    public void onRegisterPressed() {
    	main.setRegisterScene();
    }


}
