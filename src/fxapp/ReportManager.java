package fxapp;

import model.PurityReport;
import model.Report;
import model.SourceReport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Manages reports.
 */
public class ReportManager {
    private List<SourceReport> sourceReports;
    private List<PurityReport> purityReports;
    private DatabaseManager db;
    public ReportManager(DatabaseManager db) {
        try {
            sourceReports = db.<SourceReport>getPersist(SourceReport.class).retrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
            sourceReports = new LinkedList<>();
        }
        try {
            purityReports = db.<PurityReport>getPersist(PurityReport.class).retrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
            purityReports = new ArrayList<>();
        }
        this.db = db;
    }

    /**
     * Adds a report
     * @param report the report to add
     */
    public void addSourceReport(SourceReport report) {
        try {
            db.getPersist(SourceReport.class).store(report);
            sourceReports.add(report);
        } catch (SQLException e) {
            System.err.println("Error: could not store report in database: " + report);
            e.printStackTrace();
        }
    }
    public void addPurityReport(PurityReport report) {
        try {
            db.getPersist(PurityReport.class).store(report);
            purityReports.add(report);
        } catch (SQLException e) {
            System.err.println("Error: could not store report in database: " + report);
            e.printStackTrace();
        }
    }

    /**
     * Returns all water reports
     * @return stream of all reports
     */
    public Stream<Report> getAllReports() {
        return Stream.concat(sourceReports.stream(), purityReports.stream());
    }
    
    public Stream<? extends Report> getSourceReports() {
        return sourceReports.stream();
    }
    
    public Stream<? extends Report> getPurityReports() {
        return purityReports.stream();
    }
}
