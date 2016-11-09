package model;

public class HistoricalData {
    private double latMin;
    private double latMax;
    private double longMin;
    private double longMax;
    private int year;
    private String contaminantType;

    /**
     * Stores all of the data from a report in class level access variables
     *
     * @param latMin          minimum lattitude for map
     * @param latMax          maximum latitude for map
     * @param longMin         minimum longitude for map
     * @param longMax         maximum longitude for map
     * @param year            year of report
     * @param contaminantType Type of contaminant in the water
     */
    public HistoricalData(double latMin, double latMax, double longMin,
                          double longMax, int year, String contaminantType) {
        this.latMin = latMin;
        this.latMax = latMax;
        this.longMin = longMin;
        this.longMax = longMax;
        this.year = year;
        this.contaminantType = contaminantType;
    }

    /**
     * Retrieves minimum latitude
     * @return minimum latitude
     */
    public double getLatMin() {
        return latMin;
    }

    /**
     * sets minimum latitude
     * @param latMin new minimum latitude
     */
    public void setLatMin(double latMin) {
        this.latMin = latMin;
    }

    /**
     * Retrives max latitude
     * @return max latitude
     */
    public double getLatMax() {
        return latMax;
    }

    /**
     * sets max latitude
     * @param latMax new max lat
     */
    public void setLatMax(double latMax) {
        this.latMax = latMax;
    }

    /**
     * retrieves minimum longitude
     * @return min longitude
     */
    public double getLongMin() {
        return longMin;
    }

    /**
     * sets longitude minmum
     * @param longMin new longitude minimum
     */
    public void setLongMin(double longMin) {
        this.longMin = longMin;
    }

    /**
     * retrieves longitude max
     * @return longitude max
     */
    public double getLongMax() {
        return longMax;
    }

    /**
     * sets longitude max
     * @param longMax new longitude max
     */
    public void setLongMax(double longMax) {
        this.longMax = longMax;
    }

    /**
     * Retrieves year of report
     * @return year year of report
     */
    public int getYear() {
        return year;
    }

    /**
     * sets year of report
     * @param year year of report
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * gets contaminant type of report
     * @return contaminant type of report
     */
    public String getContaminantType() {
        return contaminantType;
    }

    /**
     * sets contaminant type of report
     * @param contaminantType contaminant type of report
     */
    public void setContaminantType(String contaminantType) {
        this.contaminantType = contaminantType;
    }
}

