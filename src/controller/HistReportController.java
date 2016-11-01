package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import model.HistoricalData;
import model.PermissionLevel;
import model.User;

public class HistReportController {
    private MainFXApplication main;
    private HistoricalData historicalData;

    @FXML
    private LineChart historicalChart;

    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    public void setData(HistoricalData historicalData) {
        this.historicalData = historicalData;
    }

    @FXML
    public void onCancelSelected() {
        User currentUser = main.getActiveUser();
        PermissionLevel permissionLevel = currentUser.getPermissionLevel();
        if (permissionLevel == PermissionLevel.MANAGER) {
            main.setHistReportDataScene();
        }
    }
    @FXML
    public void initialize() {
    }
}
