package test;
import controller.HistReportController;
import model.HistoricalData;
import model.Location;
import model.PurityReport;
import model.Report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CleanWaterTests {
    private static final int TIMEOUT = 2000;

    private final PrintStream originalSystemOut = System.out;
    private CleanOutputStream cleanOutputStream;
    private Stream<Report> reptStream;
    private HistReportController  histReport;
    private HistoricalData virusData;
    private HistoricalData contaminantData;
    private List<Report> streamList;

    private class CleanOutputStream extends OutputStream {

        private boolean clean = true;

        @Override
        public void write(int b) throws IOException {
            clean = false;
            originalSystemOut.write(b);
        }

        public boolean isClean() {
            return clean;
        }
    }
    
    /**
     * asserts two double arrays are equivalent
     * @param expected expected array
     * @param passed actual array
     */
    private void assertEquals( double[] expected, double[] passed) {
        if (expected.length != passed.length) {
            Assert.fail("Lists not equal length");
        } else {
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != passed[i]) {
                    for (double d : expected) {
                        System.out.print(d + ", ");
                    }
                    System.out.println("");
                    for (double d : passed) {
                        System.out.print(d + ", ");
                    }
                    Assert.fail("Lists differed at index " + i + " expected " + expected[i] + " found " + passed[i]);

                }
            }
        }
    }
    
    /**
     * asserts expr is true
     * @param expr expr to test
     */
    private void assertTrue(boolean expr) {
        if (!expr) {
            Assert.fail("expression not true");
        }
    }

    /**
     * Called before junit tests run, initializes test variables
     */
    @Before
    public void init() {
        histReport = new HistReportController();
        virusData = new HistoricalData(0,10,0,10,2016,"Virus PPM");
        contaminantData = new HistoricalData(0,5,0,5,2016,"Contaminant PPM");
        streamList = new ArrayList<>();
        cleanOutputStream = new CleanOutputStream();
        System.setOut(new PrintStream(cleanOutputStream));
    }
    //@After
    //public void checkOutput() {
      //  assertTrue(
        //        "You used print statements somewhere in your code. That's forbidden!",
          //      cleanOutputStream.isClean());
    //}
    
    /**
     * tests historical report graphs
     */
    @Test(timeout = TIMEOUT)
    public void addOneToEachMonth() {
        histReport.setData(virusData);
        streamList.add(new PurityReport(1, new Location(1,1), 13, 13, "safe", new Date(1451710800000L)));
        streamList.add(new PurityReport(2, new Location(1,1), 13, 13, "safe", new Date(1454389200000L)));
        streamList.add(new PurityReport(3, new Location(1,1), 13, 13, "safe", new Date(1456894800000L)));
        streamList.add(new PurityReport(4, new Location(1,1), 13, 13, "safe", new Date(1459569600000L)));
        streamList.add(new PurityReport(5, new Location(1,1), 13, 13, "safe", new Date(1462161600000L)));
        streamList.add(new PurityReport(6, new Location(1,1), 13, 13, "safe", new Date(1464840000000L)));
        streamList.add(new PurityReport(7, new Location(1,1), 13, 13, "safe", new Date(1467432000000L)));
        streamList.add(new PurityReport(8, new Location(1,1), 13, 13, "safe", new Date(1470110400000L)));
        streamList.add(new PurityReport(9, new Location(1,1), 13, 13, "safe", new Date(1472788800000L)));
        streamList.add(new PurityReport(10, new Location(1,1), 13, 13, "safe", new Date(1475380800000L)));
        streamList.add(new PurityReport(11, new Location(1,1), 13, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(12, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13};
        assertEquals(expected, returned);
    }

    /**
     * Tests historical report graphs
     */
    @Test(timeout = TIMEOUT)
    public void multipleSameMonth() {
        histReport.setData(virusData);
        streamList.add(new PurityReport(1, new Location(1,1), 0, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(2, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(3, new Location(1,1), 26, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(4, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(5, new Location(1,1), 0, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(6, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(7, new Location(1,1), 26, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(8, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(9, new Location(1,1), 0, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(10, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(11, new Location(1,1), 26, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(12, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(13, new Location(1,1), 0, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(14, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        streamList.add(new PurityReport(15, new Location(1,1), 26, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(16, new Location(1,1), 13, 13, "safe", new Date(1480654800000L)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 13};
        assertEquals(expected, returned);
    }

    /**
     * Tests invalid data passed to historical report graph
     */
    @Test(timeout = TIMEOUT)
    public void allInvalidData() {
        histReport.setData(contaminantData);
        streamList.add(new PurityReport(1, new Location(50,1), 13, 13, "safe", new Date(1451710800000L)));
        streamList.add(new PurityReport(2, new Location(50,1), 13, 13, "safe", new Date(1454389200000L)));
        streamList.add(new PurityReport(3, new Location(1,50), 13, 13, "safe", new Date(1456894800000L)));
        streamList.add(new PurityReport(4, new Location(50,1), 13, 13, "safe", new Date(1459569600000L)));
        streamList.add(new PurityReport(5, new Location(1,-5), 13, 13, "safe", new Date(1462161600000L)));
        streamList.add(new PurityReport(6, new Location(12,1), 13, 13, "safe", new Date(1464840000000L)));
        streamList.add(new PurityReport(7, new Location(0,1), 13, 13, "safe", new Date(1467400000L)));
        streamList.add(new PurityReport(8, new Location(1,1), 13, 13, "safe", new Date(1470400000L)));
        streamList.add(new PurityReport(9, new Location(1,1), 13, 13, "safe", new Date(1472800000L)));
        streamList.add(new PurityReport(10, new Location(1,1), 13, 13, "safe", new Date(1475800000L)));
        streamList.add(new PurityReport(11, new Location(1,1), 13, 13, "safe", new Date(1478200000L)));
        streamList.add(new PurityReport(12, new Location(1,1), 13, 13, "safe", new Date(1480600000L)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        assertEquals(expected, returned);
    }

    /**
     * Tests mix of valid and invalid data passed to historical report graphs
     */
    @Test(timeout = TIMEOUT)
    public void someInvalidData() {
        histReport.setData(virusData);
        streamList.add(new PurityReport(1, new Location(1, 1), 13, 13, "safe", new Date(1451710800000L)));
        streamList.add(new PurityReport(2, new Location(50, 1), 13, 13, "safe", new Date(1454389200000L)));
        streamList.add(new PurityReport(3, new Location(1, 1), 13, 13, "safe", new Date(1456894800000L)));
        streamList.add(new PurityReport(4, new Location(1, 50), 13, 13, "safe", new Date(1459569600000L)));
        streamList.add(new PurityReport(5, new Location(1, 1), 13, 13, "safe", new Date(1462161600000L)));
        streamList.add(new PurityReport(6, new Location(1, 1), 13, 13, "safe", new Date(1464840000000L)));
        streamList.add(new PurityReport(7, new Location(1, 1), 13, 13, "safe", new Date(1467432000000L)));
        streamList.add(new PurityReport(8, new Location(1, 1), 13, 13, "safe", new Date(1470110400000L)));
        streamList.add(new PurityReport(9, new Location(0, 1), 13, 13, "safe", new Date(1472788800000L)));
        streamList.add(new PurityReport(10, new Location(1, 1), 200, 13, "safe", new Date(1475380800000L)));
        streamList.add(new PurityReport(11, new Location(1, 1), 13, 13, "safe", new Date(1478059200000L)));
        streamList.add(new PurityReport(12, new Location(50, 1), 13, 13, "safe", new Date(1480654800000L)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {13, -1, 13, -1, 13, 13, 13, 13, 13, 200, 13, -1};
        assertEquals(expected, returned);
    }

    /**
     * Tests historical report graphs when no data is given to them.
     */
    @Test(timeout = TIMEOUT)
    public void noData() {
        histReport.setData(virusData);
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        assertEquals(expected, returned);
    }
    
    /**
     * Tests the function wasReportCreatedFirst
     */
    @Test(timeout=TIMEOUT)
    public void testReportOrdering() {
        Calendar c = Calendar.getInstance();
        
        c.set(1, 1, 1, 1, 1);
        PurityReport r1 = new PurityReport(1, new Location(1,1), 13, 13, "safe", c.getTime());
        
        c.set(2, 2, 2, 2, 2);
        PurityReport r2 = new PurityReport(1, new Location(1,1), 13, 13, "safe", c.getTime());
        
        c.set(3, 1, 1, 1, 1);
        PurityReport r3 = new PurityReport(1, new Location(1,1), 13, 13, "safe", c.getTime());
        
        c.set(3, 2, 3, 4, 5);
        PurityReport r4 = new PurityReport(1, new Location(1,1), 13, 13, "safe", c.getTime());
        
        assertTrue(r1.wasReportCreatedFirst(r2));
        assertTrue(r2.wasReportCreatedFirst(r3));
        assertTrue(r3.wasReportCreatedFirst(r4));
    }
}
