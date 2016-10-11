package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.*;

/**
 * Created by hankg_000 on 10/10/2016.
 */
public class SourceReportScreenController {
    private MainFXApplication main;
    private int reportNum = 1;

    @FXML
    private TextField waterLocationField;

    @FXML
    private ComboBox<String> waterTypeBox;

    @FXML
    private ComboBox<String> waterConditionBox;

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
        String waterType = waterTypeBox.getSelectionModel().getSelectedItem();
        String waterCondition = waterConditionBox.getSelectionModel().getSelectedItem();
        if (location.isEmpty()) {
            errorMessage.setText("Location Field was left empty");
        } else if (waterType.isEmpty()) {
            errorMessage.setText("Please enter a water type");
        } else if (waterCondition.isEmpty()) {
            errorMessage.setText("Please enter a water condition");
        } else {
            main.getReportManager().addReport(new Report(location, waterType, waterCondition, reportNum));
            reportNum++;
            main.setLogoutScene();
        }
    }

    /**
     * Take the user back to the home page
     */
    @FXML
    public void onHomeSelected() {
        main.setLogoutScene();
    }
}
