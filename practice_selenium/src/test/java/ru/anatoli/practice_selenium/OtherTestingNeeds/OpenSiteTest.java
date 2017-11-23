package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class OpenSiteTest {
    private WebDriver wd;
    private WebDriverWait wait;
    private Actions actions;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        actions = new Actions(wd);
        wd.manage().window().maximize();
        wd.get(OpenSiteDomain.GOOGLE);
    }

    @Test(enabled = true, priority = 1)
    public void test() {
        input(By.xpath("//input[@id = 'lst-ib']"), "something for search");
    }

    public void input(By locator, String value) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(OpenSiteDomain.getQuery(value));
        wd.findElement(locator).sendKeys(Keys.ENTER);
    }

    public void click(By locator) {
        wait.until(elementToBeClickable(locator)).click();
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
