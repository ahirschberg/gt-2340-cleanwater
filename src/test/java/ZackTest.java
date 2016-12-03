
import controller.MainScreenController;
import fxapp.MainFXApplication;
import model.PermissionLevel;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZackTest {
    private static final int TIMEOUT = 2000;

    private MainFXApplication testMain;
    private MainScreenController testMainController;

    private void assertScreenChange() {
        Assert.assertTrue(testMain.getActiveScreen().equals(null));
    }

    private void assertNoScreenChange() {
        Assert.assertTrue(testMain.getActiveScreen().equals(null));
    }

    /**
     * Called before junit tests run, initializes test variables
     */
    @Before
    public void init() {
        testMain = new MainFXApplication();
        testMainController = new MainScreenController();
        testMainController.registerMainApp(testMain);
        testMain.launchTestEnv();
    }

    /**
     * Tests the method MainScreenController.onSubmitQualityReport
     */
    @Test(timeout=TIMEOUT)
    public void testOnSubmitQualityReport() {
        User user = new User("user", null, PermissionLevel.USER);
        User worker = new User("worker", null, PermissionLevel.WORKER);
        User manager = new User("worker", null, PermissionLevel.MANAGER);
        User admin = new User("admin", null, PermissionLevel.ADMIN);

        testMain.setActiveUser(user);
        testMainController.onSubmitQualityReport();
        assertNoScreenChange();

        testMain.setActiveUser(worker);
        testMainController.onSubmitQualityReport();
        assertScreenChange();

        testMain.setActiveUser(manager);
        testMainController.onSubmitQualityReport();
        assertScreenChange();

        testMain.setActiveUser(admin);
        testMainController.onSubmitQualityReport();
        assertNoScreenChange();
    }
}
