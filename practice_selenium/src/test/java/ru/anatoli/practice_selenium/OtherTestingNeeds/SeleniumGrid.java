package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static org.openqa.selenium.remote.CapabilityType.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeleniumGrid {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() throws MalformedURLException {
        String browser = "FIREFOX";
        switch (browser) {
            case "CHROME":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserName", "chrome");
                chromeOptions.setCapability("platform", "VISTA");
                chromeOptions.addExtensions(new File("F:\\Private\\Programs\\No.Image.crx"));
                wd = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), chromeOptions);
                break;
            case "FIREFOX":
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("permissions.default.image", 2);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProfile(profile);
                firefoxOptions.setCapability(BROWSER_NAME, "firefox");
                firefoxOptions.setCapability(PLATFORM, "WINDOWS");
                //firefoxOptions.setCapability(VERSION, "57.0.2");
                wd = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), firefoxOptions);
                break;
            default:
                throw new BrowserNotSupportedException("Incorrect browser type!");
        }
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        System.out.println(((HasCapabilities) wd).getCapabilities());
    }

    @Test
    public void test() {
        wd.navigate().to("https://www.exposit.com");
        wait.until(urlContains("https://www.exposit.com"));
        for (int i = 0; i < getMenuItems().size(); i++) {
            getMenuItems().get(i).click();
            wait.until(attributeToBeNotEmpty(getMenuItems().get(i), "style"));
        }
    }

    public List<WebElement> getMenuItems() {
        return wd.findElements(By.xpath("//div[@class = 'menu-items menu-header-item-container']/a"));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
