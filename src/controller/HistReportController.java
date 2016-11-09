package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.HistoricalData;
import model.PermissionLevel;
import model.PurityReport;
import model.Report;
import model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class HistReportController {
    private MainFXApplication main;
    private HistoricalData historicalData;
    private ArrayList<Queue<PurityReport>> reportsList = new ArrayList<>(12);
    private double[] ppms = new double[12];

    @FXML
    private LineChart<String, Number> historicalChart;

    /**
     * Registers the main application with this controller
     *
     * @param main the main application
     */
    public void registerMainApp(MainFXApplication main) {
        this.main = main;
    }

    /**
     * Sets the scene with the current and updated data
     *
     * @param historicalData data to set the scene with
     */
    public void setData(HistoricalData historicalData) {
        this.historicalData = historicalData;
    }

    /**
     * gets stored ppm's
     * @return historical parts per million's
     */
    public double[] getPPM() {
        return ppms;
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

    /**
     * Called automatically when view is initialized
     */
    @FXML
    public void initialize() {
    }

    /**
     * sets report list to a filtered version of the given report list
     * based on historical data
     * @param reports the reports list to be filtered.
     */
    public void setReportsList(Stream<? extends Report> reports) {
        reportsList.clear();
        for (int i = 0; i < 12; i++) {
            Queue<PurityReport> queue = new LinkedList<>();
            reportsList.add(queue);
        }
        reports = reports.filter(Report ->
                Report.getLocation().getLatitude() >= historicalData.getLatMin()
                && Report.getLocation().getLatitude()
                        <= historicalData.getLatMax()
                && Report.getLocation().getLongitude()
                        >= historicalData.getLongMin()
                && Report.getLocation().getLongitude()
                        <= historicalData.getLongMax());
        reports = reports.filter(Report ->
                Report.getReportYear() == historicalData.getYear());
        reports.forEach(Report ->
                reportsList.get(Report.getReportMonth())
                        .add(Report.getPurityReport()));
    }

    /**
     * Creates graph from current report list.
     */
    public void setGraph() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (historicalChart != null) {
            historicalChart.getXAxis().setLabel("Month");
        }
        if (historicalData.getContaminantType().equals("Virus PPM")) {
            series.setName("Virus PPM from latitude "
                    + historicalData.getLatMin()
                    + "-" + historicalData.getLatMax()
                    + " and longitude " + historicalData.getLongMin()
                    + "-" + historicalData.getLongMax());
            if (historicalChart != null) {
                historicalChart.getYAxis().setLabel("PPM");
            }
            int counter = 0;
            for (Queue<PurityReport> queue : reportsList) {
                double average = 0;
                int numElements = 0;
                while (queue.peek() != null) {
                    numElements++;
                    PurityReport p = queue.poll();
                    average = average + p.getVirusPPM();
                }
                if (numElements != 0) {
                    average = average / numElements;
                    ppms[counter] = average;
                } else {
                    ppms[counter] = -1.0;
                }
                counter++;
            }
            for (int i = 0; i < 12; i++) {
                if (ppms[i] != -1.0) {
                    String str = Integer.toString(i + 1);
                    series.getData().add(new XYChart.Data<>(str, ppms[i]));
                }
            }
            if (historicalChart != null) {
                historicalChart.getData().add(series);
            }
        } else {
            series.setName("Contaminant PPM from latitude "
                    + historicalData.getLatMin()
                    + "-" + historicalData.getLatMax()
                    + " and longitude " + historicalData.getLongMin()
                    + "-" + historicalData.getLongMax());
            if (historicalChart != null) {
                historicalChart.getYAxis().setLabel("PPM");
            }
            int counter = 0;
            for (Queue<PurityReport> queue : reportsList) {
                double average = 0;
                int numElements = 0;
                while (queue.peek() != null) {
                    numElements++;
                    PurityReport p = queue.poll();
                    average = average + p.getContaminantPPM();
                }
                if (numElements != 0) {
                    average = average / numElements;
                    ppms[counter] = average;
                } else {
                    ppms[counter] = -1.0;
                }
                counter++;
            }
            for (int i = 0; i < 12; i++) {
                if (ppms[i] != -1.0) {
                    String str = Integer.toString(i + 1);
                    series.getData().add(new XYChart.Data<>(str, ppms[i]));
                }
            }
            if (historicalChart != null) {
                historicalChart.getData().add(series);
            }
        }
    }
}
