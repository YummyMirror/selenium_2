package ru.anatoli.practice_selenium;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class FirstTest {
    private WebDriver wd;
    private WebDriverWait wait;
    private final static Logger LOGGER = Logger.getLogger(FirstTest.class.getName());
    private BrowserMobProxy proxy;

    @BeforeClass
    public void before() {
        //System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        //FirefoxOptions firefoxOptions = new FirefoxOptions();
        //firefoxOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        //wd = new FirefoxDriver(firefoxOptions);
        FirefoxProfile profile = new FirefoxProfile();
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        wd = new ChromeDriver(options);
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
    }

    @Test
    public void simpleTest(Method method) {
        wd.navigate().to("https://www.google.by");
        WebElement searchField = wait.until(visibilityOfElementLocated(By.xpath("//input[@id = 'lst-ib']")));
        searchField.sendKeys("selenium webdriver");
        wd.findElement(By.xpath("//input[@name = 'btnK']")).click();
        wait.until(ExpectedConditions.titleIs("selenium webdriver - Пошук Google"));
        //All will be printed
        LOGGER.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        LOGGER.error("11111111111111111111111111111");
        LOGGER.warn("777777777777777777777777777777");
    }

    @Test
    public void s13login() {
        wd.navigate().to("http://s13.ru/wp-login.php");
        wd.findElement(By.xpath("//input[@id = 'user_login']")).sendKeys("currentUser");
        wd.findElement(By.xpath("//input[@id = 'user_pass']")).sendKeys("yPI6T91XD6LQNMhx");
        wd.findElement(By.xpath("//input[@id = 'wp-submit']")).click();
        //String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'wrap']/h1[text() = 'Консоль']"))).getText();
        String text = wd.findElement(By.xpath("//div[@class = 'wrap']/h1")).getText();
    }

    @Test
    public void usingProxy() {
        proxy.newHar("exposit");
        wd.navigate().to("https://www.exposit.com");
        List<HarEntry> entries = proxy.getHar().getLog().getEntries();
        entries.forEach(e -> LOGGER.info("URl: " + e.getRequest().getUrl() + " ---> " + "Method: " + e.getRequest().getMethod() + "\n" +
                                                "Status: " + e.getResponse().getStatus() + " ---> " + e.getResponse().getStatusText()
                                                + " ---> Comment: " + e.getResponse().getComment()));
    }

    @Test
    public void test() {
        wd.navigate().to("https://www.exposit.com/portfolio/");
        wait.until(urlContains("https://www.exposit.com/portfolio/"));
        wait.until(elementToBeClickable(By.xpath("//a[contains(@class, 'dashed-bottom')]"))).click();
        WebElement locationsTitle = wd.findElement(By.xpath("//p[@class = 'contacts-title' and text() = 'Locations']"));
        wait.until(visibilityOf(locationsTitle));
        wait.until(elementToBeClickable(By.xpath("//a[contains(@class, 'dashed-bottom')]"))).click();
        wait.until(invisibilityOf(locationsTitle));
    }

    @Test
    public void test5() {
        wd.navigate().to("https://www.exposit.com/portfolio/");
        wait.until(urlContains("https://www.exposit.com/portfolio/"));
        for (int i = 0; i < getAllProjectTypes().size(); i++) {
            getAllProjectTypes().get(i).click();
            wait.until(attributeToBeNotEmpty(getAllProjectTypes().get(i), "style"));
        }
    }

    public List<WebElement> getAllProjectTypes() {
        return wait.until(visibilityOfAllElementsLocatedBy(By.xpath("//span[@class = 'filter-item']")));
    }

    public void SetDatePicker(WebDriver wd, String xPathSelector, String date) {
        new WebDriverWait(wd, 30).until((ddate) -> wd.findElement(By.xpath(xPathSelector)).isDisplayed());
        ((JavascriptExecutor) wd).executeScript(String.format("$('{0}').datepicker('setDateUsingPicker', '{1}')", xPathSelector, date));
    }

    @AfterClass
    public void after() {
        wd.quit();
        wd = null;
    }
}
