package model;

import java.util.Date;

/**
 * A water quality report.
 */
public abstract class Report {
    private Location location;
    private int reportNum;
    private Date creationDatetime;

    /**
     * Creates a new report
     * @param reportNum the number of the report
     */
    public Report(int reportNum, Location location) {
        this(reportNum, location, new Date()); //Sets date to moment of allocation (ms precision)
    }
    
    public Report(int reportNum, Location location, Date creationDatetime) {
        this.creationDatetime = creationDatetime;
        this.location = location;
        this.reportNum = reportNum;
    }

    /**
     * Returns the number of the report
     * @return the number of the report
     */
    public int getReportNum() { return reportNum; }
    
    public Date getCreationDatetime() { return creationDatetime; }
    
    public boolean wasReportCreatedFirst(Report other) {
        return this.creationDatetime.getTime() < other.creationDatetime.getTime();
    }

    public String toString() {
        return String.format("#%d at %s", getReportNum(), getLocation());
    }

    public Location getLocation() {
        return this.location;
    }
}
