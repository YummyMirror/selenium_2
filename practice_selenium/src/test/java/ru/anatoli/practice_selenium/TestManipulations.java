package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class TestManipulations {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
    }

    @Test(enabled = true)
    public void test() {
        login("admin", "admin");
        click(By.xpath("//a[contains(@href, '?app=countries&doc=countries')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href = 'http://localhost/litecart/public_html/admin/?app=countries&doc=edit_country&country_code=AF']"))).click();
        WebElement textField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name = 'name']")));
        textField.click();
        textField.sendKeys(Keys.ARROW_LEFT);
        textField.sendKeys(Keys.ARROW_LEFT);
        textField.sendKeys(Keys.ARROW_LEFT + "1234567");
        textField.sendKeys(Keys.BACK_SPACE);
        textField.sendKeys(Keys.BACK_SPACE);
        textField.sendKeys(Keys.BACK_SPACE);
        textField.sendKeys(Keys.ENTER);
    }

    public int getCounriesWithZones(WebElement parent, By locator) {
        return Integer.parseInt(parent.findElement(locator).getAttribute("textContent"));
    }

    public List<WebElement> getAllZones() {
        return wd.findElements(By.xpath("//table[@class = 'dataTable']//tr[not(contains(@class, 'header'))]"));
    }

    public List<WebElement> getAllCountries() {
        return wd.findElements(By.xpath("//table[@class = 'dataTable']//tr[@class = 'row']"));
    }

    public void back() {
        wd.navigate().back();
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        checkBox(By.xpath("//input[@name = 'remember_me']"));
        submitForm(By.xpath("//button[@name = 'loginAs']"));
    }

    public void submitForm(By locator) {
        wd.findElement(locator).click();
    }

    public void checkBox(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
