package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static org.openqa.selenium.remote.CapabilityType.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeleniumServer {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() throws MalformedURLException {
        String browser = BrowserType.CHROME;
        switch (browser) {
            case BrowserType.CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("browserName", "chrome");
                chromeOptions.setCapability("platform", "VISTA");
                wd = new RemoteWebDriver(new URL("http://192.168.197.128:4444/wd/hub"), chromeOptions);
                break;
            case BrowserType.FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability(BROWSER_NAME, "firefox");
                firefoxOptions.setCapability(PLATFORM, "WINDOWS");
                wd = new RemoteWebDriver(new URL("http://192.168.197.128:4444/wd/hub"), firefoxOptions);
                break;
            default:
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptions.ignoreZoomSettings();
                internetExplorerOptions.setCapability(BROWSER_NAME, "internet explorer");
                internetExplorerOptions.setCapability(PLATFORM, "WINDOWS");
                wd = new RemoteWebDriver(new URL("http://192.168.197.128:4444/wd/hub"), internetExplorerOptions);
        }
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        wd.navigate().to("https://www.exposit.com");
        wait.until(urlContains("https://www.exposit.com"));
        List<WebElement> menuItems = wd.findElements(By.xpath("//div[@class = 'menu-items menu-header-item-container']/a"));
        for (int i = 0; i < menuItems.size(); i++) {
            List<WebElement> menus = wd.findElements(By.xpath("//div[@class = 'menu-items menu-header-item-container']/a"));
            menus.get(i).click();
            List<WebElement> items = wd.findElements(By.xpath("//div[@class = 'menu-items menu-header-item-container']/a"));
            wait.until(attributeToBeNotEmpty(items.get(i), "style"));
        }
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
