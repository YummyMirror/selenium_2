package ru.anatoli.practice_selenium.OtherTestingNeeds;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class UYCLoginWithoutCookies {
    private WebDriver wd;
    private WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
    }

    @Test
    public void test() {
        wd.navigate().to("http://useyourcar.exposit.com/");
        if (!areElementsPresent(By.xpath("//div[@class = 'label-big']")))
            loginAs("test.tester.88@bk.ru", "passtest");
        if (!areElementsPresent(By.xpath("//div[@class = 'label-big']"))) {
            click(By.xpath("//button[@class = 'btn-profile']"));
            click(By.xpath("//a[contains(text(), 'Мой профиль')]"));
        }
        List<WebElement> allMenuItems = getAllMenuItems().stream().limit(5).collect(Collectors.toList());
        List<WebElement> selectedMenuItems = getSelectedItems();
        allMenuItems.removeAll(selectedMenuItems);
        for (int i = 0; i < allMenuItems.size(); i++) {
            List<WebElement> all = getAllMenuItems().stream().limit(5).collect(Collectors.toList());
            List<WebElement> selected = getSelectedItems();
            all.removeAll(selected);
            all.get(i).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'title']")));
        }
    }

    @Test
    public void visibilityTest() {
        wd.navigate().to("http://useyourcar.exposit.com/");
        if (!areElementsPresent(By.xpath("//div[@class = 'label-big']")))
            loginAs("test.tester.88@bk.ru", "passtest");
        if (!areElementsPresent(By.xpath("//div[@class = 'label-big']"))) {
            click(By.xpath("//button[@class = 'btn-profile']"));
            click(By.xpath("//a[contains(text(), 'Мой профиль')]"));
        }
        List<WebElement> uploads = wd.findElements(By.xpath("//input[@id = 'fileUpload']"));
        WebElement fileUpload = uploads.get(0);
        ((JavascriptExecutor) wd).executeScript("arguments[0].style.visibility = 'visible';", fileUpload);
        fileUpload.sendKeys(new File("src/test/resources/1.jpg").getAbsolutePath());

        WebElement bindCart = wd.findElement(By.xpath("//input[@value = 'Привязать карту']"));
        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView(true);", bindCart);
        bindCart.click();
    }

    public List<WebElement> getSelectedItems() {
        return wd.findElements(By.xpath("//div[@class = 'content']//li/a[contains(@class, 'active')]"));
    }

    public List<WebElement> getAllMenuItems() {
        return wd.findElements(By.xpath("//div[@class = 'content']//li/a"));
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    public void loginAs(String login, String password) {
        openLoginForm();
        input(By.xpath("//input[@name = 'loginAs']"), login);
        input(By.xpath("//input[@name = 'password']"), password);
        submitLoginForm();
    }

    public void submitLoginForm() {
        click(By.xpath("//button[contains(text(), 'Войти')]"));
    }

    public void input(By locator, String value) {
        if (value != null) {
            String currentValue = wd.findElement(locator).getAttribute("value");
            if (!value.equals(currentValue)) {
                wd.findElement(locator).click();
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(value);
            }
        }
    }

    public void openLoginForm() {
        click(By.xpath("//button[contains(text(), 'Войти')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Войти')]")));
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
