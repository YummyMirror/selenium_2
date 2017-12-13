package ru.anatoli.practice_selenium.HomeTask19.app;

import com.google.common.io.Files;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import ru.anatoli.practice_selenium.HomeTask19.pages.BasketPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.MainPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.ProductPage;
import sun.plugin.dom.exception.BrowserNotSupportedException;
import java.io.File;
import java.io.IOException;
import static java.lang.System.setProperty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Application {
    //private EventFiringWebDriver wd;
    private WebDriver wd;
    private WebDriverWait wait;
    private MainPage mainPage;
    private ProductPage productPage;
    private BasketPage basketPage;
    private String browser;
    //Constructor
    public Application(String browser) {
        this.browser = browser;
    }

    public class MyEventListener extends AbstractWebDriverEventListener{
        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println("!!! Exceptions is: " + throwable);
            File screenShot = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
            File file = new File("d:/" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(screenShot, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUp() {
        wd = getBrowser(browser);
        wait = new WebDriverWait(wd, 10);
        //wd.register(new MyEventListener());
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/en/");
        wait.until(visibilityOfElementLocated(By.xpath("//div[@id = 'region']")));

        //Delegates
        mainPage = new MainPage(wd, wait);
        productPage = new ProductPage(wd, wait);
        basketPage = new BasketPage(wd, wait);
    }

    public void tearDown() {
        wd.quit();
        if (wd != null)
            wd = null;
    }

    //Getters of Delegates
    public MainPage getMainPage() {
        return mainPage;
    }

    public ProductPage getProductPage() {
        return productPage;
    }

    public BasketPage getBasketPage() {
        return basketPage;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
    }

    public WebDriver getBrowser(String browser) {
        switch (browser) {
            case "CHROME":
                wd = new ChromeDriver();
                break;
            case "FIREFOX":
                setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver.exe");
                FirefoxOptions optionsFF = new FirefoxOptions();
                optionsFF.setLegacy(false);
                optionsFF.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
                wd = new EventFiringWebDriver(new FirefoxDriver(optionsFF));
                break;
            default:
                throw new BrowserNotSupportedException("Incorrect browser type input!");
        }
        return wd;
    }
}
