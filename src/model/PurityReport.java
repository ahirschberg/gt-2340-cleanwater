package model;

import java.util.Date;

public class PurityReport extends Report {
    private double virusPPM;
    private double contaminantPPM;
    private String waterCondition;
    private static int purityReportNum = 0;

    /**
     * Creates a Purity Report
     *
     * @param location location of water
     * @param virusPPM virus parts per million
     * @param contaminantPPM contaminant parts per million
     * @param waterCondition condition of water
     */
    public PurityReport(Location location,
                        double virusPPM,
                        double contaminantPPM,
                        String waterCondition) {
        this(++purityReportNum, location, virusPPM,
                contaminantPPM, waterCondition);
    }

    /**
     * Initializes purity report with given info
     * @param num report number
     * @param location report location
     * @param virusPPM parts per million of virus
     * @param contaminantPPM parts per million of contaminants
     * @param waterCondition condition of water
     */
    public PurityReport(int num, Location location, double virusPPM,
                        double contaminantPPM, String waterCondition) {
        this(num, location, virusPPM, contaminantPPM,
                waterCondition, new Date());
    }

    /**
     * Initializes purity report with given info
     * @param num report number
     * @param location report location
     * @param virusPPM parts per million of virus
     * @param contaminantPPM parts per million of contaminants
     * @param waterCondition condition of water
     * @param creationDatetime date and time of report creation
     */
    public PurityReport(int num, Location location, double virusPPM,
                        double contaminantPPM, String waterCondition,
                        Date creationDatetime) {
        super(num, location, creationDatetime);
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.waterCondition = waterCondition;
        purityReportNum = Math.max(purityReportNum, num);
    }

    /**
     * gets water condition of stored report
     * @return condition of water
     */
    public String getWaterCondition() {
        return waterCondition;
    }

    /**
     * gets parts per million of virus
     * @return parts per million of virus
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * gets parts per million of contaminants
     * @return parts per million of contaminants
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * converts report to string
     * @return string generated
     */
    public String toString() {
        return String
                .format("Purity Report %s Virus PPM %s, Contaminant PPM %s",
                super.toString(), virusPPM, contaminantPPM);
    }
}
