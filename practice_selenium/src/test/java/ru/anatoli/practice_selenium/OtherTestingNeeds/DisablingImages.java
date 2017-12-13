package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import static java.lang.System.setProperty;

public class DisablingImages {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        String browser = "CHROME";
        switch (browser) {
            case "CHROME":
                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.addExtensions(new File("F:\\Private\\Programs\\No.Image.crx"));
                wd = new ChromeDriver(optionsChrome);
                break;
            case "FIREFOX":
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver.exe");
                FirefoxProfile profileFireFox = new FirefoxProfile();
                profileFireFox.setPreference("permissions.default.image", 2);
                FirefoxOptions optionsFF = new FirefoxOptions();
                optionsFF.setProfile(profileFireFox);
                wd = new FirefoxDriver(optionsFF);
                break;
            default:
                wd = new InternetExplorerDriver();
        }
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        //wd.navigate().to("https://www.exposit.com");
        wd.navigate().to("https://www.forte.fit/");
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
