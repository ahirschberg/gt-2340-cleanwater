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

    /**
     * Initializes report manager
     * @param db database manager to initialize reports with.
     */
    public ReportManager(DatabaseManager db) {
        try {
            sourceReports = db.getPersistence(SourceReport.class).retrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
            sourceReports = new LinkedList<>();
        }
        try {
            purityReports = db.getPersistence(PurityReport.class).retrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
            purityReports = new ArrayList<>();
        }
        this.db = db;
    }

    /**
     * Adds a report
     *
     * @param report the report to add
     */
    public void addSourceReport(SourceReport report) {
        try {
            db.getPersistence(SourceReport.class).store(report);
            sourceReports.add(report);
        } catch (SQLException e) {
            System.err.println("Error: could not store report in database: "
                    + report);
            e.printStackTrace();
        }
    }

    /**
     * Adds a purity report to the manager
     * @param report report to add
     */
    public void addPurityReport(PurityReport report) {
        try {
            db.getPersistence(PurityReport.class).store(report);
            purityReports.add(report);
        } catch (SQLException e) {
            System.err.println("Error: could not store report in database: "
                    + report);
            e.printStackTrace();
        }
    }

    /**
     * Returns all water reports
     *
     * @return stream of all reports
     */
    public Stream<Report> getAllReports() {
        return Stream.concat(sourceReports.stream(), purityReports.stream());
    }

    /**
     * Retrieves the stored source reports.
     * @return the source reports
     */
    public Stream<? extends Report> getSourceReports() {
        return sourceReports.stream();
    }
    
    /**
     * Retrieves the stored purity reports.
     * @return the purity reports
     */
    public Stream<? extends Report> getPurityReports() {
        return purityReports.stream();
    }
}
