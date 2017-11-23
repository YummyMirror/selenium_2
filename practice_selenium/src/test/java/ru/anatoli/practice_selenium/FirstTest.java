package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        System.setProperty("webdriver.gecko.driver", "F:\\Private\\Programs\\geckodriver\\geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        wd = new FirefoxDriver(firefoxOptions);
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 10);
    }

    @Test
    public void simpleTest() {
        wd.navigate().to("https://www.google.by");
        wd.findElement(By.xpath("//input[@id = 'gs_htif0']")).sendKeys("selenium webdriver");
        wd.findElement(By.xpath("//input[@name = 'btnK']")).click();
        wait.until(ExpectedConditions.titleIs("selenium webdriver - Пошук Google"));
    }

    public void SetDatePicker(WebDriver wd, String xPathSelector, String date) {
        new WebDriverWait(wd, 30).until((ddate) -> wd.findElement(By.xpath(xPathSelector)).isDisplayed());
        ((JavascriptExecutor) wd).executeScript(String.format("$('{0}').datepicker('setDate', '{1}')", xPathSelector, date));
    }

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
