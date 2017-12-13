package ru.anatoli.practice_selenium.OtherTestingNeeds;

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

public class LoginCarSharing {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://useyourcar.exposit.com/loginAs");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'brand-text']")));
    }

    @Test
    public void loginTest() {
        loginAs("test.tester.88@bk.ru", "passtest");
        click(By.xpath("//span[@class = 'edit-text']"));
        click(By.xpath("//span[@class = 'clear-icon']"));
        click(By.xpath("//span[@class = 'calendar-icon']"));
        List<WebElement> daysInMonth = wd.findElements(By.xpath("//table[@class = 'caltable']//td[contains(@class, 'currmonth')]//span"));
        String date = daysInMonth.stream().findAny().get().getText();
    }

    public void loginAs(String login, String password) {
        input(By.xpath("//input[@name = 'loginAs']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        click(By.xpath("//button[contains(text(), ' Войти')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'name']")));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void input(By locator, String value) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd  =null;
    }
}
