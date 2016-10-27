package fxapp;

import model.PurityReport;
import model.Report;
import model.SourceReport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Manages reports.
 */
public class ReportManager {
    private List<SourceReport> sourceReports;
    private List<PurityReport> purityReports;
    public ReportManager() {
        sourceReports = new ArrayList<>();
        purityReports = new ArrayList<>();
    }

    /**
     * Adds a report
     * @param report the report to add
     */
    public void addReport(SourceReport report) {
        sourceReports.add(report);
    }
    public void addPurityReport(PurityReport report){
        purityReports.add(report);
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
