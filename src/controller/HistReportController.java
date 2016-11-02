package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import model.HistoricalData;
import model.PermissionLevel;
import model.User;

public class HistReportController {
    private MainFXApplication main;
    private HistoricalData historicalData;

    @FXML
    private LineChart historicalChart;

    /**
     * Registers the main application with this controller
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Sets the scene with the current and updated data
     * @param historicalData data to set the scene with
     */
    public void setData(HistoricalData historicalData) {
        this.historicalData = historicalData;
    }

    /**
     * Shows data of the report if authorization level is correct
     */
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
        historicalChart.getXAxis().setLabel("Month");
    }
}
