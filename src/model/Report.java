package model;

/**
 * A water quality report.
 */
public abstract class Report {
    private Location location;
    private int reportNum;

    /**
     * Creates a new report
     * @param reportNum the number of the report
     */
    public Report(int reportNum, Location location) {
        this.location = location;
        this.reportNum = reportNum;
    }

    /**
     * Returns the number of the report
     * @return the number of the report
     */
    public int getReportNum() { return reportNum; }


    public String toString() {
        return String.format("#%d at %s", getReportNum(), getLocation());
    }

    public Location getLocation() {
        return this.location;
    }
}
