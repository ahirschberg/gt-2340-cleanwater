package controller;


import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HistReportDataController {
    private MainFXApplication main;

    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    @FXML
    public void initialize() {}

    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    public void onSubmitSelected() {}

    @FXML
    public void onCancelSelected() {
        main.setMainScene();
    }
}
