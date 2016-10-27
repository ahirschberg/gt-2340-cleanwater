package model;

/**
 * Created by alex on 10/26/16.
 */
public class SourceReport extends Report {

    private String waterType;
    private String waterCondition;
    private static int sourceReportNum = 0;

    /**
     * Creates a new Source Report
     * @param location the location the report refers to
     * @param waterType the type of water source
     * @param waterCondition the quality of water source
     */
    public SourceReport(Location location, String waterType, String waterCondition) {
        super(++sourceReportNum, location);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
    }

    /**
     * Returns the water type of the report
     * @return water type, as a string
     */
    public String getWaterType() {
        return waterType;
    }

    public String toString() {
        return String.format("Source Report %s %s, %s", super.toString(), getWaterType(), getWaterCondition());
    }

    /**
     * Returns the water condition of the report
     * @return condition, as a string
     */
    public String getWaterCondition() {
        return waterCondition;
    }
}
