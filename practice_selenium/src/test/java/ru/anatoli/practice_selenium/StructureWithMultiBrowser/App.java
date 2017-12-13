package ru.anatoli.practice_selenium.StructureWithMultiBrowser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
    private WebDriver wd;
    private WebDriverWait wait;
    private AppBase appBase;

    public void setUp(String browser) {
        appBase = new AppBase();
        wd = appBase.getBrowser(browser);
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    public void tearDown() {
        wd.quit();
        if (wd != null)
            wd = null;
    }

    //Getters
    public AppBase getAppBase() {
        return appBase;
    }
}
