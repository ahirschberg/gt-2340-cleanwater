package model;

/**
 * A water quality report.
 */
public class Report {
    private double latitude;
    private double longitude;
    private String waterType;
    private String waterCondition;
    private int reportNum;

    /**
     * Creates a new report
     * @param waterType the type of water source
     * @param waterCondition the quality of water source
     * @param reportNum the number of the report
     */
    public Report(double latitude, double longitude, String waterType, String waterCondition, int reportNum) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.reportNum = reportNum;
    }

    /**
     * Returns the location of the report
     * @return location, as a string
     */
    public String getLocation() {
        return latitude + "," + longitude;
    }

    /**
     * Returns the water type of the report
     * @return water type, as a string
     */
    public String getWaterType() {
        return waterType;
    }

    /**
     * Returns the water condition of the report
     * @return condition, as a string
     */
    public String getWaterCondition() {
        return waterCondition;
    }

    /**
     * Returns the number of the report
     * @return the number of the report
     */
    public int getReportNum() { return reportNum; }

    public String toString() {
        return String.format("Water Report #%d at %s: %s, %s", getReportNum(), getLocation(), getWaterType(),
                getWaterCondition());
    }
}
