package ru.anatoli.practice_selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;
import java.util.stream.Collectors;

public class WindowHandles {
    WebDriver wd;
    WebDriverWait wait;

    @BeforeMethod
    public void before() {
        wd = new ChromeDriver();
        wait = new WebDriverWait(wd, 10);
        wd.manage().window().maximize();
        wd.navigate().to("https://google.by");
    }

    @Test
    public void test() {
        String google = wd.getWindowHandle();
        String mainWindow = wd.getWindowHandle();
        Set<String> windowHandlesBefore = wd.getWindowHandles();
        ((JavascriptExecutor) wd).executeScript("window.open();");
        String newWindow = wait.until(waitForNewWindow(windowHandlesBefore));
        wd.switchTo().window(newWindow);
        wd.navigate().to("https://yandex.by");
        wd.close();
        wd.switchTo().window(mainWindow);
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

    @AfterMethod
    public void after() {
        wd.quit();
        wd = null;
    }
}
