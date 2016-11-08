package model;

import java.util.Date;

public class SourceReport extends Report {

    private String waterType;
    private String waterCondition;
    private static int sourceReportNum = 0;

    /**
     * Creates a new Source Report
     *
     * @param location       the location the report refers to
     * @param waterType      the type of water source
     * @param waterCondition the quality of water source
     */
    public SourceReport(Location location, String waterType,
                        String waterCondition) {
        this(++sourceReportNum, location, waterType, waterCondition);
    }

    public SourceReport(int num, Location location,
                        String waterType, String waterCondition) {
        this(num, location, waterType, waterCondition, new Date());
    }

    public SourceReport(int num, Location location, String waterType,
                        String waterCondition, Date creationDatetime) {
        super(num, location, creationDatetime);
        this.waterType = waterType;
        this.waterCondition = waterCondition;

        sourceReportNum = Math.max(num, sourceReportNum);
    }

    /**
     * Returns the water type of the report
     *
     * @return water type, as a string
     */
    public String getWaterType() {
        return waterType;
    }

    public String toString() {
        return String.format("%s Source Report %s %s, %s",
                getCreationDatetime().toString(),
                super.toString(), getWaterType(), getWaterCondition());
    }

    /**
     * Returns the water condition of the report
     *
     * @return condition, as a string
     */
    public String getWaterCondition() {
        return waterCondition;
    }
}
