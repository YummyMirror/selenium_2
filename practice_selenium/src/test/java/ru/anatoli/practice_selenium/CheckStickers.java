package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class CheckStickers {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public  void before() {
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setLegacy(false);
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(options);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("http://localhost/litecart/public_html/en/");
    }

    @Test
    public void checkStickers() {
        List<WebElement> ducks = getAllDucks();

        for (WebElement duck : ducks) {
            assertTrue(duck.findElements(By.xpath("./div[starts-with(@class, 'sticker')]")).size() == 1);
        }
    }

    public List<WebElement> getAllDucks() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class = 'image-wrapper']")));
    }

    @AfterMethod
    public void after() {
        wd.quit();;
        wd = null;
    }
}
