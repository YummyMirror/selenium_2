package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SeleniumCloud {
    private WebDriver wd;
    private WebDriverWait wait;

    @Parameters("browser")
    @BeforeClass
    public void before(String browser) throws MalformedURLException {
        //String browser = "CHROME";
        switch (browser) {
            case "CHROME":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("os", "OS X");
                chromeOptions.setCapability("os_version", "Sierra");
                chromeOptions.setCapability("browser", "Chrome");
                chromeOptions.setCapability("browser_version", "55");
                //chromeOptions.setCapability("project", "My_Project");
                //chromeOptions.setCapability("build", "My_Build");
                //chromeOptions.setCapability("name", "My_Name");
                chromeOptions.setCapability("browserstack.debug", true);
                chromeOptions.setCapability("resolution", "1920x1080");
                wd = new RemoteWebDriver(new URL("https://ivan1657:DXzpGyDpod7cAQpjjFHo@hub-cloud.browserstack.com/wd/hub"), chromeOptions);
                break;
            case "FIREFOX":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("os", "WINDOWS");
                firefoxOptions.setCapability("os_version", "XP");
                firefoxOptions.setCapability("browser", "Firefox");
                firefoxOptions.setCapability("browser_version", "47");
                firefoxOptions.setCapability("browserstack.debug", true);
                firefoxOptions.setCapability("resolution", "1920x1080");
                wd = new RemoteWebDriver(new URL("https://ivan1657:DXzpGyDpod7cAQpjjFHo@hub-cloud.browserstack.com/wd/hub"), firefoxOptions);
                break;
            case "SAFARI":
                SafariOptions safariOptions = new SafariOptions();
                safariOptions.setCapability("os", "OS X");
                safariOptions.setCapability("os_version", "High Sierra");
                safariOptions.setCapability("browser", "Safari");
                safariOptions.setCapability("browser_version", "11");
                safariOptions.setCapability("browserstack.debug", true);
                wd = new RemoteWebDriver(new URL("https://ivan1657:DXzpGyDpod7cAQpjjFHo@hub-cloud.browserstack.com/wd/hub"), safariOptions);
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

    @Test
    public void test2() {
        wait.until(elementToBeClickable(By.xpath("//a[contains(@class, 'dashed-bottom')]"))).click();
        WebElement locationsTitle = wd.findElement(By.xpath("//p[@class = 'contacts-title' and text() = 'Locations']"));
        wait.until(visibilityOf(locationsTitle));
        wait.until(elementToBeClickable(By.xpath("//a[contains(@class, 'dashed-bottom')]"))).click();
        wait.until(invisibilityOf(locationsTitle));
    }

    @Test
    public void test3() {
        for (int i = 0; i < getAllProjectTypes().size(); i++) {
            getAllProjectTypes().get(i).click();
            wait.until(attributeToBeNotEmpty(getAllProjectTypes().get(i), "style"));
        }
    }

    public List<WebElement> getAllProjectTypes() {
        return wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//span[@class = 'filter-item']")));
    }

    public List<WebElement> getMenuItems() {
        return wd.findElements(By.xpath("//div[@class = 'menu-items menu-header-item-container']/a"));
    }

    @AfterClass
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
