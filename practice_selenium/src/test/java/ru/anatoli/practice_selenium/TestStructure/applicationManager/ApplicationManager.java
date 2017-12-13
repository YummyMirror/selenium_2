package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import static java.lang.System.setProperty;
import static org.testng.Assert.assertTrue;

public class ApplicationManager {
    //private EventFiringWebDriver wd;
    private WebDriver wd;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;
    private NavigationHelper navigationHelper;
    private SessionHelper sessionHelper;
    private CountriesHelper countriesHelper;

    public class MyListener extends AbstractWebDriverEventListener {
        private final Logger LOGGER = Logger.getLogger(MyListener.class);

        @Override
        public void beforeNavigateTo(String url, WebDriver driver) {
            LOGGER.info("Going to open " + url);
        }

        @Override
        public void afterNavigateTo(String url, WebDriver driver) {
            LOGGER.info("Opened the " + url);
        }

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            LOGGER.info("Going to find " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            LOGGER.info("Found " + by);
        }

        @Override
        public void beforeClickOn(WebElement element, WebDriver driver) {
            //LOGGER.info("Going to click element " + "'" + element.getAttribute("name") + "'");
        }

        @Override
        public void afterClickOn(WebElement element, WebDriver driver) {
            //LOGGER.info("Clicked element " + "'" + element.getAttribute("name") + "'");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            LOGGER.error("Exception occurred: ", throwable);
            File screenshotAs = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
            File file = new File("d:/screenshot_" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(screenshotAs, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init(String browser) {
        //wd = new EventFiringWebDriver(new ChromeDriver());
        wd = getDriver(browser);
        wait = new WebDriverWait(wd, 10);
        actions = new Actions(wd);
        //wd.register(new MyListener());
        js = (JavascriptExecutor) wd;
        //Delegates
        sessionHelper = new SessionHelper(wd, wait, actions);
        navigationHelper = new NavigationHelper(wd, wait, actions);
        countriesHelper = new CountriesHelper(wd, wait, actions);

        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
        //Login
        getSessionHelper().login("admin", "admin");
        assertTrue(getSessionHelper().getLoginSuccessMessage().equals("You are now logged in as admin"));
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

    public CountriesHelper getCountriesHelper() {
        return countriesHelper;
    }

    public WebDriver getDriver(String browser) {
        switch (browser) {
            case "CHROME":
                wd = new ChromeDriver();
                break;
            case "FIREFOX":
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver.exe");
                wd = new FirefoxDriver();
                break;
            default:
                setProperty("webdriver.ie.driver", "F:\\Private\\Programs\\IEDriverServer.exe");
                wd = new InternetExplorerDriver();
                break;
        }
        return wd;
    }
}
