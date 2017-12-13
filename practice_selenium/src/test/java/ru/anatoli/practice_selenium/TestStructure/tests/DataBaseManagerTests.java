package ru.anatoli.practice_selenium.TestStructure.tests;

import org.testng.annotations.Test;
import java.util.Set;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DataBaseManagerTests extends TestBase {
    @Test(enabled = true)
    public void testBDManager() {
        applicationManager.getSessionHelper().login("admin", "admin");
        assertTrue(applicationManager.getSessionHelper().getLoginSuccessMessage().equals("You are now logged in as admin"));

        String currentWindowHandle = applicationManager.getNavigationHelper().getCurrentWindowHandle();
        Set<String> currentWindowHandles = applicationManager.getNavigationHelper().getWindowHandles();
        applicationManager.getNavigationHelper().openDBManager();
        String newWindowHandle = applicationManager.getNavigationHelper().getNewWindowHandle(currentWindowHandles);
        Set<String> handlesAfterOpening = applicationManager.getNavigationHelper().getWindowHandles();
        applicationManager.getNavigationHelper().switchToWindow(newWindowHandle);

        assertEquals(currentWindowHandles.size() + 1, handlesAfterOpening.size());

        applicationManager.getNavigationHelper().closeCurrentWindow();
        applicationManager.getNavigationHelper().switchToWindow(currentWindowHandle);

        assertEquals(currentWindowHandles.size(), 1);
    }
}
