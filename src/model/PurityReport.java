package model;

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
        super(++purityReportNum, location);
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.waterCondition = waterCondition;
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
