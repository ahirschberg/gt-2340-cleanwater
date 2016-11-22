
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import model.Location;
import model.PurityReport;

public class ReportDateTests {
    private static final int TIMEOUT = 2000;
    
    /**
     * Tests that a report created without specifying a date
     * records its date as when its constructor was first run.
     */
    @Test(timeout=TIMEOUT)
    public void testReportAutodating() {
        Date expected = new Date();
        PurityReport r1 = new PurityReport(1, new Location(1,1), 13, 13, "safe");
        Date actual = r1.getCreationDatetime();
        CleanWaterTests.assertTrue(actual.getTime() - expected.getTime() <= 1000); //Within 1 second
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
        
        CleanWaterTests.assertTrue(r1.wasReportCreatedFirst(r2));
        CleanWaterTests.assertTrue(r2.wasReportCreatedFirst(r3));
        CleanWaterTests.assertTrue(r3.wasReportCreatedFirst(r4));
    }
}
