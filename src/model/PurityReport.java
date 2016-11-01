package model;

import java.util.Date;

public class PurityReport extends Report{
    private double virusPPM;
    private double contaminantPPM;
    private String waterCondition;
    private static int purityReportNum = 0;

    /**
     * Creates a Purity Report
     * @param location
     * @param virusPPM
     * @param contaminantPPM
     * @param waterCondition
     */
    public PurityReport(Location location,
                        double virusPPM, double contaminantPPM, String waterCondition) {
        this(++purityReportNum, location, virusPPM, contaminantPPM, waterCondition);
    }
    public PurityReport(int num, Location location, double virusPPM, double contaminantPPM, String waterCondition) {
        this(num, location, virusPPM, contaminantPPM, waterCondition, new Date());
    }
    public PurityReport(int num, Location location, double virusPPM, double contaminantPPM, String waterCondition, Date creationDatetime) {
        super(num, location, creationDatetime);
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.waterCondition = waterCondition;
        purityReportNum = Math.max(purityReportNum, num);
    }
    public String getWaterCondition() {
        return waterCondition;
    }
    public double getVirusPPM() { return virusPPM; }
    public double getContaminantPPM() { return contaminantPPM; }
    public String toString() {
        return String.format("Purity Report %s Virus PPM %s, Contaminant PPM %s", super.toString(), virusPPM, contaminantPPM);
    }
}
