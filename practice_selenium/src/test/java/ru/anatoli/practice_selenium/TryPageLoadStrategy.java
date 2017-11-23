package ru.anatoli.practice_selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import static org.testng.Assert.assertEquals;

public class TryPageLoadStrategy {
    private WebDriver wd;
    private WebDriverWait wait;
    private Properties properties;

    @BeforeMethod
    public void before() throws IOException {
        properties = new Properties();
        File propertiesFile = new File("src/test/resources/forteFit.properties");
        FileReader reader = new FileReader(propertiesFile);
        properties.load(reader);
        String browser = properties.getProperty("browser");
        switch (browser) {
            case "FIREFOX":
                System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setLegacy(false);
                firefoxOptions.setCapability("unexpectedAlertBehaviour", "dismiss");
                firefoxOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                wd = new FirefoxDriver(firefoxOptions);
                break;
            case "CHROME":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("pageLoadStrategy", "normal");
                //chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                wd = new ChromeDriver();
                break;
            case "IE":
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                internetExplorerOptions.ignoreZoomSettings();
                internetExplorerOptions.introduceFlakinessByIgnoringSecurityDomains();
                internetExplorerOptions.setCapability("pageLoadStrategy", "eager"); //CORRECT
                wd = new InternetExplorerDriver(internetExplorerOptions);
                break;
            default:
                System.out.println("Incorrect type of browser!");
        }
        wait = new WebDriverWait(wd, 30);
        wd.manage().window().maximize();
        System.out.println(((HasCapabilities) wd).getCapabilities());
        wd.navigate().to(properties.getProperty("baseUrl"));
    }

    @Test
    public void test() {
        String loginFormText = openLoginPage();
        assertEquals(loginFormText, "Welcome Back");
        login(properties.getProperty("login"), properties.getProperty("password"));

        WebElement footer = wd.findElement(By.xpath("//footer//span[contains(text(), 'All Rights Reserved.')]"));
        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", footer);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id = 'class-Upcoming']//h2[contains(text(), 'Upcoming')]")));
        List<WebElement> upcomingClasses = wd.findElements(By.xpath("//div[@id = 'class-Upcoming']//div[@class = 'carousel-item-info']/a"));
        if (upcomingClasses.size() != 0) {
            for (int i = 0; i < upcomingClasses.size(); i++) {
                WebElement footer2 = wd.findElement(By.xpath("//footer//span[contains(text(), 'All Rights Reserved.')]"));
                ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", footer2);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id = 'class-Upcoming']//h2[contains(text(), 'Upcoming')]")));
                List<WebElement> classes = wd.findElements(By.xpath("//div[@id = 'class-Upcoming']//div[@class = 'carousel-item-info']/a"));

                WebElement upcomingTitle = wd.findElement(By.xpath("//div[@id = 'class-Upcoming']//h2"));
                ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", upcomingTitle);
                while (!isElementClicable(classes.get(i))) {
                    wd.findElement(By.xpath("//div[@id = 'class-Upcoming']//a[@class = 'carousel-arrow carousel-arrow-right']")).click();
                }
                classes.get(i).click();
                areElementsPresent(By.xpath("//div[@class = 'video-player-messages']"));
                wd.navigate().back();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'banner')]")));
            }
        } else {
            System.out.println("upcomingClasses collection is EMPTY!");
        }
    }

    public String openLoginPage() {
        click(By.xpath("//a[@href = '/account/login']"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@id = 'loginForm']//h1"))).getText();
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@id = 'Email']"), login);
        input(By.xpath("//input[@id = 'Password']"), password);
        check(By.xpath("//input[@id = 'RememberMe']"));
        click(By.xpath("//button[@id = 'loginButton']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id = 'user-menu-link']")));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void check(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    public boolean areElementsPresent(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)).size() > 0;
    }

    public boolean isElementClicable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
