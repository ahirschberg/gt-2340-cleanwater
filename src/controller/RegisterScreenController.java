package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Admin;
import model.Manager;
import model.Token;
import model.User;
import model.Worker;

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
    private ComboBox permissionBox;

    @FXML
    public void initialize() {
        permissionBox.getItems().addAll("User", "Worker", "Manager", "Admin");
    }
    
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }
    
    @FXML
    public void onRegisterSelected() {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	String confirmPassword = confirmPasswordField.getText();
    	if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
    		errorMessage.setText("Required field left blank.");
    	} else if (!password.equals(confirmPassword)) {
    		errorMessage.setText("Password fields do not match.");
    	} else {
    	    //Create account with appropriate permissions
    	    String perms = (String) permissionBox.getSelectionModel().getSelectedItem();
    	    User newUser;
    	    if (perms.equals("User")) {
    	        newUser = new User(username);
    	    } else if (perms.equals("Worker")) {
    	        newUser = new Worker(username);
    	    } else if (perms.equals("Manager")) {
                newUser = new Manager(username);
            } else {
                newUser = new Admin(username);
            }
    	    
    	    main.notifyRegistration(newUser, Token.fromCredentials(username, password));
            main.setLoginScene();
    	}
    }

    @FXML
    public void onLoginSelected() {
    	main.setLoginScene();
    }

}
