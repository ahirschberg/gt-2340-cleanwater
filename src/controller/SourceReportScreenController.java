package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Admin;
import model.Manager;
import model.Token;
import model.User;
import model.Worker;

/**
 * Created by hankg_000 on 10/10/2016.
 */
public class SourceReportScreenController {
    private MainFXApplication main;

    @FXML
    private TextField waterLocationField;

    @FXML
    private ComboBox waterTypeBox;

    @FXML
    private ComboBox waterConditionBox;

    @FXML
    private Text errorMessage;

    @FXML
    public void initialize() {
        waterTypeBox.getItems().addAll("Bottled", "Well", "Stream", "Lake", "Spring", "Other");
        waterConditionBox.getItems().addAll("Waste", "Treatable-Clear", "Treatable-Muddy", "Potable");
    }

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    @FXML
    public void onSubmitSelected() {
        String location = waterLocationField.getText();
        if (location.isEmpty()) {
            errorMessage.setText("Location Field was left empty");
        } else if (waterTypeBox.getSelectionModel().isEmpty()) {
            errorMessage.setText("Please enter a water type");
        } else if (waterConditionBox.getSelectionModel().isEmpty()) {
            errorMessage.setText("Please enter a water condition");
        } else {
            //missing report submission functionality
            main.setLogoutScene();
        }
    }

    /**
     * When the user selects the login button, take them back
     * to the login screen.
     */
    @FXML
    public void onHomeSelected() {
        main.setLogoutScene();
    }
}
