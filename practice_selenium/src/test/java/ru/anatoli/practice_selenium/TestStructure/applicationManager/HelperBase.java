package ru.anatoli.practice_selenium.TestStructure.applicationManager;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HelperBase {
    protected WebDriver wd;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;
    public static final Logger LOGGER = Logger.getLogger(HelperBase.class);

    //Constructor
    public HelperBase(WebDriver wd, WebDriverWait wait, Actions actions) {
        this.wd = wd;
        this.wait = wait;
        this.actions = actions;
        this.js = (JavascriptExecutor) wd;
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void check(By locator) {
        wd.findElement(locator).click();
    }

    public void input(By locator, String value) {
        if (value != null) {
            click(locator);
            wd.findElement(locator).clear();
            wd.findElement(locator).sendKeys(value);
        }
    }

    public void waitForVisibilityOfElement(By locator) {
        wait.until(visibilityOfElementLocated(locator));
    }

    public List<LogEntry> getLogs() {
        return wd.manage().logs().get(LogType.BROWSER).getAll();
    }

    public Set<String> getWindowHandles() {
        return wd.getWindowHandles();
    }

    public String getCurrentWindowHandle() {
        return wd.getWindowHandle();
    }

    public void openDBManager() {
        click(By.xpath("//a[contains(@href, 'action=edit&key=database_admin_link')]"));
    }

    public ExpectedCondition<String> waitForNewWindowHandle(Set<String> oldWindowHandles) {
        return wd -> {
            Set<String> handles = wd.getWindowHandles();
            handles.removeAll(oldWindowHandles);
            return handles.size() > 0 ? handles.stream().findAny().get() : null;
        };
    }

    public String getNewWindowHandle(Set<String> oldWindowHandles) {
        return wait.until(waitForNewWindowHandle(oldWindowHandles));
    }

    public void switchToWindow(String handle) {
        wd.switchTo().window(handle);
    }

    public void closeCurrentWindow() {
        wd.close();
    }

    public void openUrl(String url) {
        wd.navigate().to(url);
        wait.until(urlContains(url));
    }
}
