package ru.anatoli.practice_selenium.JQueryUIActions;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.System.setProperty;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertEquals;

public class DownloadFile {
    private WebDriver wd;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private static final String FILEPATH = "F:\\Download";
    private static final Logger LOGGER = Logger.getLogger(DownloadFile.class);
    private BrowserMobProxy proxy;

    @BeforeSuite
    public void before() {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        String browser = BrowserType.FIREFOX;
        switch (browser) {
            case BrowserType.CHROME:
                ChromeOptions optionsCHROME = new ChromeOptions();
                optionsCHROME.setProxy(seleniumProxy);
                wd = new ChromeDriver(optionsCHROME);
                break;
            case BrowserType.FIREFOX:
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver.exe");
                FirefoxOptions options = getFireFoxOptions(seleniumProxy);
                wd = new FirefoxDriver(options);
                break;
            case BrowserType.IE:
                setProperty("webdriver.ie.driver", "F:\\Private\\Programs\\IEDriverServer.exe");
                InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                optionsIE.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                optionsIE.setCapability(CapabilityType.PROXY, seleniumProxy);
                wd = new InternetExplorerDriver(optionsIE);
                break;
            default:
                LOGGER.error("Incorrect browser type!!!");
                throw new BrowserNotSupportedException("Incorrect browser type input!");
        }
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        js = (JavascriptExecutor) wd;
        LOGGER.info("CAPABILITIES: " + ((HasCapabilities) wd).getCapabilities());
    }

    public FirefoxOptions getFireFoxOptions(Proxy seleniumProxy) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", FILEPATH);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        FirefoxOptions options = new FirefoxOptions();
        options.setLegacy(false);
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        options.setProfile(profile);
        return options;
    }

    @Test
    public void download() {
        wd.navigate().to("http://toolsqa.com/automation-practice-form/");
        wait.until(visibilityOfElementLocated(By.xpath("//h1[text() = 'Practice Automation Form']")));
        WebElement downloadElement = wd.findElement(By.xpath("//a[text() = 'Test File to Download']"));
        js.executeScript("arguments[0].scrollIntoView(true);", downloadElement);
        proxy.newHar();
        wait.until(elementToBeClickable(downloadElement)).click();
        List<HarEntry> entries = proxy.endHar().getLog().getEntries();
        List<HarEntry> fileInfo = entries.stream()
                                .filter(e -> e.getResponse()
                                        .getStatus() == 200 && e.getResponse().getContent().getMimeType().equals("application/octet-stream"))
                                .collect(Collectors.toList());
        if (fileInfo.size() == 1) {
            LOGGER.info("Downloading is successfully");
        } else {
            LOGGER.warn("Downloading is not good");
        }
        assertEquals(fileInfo.size(), 1);
    }

    @AfterSuite
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
