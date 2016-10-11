package fxapp;

import model.Report;
import model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReportManager {
    private List<Report> reports;
    public ReportManager() {
        reports = new ArrayList<>();
    }

    public void addReport(Report report) {
        reports.add(report);
        System.out.println(reports);
    }

    public Iterator<Report> getReports() {
        return reports.iterator();
    }
}