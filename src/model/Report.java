package model;

import java.util.Calendar;
import java.util.Date;

/**
 * A water quality report.
 */
public abstract class Report {
    private final Location location;
    private final int reportNum;
    private final Date creationDatetime;

    /**
     * Creates a new report
     *
     * @param reportNum the number of the report
     * @param location location of water
     */
    public Report(int reportNum, Location location) {
        this(reportNum, location, new Date());
    }
    
    /**
     * Creates a new report
     *
     * @param reportNum the number of the report
     * @param location location of water
     * @param creationDatetime date and time of creation of report
     */
    Report(int reportNum, Location location, Date creationDatetime) {
        this.creationDatetime = creationDatetime;
        this.location = location;
        this.reportNum = reportNum;
    }

    /**
     * Returns the number of the report
     *
     * @return the number of the report
     */
    public int getReportNum() {
        return reportNum;
    }

    /**
     * gets date and time of creation of report
     * @return date and time of creation of report
     */
    public Date getCreationDatetime() {
        return creationDatetime;
    }

    /**
     * determines which of two reports was first created
     * @param other report to compare this to
     * @return true if this predates other.
     */
    public boolean wasReportCreatedFirst(Report other) {
        return this.creationDatetime.getTime()
                < other.creationDatetime.getTime();
    }

    /**
     * converts report to string
     * @return generated string
     */
    public String toString() {
        return String.format("#%d at %s time: %s",
                getReportNum(), getLocation(), getCreationDatetime());
    }

    /**
     * gets location of water
     * @return location of water
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * gets month report was made in
     * @return month report was made in
     */
    public int getReportMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(creationDatetime);
        return cal.get(Calendar.MONTH);
    }

    /**
     * gets year report was made in
     * @return year report was made in
     */
    public int getReportYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(creationDatetime);
        return cal.get(Calendar.YEAR);
    }

    /**
     * gets this as purity report or null if this is not a purity report
     * @return this as purity report or null
     */
    public PurityReport getPurityReport() {
        if (this instanceof PurityReport) {
            return (PurityReport) this;
        } else {
            return null;
        }
    }
}
