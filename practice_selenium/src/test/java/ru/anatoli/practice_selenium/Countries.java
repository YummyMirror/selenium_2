package ru.anatoli.practice_selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.assertTrue;

public class Countries {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/login.php");
    }

    @Test(enabled = true)
    public void test() {
        login("admin", "admin");
        click(By.xpath("//a[contains(@href, '?app=countries&doc=countries')]"));
        List<WebElement> countries = getAllCountries();
        for (int i = 0; i < countries.size(); i++) {
            if (i != countries.size() - 1) {
                String firstCountry = countries.get(i).findElement(By.xpath("./td[not(contains(@style, 'text'))]/a")).getAttribute("textContent");
                String secondCountry = countries.get(i + 1).findElement(By.xpath("./td[not(contains(@style, 'text'))]/a")).getAttribute("textContent");
                assertTrue(firstCountry.compareTo(secondCountry) < 0);
            }
        }
        List<WebElement> countriesWithZones = countries.stream()
                                                       .filter((country) -> getCounriesWithZones(country, By.xpath("./td[6]")) > 0)
                                                       .collect(Collectors.toList());
        for (int j = 0; j < countriesWithZones.size(); j++) {
            List<WebElement> cWithZone = getAllCountries().stream()
                                                          .filter((country) -> getCounriesWithZones(country, By.xpath("./td[6]")) > 0)
                                                          .collect(Collectors.toList());
            WebElement country = cWithZone.get(j).findElement(By.xpath("./td[contains(@style, 'text')]/a"));
            ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", country);
            country.click();
            List<WebElement> zones = getAllZones();
            zones.remove(zones.size() - 1);
            for (int z = 0; z < zones.size(); z++) {
                if (z != zones.size() - 1) {
                    String firstZone = zones.get(z).findElement(By.xpath("./td[3]/input")).getAttribute("value");
                    String secondZone = zones.get(z + 1).findElement(By.xpath("./td[3]/input")).getAttribute("value");
                    assertTrue(firstZone.compareTo(secondZone) < 0);
                }
            }
            back();
        }
    }

    @Test(enabled = true)
    public void test2() {
        login("admin", "admin");
        click(By.xpath("//a[contains(@href, '?app=geo_zones&doc=geo_zones')]"));
        List<WebElement> coutries = getAllCountries();
        for (int i = 0; i < coutries.size(); i++) {
            List<WebElement> allCountries = getAllCountries();
            allCountries.get(i).findElement(By.xpath("./td[not(contains(@style, 'text'))]/a")).click();
            List<WebElement> zones = wd.findElements(By.xpath("//table[@class = 'dataTable']//select[contains(@name, '[zone_code]')]/option[@selected = 'selected']"));
            for (int j = 0; j < zones.size(); j++) {
                if (j != zones.size() - 1) {
                    String firstZone = zones.get(j).getText();
                    String secondZone = zones.get(j + 1).getText();
                    assertTrue(firstZone.compareTo(secondZone) < 0);
                }
            }
            back();
        }
    }

    @Test
    public void test3() {
        login("admin", "admin");
        click(By.xpath("//a[contains(@href, '?app=countries&doc=countries')]"));
        WebElement usa = wd.findElement(By.xpath("//a[contains(@href, 'code=US')]"));
        WebElement enable = wd.findElement(By.xpath("//button[@name = 'enable']"));
        WebElement h1 = wd.findElement(By.xpath("//h1"));
        while (!isElementClickable(By.xpath("//a[contains(@href, 'code=US')]"))) {
            ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", enable);
            ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", h1);
        }
        usa.click();
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
        submitForm(By.xpath("//button[@name = 'login']"));
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

    public boolean isElementClickable(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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
