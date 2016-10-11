package model;

public class Report {
    String location;
    String waterType;
    String waterCondition;
    int reportNum;

    public Report(String location, String waterType, String waterCondition, int reportNum) {
        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.reportNum = reportNum;
    }

    public String getLocation() {
        return location;
    }

    public String getWaterType() {
        return waterType;
    }

    public String getWaterCondition() {
        return waterCondition;
    }

    public int getReportNum() { return reportNum; }

    public String toString() {
        return String.format("<Water Report at %s: %s, %s", getLocation(), getWaterType(),
                getWaterCondition(), getReportNum());
    }
}
