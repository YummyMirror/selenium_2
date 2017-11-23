package ru.anatoli.practice_selenium.RemoteExecution;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;


public class GridExecution {
    private WebDriver wdLocal;
    private WebDriver wdRemote;
    private WebDriverWait wait;
    private String browser = BrowserType.FIREFOX;

    @BeforeMethod
    public void before() throws MalformedURLException {
        switch (browser) {
            case BrowserType.CHROME:
                ChromeOptions chromeOptionsLocal = new ChromeOptions();
                chromeOptionsLocal.setCapability("browserName", "chrome");
                chromeOptionsLocal.setCapability("platform", "WIN10");
                wdLocal = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), chromeOptionsLocal);

                ChromeOptions chromeOptionsRemote = new ChromeOptions();
                chromeOptionsRemote.setCapability("browserName", "chrome");
                chromeOptionsRemote.setCapability("platform", "VISTA");
                wdRemote = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), chromeOptionsRemote);
                break;
            case BrowserType.FIREFOX:
                FirefoxOptions firefoxOptionsLocal = new FirefoxOptions();
                firefoxOptionsLocal.setCapability("browserName", "firefox");
                firefoxOptionsLocal.setCapability("platform", "WINDOWS");
                wdLocal = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), firefoxOptionsLocal);

                FirefoxOptions firefoxOptionsRemote = new FirefoxOptions();
                firefoxOptionsRemote.setCapability("browserName", "firefox");
                firefoxOptionsRemote.setCapability("platform", "WINDOWS");
                wdRemote = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), firefoxOptionsRemote);
                break;
            case BrowserType.IE:
                InternetExplorerOptions internetExplorerOptionsLocal = new InternetExplorerOptions();
                internetExplorerOptionsLocal.ignoreZoomSettings();
                internetExplorerOptionsLocal.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptionsLocal.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
                internetExplorerOptionsLocal.setCapability(CapabilityType.PLATFORM, "WINDOWS");
                wdLocal = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), internetExplorerOptionsLocal);

                InternetExplorerOptions internetExplorerOptionsRemote = new InternetExplorerOptions();
                internetExplorerOptionsRemote.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptionsRemote.ignoreZoomSettings();
                internetExplorerOptionsRemote.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
                internetExplorerOptionsRemote.setCapability(CapabilityType.PLATFORM, "WINDOWS");
                wdRemote = new RemoteWebDriver(new URL("http://192.168.95.1:4444/wd/hub"), internetExplorerOptionsRemote);
                break;
            default:
                throw new UnreachableBrowserException("Incorrect browser type!");
        }
        wdLocal.manage().window().maximize();
        wdLocal.navigate().to("https://google.by");

        wdRemote.manage().window().maximize();
        wdRemote.navigate().to("https://yandex.by");
    }

    @Test
    public void test() {

    }

    @AfterMethod
    public void after() {
        wdLocal.quit();
        wdLocal = null;

        wdRemote.quit();
        wdRemote = null;
    }
}
