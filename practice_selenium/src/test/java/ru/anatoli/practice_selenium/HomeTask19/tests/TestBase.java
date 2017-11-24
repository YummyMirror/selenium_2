package ru.anatoli.practice_selenium.HomeTask19.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.anatoli.practice_selenium.HomeTask19.app.Application;

public class TestBase {
    protected static Application app = new Application();

    @BeforeSuite
    public void start() {
        app.setUp();
    }

    @AfterSuite
    public void stop() {
        app.tearDown();
    }
}
