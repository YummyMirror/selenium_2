package ru.anatoli.practice_selenium.RemoteExecution;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import static org.testng.Assert.assertTrue;

public class StandaloneServerExecution {
    private WebDriver wd;
    private WebDriverWait wait;
    private String browser = BrowserType.CHROME;

    @BeforeMethod
    public void before() throws MalformedURLException {
        switch (browser) {
            case BrowserType.CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserName", "chrome");
                wd = new RemoteWebDriver(new URL("http://192.168.197.128:4444/wd/hub"), chromeOptions);
                break;
            case BrowserType.FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability(CapabilityType.BROWSER_NAME, "firefox");
                wd = new RemoteWebDriver(new URL("http://192.168.197.128:4444/wd/hub"), firefoxOptions);
                break;
            default:
                throw new UnreachableBrowserException("Incorrect type of browser was entered!");
        }
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("https:google.com");
    }

    @Test
    public void test() {
        assertTrue(wait.until(ExpectedConditions.titleIs("Google")));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
