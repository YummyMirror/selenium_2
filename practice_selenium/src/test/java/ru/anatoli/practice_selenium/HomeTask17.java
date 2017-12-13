package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.testng.Assert.assertTrue;

public class HomeTask17 {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
    }

    @Test
    public void test() {
        login("admin", "admin");
        openCatalogPage();
        openCategoryPage();
        for (int i = 0; i < getAllProducts().size(); i++) {
            WebElement duck = getAllProducts().get(i);
            openDuck(duck);
            assertTrue(getBrowserLogs().size() == 0);
            back();
        }

        for (int j = 0; j < getSubFolders().size(); j++) {
            WebElement subFolder = getSubFolders().get(j).findElement(By.xpath("../a"));
            String href = subFolder.getAttribute("href");
            int subFolderId = Integer.parseInt(href.substring(href.length() - 1));
            subFolder.click();
            for (int s = 0; s < getAllSubProducts(subFolderId).size(); s++) {
                WebElement subDucks = getAllSubProducts(subFolderId).get(s);
                openDuck(subDucks);
                assertTrue(getBrowserLogs().size() == 0);
                back();
            }
        }
    }

    public List<WebElement> getAllSubProducts(int subFolderId) {
        return wd.findElements(By.xpath("//table//tr[@class = 'row']/td[3]/a[contains(@href, 'category_id=" + subFolderId + "')]"));
    }

    public List<WebElement> getSubFolders() {
        return wd.findElements(By.xpath("//table//tr[@class = 'row']/td[3]/i[@class = 'fa fa-folder']"));
    }

    public void openDuck(WebElement duck) {
        duck.click();
        wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Edit Product')]")));
    }

    public List<LogEntry> getBrowserLogs() {
        return wd.manage().logs().get(LogType.BROWSER).getAll();
    }

    public List<WebElement> getAllProducts() {
        return wd.findElements(By.xpath("//table//tr[@class = 'row']/td[3]/a[contains(@href, 'product_id')]"));
    }

    public void back() {
        wd.navigate().back();
    }

    public void openCategoryPage() {
        wd.findElement(By.xpath("//a[contains(@href, '?app=catalog&doc=catalog&category_id=1')]")).click();
        wait.until(ExpectedConditions.urlContains("category_id"));
    }

    public void openCatalogPage() {
        click(By.xpath("//a[contains(@href, '?app=catalog&doc=catalog')]"));
        wait.until(visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Catalog')]")));
    }

    public void login(String login, String password) {
        input(By.xpath("//input[@name = 'username']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        check(By.xpath("//input[@name = 'remember_me']"));
        click(By.xpath("//button[@name = 'loginAs']"));
        wait.until(visibilityOfElementLocated(By.xpath("//a[contains(@href, 'logout.php')]")));
    }

    public void check(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        click(locator);
        wd.findElement(locator).clear();
        wd.findElement(locator).sendKeys(value);
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @AfterMethod
    public void after() {
        wd.quit();
        if (wd != null)
            wd = null;
    }
}
