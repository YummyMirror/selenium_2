package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplicationManager {
    private WebDriver wd;
    private WebDriverWait wait;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;

    public void init() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
        //Delegates
        sessionHelper = new SessionHelper(wd, wait);
        navigationHelper = new NavigationHelper(wd, wait);
    }

    public void tearDown() {
        wd.quit();
        if (wd != null)
            wd = null;
    }

    //Getters of Delegates
    public SessionHelper getSessionHelper() {
        return sessionHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
}
