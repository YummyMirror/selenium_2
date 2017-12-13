package ru.anatoli.practice_selenium.HomeTask19.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import ru.anatoli.practice_selenium.HomeTask19.app.Application;

@Listeners(MyTestListener.class)
public class TestBase {
    protected static Application app = new Application("CHROME");

    @BeforeSuite
    public void start() {
        //context.setAttribute("app", app);
        app.setUp();
    }

    @AfterSuite
    public void stop() {
        app.tearDown();
    }
}
