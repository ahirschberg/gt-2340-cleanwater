package model;

public class PurityReport {
    private double latitude;
    private double longitude;
    private int reportNum;
    private double virusPPM;
    private double contaminantPPM;
    private String condition;

    public PurityReport(double latitude, double longitude, double virusPPM, double contaminantPPM, String condition, int reportNum) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.condition = condition;
        this.reportNum = reportNum;
    }
    /**
     * Returns the location of the report
     * @return location, as a string
     */
    public String getLocation() {
        return latitude + "," + longitude;
    }
    public int getReportNum() { return reportNum; }
    /**
     * Gets the latitude, as a double
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude, as a double
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }
    public double getVirusPPM() { return virusPPM; }
    public double getContaminantPPM() { return contaminantPPM; }
    public String toString() {
        return String.format("Water Report #%d at %s: %s, %s", getReportNum(), getLocation(), virusPPM, contaminantPPM);
    }
}
