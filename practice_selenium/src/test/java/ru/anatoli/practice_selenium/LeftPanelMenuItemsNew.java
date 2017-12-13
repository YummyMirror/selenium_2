package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class LeftPanelMenuItemsNew {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public  void before() {
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
    }

    @Test
    public void test() {
        //Login
        login("admin", "admin");
        assertTrue(getText(By.xpath("//div[@class = 'notice success']")).equals("You are now logged in as admin"));

        List<WebElement> sections = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@id = 'app-']/a")));
        for (int i = 0; i < sections.size(); i++) {
            wd.findElements(By.xpath("//li[@id = 'app-']/a")).get(i).click();
            areElementsPresent(By.xpath("//td[@id = 'content']/h1"));

            List<WebElement> subSections = wd.findElements(By.xpath("//li[@id = 'app-' and @class = 'selected']//li/a"));
            for (int j = 0; j < subSections.size(); j++) {
                wd.findElements(By.xpath("//li[@id = 'app-' and @class = 'selected']//li/a")).get(j).click();
                areElementsPresent(By.xpath("//td[@id = 'content']/h1"));
            }
        }
    }

    public void login(String username, String password) {
        input(By.xpath("//input[@name = 'username']"), username);
        input(By.xpath("//input[@name = 'password']"), password);
        click(By.xpath("//input[@name = 'remember_me']"));
        click(By.xpath("//button[@name = 'loginAs']"));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void input(By locator, String value) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public boolean areElementsPresent(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)).size() > 0;
    }

    @AfterMethod
    public void after() {
        wd.quit();;
        wd = null;
    }
}
