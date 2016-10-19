package fxapp;

import model.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Manages reports.
 */
public class ReportManager {
    private List<Report> reports;
    public ReportManager() {
        reports = new ArrayList<>();
    }

    /**
     * Adds a report
     * @param report the report to add
     */
    public void addReport(Report report) {
        reports.add(report);
        System.out.println(reports);
    }

    /**
     * Returns all water reports
     * @return stream of all reports
     */
    public Stream<Report> getReports() {
        return reports.stream();
    }
}
