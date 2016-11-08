import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import controller.HistReportController;
import model.HistoricalData;
import model.Location;
import model.PurityReport;
import model.Report;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.TIMEOUT;

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
    private void assertEquals( double[] expected, double[] passed) {
        if (expected.length != passed.length) {
            Assert.fail("Lists not equal length");
        } else {
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != passed[i]) {
                    Assert.fail("Lists differed at index " + i);
                }
            }
        }
    }

    @Before
    public void init() {
        histReport = new HistReportController();
        virusData = new HistoricalData(0,5,0,5,2016,"Virus PPM");
        contaminantData = new HistoricalData(0,5,0,5,2016,"Contaminant PPM");
        streamList = new ArrayList<>();
        cleanOutputStream = new CleanOutputStream();
        System.setOut(new PrintStream(cleanOutputStream));
    }
    @After
    public void checkOutput() {
        assertTrue(
                "You used print statements somewhere in your code. That's forbidden!",
                cleanOutputStream.isClean());
    }
    @Test(timeout = TIMEOUT)
    public void addOneToEachMonth() {
        histReport.setData(virusData);
        streamList.add(new PurityReport(1, new Location(1,1), 13, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(2, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(3, new Location(1,1), 13, 13, "safe", new Date(1451687943)));
        streamList.add(new PurityReport(4, new Location(1,1), 13, 13, "safe", new Date(1454366343)));
        streamList.add(new PurityReport(5, new Location(1,1), 13, 13, "safe", new Date(1456871943)));
        streamList.add(new PurityReport(6, new Location(1,1), 13, 13, "safe", new Date(1459546743)));
        streamList.add(new PurityReport(7, new Location(1,1), 13, 13, "safe", new Date(1462138743)));
        streamList.add(new PurityReport(8, new Location(1,1), 13, 13, "safe", new Date(1464817143)));
        streamList.add(new PurityReport(9, new Location(1,1), 13, 13, "safe", new Date(1467409143)));
        streamList.add(new PurityReport(10, new Location(1,1), 13, 13, "safe", new Date(1470087543)));
        streamList.add(new PurityReport(11, new Location(1,1), 13, 13, "safe", new Date(1472765943)));
        streamList.add(new PurityReport(12, new Location(1,1), 13, 13, "safe", new Date(1475357943)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13};
        assertEquals(returned, expected);
    }

    @Test(timeout = TIMEOUT)
    public void multipleSameMonth() {
        histReport.setData(virusData);
        streamList.add(new PurityReport(1, new Location(1,1), 0, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(2, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(3, new Location(1,1), 26, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(4, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(5, new Location(1,1), 0, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(6, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(7, new Location(1,1), 26, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(8, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(9, new Location(1,1), 0, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(10, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(11, new Location(1,1), 26, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(12, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(13, new Location(1,1), 0, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(14, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        streamList.add(new PurityReport(15, new Location(1,1), 26, 13, "safe", new Date(1478558343)));
        streamList.add(new PurityReport(16, new Location(1,1), 13, 13, "safe", new Date(1480631943)));
        reptStream = streamList.stream();
        histReport.setReportsList(reptStream);
        histReport.setGraph();
        double[] returned = histReport.getPPM();
        double[] expected = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 13};
        assertEquals(returned, expected);
    }

}
