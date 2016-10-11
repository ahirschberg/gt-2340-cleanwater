package model;

public class Report {
    String location;
    String waterType;
    String waterCondition;

    public Report(String location, String waterType, String waterCondition) {
        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
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

    public String toString() {
        return String.format("<Water Report at %s: %s, %s", getLocation(), getWaterType(), getWaterCondition());
    }
}
