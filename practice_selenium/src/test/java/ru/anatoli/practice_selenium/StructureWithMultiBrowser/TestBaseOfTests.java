package ru.anatoli.practice_selenium.StructureWithMultiBrowser;

import org.testng.annotations.*;

public class TestBaseOfTests {
    protected static final App app = new App();

    @BeforeClass
    @Parameters("browser")
    public void before(String browser) {
        app.setUp(browser);
    }

    @AfterClass
    public void after() {
        app.tearDown();
    }
}
