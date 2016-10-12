package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.User;

/**
 * Handles the user profile screen of the app.
 */
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
    private TextField orgField;

    @FXML
    public void initialize() {
    }

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Initialize the fields to the data stored in the user's record
     */
    public void initFields() {
        User active = main.getActiveUser();
        nameField.setText(active.getName());
        emailField.setText(active.getEmail());
        streetField.setText(active.getStreet());
        cityField.setText(active.getCity());
        stateField.setText(active.getState());
        countryField.setText(active.getCountry());
        orgField.setText(active.getOrg());
    }

    /**
     * Called when the user selects the save button
     */
    @FXML
    public void onProfileSaved() {
        main.getActiveUser().setProfile(
                nameField.getText(),
                emailField.getText(),
                streetField.getText(),
                cityField.getText(),
                stateField.getText(),
                countryField.getText(),
                orgField.getText());
                main.setMainScene();
    }

    /**
     * Called when the user cancels their profile changes
     */
    @FXML
    public void onCancel() {
        main.setMainScene();
    }

}