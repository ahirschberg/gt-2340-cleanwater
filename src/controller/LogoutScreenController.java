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

public class LogoutScreenController {
    private MainFXApplication main;
    
    @FXML
    public void initialize() {
    }

    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }
    
    public void show() {
        main.setLogoutScene();
    }

   @FXML
   public void onLogoutPressed() {
       main.setLoginScene();
   }
}
