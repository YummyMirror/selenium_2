package ru.anatoli.practice_selenium.TestStructure.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.anatoli.practice_selenium.TestStructure.applicationManager.ApplicationManager;

public class TestBase {
    protected static ApplicationManager applicationManager = new ApplicationManager();

    @BeforeSuite
    public void start() {
        applicationManager.init();
    }

    @AfterSuite
    public void stop() {
        applicationManager.tearDown();
    }
}
