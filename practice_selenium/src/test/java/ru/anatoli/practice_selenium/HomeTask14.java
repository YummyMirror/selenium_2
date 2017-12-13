package ru.anatoli.practice_selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HomeTask14 {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("http://localhost/litecart/public_html/admin/loginAs.php");
    }

    @Test
    public void test() {
//        First version
//        loginAs("admin", "admin");
//        openCountriesPage();
//        openAustriaCountry();
//        List<WebElement> blankLinks = getBlankLinks();
//        for (int i = 0; i < blankLinks.size(); i++) {
//            List<WebElement> links = getBlankLinks();
//            String mainWindow = wd.getWindowHandle();
//            links.get(i).click();
//            Set<String> windowHandlesBefore = wd.getWindowHandles();
//            String newWindow = getNewWindow(mainWindow, windowHandlesBefore);
//            openNewWindow(newWindow);
//            close();
//            openOldWindow(mainWindow);
        login("admin", "admin");
        openCountriesPage();
        openAustriaCountry();
        List<WebElement> blankLinks = getBlankLinks();
        for (int i = 0; i < blankLinks.size(); i++) {
            List<WebElement> links = getBlankLinks();
            String mainWindow = wd.getWindowHandle();
            Set<String> oldWindowHandles = wd.getWindowHandles();
            links.get(i).click();
            String newWindow = wait.until(waitForNewWindow(oldWindowHandles));
            openNewWindow(newWindow);
            close();
            openOldWindow(mainWindow);
        }
    }

    public ExpectedCondition<String> waitForNewWindow(Set<String> oldWindowHandles) {
        return wd -> {
            Set<String> handles = wd.getWindowHandles();
            handles.removeAll(oldWindowHandles);
            if (handles.size() > 0)
                return handles.stream().findAny().get();
            else
                return null;
        };
    }

    public void openOldWindow(String mainWindow) {
        wd.switchTo().window(mainWindow);
    }

    public void close() {
        wd.close();
    }

    public void openNewWindow(String newWindow) {
        wd.switchTo().window(newWindow);
        wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("wikipedia"), ExpectedConditions.urlContains("informatica")));
    }

    public String getNewWindow(String mainWindow, Set<String> windowHandlesBefore){
        Set<String> newWindow = windowHandlesBefore.stream()
                                                   .filter((h) -> !h.equals(mainWindow))
                                                   .collect(Collectors.toSet());
        return newWindow.stream().findAny().get();
    }

    public List<WebElement> getBlankLinks() {
        return wd.findElements(By.xpath("//a[@target = '_blank']/i[@class = 'fa fa-external-link']"));
    }

    public void openAustriaCountry() {
        click(By.xpath("//a[contains(@href, 'edit_country&country_code=AT')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Edit Country')]")));
    }

    public void openCountriesPage() {
        click(By.xpath("//a[contains(@href, '?app=countries&doc=countries')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Countries')]")));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
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
