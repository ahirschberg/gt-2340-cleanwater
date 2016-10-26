package model;

/**
 * A water quality report.
 */
public abstract class Report {
    private Location location;
    private static String REPORT_TYPE;
    private int reportNum;

    /**
     * Creates a new report
     * @param reportNum the number of the report
     */
    public Report(int reportNum, Location location) {
        this.location = location;
        this.reportNum = reportNum;
    }

    public static void setReportType(String reportType) {
        REPORT_TYPE = reportType;
    }

    /**
     * Returns the number of the report
     * @return the number of the report
     */
    public int getReportNum() { return reportNum; }


    public String toString() {
        return String.format("%s Report #%d at %s", REPORT_TYPE, getReportNum(), getLocation());
    }

    public Location getLocation() {
        return this.location;
    }
}
