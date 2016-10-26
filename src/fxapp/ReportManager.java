package fxapp;

import model.PurityReport;
import model.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Manages reports.
 */
public class ReportManager {
    private List<Report> sourceReports;
    private List<PurityReport> purityReports;
    public ReportManager() {

        sourceReports = new ArrayList<>();
        purityReports = new ArrayList<>();
    }

    /**
     * Adds a report
     * @param report the report to add
     */
    public void addReport(Report report) {
        sourceReports.add(report);
        System.out.println(sourceReports);
    }
    public void addPurityReport(PurityReport report){
        purityReports.add(report);
        System.out.println(purityReports);
    }

    /**
     * Returns all water reports
     * @return stream of all reports
     */
    public Stream<Report> getSourceReports() {
        return sourceReports.stream();
    }
}
