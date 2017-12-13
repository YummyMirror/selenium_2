package ru.anatoli.practice_selenium.TestStructure.tests;

import org.testng.annotations.*;
import ru.anatoli.practice_selenium.TestStructure.applicationManager.ApplicationManager;

public class TestBase {
    protected static ApplicationManager applicationManager = new ApplicationManager();

    @Parameters("browser")
    @BeforeTest
    public void start(String browser) {
        applicationManager.init(browser);
    }

    @AfterTest
    public void stop() {
        applicationManager.tearDown();
    }
}
