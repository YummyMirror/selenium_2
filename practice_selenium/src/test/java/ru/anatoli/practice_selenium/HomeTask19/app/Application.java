package ru.anatoli.practice_selenium.HomeTask19.app;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.anatoli.practice_selenium.HomeTask19.pages.BasketPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.MainPage;
import ru.anatoli.practice_selenium.HomeTask19.pages.ProductPage;
import java.io.File;
import java.io.IOException;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Application {
    private EventFiringWebDriver wd;
    private WebDriverWait wait;
    private MainPage mainPage;
    private ProductPage productPage;
    private BasketPage basketPage;

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
        wd = new EventFiringWebDriver(new ChromeDriver());
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
}
